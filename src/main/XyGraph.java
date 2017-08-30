/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.DiffMarkerPoints;
import XYG_BASIC.MyGraphXY;
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

    private MySerie serieLimitL;
    private MySerie serieLimitU;

    public XyGraph(String title, int displayMode) {
        super(title, new MyGraphXY(), displayMode);
        Gui.GraphPanel.add(getGraph());
        init();
    }
    
     private void init(){
        addDiffMarkerOutPutComponent(DiffMarkerPoints.CALC_SUMM, Gui.jTextFieldSumm);
        addDiffMarkerOutPutComponent(DiffMarkerPoints.CALC_AVERAGE, Gui.jTextFieldAverage);
    }

    @Override
    public void initializeA() {
        //
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

        this.setDrawMarker(false);
        this.setMarkerDotted(true);
        this.setMarkerInfo(1);
        this.setMarkerAutoReset(false);
    }

    @Override
    public void initializeB() {
        //
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
        //
        serieLimitL = new MySerie("LSL", Color.red);
        serieLimitU = new MySerie("USL", Color.red);
        this.addSerie(serieLimitL);
        this.addSerie(serieLimitU);
        adjustLimitSeries();
    }

    private void adjustLimitSeries() {
        PointHighLighter.addSerie(serieLimitL);
        PointHighLighter.addSerie(serieLimitU);

        serieLimitL.setLineThickness(1);
        serieLimitL.setPointThickness(0.5);
        serieLimitL.setPointColor(Color.red);

        serieLimitU.setLineThickness(1);
        serieLimitU.setPointThickness(0.5);
        serieLimitU.setPointColor(Color.red);

//        serieLimitU.setDrawPoints(false);
//        serieLimitU.setLineThickness(1);
    }

    public void addData(ResultSet rs, String valueColName) {

        try {
            while (rs.next()) {
                double val = rs.getDouble(valueColName);
                double minLim = rs.getDouble("LSL");
                double maxLim = rs.getDouble("USL");
                addPointBySerie(minLim, "LSL");
                addPointBySerie(maxLim, "USL");
//                    setLimits(minLim, maxLim);

                MyPoint p = new MyPoint(((int) val), val);

//                p.addPointInfo("mode", mode);

                addPoint(p);

            }
        } catch (SQLException ex) {
            Logger.getLogger(MyXYGB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
