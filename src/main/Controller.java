/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyGraphContainer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sql.SQL_Q;
import sql.ShowMessage;
import sql.Sql_B;

/**
 *
 * @author KOCMOC
 */
public class Controller {

    private Sql_B sql = new Sql_B(false, true);
    private XyGraph graph = new XyGraph("Test", MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
    private Histogram histogram = new Histogram("Histogram", MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
    private String PATH;
    private ShowMessage OUT;

    public Controller(String PATH,ShowMessage OUT) {
        this.PATH = PATH;
        this.OUT = OUT;
        connect();
    }

    private void connect() {
        try {
            sql.connect_mdb("", "", PATH);
            OUT.showMessage("Connected");
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
            ResultSet rs = sql.execute(q,OUT);
            graph.addData(rs, "value");
            rs.first();
            histogram.addData(rs,"value");
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
