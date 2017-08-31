/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

/**
 *
 * @author KOCMOC
 */
public class SQL_Q {

    public static final String PRIM_TABLE = "REsultsN";
    public static final String QUALITY = "Quality";
    public static final String ORDER = "order";

    public static String test_a(String quality) {
        return "SELECT  * FROM resultsN\n"
                + "WHERE resultsN.Quality=" + quotes(quality, false)
                + " AND resultsN.Name='ML'\n"
                + "ORDER BY resultsN.order, resultsN.BatchNo, resultsN.TestNo";
    }

//    public static String test_a() {
//        return "SELECT  * FROM resultsN\n"
//                + "WHERE resultsN.Quality='0004720-D' AND resultsN.TestCode='10191' AND resultsN.Name='ML'\n"
//                + "ORDER BY resultsN.order, resultsN.BatchNo, resultsN.TestNo";
//    }
    public static String fill_quality_combo_box() {
        return "SELECT distinct " + QUALITY + "  from " + PRIM_TABLE;
    }

    public static String fill_order_combo_box(String quality) {
        return "SELECT distinct " + ORDER + " from " + PRIM_TABLE
                + " WHERE " + QUALITY + "=" + quotes(quality, false);
    }

    public static String fillUniversal(String actualComboParam, String quality, String order) {
        String query = "SELECT distinct " + actualComboParam + " from " + PRIM_TABLE;
        //
        if (quality != null && quality.isEmpty() == false && actualComboParam.equals(QUALITY) == false) {
            query += " WHERE " + QUALITY + "=" + quotes(quality, false);
        } else if (order != null && order.isEmpty() == false && actualComboParam.equals(ORDER) == false) {
            query += "AND " + ORDER + "=" + quotes(order, false);
        }
        //
        System.out.println("query: " + query);
        return query;
    }

    public static void main(String[] args) {
        System.out.println(fill_quality_combo_box());
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
}
