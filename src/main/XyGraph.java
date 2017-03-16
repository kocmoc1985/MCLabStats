/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyPoint;
import XYG_BASIC.MySerie;
import XYG_BASIC.MyXYGB;
import XYG_BASIC.PointHighLighter;
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

    @Override
    public void initializeA() {
        this.setTitleSize(20, true);
        this.setTitleSize(20, true);
        this.setTitleColor(Color.black);
//        this.setBorderHeadAndFootComponents(BorderFactory.createLineBorder(Color.darkGray));
        this.setHeadHeight(0.1);
        //
        // setAxisScaling(...) & setDrawGrid(...) influence each other!
        this.setAxisScaling(true, true);
//        this.setDrawGrid(true);
//        this.setDisableScalingWhenGrid();
        this.setGridColor(Color.black);
        this.setScaleXYaxisLength(1.2);
        //
//        this.setBackgroundColorOfGraph(Color.BLACK);
        this.setDrawMarker(true);
        this.setMarkerDotted(true);
        this.setMarkerInfo(1);
        this.setMarkerAutoReset(false);
    }

    @Override
    public void initializeB() {
        serie = new MySerie(getTitle());
        //
        serie.setDrawPoints(true);
        serie.setPointThickness(1);
//        serie.setPointHighLightColor(Color.red);
//        serie.setPointColor(Color.red);

        serie.setDrawLines(true);
        serie.setLineThickness(1);
        serie.setLineDotted();
        serie.setCurveColor(Color.red);
        serie.setOverallScale(true);
        //
        this.addSerie(serie);
        //
        PointHighLighter.addSerie(serie);
    }
    private boolean LIMITS_SET = false;

    public void addData(ResultSet rs, String valueColName, String modeColName) {
        LIMITS_SET = false;
        try {
            while (rs.next()) {
                double val = rs.getDouble(valueColName);
                if (LIMITS_SET == false) {
                    LIMITS_SET = true;
                    double minLim = rs.getDouble("LSL");
                    double maxLim = rs.getDouble("USL");
                    setLimits(minLim, maxLim);
                }

                MyPoint p = new MyPoint(((int) val), val);

//                p.addPointInfo("mode", mode);

                addPoint(p);

            }
        } catch (SQLException ex) {
            Logger.getLogger(MyXYGB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
