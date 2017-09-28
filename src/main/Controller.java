/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.DiffMarkerAction;
import XYG_BASIC.MyGraphContainer;
import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MyPoint;
import XYG_BASIC.MySerie;
import XYG_HISTO.MyGraphXY_H;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import other.HelpA;
import other.JComboBoxM;
import sql.SQL_Q;
import sql.ShowMessage;
import sql.Sql_B;

/**
 *
 * @author KOCMOC
 */
public class Controller implements DiffMarkerAction, BarGraphListener {

    private Sql_B sql = new Sql_B(false, true);
    private GG gg;
    private XyGraph xygraph = new XyGraph("mooney", MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
    private ShowMessage OUT;
    private Gui gui;

    public Controller(ShowMessage OUT) {
        //
        this.OUT = OUT;
        this.gui = (Gui) OUT;
        //
        Gui.GraphPanel.add(xygraph.getGraph());
        //
        defineInitialGistoGraph();
        //
        connect();
        //
    }

    private void defineInitialGistoGraph() {
        //===
        gg = new GistoGraph("Histogram", new MyGraphXY_H(), MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //====
        xygraph.setGistoGraph(gg);
        xygraph.addDiffMarkersSetListener(this);
        Gui.HistoPanel.add(gg.getGraph());
        //
        if (gg instanceof GistoGraphM == false) {
            gg.addDiffMarkersSetListener(this);// GG triggers event which is processed in this class
        }
        //
    }

    public void switchToBarGraph() {
        MyGraphXY_H_M mgxyhm = new MyGraphXY_H_M();
        mgxyhm.addBarGraphListener(this);//        mgxyhm triggers event which is processed in this class
        gg = new GistoGraphM("BarDiagram", mgxyhm, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //
        xygraph.setGistoGraph(gg);
        //
        Gui.HistoPanel.removeAll();
        Gui.HistoPanel.add(gg.getGraph());
        //
        // OBS, pay attention here, if i do reval... and repaint of Histopanel it won't work
        // This was an enormous problem
        gui.revalidate();
        gui.repaint();
        //
        rebuildGraph();
    }

    public void switchToPlotGraph() {
        //
        gg = new GistoGraph("PlotDiagram", new MyGraphXY_H(), MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        xygraph.setGistoGraph(gg);
        //
        Gui.HistoPanel.removeAll();
        Gui.HistoPanel.add(gg.getGraph());
        //
        //
        if (gg instanceof GistoGraphM == false) {
            gg.addDiffMarkersSetListener(this);// GG triggers event which is processed in this class
        }
        //
        gui.revalidate();
        gui.repaint();
        //
        rebuildGraph();
    }

    public void deleteFromBarGraph() {
        GistoGraphM ggm = (GistoGraphM) gg;
        ggm.myGraphXY.deleteAllPointsFromSerie(ggm.serie);
    }

    @Override
    public void barGraphHoverOutEvent(MouseEvent e) {
        xygraph.getSerie().resetPointsColorAndForm();
    }

    @Override
    public void barGraphHoverEvent(MouseEvent e, MyPoint_H_M point) {
        if (e.getSource() instanceof MyPoint_H_M) {
            highLightPoints(point.getRangeStart(), point.getRangeEnd(), true);
        }
    }

    @Override
    public void markersSet(MyGraphXY trigerInstance, MyPoint markerA, MyPoint markerB) {
        System.out.println("Markers set: " + trigerInstance);
        if (trigerInstance instanceof MyGraphXY_H) {
            //
            double min = markerA.x_Display;
            double max = markerB.x_Display;
            //
            highLightPoints(min, max, false);
            //
            //
            String where = SQL_Q.buildAdditionalWhereGistoGram("" + min, "" + max);
            //
            Thread x = new Thread(new BuildTableThread(where));
            x.start();
        }else if(trigerInstance instanceof MyGraphXY){
             //
            double min = markerA.y_Display;
            double max = markerB.y_Display;
            //
//            highLightPoints(min, max, false);
            //
            //
            String where = SQL_Q.buildAdditionalWhereGistoGram("" + min, "" + max);
            //
            Thread x = new Thread(new BuildTableThread(where));
            x.start();
        }
    }
    
    

    @Override
    public void markersUnset(MyGraphXY mgxy) {
        xygraph.getSerie().resetPointsColorAndForm();
    }

    private void highLightPoints(double min, double max, boolean barGraph) {
        //
        MySerie serie = xygraph.getSerie();
        //
        serie.resetPointsColorAndForm();
        //
        for (MyPoint point : serie.getPoints()) {
            if (point.y_Display >= min && point.y_Display <= max && barGraph == false) {
                point.setPointColor(Color.MAGENTA);
                point.setPointDrawRect(true);
            }
            //
            if (point.y_Display >= min && point.y_Display < max && barGraph) {
                point.setPointColor(Color.MAGENTA);
                point.setPointDrawRect(true);
            }
        }
        //
        xygraph.getGraph().repaint();
        xygraph.getGraph().updateUI();
    }

    private void connect() {
        try {
            sql.connect_mdb("", "", "c:/test/data.mdb");
//            sql.connect_odbc("", "", "MC_LAB");
//            sql.connect_jdbc("10.87.0.2", "1433", "MCLAB_COMPOUND", "sa", "");
            OUT.showMessage("Connected");
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rebuildGraph() {
        try {
            //
//            resultSet.first();
//            //
//            xygraph.deleteAllPointsFromAllSeries();
//            xygraph.addData(resultSet, "value");
            //
            xygraph.getSerie().resetPointsColorAndForm();
            //
            resultSet.first();
            //
            gg.addData(resultSet, "value", "#.##");
            //
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    private ResultSet resultSet;

    public void buildGraph() {
        //
//        String q = SQL_Q.showResult(gui, null, null, null);
        String q = SQL_Q.forTest();
        //
        if (q == null) {
            return;
        }
        //
        try {
            //
            ResultSet rs = sql.execute(q, OUT);
            this.resultSet = rs;
            xygraph.deleteAllPointsFromAllSeries();
            xygraph.addData(rs, "value");
            //
            rs.first();
            //
//            gg.addLimits(rs);
            //
            gg.addData(rs, "value", "#.##");
            //
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buildTable(String addditionalWhere) {
        //
        String q = SQL_Q.showResult(gui, SQL_Q.ORDER, "ASC", addditionalWhere);
        //
        if (q == null) {
            return;
        }
        //
        try {
            ResultSet rs = sql.execute(q, OUT);
            //
            HelpA.build_table_common(rs, gui.jTableMain, q);
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    class BuildTableThread implements Runnable {

        private final String additionalWhere;

        public BuildTableThread(String additionalWhere) {
            this.additionalWhere = additionalWhere;
        }

        @Override
        public void run() {
            buildTable(additionalWhere);
        }
    }

    //==========================================================================
    //==========================================================================
    public void clearComponents() {
        //
        ArrayList<JComboBox> list = gui.getJCOMBO_LIST();
        //
        for (JComboBox jComboBox : list) {
            jComboBox.setSelectedItem(null);
            jComboBox.setEditable(false);
            jComboBox.setBackground(Gui.INITIAL_BG_COLOR_COMBO);
        }
        //
        try {
            gui.datePicker1.setDate(null);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        gui.repaint();
    }
    //==========================================================================
    //==========================================================================

    public void fillComboStandard(JComboBoxM jcbm) {
        //
        String q = SQL_Q.fillAuto(jcbm.getPARAMETER(), gui);
        //
        OUT.showMessage(q);
        //
        jcbm.setFLAG_WAIT(HelpA.fillComboBox_with_wait(jcbm, jcbm.getFLAG_WAIT(), q, sql));
        //
        resetFlagsWaitSelective(jcbm);
    }

    public void resetFlagsWaitSelective(JComboBoxM jcbm) {
        ArrayList<JComboBox> list = gui.getJCOMBO_LIST();
        //
        for (JComboBox jComboBox : list) {
            //
            JComboBoxM boxM = (JComboBoxM) jComboBox;
            //
            if (boxM.getPARAMETER().equals(jcbm.getPARAMETER()) == false) {
                boxM.setFLAG_WAIT(0);
            }
            //
        }
    }

    public void resetFlagWaits() {
        ArrayList<JComboBox> list = gui.getJCOMBO_LIST();
        //
        for (JComboBox jComboBox : list) {
            //
            JComboBoxM boxM = (JComboBoxM) jComboBox;
            //
            boxM.setFLAG_WAIT(0);
        }
    }
    //==========================================================================
}
