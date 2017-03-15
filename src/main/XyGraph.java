/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyXYGB;
import XYG_MC.Point;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class XyGraph extends MyXYGB {

    public XyGraph(String title) {
        super(title);
    }

    public XyGraph(String title, int displayMode) {
        super(title, displayMode);
        System.out.println("GRAPH: " + getGraph());
        Gui.GraphPanel.add(getGraph());

    }

    public void addData(ResultSet rs, String valueColName, String modeColName) {
        try {
            while (rs.next()) {
                double val = rs.getDouble(valueColName);
//                String mode = rs.getString(modeColName);

                val = val * 1000;

                Point p = new Point(((int) val), "" + val);

//                if (mode.equals("1")) {
//                    p.setPointColor(Color.red);
//                } else {
//                    p.setPointColor(Color.blue);
//                }

//                p.addPointInfo("mode", mode);

                addPoint(p);

            }
        } catch (SQLException ex) {
            Logger.getLogger(MyXYGB.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
}
