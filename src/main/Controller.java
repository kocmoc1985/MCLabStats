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

    private Sql_B sql_common_g = new Sql_B(false, Main.LOG_CONNECTION_STRING);
    private Sql_B sql_histogram_g = new Sql_B(false, Main.LOG_CONNECTION_STRING);
    private Sql_B sql_polygon_g = new Sql_B(false, Main.LOG_CONNECTION_STRING);
    private Sql_B sql_table = new Sql_B(false, Main.LOG_CONNECTION_STRING); // obs this one is for building table
    private Sql_B[] SQL_ARR = {sql_common_g, sql_histogram_g, sql_polygon_g, sql_table};
    private BasicGraphListener gg;
    private XyGraph_M xygraph = new XyGraph_M("mooney", MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
    private ShowMessage OUT;
    private Main gui;
    private Properties p;
    private int MARKERS_SET_P_INDEX_FIRST = -1;
    private int MARKERS_SET_P_INDEX_LAST = -1;
    private String ORDER_BY_PARAM = SQL_Q.TEST_DATE;
    private String ORDER_ASC_DESC = "ASC";
    private boolean MARKERS_SET = false;
    private boolean ALL_ENTRIES_SHOWN_TABLE = false;

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
        //
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

    private synchronized void tableHeaders() {
        //
        ResultSet rs;
        //
        try {
            rs = sql_table.execute("SELECT * from " + SQL_Q.PRIM_TABLE + " WHERE [Quality]='xxxxxx-xxxx'");
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
//            Thread x = new Thread(new BuildTableThread(where));
//            x.start();
            //
            buildTableByThread(where);
            //
        } else if (trigerInstance instanceof MyGraphXY) {
            //
            MARKERS_SET_P_INDEX_FIRST = markerA.POINT_INDEX + 1;
            MARKERS_SET_P_INDEX_LAST = markerB.POINT_INDEX + 1;
            //
            //
//            Thread x = new Thread(new BuildTableThread(null));
//            x.start();
            //
            buildTableByThread(null);
            //
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
                point.POINT_D = (int) (point.POINT_D * 1.5);
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

    public void addDiffMarkerPointsPolygonGraph() {
        if (gg instanceof PolygonGraph && gg instanceof HistogramGraph == false) {
            PolygonGraph pg = (PolygonGraph) gg;
            pg.addDiffMarkerPoints();
        }
    }

    public void removeDiffMarkerPointsPolygonGraph() {
        if (gg instanceof PolygonGraph && gg instanceof HistogramGraph == false) {
            PolygonGraph pg = (PolygonGraph) gg;
            pg.removeDiffMarkerPoints();
            pg.getSerie().resetPointsColorAndForm();
            MARKERS_SET = false;
            resetGraphs();
            buildTableByThread(null);
        }
    }

    public void addDiffMarkerPointsCommonGraph() {
        xygraph.addDiffMarkerPoints();
    }

    public void removeDiffMarkerPointsCommonGraph() {
        xygraph.removeDiffMarkerPoints();
        xygraph.getSerie().resetPointsColorAndForm();
        MARKERS_SET = false;
        resetGraphs();
        buildTableByThread(null);
    }

    private void connect() {
        try {
            //
//            if (Main.RUNING_IN_NETBEANS) {
//                for (Sql_B sql_B : SQL_ARR) {
//                    sql_B.connect_mdb_java_8("", "", "data.mdb");
//                }
//            }

            //
            if (Main.DEMO_MODE && Main.RUNING_IN_NETBEANS) {
                for (Sql_B sql_B : SQL_ARR) {
                    sql_B.connect_mdb_java_8("", "", "data.mdb");
                }
                return;
            }

            if (Main.DEMO_MODE) {
                //
                for (Sql_B sql_B : SQL_ARR) {
                    sql_B.connect_mdb_java_8("", "", "data.mdb");
                }
                //
            } else {
                //
                if (Main.RUNING_IN_NETBEANS) {
                    for (Sql_B sql_B : SQL_ARR) {
                        sql_B.connect_mdb_java_8("", "", "data.mdb");
                    }
                }
                //
                if (Main.RUNING_IN_NETBEANS == false) {
                    for (Sql_B sql_B : SQL_ARR) {
                        if (Main.MY_SQL) {
                            sql_B.connectMySql(p.getProperty("sql_host"), p.getProperty("sql_port"),
                                    p.getProperty("sql_db_name"), p.getProperty("sql_user"), p.getProperty("sql_pass"));
                        } else {
                            sql_B.connect_jdbc(p.getProperty("sql_host"), p.getProperty("sql_port"),
                                    p.getProperty("sql_db_name"), p.getProperty("sql_user"), p.getProperty("sql_pass"));
                        }
                    }
                }
                //
            }

            //
//            for (Sql_B sql_B : SQL_ARR) {
//                sql_B.connect_odbc("", "", "MC_LAB");
//            }
            //
            //
//            if (Main.RUNING_IN_NETBEANS == false) {
//                for (Sql_B sql_B : SQL_ARR) {
//                    sql_B.connect_jdbc(p.getProperty("sql_host"), p.getProperty("sql_port"),
//                            p.getProperty("sql_db_name"), p.getProperty("sql_user"), p.getProperty("sql_pass"));
//                }
//            }
            //
            OUT.showMessage("Connected");
            //
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void resetGraphs() {
        xygraph.removeDiffMarkerPoints();
        //
        xygraph.getSerie().resetPointsColorAndForm();
        //
        String q = getQ();
        //
        if (gg instanceof HistogramGraph) {
            gg.addData(sql_histogram_g, q, "value");
        } else if (gg instanceof PolygonGraph) {
            gg.addData(sql_polygon_g, q, "value");
        }
    }

    private String getQ() {
        return SQL_Q.showResult(gui, ORDER_BY_PARAM, ORDER_ASC_DESC, null);
    }

    public boolean buildGraphs() {
        //
        if (gui.obligatoryBoxesFilled() == false) {
            return false;
        }
        //
        buildTableByThread(null);
        //
        String q = getQ();
//        String q = SQL_Q.forTestC();
        //
        if (q == null) {
            return false;
        }
        //
        xygraph.deleteAllPointsFromAllSeries();
        //
        xygraph.addData(sql_common_g, q, "value");
        //
//            gg.addLimits(rs);
        //
        if (gg instanceof HistogramGraph) {
            gg.addData(sql_histogram_g, q, "value");
        } else if (gg instanceof PolygonGraph) {
            gg.addData(sql_polygon_g, q, "value");
        }
        //
        return true;
    }

    public synchronized void buildTableByThread(String addditionalWhere) {
        Thread x = new Thread(new BuildTableThread(addditionalWhere));
        x.start();
    }

    /**
     * @deprecated - shall only be called by the TableBuilderThread
     * @param addditionalWhere
     */
    private synchronized void buildTable(String addditionalWhere) {
        //
        tableHeaders();
        //
        if (addditionalWhere == null && MARKERS_SET_P_INDEX_FIRST == -1 && MARKERS_SET_P_INDEX_LAST == -1) {
            ALL_ENTRIES_SHOWN_TABLE = true;
        } else {
            ALL_ENTRIES_SHOWN_TABLE = false;
        }
        //
        String q = SQL_Q.showResult(gui, ORDER_BY_PARAM, ORDER_ASC_DESC, addditionalWhere);
        //
        if (q == null) {
            return;
        }
        //
        try {
            //
            ResultSet rs = sql_table.execute(q, OUT);
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
            //
            HelpA.hideColumnByName(gui.jTableMain, SQL_Q.QUALITY);
            HelpA.hideColumnByName(gui.jTableMain, SQL_Q.ORDER);
            //
            //<#GFT-SPECIAL-DEMO>
//            gui.jButtonClear.setEnabled(false);
//            gui.jComboBoxOrder.setEnabled(false);
//            gui.jComboBoxLSL.setEnabled(false);
//            gui.jComboBoxUSL.setEnabled(false);
//            gui.jComboBoxDateA.setEnabled(false);
//            gui.jComboBoxBatch.setEnabled(false);
//            //
//            gui.jComboBoxQuality.setSelectedItem(null);
            //</#GFT-SPECIAL-DEMO>
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
        //
        if (ALL_ENTRIES_SHOWN_TABLE == false) {
            return;
        }
        //
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

    public void showTestItemOnBtnClick(boolean next) {
        //
        JComboBoxA testName = (JComboBoxA) gui.jComboBoxTestName;
        //
        int selectedIndex = testName.getSelectedIndex();
        //
        try {
            //
            if (next) {
                testName.setSelectedIndex(selectedIndex + 1);
            } else {
                testName.setSelectedIndex(selectedIndex - 1);
            }
            //
            gui.buildGraphs();
            //
        } catch (Exception ex) {
        }
        //
    }

    //==========================================================================
    //==========================================================================
    public void fillComboStandard(JComboBoxA jcbm) {
        //
        String q = SQL_Q.fillAuto(jcbm.getPARAMETER(), gui);
        //
        OUT.showMessage(q);
        //
//        if(fill(jcbm, (JComboBoxA) gui.jComboBoxTestName)){
        jcbm.setFLAG_WAIT(HelpA.fillComboBox_with_wait(jcbm, jcbm.getFLAG_WAIT(), q, sql_common_g));
//        }
        //
        resetFlagsWaitSelective(jcbm);
        //
    }

    private boolean fill(JComboBoxA jcbm, JComboBoxA jcomboTestName) {
        //
        if (jcbm.equals(jcomboTestName)) {
            return true;
        }
        //
        String jcomboTestNameValue = HelpA.getComboBoxSelectedValue_b(jcomboTestName);
        //
        String jcbmValue = HelpA.getComboBoxSelectedValue_b(jcbm);
        //
        if (jcomboTestNameValue != null && jcbmValue != null) {
            return false;
        } else {
            return true;
        }
        //
    }

    private void resetFlagsWaitSelective(JComboBoxA jcbm) {
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

    public void reset() {
        clearComponents();
        resetFlagWaits();
        clearEntriesFromCBoxes();
    }

    private void clearEntriesFromCBoxes() {
        //
        ArrayList<JComboBox> list = gui.getJCOMBO_LIST();
        //
        for (JComboBox jComboBox : list) {
            //
            JComboBoxA boxA = (JComboBoxA) jComboBox;
            //
            boxA.clearContent();
            //
        }
    }

    private void clearComponents() {
        //
        ArrayList<JComboBox> list = gui.getJCOMBO_LIST();
        //
        for (JComboBox jComboBox : list) {
            jComboBox.setSelectedItem(null);
            jComboBox.setEditable(false);
            jComboBox.setEnabled(true);
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

    private void resetFlagWaits() {
        ArrayList<JComboBox> list = gui.getJCOMBO_LIST();
        //
        for (JComboBox jComboBox : list) {
            //
            JComboBoxA boxA = (JComboBoxA) jComboBox;
            //
            boxA.setFLAG_WAIT(0);
        }
    }
    //==========================================================================
}
