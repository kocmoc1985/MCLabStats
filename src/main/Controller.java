/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyGraphContainer;
import XYG_MC.MCStatsCommonG;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sql.SQL_Q;
import sql.Sql_B;

/**
 *
 * @author KOCMOC
 */
public class Controller {

    private Sql_B sql = new Sql_B(false, true);
    private XyGraph graph = new XyGraph("Test", MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
    private String PATH;

    public Controller(String PATH) {
        this.PATH = PATH;
        connect();
    }

    private void connect() {
        try {
            sql.connect_mdb("", "", PATH);
            System.out.println("Connected");
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void testBuild() {
        //
        String q = SQL_Q.test_a();
        //
        try {
            ResultSet rs = sql.execute(q);
            graph.addData(rs, "value", "ModeColName");
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
