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
import java.util.Properties;
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
    private Sql_B sql_b = new Sql_B(false, true); // obs this one is for building table
    private GG gg;
    private XyGraph xygraph = new XyGraph("mooney", MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
    private ShowMessage OUT;
    private Gui gui;
    private ResultSet resultSet;
    private Properties p;
    private int MARKERS_SET_P_INDEX_FIRST = -1;
    private int MARKERS_SET_P_INDEX_LAST = -1;
    private String ORDER_BY_PARAM = SQL_Q.TEST_DATE;
    private String ORDER_ASC_DESC = "ASC";

    public Controller(ShowMessage OUT, Properties p) {
        //
        this.OUT = OUT;
        this.gui = (Gui) OUT;
        this.p = p;
        //
        Gui.GraphPanel.add(xygraph.getGraph());
        //
        defineInitialGistoGraph();
        //
        connect();
        //
        tableHeaders();
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

    private void tableHeaders() {
        //
        ResultSet rs;
        //
        try {
            rs = sql.execute("SELECT * from " + SQL_Q.PRIM_TABLE + " WHERE [Quality]='xxxxxx-xxxx'");
            HelpA.build_table_common_return(rs, gui.jTableMain);
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        resetGraphs();
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
        resetGraphs();
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
        } else if (trigerInstance instanceof MyGraphXY) {
            //
            MARKERS_SET_P_INDEX_FIRST = markerA.POINT_INDEX + 1;
            MARKERS_SET_P_INDEX_LAST = markerB.POINT_INDEX + 1;
            //
            //
            Thread x = new Thread(new BuildTableThread(null));
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

    public void addDiffMarkerPoints() {
        xygraph.addDiffMarkerPoints();
    }

    public void removeDiffMarkerPoints() {
        xygraph.removeDiffMarkerPoints();
        xygraph.getSerie().resetPointsColorAndForm();
    }

    private void connect() {
        try {
            sql.connect_mdb("", "", "c:/test/data.mdb");
            sql_b.connect_mdb("", "", "c:/test/data.mdb");
            //
//            sql.connect_odbc("", "", "MC_LAB");
//            sql.connect_jdbc("10.87.0.2", "1433", "MCLAB_COMPOUND", "sa", "");
            //
            //
            //
            //
//            sql.connect_jdbc(p.getProperty("sql_host"), p.getProperty("sql_port"),
//                    p.getProperty("sql_db_name"), p.getProperty("sql_user"), p.getProperty("sql_pass"));
//            
//            sql_b.connect_jdbc(p.getProperty("sql_host"), p.getProperty("sql_port"),
//                    p.getProperty("sql_db_name"), p.getProperty("sql_user"), p.getProperty("sql_pass"));
            //
            OUT.showMessage("Connected");
            //
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void resetGraphs() {
        try {
            xygraph.removeDiffMarkerPoints();
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

    public void buildGraphs() {
        //
//        if (gui.obligatoryBoxesFilled() == false) {
//            return;
//        }
        //
        String q = SQL_Q.showResult(gui, ORDER_BY_PARAM, ORDER_ASC_DESC, null);
//        String q = SQL_Q.forTestB();
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
            rs.beforeFirst();
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
        String q = SQL_Q.showResult(gui, ORDER_BY_PARAM, ORDER_ASC_DESC, addditionalWhere);
        //
        if (q == null) {
            return;
        }
        //
        try {
            ResultSet rs = sql_b.execute(q, OUT);
            //
            if (MARKERS_SET_P_INDEX_FIRST != -1 && MARKERS_SET_P_INDEX_LAST != -1) {
                HelpA.build_table_common(rs, gui.jTableMain, q, MARKERS_SET_P_INDEX_FIRST, MARKERS_SET_P_INDEX_LAST);
                MARKERS_SET_P_INDEX_FIRST = -1;
                MARKERS_SET_P_INDEX_LAST = -1;
            } else {
                HelpA.build_table_common(rs, gui.jTableMain, q);
            }
            //
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
