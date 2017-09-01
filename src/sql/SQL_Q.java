/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import main.Gui;
import other.HelpA;

/**
 *
 * @author KOCMOC
 */
public class SQL_Q {

    public static final String PRIM_TABLE = "REsultsN";
    public static final String QUALITY = "Quality";
    public static final String ORDER = "order";
    public static final String BATCH = "BatchNo";
    public static final String TEST_CODE = "TestCode";
    public static final String TEST_NAME = "Name";
    public static final String LSL = "LSL";
    public static final String USL = "USL";

    public static String test_a(String quality) {
        return "SELECT  * FROM resultsN\n"
                + "WHERE resultsN.Quality=" + quotes(quality, false)
                + " AND resultsN.Name='ML'\n"
                + "ORDER BY resultsN.order, resultsN.BatchNo, resultsN.TestNo";
    }

    public static String showResult() {
        //
        String query = "SELECT * from " + PRIM_TABLE;
        //
        String quality = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxQuality);
        String order = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxOrder);
        String batch = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxBatch);
        String testCode = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxTestCode);
        String testName = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxTestName);
        String lsl = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxLSL);
        String usl = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxUSL);
        //
        if (quality != null && quality.isEmpty() == false) {
            query += " AND [" + QUALITY + "]=" + quotes(quality, false);
        }
        if (order != null && order.isEmpty() == false) {
            query += " AND [" +  ORDER + "]=" + quotes(order, false);
        }
        if (batch != null && batch.isEmpty() == false) {
            query += " AND [" + BATCH + "]=" + quotes(batch, false);
        }
        if (testCode != null && testCode.isEmpty() == false) {
            query += " AND [" + TEST_CODE + "]=" + quotes(testCode, false);
        }
        if (testName != null && testName.isEmpty() == false) {
            query += " AND [" + TEST_NAME + "]=" + quotes(testName, false);
        }
        if (lsl != null && lsl.isEmpty() == false) {
            query += " AND [" + LSL + "]=" + quotes(lsl, true);
        }
        if (usl != null && usl.isEmpty() == false) {
            query += " AND [" + USL + "]=" + quotes(usl, true);
        }
        //
        if (query.contains("WHERE") == false) {
            query = query.replaceFirst("AND", "WHERE");
        }
        //
        System.out.println("query: " + query);
        return query;
    }

    public static void main(String[] args) {
        showResult();
    }

    public static String fillAuto(String actualComboParam) {
        //
        String query = "SELECT [" + actualComboParam + "] from " + PRIM_TABLE;
        //
        String quality = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxQuality);
        String order = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxOrder);
        String batch = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxBatch);
        String testCode = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxTestCode);
        String testName = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxTestName);
        String lsl = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxLSL);
        String usl = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxUSL);
        //
        if (quality != null && quality.isEmpty() == false && actualComboParam.equals(QUALITY) == false) {
            query += " AND [" + QUALITY + "]=" + quotes(quality, false);
        }  if (order != null && order.isEmpty() == false && actualComboParam.equals(ORDER) == false) {
            query += " AND [" + ORDER + "]=" + quotes(order, false);
        }  if (batch != null && batch.isEmpty() == false && actualComboParam.equals(BATCH) == false) {
            query += " AND [" + BATCH + "]=" + quotes(batch, false);
        }  if (testCode != null && testCode.isEmpty() == false && actualComboParam.equals(TEST_CODE) == false) {
            query += " AND [" + TEST_CODE + "]=" + quotes(testCode, false);
        }  if (testName != null && testName.isEmpty() == false && actualComboParam.equals(TEST_NAME) == false) {
            query += " AND [" + TEST_NAME + "]=" + quotes(testName, false);
        }  if (lsl != null && lsl.isEmpty() == false && actualComboParam.equals(LSL) == false) {
            query += " AND [" + LSL + "]=" + quotes(lsl, true);
        }  if (usl != null && usl.isEmpty() == false && actualComboParam.equals(USL) == false) {
            query += " AND [" + USL + "]=" + quotes(usl, true);
        }
        //
        if (query.contains("WHERE") == false) {
            query = query.replaceFirst("AND", "WHERE");
        }
        //
        query += " GROUP BY [" + actualComboParam + "]";
        query += " ORDER BY [" + actualComboParam + "] DESC";
        //
        System.out.println("query: " + query);
        return query;
    }

    public static String quotes(String str, boolean number) {
        //
        if (str == null || str.equals("NULL")) {
            return "NULL";
        }
        //
        if (number) {
            return str.replaceAll("'", "");
        } else {
            if (str.contains("'")) {
                return str;
            } else {
                return "'" + str + "'";
            }
        }
    }

    private static String fill_quality_combo_box() {
        return "SELECT distinct " + QUALITY + "  from " + PRIM_TABLE;
    }

    private static String fill_order_combo_box(String quality) {
        return "SELECT distinct " + ORDER + " from " + PRIM_TABLE
                + " WHERE " + QUALITY + "=" + quotes(quality, false);
    }
}
