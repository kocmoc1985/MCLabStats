/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author KOCMOC
 */
public class HelpA {
    
    
    
     public static String get_proper_date_time_same_format_on_all_computers() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }
}
