/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MyPoint;
import XYG_BASIC.MySerie;
import XYG_BASIC.MyXYGB;
import XYG_BASIC.PointDeletedAction;
import XYG_BASIC.PointHighLighter;
import XYG_HISTO.MyPointH;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class XyGraph extends MyXYGB implements PointDeletedAction {

    private MySerie serieLimitL;
    private MySerie serieLimitU;
    private GG gistoGraph;

    public XyGraph(String title, int displayMode) {
        super(title, new MyGraphXY(), displayMode);
        init();
    }

    public void setGistoGraph(GG gg) {
        this.gistoGraph = gg;
        // THIS triggers event which is processed in gg class
        this.addDiffMarkersSetListener(gg);
    }

    @Override
    public void pointDeleted(MyPoint mp) {
        gistoGraph.rebuildData(serie.getPoints(), gistoGraph.getRound());
        this.rebuildData();
    }

    private void init() {
        serie.addPointDeletedActionListener(this);
        addDiffMarkerOutPutComponent(DiffMarkerPointsH.CALC_SUMM, Gui.jTextFieldSumm);
        addDiffMarkerOutPutComponent(DiffMarkerPointsH.CALC_AVERAGE, Gui.jTextFieldAverage);
        addDiffMarkerOutPutComponent(DiffMarkerPointsH.CALC_MEDIAN, Gui.jTextFieldMedian);
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
        serie = new MySerieH(getTitle());
        //
        serie.setDrawPoints(true);
        serie.setPointThickness(1);
//        serie.setPointHighLightColor(Color.red);
//        serie.setPointColor(Color.red);

        serie.setDrawLines(true);
        serie.setLineThickness(1);
        serie.setLineDotted();
        serie.setCurveColor(Color.BLUE);
        serie.setOverallScale(true);
        //
        this.addSerie(serie,true,this);
        //
        PointHighLighter.addSerie(serie);
        //
        serieLimitL = new MySerie("LSL", Color.red);
        serieLimitU = new MySerie("USL", Color.red);
        this.addSerie(serieLimitL,false,this);
        this.addSerie(serieLimitU,false,this);
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

        boolean diffMarkerPointsDeleteFlag = true;

        try {
            double sum_all_values = 0;
            double average;
            int devide_with = 0;
            //
            rs.first();
            //
            while (rs.next()) {
                //
                double act_value = parseDouble(rs.getString(valueColName));
                //
                if (act_value > 0) {
                    sum_all_values += act_value;
                    devide_with++;
                }
            }
            //
            average = sum_all_values / devide_with;
            //
            rs.first();
            //
            int filtered = 0;
            //
            while (rs.next()) {
                //
                double val = processValue(rs.getString(valueColName));
                //
                double lsl = rs.getDouble("LSL");
                MyPoint LSL = new MyPoint((int) lsl, lsl);
                //
                double usl = rs.getDouble("USL");
                MyPoint USL = new MyPoint((int) usl, usl);
                //
                addPointBySerie(LSL, serieLimitL);
                addPointBySerie(USL, serieLimitU);
                //
//                    setLimits(minLim, maxLim);
                //
                MyPointH p;
                //
//                if (val > (average * 3)) {
//                    p = new MyPoint(((int) (average)), (average));
//                    p.setPointColor(Color.red);
//                    p.setPointDimenssion(16);
//                } else {
                p = new MyPointH(val, val, LSL, USL);
//                }
                //
                p.addPointInfo("Serie", rs.getString("Name"));
                p.addPointInfo("Quality", rs.getString("Quality"));
                p.addPointInfo("Order", rs.getString("order"));
                p.addPointInfo("Batch", rs.getString("BatchNo"));
                p.addPointInfo("Status", rs.getString("Status"));
                //
                if (val < (average * 2)) {
//                    addPoint(p);
                    addPointWithDiffMarkerPointsDelete(p, diffMarkerPointsDeleteFlag);
                    diffMarkerPointsDeleteFlag = false;
                } else {
                    filtered++;
                }
                //
            }
            //
            System.out.println("Filtered batches: " + filtered);
            //
        } catch (SQLException ex) {
            Logger.getLogger(XyGraph.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void rebuildData() {
        //
        ArrayList<MyPointH> newList = new ArrayList<>();
        //
        for (MyPoint p : serie.getPoints()) {
            MyPointH pp = (MyPointH) p;
            MyPointH mph = new MyPointH(pp.y_Real, pp.y_Scaled, pp.getLSL(), pp.getUSL());
            
            newList.add(mph);
        }
        //
        deleteAllPointsFromAllSeries();
        //
        boolean diffMarkerPointsDeleteFlag = true;
        //
        for (MyPoint p : newList) {
            MyPointH phm = (MyPointH) p;
            addPointBySerie(phm.getLSL(), serieLimitL);
            addPointBySerie(phm.getUSL(), serieLimitU);
            addPointWithDiffMarkerPointsDelete(phm, diffMarkerPointsDeleteFlag);
            diffMarkerPointsDeleteFlag = false;
        }
    }

    private double processValue(String value) {
        try {
            double x = Double.parseDouble(value);
            return x;
        } catch (Exception ex) {
            return 0;
        }
    }

    private double parseDouble(String str) {
        if (str == null || str.equals("null")) {
            return 0;
        }

        try {
            double x = Double.parseDouble(str);
            return x;
        } catch (Exception ex) {
            return 0;
        }
    }
}
