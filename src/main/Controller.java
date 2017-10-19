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
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import other.HelpA;
import other.JComboBoxA;
import sql.SQL_Q;
import sql.ShowMessage;
import sql.Sql_B;

/**
 *
 * @author KOCMOC
 */
public class Controller implements DiffMarkerAction, BarGraphListener, PointGraphListener, MouseListener, KeyListener {

    private Sql_B sql = new Sql_B(false, true);
    private Sql_B sql_b = new Sql_B(false, true); // obs this one is for building table
    private BasicGraphListener gg;
    private XyGraph_M xygraph = new XyGraph_M("mooney", MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
    private ShowMessage OUT;
    private Main gui;
    private ResultSet resultSet;
    private Properties p;
    private int MARKERS_SET_P_INDEX_FIRST = -1;
    private int MARKERS_SET_P_INDEX_LAST = -1;
    private String ORDER_BY_PARAM = SQL_Q.TEST_DATE;
    private String ORDER_ASC_DESC = "ASC";
    private boolean MARKERS_SET = false;

    public Controller(ShowMessage OUT, Properties p) {
        //
        this.OUT = OUT;
        this.gui = (Main) OUT;
        this.p = p;
        //
        Main.GraphPanel.add(xygraph.getGraph());
        //
        defineInitialGraph();
        //
        connect();
        //
        tableHeaders();
        //
        initOther();
    }

    private void initOther() {
        this.gui.jTableMain.addMouseListener(this);
        this.gui.jTableMain.addKeyListener(this);
    }

    private void defineInitialGraph() {
        //===
        MyGraphXY_PG mgxyh = new MyGraphXY_PG();
        mgxyh.addPointGraphListener(this);
        //
        gg = new PolygonGraph("Polygon Graph", mgxyh, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //
        //====
        xygraph.setGistoGraph(gg);
        xygraph.addDiffMarkersSetListener(this);
        Main.HistoPanel.add(gg.getGraph());
        //
        if (gg instanceof HistogramGraph == false) {
            gg.addDiffMarkersSetListener(this);// BasicGraphListener triggers event which is processed in this class
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

    public void switchToHistogramBarGraph() {
        //
        MyGraphXY_HG mgxyhm = new MyGraphXY_HG();
        mgxyhm.addBarGraphListener(this);//        mgxyhm triggers event which is processed in this class
        gg = new HistogramGraph("Histogram", mgxyhm, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //
        xygraph.setGistoGraph(gg);
        //
        Main.HistoPanel.removeAll();
        Main.HistoPanel.add(gg.getGraph());
        //
        // OBS, pay attention here, if i do reval... and repaint of Histopanel it won't work
        // This was an enormous problem
        gui.revalidate();
        gui.repaint();
        //
        resetGraphs();
    }

    public void switchToFrequencyPolygonGraph() {
        //
        MyGraphXY_PG mgxyh = new MyGraphXY_PG();
        mgxyh.addPointGraphListener(this);
        //
        gg = new PolygonGraph("Polygon Frequency Graph", mgxyh, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //
        xygraph.setGistoGraph(gg);
        //
        Main.HistoPanel.removeAll();
        Main.HistoPanel.add(gg.getGraph());
        //
        //
        if (gg instanceof HistogramGraph == false) {
            gg.addDiffMarkersSetListener(this);// BasicGraphListener triggers event which is processed in this class
        }
        //
        gui.revalidate();
        gui.repaint();
        //
        resetGraphs();
    }

    public void deleteFromBarGraph() {
        HistogramGraph ggm = (HistogramGraph) gg;
        ggm.myGraphXY.deleteAllPointsFromSerie(ggm.serie);
    }

    @Override
    public void pointGraphHoverEvent(MouseEvent e, MyPoint point) {
        if (MARKERS_SET == false) {
            highLightPointsByValue(point.x_Display, point.x_Display, false);
        }
    }

    @Override
    public void pointGraphHoverOutEvent(MouseEvent e) {
        if (MARKERS_SET == false) {
            xygraph.getSerie().resetPointsColorAndForm();
        }

    }

    @Override
    public void barGraphHoverOutEvent(MouseEvent e) {
        if (MARKERS_SET == false) {
            xygraph.getSerie().resetPointsColorAndForm();
        }
    }

    @Override
    public void barGraphHoverEvent(MouseEvent e, MyPoint_HG point) {
        if (e.getSource() instanceof MyPoint_HG) {
            if (MARKERS_SET == false) {
                highLightPointsByValue(point.getRangeStart(), point.getRangeEnd(), true);
            }
        }
    }

    @Override
    public void markersSet(MyGraphXY trigerInstance, MyPoint markerA, MyPoint markerB) {
        //
        MARKERS_SET = true;
        //
        if (trigerInstance instanceof MyGraphXY_PG) {
            //
            double min = markerA.x_Display;
            double max = markerB.x_Display;
            //
            highLightPointsByValue(min, max, false);
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
        //
        MARKERS_SET = false;
        //
        xygraph.getSerie().resetPointsColorAndForm();
    }

    private void highLightPointByIndex(int index) {
        //
        MySerie serie = xygraph.getSerie();
        //
        serie.resetPointsColorAndForm();
        //
        for (MyPoint point : serie.getPoints()) {
            if (point.getPointIndex() == index) {
                point.setPointColor(Color.MAGENTA);
                point.setPointDrawRect(true);
                point.POINT_D = (int)(point.POINT_D * 1.5);
            }
        }
        //
        xygraph.getGraph().revalidate();
        xygraph.getGraph().repaint();
    }

    private void highLightPointsByValue(double min, double max, boolean barGraph) {
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
        xygraph.getGraph().revalidate();
        xygraph.getGraph().repaint();
//        xygraph.getGraph().updateUI();
    }

    public void addDiffMarkerPoints() {
        xygraph.addDiffMarkerPoints();
    }

    public void removeDiffMarkerPoints() {
        xygraph.removeDiffMarkerPoints();
        xygraph.getSerie().resetPointsColorAndForm();
        MARKERS_SET = false;
    }

    private void connect() {
        try {
            sql.connect_mdb("", "", "c:/test/data.mdb");
            sql_b.connect_mdb("", "", "c:/test/data.mdb");
            //
//            sql.connect_mdb("", "", "data.mdb");
//            sql_b.connect_mdb("", "", "data.mdb");
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

    public synchronized void resetGraphs() {
        try {
            xygraph.removeDiffMarkerPoints();
            //
            xygraph.getSerie().resetPointsColorAndForm();
            //
            resultSet.first();
            //
            gg.addData(resultSet, "value");
            //
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buildGraphs() {
        //
        if (gui.obligatoryBoxesFilled() == false) {
            return;
        }
        //
        String q = SQL_Q.showResult(gui, ORDER_BY_PARAM, ORDER_ASC_DESC, null);
//        String q = SQL_Q.forTestC();
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
            gg.addData(rs, "value");
            //
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buildTableByThread(String addditionalWhere) {
        Thread x = new Thread(new BuildTableThread(addditionalWhere));
        x.start();
    }

    private synchronized void buildTable(String addditionalWhere) {
        //
        String q = SQL_Q.showResult(gui, ORDER_BY_PARAM, ORDER_ASC_DESC, addditionalWhere);
        //
        if (q == null) {
            return;
        }
        //
        try {
            //
            ResultSet rs = sql_b.execute(q, OUT);
            //
            rs.beforeFirst();
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

    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getSource() == gui.jTableMain) {
            showCurrTableEntryOnGraph();
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == gui.jTableMain) {
            showCurrTableEntryOnGraph();
        }
    }

    private void showCurrTableEntryOnGraph() {
        int row = gui.jTableMain.getSelectedRow();
        highLightPointByIndex(row);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
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
            jComboBox.setBackground(Main.INITIAL_BG_COLOR_COMBO);
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

    public void fillComboStandard(JComboBoxA jcbm) {
        //
        String q = SQL_Q.fillAuto(jcbm.getPARAMETER(), gui);
        //
        OUT.showMessage(q);
        //
        jcbm.setFLAG_WAIT(HelpA.fillComboBox_with_wait(jcbm, jcbm.getFLAG_WAIT(), q, sql));
        //
        resetFlagsWaitSelective(jcbm);
    }

    public void resetFlagsWaitSelective(JComboBoxA jcbm) {
        ArrayList<JComboBox> list = gui.getJCOMBO_LIST();
        //
        for (JComboBox jComboBox : list) {
            //
            JComboBoxA boxM = (JComboBoxA) jComboBox;
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
            JComboBoxA boxM = (JComboBoxA) jComboBox;
            //
            boxM.setFLAG_WAIT(0);
        }
    }
    //==========================================================================
}
