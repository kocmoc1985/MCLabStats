/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.util.ArrayList;
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
    public static final String TEST_DATE = "testdate";

    public static String test_a(String quality) {
        return "SELECT  * FROM resultsN\n"
                + "WHERE resultsN.Quality=" + quotes(quality, false)
                + " AND resultsN.Name='ML'\n"
                + "ORDER BY resultsN.order, resultsN.BatchNo, resultsN.TestNo";
    }

    private static ArrayList<FillAutoEntry> buildList() {
        ArrayList<FillAutoEntry> list = new ArrayList<>();
        //
        list.add(new FillAutoEntry(QUALITY, HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxQuality), false));
        list.add(new FillAutoEntry(ORDER, HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxOrder), false));
        list.add(new FillAutoEntry(BATCH, HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxBatch), true));
        list.add(new FillAutoEntry(TEST_CODE, HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxTestCode), false));
        list.add(new FillAutoEntry(TEST_NAME, HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxTestName), false));
        list.add(new FillAutoEntry(LSL, HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxLSL), true));
        list.add(new FillAutoEntry(USL, HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxUSL), true));
        //
        return list;
    }

    public static String showResult(Gui gui) {
        //
        String query = "SELECT * from " + PRIM_TABLE;
        //
        ArrayList<FillAutoEntry> list = buildList();
        //
        for (FillAutoEntry entry : list) {
            //
            String ACT_COMBO_PARAM = entry.getColName();
            String value = entry.getValue();
            boolean isNum = entry.isNumber();
            //
            if (value != null && value.isEmpty() == false) {
                query += " AND [" + ACT_COMBO_PARAM + "]=" + quotes(value, isNum);
            }
        }
        //
        String dateA = HelpA.getComboBoxSelectedValue_b(gui.jComboBoxDateA);
        String dateB = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxDateB);
        //
        if ((dateB != null && dateB.isEmpty() == false) && (dateA != null && dateA.isEmpty() == false)) {
            query += " AND [" + TEST_DATE + "] >=" + quotes(dateA, false);
            query += " AND [" + TEST_DATE + "] <=" + quotes(dateB, false);
        } else {
            if (dateA != null && dateA.isEmpty() == false) {
                query += " AND [" + TEST_DATE + "]=" + quotes(dateA, false);
            }
        }
        //
        if (query.contains("WHERE") == false) {
            query = query.replaceFirst("AND", "WHERE");
        }
        //
        System.out.println("query: " + query);
        return query;
    }


    public static String fillAuto(String actualComboParam,Gui gui) {
        //
        String query = "SELECT DISTINCT [" + actualComboParam + "], COUNT(" + actualComboParam + ") as 'ammount'"
                + " from " + PRIM_TABLE;
        //
        ArrayList<FillAutoEntry> list = buildList();
        //
        for (FillAutoEntry entry : list) {
            //
            String ACT_COMBO_PARAM = entry.getColName();
            String value = entry.getValue();
            boolean isNum = entry.isNumber();
            //
            if (value != null && value.isEmpty() == false && actualComboParam.equals(ACT_COMBO_PARAM) == false) {
                query += " AND [" + ACT_COMBO_PARAM + "]=" + quotes(value, isNum);
            }
        }
        //
        //
        String dateA = HelpA.getComboBoxSelectedValue_b(gui.jComboBoxDateA);
        String dateB = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxDateB);
        //
        if ((dateB != null && dateB.isEmpty() == false) && (dateA != null && dateA.isEmpty() == false)) {
            query += " AND [" + TEST_DATE + "] >=" + quotes(dateA, false);
            query += " AND [" + TEST_DATE + "] <=" + quotes(dateB, false);
        } else {
            if (dateA != null && dateA.isEmpty() == false) {
                query += " AND [" + TEST_DATE + "]=" + quotes(dateA, false);
            }
        }
        //
        if (query.contains("WHERE") == false) {
            query = query.replaceFirst("AND", "WHERE");
        }
        //
        query += " GROUP BY [" + actualComboParam + "]";
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

    private static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    /**
     * @deprecated 
     * @return 
     */
//    public static String showResultB() {
//        //
//        String query = "SELECT * from " + PRIM_TABLE;
//        //
//        String quality = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxQuality);
//        String order = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxOrder);
//        String batch = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxBatch);
//        String testCode = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxTestCode);
//        String testName = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxTestName);
//        String lsl = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxLSL);
//        String usl = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxUSL);
//        String dateA = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxDateA);
//        String dateB = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxDateB);
//        //
//        if (quality != null && quality.isEmpty() == false) {
//            query += " AND [" + QUALITY + "]=" + quotes(quality, false);
//        }
//        if (order != null && order.isEmpty() == false) {
//            query += " AND [" + ORDER + "]=" + quotes(order, false);
//        }
//        if (batch != null && batch.isEmpty() == false) {
//            query += " AND [" + BATCH + "]=" + quotes(batch, isNumber(batch));
//        }
//        if (testCode != null && testCode.isEmpty() == false) {
//            query += " AND [" + TEST_CODE + "]=" + quotes(testCode, false);
//        }
//        if (testName != null && testName.isEmpty() == false) {
//            query += " AND [" + TEST_NAME + "]=" + quotes(testName, false);
//        }
//        if (lsl != null && lsl.isEmpty() == false) {
//            query += " AND [" + LSL + "]=" + quotes(lsl, true);
//        }
//        if (usl != null && usl.isEmpty() == false) {
//            query += " AND [" + USL + "]=" + quotes(usl, true);
//        }
//        //
//        if ((dateB != null && dateB.isEmpty() == false) && (dateA != null && dateA.isEmpty() == false)) {
//            query += " AND [" + TEST_DATE + "] >=" + quotes(dateA, false);
//            query += " AND [" + TEST_DATE + "] <=" + quotes(dateB, false);
//        } else {
//            if (dateA != null && dateA.isEmpty() == false) {
//                query += " AND [" + TEST_DATE + "]=" + quotes(dateA, false);
//            }
//        }
//        //
//        if (query.contains("WHERE") == false) {
//            query = query.replaceFirst("AND", "WHERE");
//        }
//        //
//        System.out.println("query: " + query);
//        return query;
//    }

    /**
     * @deprecated @param actualComboParam
     * @return
     */
//    private static String fillAutoB(String actualComboParam) {
//        //
//        String query = "SELECT DISTINCT [" + actualComboParam + "], COUNT(" + actualComboParam + ") as 'ammount'"
//                + " from " + PRIM_TABLE;
//        //
//        String quality = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxQuality);
//        String order = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxOrder);
//        String batch = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxBatch);
//        String testCode = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxTestCode);
//        String testName = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxTestName);
//        String lsl = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxLSL);
//        String usl = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxUSL);
//        String dateA = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxDateA);
//        String dateB = HelpA.getComboBoxSelectedValue_b(Gui.jComboBoxDateB);
//        //
//        if (quality != null && quality.isEmpty() == false && actualComboParam.equals(QUALITY) == false) {
//            query += " AND [" + QUALITY + "]=" + quotes(quality, false);
//        }
//        if (order != null && order.isEmpty() == false && actualComboParam.equals(ORDER) == false) {
//            query += " AND [" + ORDER + "]=" + quotes(order, false);
//        }
//        if (batch != null && batch.isEmpty() == false && actualComboParam.equals(BATCH) == false) {
//            query += " AND [" + BATCH + "]=" + quotes(batch, isNumber(batch)); // OBS! Is Integer
//        }
//        if (testCode != null && testCode.isEmpty() == false && actualComboParam.equals(TEST_CODE) == false) {
//            query += " AND [" + TEST_CODE + "]=" + quotes(testCode, false);
//        }
//        if (testName != null && testName.isEmpty() == false && actualComboParam.equals(TEST_NAME) == false) {
//            query += " AND [" + TEST_NAME + "]=" + quotes(testName, false);
//        }
//        if (lsl != null && lsl.isEmpty() == false && actualComboParam.equals(LSL) == false) {
//            query += " AND [" + LSL + "]=" + quotes(lsl, true);
//        }
//        if (usl != null && usl.isEmpty() == false && actualComboParam.equals(USL) == false) {
//            query += " AND [" + USL + "]=" + quotes(usl, true);
//        }
//
//        if ((dateB != null && dateB.isEmpty() == false) && (dateA != null && dateA.isEmpty() == false)) {
//            query += " AND [" + TEST_DATE + "] >=" + quotes(dateA, false);
//            query += " AND [" + TEST_DATE + "] <=" + quotes(dateB, false);
//        } else {
//            if (dateA != null && dateA.isEmpty() == false) {
//                query += " AND [" + TEST_DATE + "]=" + quotes(dateA, false);
//            }
//        }
//
////        if (dateA != null && dateA.isEmpty() == false && actualComboParam.equals(TEST_DATE) == false) {
////            query += " AND [" + TEST_DATE + "]=" + quotes(dateA, false);
////        }
//        //
//
//        if (query.contains("WHERE") == false) {
//            query = query.replaceFirst("AND", "WHERE");
//        }
//        //
////        query += " GROUP BY [" + actualComboParam + "]";
//        query += " GROUP BY [" + actualComboParam + "]";
////        query += " ORDER BY [" + actualComboParam + "] DESC";
//        //
//        System.out.println("query: " + query);
//        return query;
//    }

    private static String fill_quality_combo_box() {
        return "SELECT distinct " + QUALITY + "  from " + PRIM_TABLE;
    }

    private static String fill_order_combo_box(String quality) {
        return "SELECT distinct " + ORDER + " from " + PRIM_TABLE
                + " WHERE " + QUALITY + "=" + quotes(quality, false);
    }
}
