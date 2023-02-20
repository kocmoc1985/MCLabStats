/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import com.michaelbaranov.microba.calendar.DatePicker;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JComboBox;
import main.Main;
import static main.Main.DATE_FORMAT;
import other.CONSTANTS;
import other.HelpAB;
import other.JComboBoxA;

/**
 *
 * @author KOCMOC
 */
public class SQL_QQ {

    public static String PRIM_TABLE = loadFromProps();
    public static String QUALITY = "Quality";
    public static String ORDER = "order";
    public static String BATCH = "BatchNo";
    public static String TEST_CODE = "TestCode";
    public static String TEST_NAME = "Name";
    public static String LSL = "LSL";
    public static String USL = "USL";
    public static String TEST_DATE = "testdate";
    public static String TEST_VALUE = "value";
    public static String TEST_STATUS = "Status";
    //
//    public static final String PRIM_TABLE = "dbagb3.fnMC04V3()";
//    public static final String QUALITY = "Recipe";
//    public static final String ORDER = "Order";
//    public static final String BATCH = "Batch";
//    public static final String TEST_CODE = "TestProcedure";
//    public static final String TEST_NAME = "TestTag";
//    public static final String LSL = "LSL";
//    public static final String USL = "USL";
//    public static final String TEST_DATE = "TestDate";
//    public static final String TEST_VALUE = "TestResult";
//    public static final String TEST_STATUS = "TestStatus";

    private static String loadFromProps() {
        Properties p = HelpAB.properties_load_properties("main.properties", false);
        return p.getProperty("resultsn_name", "REsultsN");
    }

    private static ArrayList<FillAutoEntry> buildList(Main gui) {
        //
        ArrayList<FillAutoEntry> list = new ArrayList<>();
        //
        for (JComboBox box : gui.getJCOMBO_LIST()) {
            //
            JComboBoxA boxM = (JComboBoxA) box;
            //
            if (boxM.getPARAMETER().equals(TEST_DATE) == false) {
                list.add(new FillAutoEntry(boxM.getPARAMETER(), HelpAB.getComboBoxSelectedValue_bb(boxM), boxM.isNUMBER()));
            }
            //
        }
        //
        return list;
        //
    }

    private static ArrayList<FillAutoEntry> buildListB(Main gui) {
        ArrayList<FillAutoEntry> list = new ArrayList<>();
        //
        list.add(new FillAutoEntry(QUALITY, HelpAB.getComboBoxSelectedValue_bb(gui.jComboBoxQuality), false));
        list.add(new FillAutoEntry(ORDER, HelpAB.getComboBoxSelectedValue_bb(gui.jComboBoxOrder), false));
        list.add(new FillAutoEntry(BATCH, HelpAB.getComboBoxSelectedValue_bb(gui.jComboBoxBatch), true));
        list.add(new FillAutoEntry(TEST_CODE, HelpAB.getComboBoxSelectedValue_bb(gui.jComboBoxTestCode), false));
        list.add(new FillAutoEntry(TEST_NAME, HelpAB.getComboBoxSelectedValue_bb(gui.jComboBoxTestName), false));
        list.add(new FillAutoEntry(LSL, HelpAB.getComboBoxSelectedValue_bb(gui.jComboBoxLSL), true));
        list.add(new FillAutoEntry(USL, HelpAB.getComboBoxSelectedValue_bb(gui.jComboBoxUSL), true));
        //
        return list;
    }

    public static String buildAdditionalWhereGistoGram(String lowerValue, String higherValue) {
        return " AND [" + TEST_VALUE + "]>=" + quotes(lowerValue, true) + ""
                + " AND [" + TEST_VALUE + "] <=" + quotes(higherValue, true);
    }

    public static String buildAdditionalWhereXyGraph(int firstIndex, int lastIndex) {
        return " AND [" + TEST_VALUE + "]>=" + quotes("" + firstIndex, true) + ""
                + " AND [" + TEST_VALUE + "] <=" + quotes("" + lastIndex, true);
    }

//    public static String tableHeaders(boolean mysql){
//        String q = "SELECT * from " + PRIM_TABLE + " WHERE [Quality]='xxxxxx-xxxx'";
//        //
//        if(){
//            
//        }
//    }
    public static String forTest() {
        return "SELECT * from " + PRIM_TABLE + " WHERE [TestCode]='10171' AND [Name]='ML' AND [testdate]='09/12/14'";
    }

    public static String forTestB() {
        return "SELECT * from " + PRIM_TABLE + " WHERE [" + SQL_QQ.QUALITY + "] = '1802860-ST220' AND [" + SQL_QQ.TEST_CODE + "]='10194' AND [" + SQL_QQ.TEST_NAME + "]='ML'";
    }

    public static String forTestC() {
        return "SELECT * from " + PRIM_TABLE + " WHERE [" + SQL_QQ.QUALITY + "] = '9401696-ST59' AND [" + SQL_QQ.TEST_CODE + "]='30001' AND [" + SQL_QQ.TEST_NAME + "]='HHMedian'";
    }

    public static String showResult(Main gui, String orderBy, String ascOrDesc, String additionalWhere) {
        //
        int nullCounter = 0;
        //
        String query = "SELECT * from " + PRIM_TABLE;
        //
        ArrayList<FillAutoEntry> list = buildList(gui);
        //
        for (FillAutoEntry entry : list) {
            //
            String ACT_COMBO_PARAM = entry.getColName();
            String value = entry.getValue();
            boolean isNum = entry.isNumber();
            //
            if (value != null && value.isEmpty() == false) {
                query += " AND [" + ACT_COMBO_PARAM + "]=" + quotes(value, isNum);
            } else {
                nullCounter++;
            }
        }
        //
        if (nullCounter == list.size()) {
            return null;
        }
        //======================================================================
        String date_ = HelpAB.getComboBoxSelectedValue_bb(gui.jComboBoxDate);
        //
        String dateA = HelpAB.datePickerGetDate(gui.datePickerA);
        String dateB = HelpAB.datePickerGetDate(gui.datePickerB);
        //
        if (HelpAB.bothDatesPresent(dateA, dateB)) {
            query += " AND [" + TEST_DATE + "] >=" + quotes(dateA, false);
            query += " AND [" + TEST_DATE + "] <=" + quotes(dateB, false);
        } else {
            if (date_ != null && date_.isEmpty() == false) {
                query += " AND [" + TEST_DATE + "]=" + quotes(date_, false);
            }
        }
        //======================================================================
        //
        if (additionalWhere != null && additionalWhere.isEmpty() == false) {
            query += additionalWhere;
        }
        //
        //
        if (query.contains("WHERE") == false) {
            query = query.replaceFirst("AND", "WHERE");
        }
        //
        if ((orderBy != null && orderBy.isEmpty() == false) && (ascOrDesc != null && ascOrDesc.isEmpty() == false)) {
            if (ascOrDesc.toLowerCase().equals("asc")) {
                query += " ORDER BY [" + orderBy + "] ASC";
            } else if (ascOrDesc.toLowerCase().equals("desc")) {
                query += " ORDER BY [" + orderBy + "] DESC";
            }
        }
        //
//        System.out.println("query: " + query);
        return query;
    }


    public static String fillAuto(String actualComboParam, Main gui) {
        //
        String query;
        //
        if (Main.COMPANY_NAME.equals(CONSTANTS.COMPANY_NAME_CEAT)) {
            query = "SELECT DISTINCT [" + actualComboParam + "] from " + PRIM_TABLE;
        } else {
            query = "SELECT DISTINCT [" + actualComboParam + "], COUNT([" + actualComboParam + "]) as ammount"
                    + " from " + PRIM_TABLE;
        }
        //
        System.out.println("QUERY: " + query);
        //
        ArrayList<FillAutoEntry> list = buildList(gui);
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
        //======================================================================
        String date_ = HelpAB.getComboBoxSelectedValue_bb(gui.jComboBoxDate);
        //
        String dateA = HelpAB.datePickerGetDate(gui.datePickerA);
        String dateB = HelpAB.datePickerGetDate(gui.datePickerB);
        //
        if (HelpAB.bothDatesPresent(dateA, dateB)) {
            query += " AND [" + TEST_DATE + "] >=" + quotes(dateA, false);
            query += " AND [" + TEST_DATE + "] <=" + quotes(dateB, false);
        } else {
            if (date_ != null && date_.isEmpty() == false) {
                query += " AND [" + TEST_DATE + "]=" + quotes(date_, false);
            }
        }
        //======================================================================
        //
        if (query.contains("WHERE") == false) {
            query = query.replaceFirst("AND", "WHERE");
        }
        //
        query += " GROUP BY [" + actualComboParam + "]";
        //
        //
        if (actualComboParam.equals(TEST_DATE)) {
            query += " ORDER BY [" + actualComboParam + "] desc";
        } else if (actualComboParam.equals(QUALITY) && Sql_B.MDB_CONNECTION == false) {
//            query += " ORDER BY [ammount] desc";
        }
        //
//        System.out.println("query: " + query);
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
     * @deprecated @return
     */
//    public static String showResultB() {
//        //
//        String query = "SELECT * from " + PRIM_TABLE;
//        //
//        String quality = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxQuality);
//        String order = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxOrder);
//        String batch = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxBatch);
//        String testCode = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxTestCode);
//        String testName = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxTestName);
//        String lsl = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxLSL);
//        String usl = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxUSL);
//        String date_ = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxDateA);
//        String dateB = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxDateB);
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
//        if ((dateB != null && dateB.isEmpty() == false) && (date_ != null && date_.isEmpty() == false)) {
//            query += " AND [" + TEST_DATE + "] >=" + quotes(date_, false);
//            query += " AND [" + TEST_DATE + "] <=" + quotes(dateB, false);
//        } else {
//            if (date_ != null && date_.isEmpty() == false) {
//                query += " AND [" + TEST_DATE + "]=" + quotes(date_, false);
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
//        String quality = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxQuality);
//        String order = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxOrder);
//        String batch = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxBatch);
//        String testCode = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxTestCode);
//        String testName = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxTestName);
//        String lsl = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxLSL);
//        String usl = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxUSL);
//        String date_ = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxDateA);
//        String dateB = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxDateB);
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
//        if ((dateB != null && dateB.isEmpty() == false) && (date_ != null && date_.isEmpty() == false)) {
//            query += " AND [" + TEST_DATE + "] >=" + quotes(date_, false);
//            query += " AND [" + TEST_DATE + "] <=" + quotes(dateB, false);
//        } else {
//            if (date_ != null && date_.isEmpty() == false) {
//                query += " AND [" + TEST_DATE + "]=" + quotes(date_, false);
//            }
//        }
//
////        if (date_ != null && date_.isEmpty() == false && actualComboParam.equals(TEST_DATE) == false) {
////            query += " AND [" + TEST_DATE + "]=" + quotes(date_, false);
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
