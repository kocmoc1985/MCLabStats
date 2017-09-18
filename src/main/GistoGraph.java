/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MyPoint;
import XYG_BASIC.PointDeletedAction;
import XYG_BASIC.PointHighLighter;
import XYG_HISTO.HistograMM;
import XYG_HISTO.MyGraphXY_H;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import other.HelpA;

/**
 *
 * @author KOCMOC
 */
public class GistoGraph extends HistograMM {

    public GistoGraph(String title, MyGraphXY_H xY_H, int displayMode) {
        super(title, xY_H, displayMode);

    }

    @Override
    public void markersSet(MyGraphXY trigerInstance, MyPoint markerA, MyPoint markerB) {
        if (trigerInstance instanceof MyGraphXY_H == false) {
            rebuildData(resultSet, valueColName, round, markerA.getPointIndex(), markerB.getPointIndex());
        }
    }

    @Override
    public void addData(ResultSet rs, String valueColName, String round) {
        ArrayList<Double> list = new ArrayList<>();
        int steps = 4;
        try {
            while (rs.next()) {
                double val = rs.getDouble(valueColName);
                list.add(val);
            }
            //
            Collections.sort(list);
            double min = list.get(0);
            double step = calcStep(list, min, steps);
            defineSteps(min, step, steps, list);
            //
        } catch (SQLException ex) {
            Logger.getLogger(GistoGraph.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private double calcStep(ArrayList<Double> list, double min, int steps) {
        double sum = 0;
        //
        for (Double val : list) {
            sum += val;
        }
        //
        double av = sum / list.size();
        //
        double step = (av - min) / steps;
        //
        return step;
    }

    private ArrayList<Step> defineSteps(double min, double step, int steps, ArrayList<Double> list) {
        ArrayList<Step> stepList = new ArrayList<>();
        Step step_;
        double min_ = 0;
        for (int i = 0; i < steps; i++) {
            if (i == 0) {
                min_ = HelpA.roundDouble_(min + step, "%2.2f");
                step_ = new Step(min, min_, list);
            } else {
                double lh = HelpA.roundDouble_(min_ + step, "%2.2f");
                step_ = new Step(min_, lh, list);
                min_ = HelpA.roundDouble_(min_ + step, "%2.2f");
            }
            stepList.add(step_);
        }
        return stepList;
    }

    class Step {

        ArrayList<Double> list;
        double limLow;
        double limHigh;
        double ammount;

        public Step(double limLow, double limHigh, ArrayList<Double> list) {
            this.limLow = limLow;
            this.limHigh = HelpA.roundDouble_(limHigh, "%2.2f");
            this.list = list;
            count();
        }

        private void count() {
            for (Double val : list) {
                //
                if (val >= limLow && val <= limHigh) {
                    ammount++;
                }
            }
            System.out.println("ammount step1: " + ammount);
            System.out.println("");
        }

        
    }

    /**
     * @deprecated @param rs
     */
    @Override
    public void addLimits(ResultSet rs) {
        super.addLimits(rs); //To change body of generated methods, choose Tools | Templates.
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
        this.setShowPopUpLeftClick(true);
        this.setPointHighLighterEnabled(true);
        this.setDisableScalingWhenGrid();
        this.setGridColor(Color.black);
//        this.setScaleXYaxisLength(1.2);
        //
//        this.setBackgroundColorOfGraph(Color.BLACK);
        this.setDrawMarker(false);
        this.setMarkerDotted(true);
        this.setMarkerInfo(1);
        this.setMarkerAutoReset(false);
    }

    @Override
    public void initializeB() {
        serie = new MySerieM(getTitle());
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
        PointHighLighter.addSerieSingle(serie);
    }
}
