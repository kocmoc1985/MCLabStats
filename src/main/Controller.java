/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyGraphContainer;
import XYG_HISTO.MyGraphXY_H;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private GistoGram histogram = new GistoGram("Histogram", new MyGraphXY_H(), MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
    private XyGraph graph = new XyGraph("mooney", MyGraphContainer.DISPLAY_MODE_FULL_SCREEN, histogram);
    private String PATH;
    private ShowMessage OUT;
    private Gui gui;

    public Controller(String PATH, ShowMessage OUT) {
        this.PATH = PATH;
        this.OUT = OUT;
        this.gui = (Gui) OUT;
        connect();
        //
    }
    private static long flagWaitQualityCombo;

    public void fillComboQuality() {
//        HelpA.fillComboBox(sql, gui.jComboBoxQuality, SQL_Q.fill_quality_combo_box(), "Quality", false, false);
        String q = SQL_Q.fill_quality_combo_box();
        OUT.showMessage(q);
        flagWaitQualityCombo = HelpA.fillComboBox_with_wait(gui.jComboBoxQuality, flagWaitQualityCombo, SQL_Q.fill_quality_combo_box(), sql);
    }
    private static long flagWaitOrderCombo;

    public void fillComboOrder() {
        String q = SQL_Q.fill_order_combo_box(HelpA.getComboBoxSelectedValue(gui.jComboBoxQuality));
        OUT.showMessage(q);
        flagWaitOrderCombo = HelpA.fillComboBox_with_wait(gui.jComboBoxOrder, flagWaitOrderCombo, q, sql);
    }

    private void connect() {
        try {
//            sql.connect_mdb("", "", PATH);
            sql.connect_odbc("", "", "MC_LAB");
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
        String q = SQL_Q.test_a(HelpA.getComboBoxSelectedValue(gui.jComboBoxQuality));
        //
        try {
            //
            ResultSet rs = sql.execute(q, OUT);
            graph.deleteAllPointsFromAllSeries();
            graph.addData(rs, "value");
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
}
