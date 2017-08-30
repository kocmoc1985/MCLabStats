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
        return "SELECT  * FROM resultsN\n"
                + "WHERE resultsN.Quality='0000483' AND resultsN.Name='ML'\n"
                + "ORDER BY resultsN.order, resultsN.BatchNo, resultsN.TestNo";
    }
    
//    public static String test_a() {
//        return "SELECT  * FROM resultsN\n"
//                + "WHERE resultsN.Quality='0004720-D' AND resultsN.TestCode='10191' AND resultsN.Name='ML'\n"
//                + "ORDER BY resultsN.order, resultsN.BatchNo, resultsN.TestNo";
//    }

    
    
    public static String fill_quality_chk_box(){
         return "SELECT distinct Quality from REsultsN";
    }
    
    public static void main(String[] args) {
        System.out.println(fill_quality_chk_box());
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
