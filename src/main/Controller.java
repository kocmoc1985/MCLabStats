/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyGraphContainer;
import XYG_HISTO.MyGraphXY_H;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import other.HelpA;
import sql.SQL_Q;
import sql.ShowMessage;
import sql.Sql_B;

/**
 *
 * @author KOCMOC
 */
public class Controller {

    private Sql_B sql = new Sql_B(false, true);
    private GistoGraph histogram = new GistoGraph("Histogram", new MyGraphXY_H(), MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
    private XyGraph xygraph = new XyGraph("mooney", MyGraphContainer.DISPLAY_MODE_FULL_SCREEN, histogram);
    private ShowMessage OUT;
    private Gui gui;

    public Controller(ShowMessage OUT) {
        this.OUT = OUT;
        this.gui = (Gui) OUT;
        //
        Gui.GraphPanel.add(xygraph.getGraph());
        Gui.HistoPanel.add(histogram.getGraph());
        //
        connect();
        //
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
    private boolean oneTime = false;

    public void testBuild() {
        //
//        String q = SQL_Q.test_a(HelpA.getComboBoxSelectedValue(Gui.jComboBoxQuality));
        String q = SQL_Q.showResult();
        //
        try {
            //
            ResultSet rs = sql.execute(q, OUT);
            xygraph.deleteAllPointsFromAllSeries();
            xygraph.addData(rs, "value");
            //
            rs.first();
            //
            histogram.addData(rs, "value", "#.##");
            //
            oneTime = true;
            //
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //==========================================================================
    //==========================================================================

    public void clearBoxes() {

        Gui.jComboBoxQuality.setSelectedItem(null);
        Gui.jComboBoxOrder.setSelectedItem(null);
        Gui.jComboBoxBatch.setSelectedItem(null);
        Gui.jComboBoxTestCode.setSelectedItem(null);
        Gui.jComboBoxTestName.setSelectedItem(null);
        Gui.jComboBoxLSL.setSelectedItem(null);
        Gui.jComboBoxUSL.setSelectedItem(null);
        Gui.jComboBoxDateA.setSelectedItem(null);

        Gui.jComboBoxQuality.setEditable(false);
        Gui.jComboBoxOrder.setEditable(false);
        Gui.jComboBoxBatch.setEditable(false);
        Gui.jComboBoxTestCode.setEditable(false);
        Gui.jComboBoxTestName.setEditable(false);
        Gui.jComboBoxLSL.setEditable(false);
        Gui.jComboBoxUSL.setEditable(false);
        Gui.jComboBoxDateA.setEditable(false);

        gui.repaint();
    }
    //==========================================================================
    //==========================================================================
    private static long flagWaitQualityCombo;

    public void fillComboQuality() {
        //
        String q = SQL_Q.fillAuto(SQL_Q.QUALITY);
        //
        OUT.showMessage(q);
        //
        flagWaitQualityCombo = HelpA.fillComboBox_with_wait(Gui.jComboBoxQuality, flagWaitQualityCombo, q, sql);
        //
        flagWaitOrderCombo = 0;
        flagWaitBatchCombo = 0;
        flagWaitTestCodeCombo = 0;
        flagWaitTestNameCombo = 0;
        flagWaitLSLCombo = 0;
        flagWaitUSLCombo = 0;
        flagWaitDateACombo = 0;
        //
    }
    private static long flagWaitOrderCombo;

    public void fillComboOrder() {
        //
        String q = SQL_Q.fillAuto(SQL_Q.ORDER);
        //
        OUT.showMessage(q);
        //
        flagWaitOrderCombo = HelpA.fillComboBox_with_wait(Gui.jComboBoxOrder, flagWaitOrderCombo, q, sql);
        //
        flagWaitQualityCombo = 0;
        flagWaitBatchCombo = 0;
        flagWaitTestCodeCombo = 0;
        flagWaitTestNameCombo = 0;
        flagWaitLSLCombo = 0;
        flagWaitUSLCombo = 0;
        flagWaitDateACombo = 0;
        //
    }
    private static long flagWaitBatchCombo;

    public void fillComboBatch() {
        //
        String q = SQL_Q.fillAuto(SQL_Q.BATCH);
        //
        OUT.showMessage(q);
        //
        flagWaitBatchCombo = HelpA.fillComboBox_with_wait(Gui.jComboBoxBatch, flagWaitBatchCombo, q, sql);
        //
        flagWaitQualityCombo = 0;
        flagWaitOrderCombo = 0;
        flagWaitTestCodeCombo = 0;
        flagWaitTestNameCombo = 0;
        flagWaitLSLCombo = 0;
        flagWaitUSLCombo = 0;
        flagWaitDateACombo = 0;
    }
    private static long flagWaitTestCodeCombo;

    public void fillComboTestCode() {
        //
        String q = SQL_Q.fillAuto(SQL_Q.TEST_CODE);
        //
        OUT.showMessage(q);
        //
        flagWaitTestCodeCombo = HelpA.fillComboBox_with_wait(Gui.jComboBoxTestCode, flagWaitTestCodeCombo, q, sql);
        //
        flagWaitQualityCombo = 0;
        flagWaitOrderCombo = 0;
        flagWaitBatchCombo = 0;
        flagWaitTestNameCombo = 0;
        flagWaitLSLCombo = 0;
        flagWaitUSLCombo = 0;
        flagWaitDateACombo = 0;
    }
    private static long flagWaitTestNameCombo;

    public void fillComboTestName() {
        //
        String q = SQL_Q.fillAuto(SQL_Q.TEST_NAME);
        //
        OUT.showMessage(q);
        //
        flagWaitTestNameCombo = HelpA.fillComboBox_with_wait(Gui.jComboBoxTestName, flagWaitTestNameCombo, q, sql);
        //
        flagWaitQualityCombo = 0;
        flagWaitOrderCombo = 0;
        flagWaitBatchCombo = 0;
        flagWaitTestCodeCombo = 0;
        flagWaitLSLCombo = 0;
        flagWaitUSLCombo = 0;
        flagWaitDateACombo = 0;
    }
    private static long flagWaitLSLCombo;

    public void fillComboLSL() {
        //
        String q = SQL_Q.fillAuto(SQL_Q.LSL);
        //
        OUT.showMessage(q);
        //
        flagWaitLSLCombo = HelpA.fillComboBox_with_wait(Gui.jComboBoxLSL, flagWaitLSLCombo, q, sql);
        //
        flagWaitQualityCombo = 0;
        flagWaitOrderCombo = 0;
        flagWaitBatchCombo = 0;
        flagWaitTestCodeCombo = 0;
        flagWaitTestNameCombo = 0;
        flagWaitUSLCombo = 0;
        flagWaitDateACombo = 0;
    }
    private static long flagWaitUSLCombo;

    public void fillComboUSL() {
        //
        String q = SQL_Q.fillAuto(SQL_Q.USL);
        //
        OUT.showMessage(q);
        //
        flagWaitUSLCombo = HelpA.fillComboBox_with_wait(Gui.jComboBoxUSL, flagWaitUSLCombo, q, sql);
        //
        flagWaitQualityCombo = 0;
        flagWaitOrderCombo = 0;
        flagWaitBatchCombo = 0;
        flagWaitTestCodeCombo = 0;
        flagWaitTestNameCombo = 0;
        flagWaitLSLCombo = 0;
        flagWaitDateACombo = 0;
    }
    private static long flagWaitDateACombo;

    public void fillComboDateA() {
        //
        String q = SQL_Q.fillAuto(SQL_Q.TEST_DATE);
        //
        OUT.showMessage(q);
        //
        flagWaitDateACombo = HelpA.fillComboBox_with_wait(Gui.jComboBoxDateA, flagWaitDateACombo, q, sql);
        //
        flagWaitQualityCombo = 0;
        flagWaitOrderCombo = 0;
        flagWaitBatchCombo = 0;
        flagWaitTestCodeCombo = 0;
        flagWaitTestNameCombo = 0;
        flagWaitLSLCombo = 0;
    }

    public void resetFlagWaits() {
        flagWaitQualityCombo = 0;
        flagWaitOrderCombo = 0;
        flagWaitBatchCombo = 0;
        flagWaitTestCodeCombo = 0;
        flagWaitTestNameCombo = 0;
        flagWaitLSLCombo = 0;
        flagWaitUSLCombo = 0;
        flagWaitDateACombo = 0;
    }

    private void mapFalgs() {
        HashMap<String, Long> flag_wait_map = new HashMap<>();
        //
        flag_wait_map.put(SQL_Q.QUALITY, flagWaitQualityCombo);
    }
    //==========================================================================
}
