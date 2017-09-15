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
import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import other.HelpA;
import other.JComboBoxM;
import sql.SQL_Q;
import static sql.SQL_Q.TEST_DATE;
import static sql.SQL_Q.quotes;
import sql.ShowMessage;
import sql.Sql_B;

/**
 *
 * @author KOCMOC
 */
public class Controller implements DiffMarkerAction {
    
    private Sql_B sql = new Sql_B(false, true);
    private GistoGraph histogram = new GistoGraph("Histogram", new MyGraphXY_H(), MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
    private XyGraph xygraph = new XyGraph("mooney", MyGraphContainer.DISPLAY_MODE_FULL_SCREEN, histogram);
    private ShowMessage OUT;
    private Gui gui;
    
    public Controller(ShowMessage OUT) {
        //
        this.OUT = OUT;
        this.gui = (Gui) OUT;
        //
        Gui.GraphPanel.add(xygraph.getGraph());
        Gui.HistoPanel.add(histogram.getGraph());
        //
        histogram.addDiffMarkersSetListener(this);
        //
        connect();
        //
    }
    
    @Override
    public void markersSet(MyGraphXY trigerInstance, MyPoint markerA, MyPoint markerB) {
        if (trigerInstance instanceof MyGraphXY_H) {
            //
            double min = markerA.x_Display;
            double max = markerB.x_Display;
            //
            highLightPoints(min, max);
            //
            //
            String where = SQL_Q.buildAdditionalWhereGistoGram("" + min, "" + max);
            //
            Thread x = new Thread(new BuildTableThread(where));
            x.start();
        }
    }
    
    private void highLightPoints(double min, double max) {
        //
        MySerie serie = xygraph.getSerie();
        //
        serie.resetPointsColorAndForm();
        //
        for (MyPoint point : serie.getPoints()) {
            if (point.y_Display >= min && point.y_Display <= max) {
                point.setPointColor(Color.MAGENTA);
                point.setPointDrawRect(true);
            }
            //
        }
        //
        xygraph.getGraph().repaint();
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
    
    public void buildGraph() {
        //
        String q = SQL_Q.showResult(gui, null, null, null);
        //
        if (q == null) {
            return;
        }
        //
        try {
            //
            ResultSet rs = sql.execute(q, OUT);
            xygraph.deleteAllPointsFromAllSeries();
            xygraph.addData(rs, "value");
            //
            rs.first();
            //
//            histogram.addLimits(rs);
            histogram.addData(rs, "value", "#.##");
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
