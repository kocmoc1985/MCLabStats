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

    public static String test_a() {
        return "SELECT top 100 * FROM resultsN\n"
                + "WHERE resultsN.Quality='0004720-D' AND resultsN.TestCode='10191' AND resultsN.Name='ML'\n"
                + "ORDER BY resultsN.order, resultsN.BatchNo, resultsN.TestNo";
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
