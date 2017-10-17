/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MyPoint;
import XYG_BASIC.PointHighLighter;
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
public class HistogramGraph extends PolygonGraph implements BasicGraphListener {

    private ArrayList<Step> stepList = new ArrayList<>();
    private String rounding;

    public HistogramGraph(String title, MyGraphXY_PG mgxyh, int displayMode) {
        super(title, mgxyh, displayMode);
    }

    @Override
    public void markersSet(MyGraphXY trigerInstance, MyPoint markerA, MyPoint markerB) {
        if (trigerInstance instanceof MyGraphXY_PG == false) {
            rebuildData(resultSet, valueColName, round, markerA.getPointIndex(), markerB.getPointIndex());
        }
    }

    @Override
    public void rebuildData(ResultSet rs, String valueColName, String round, int start, int end) {
        //
        deleteAllPointsFromSerie(serie);
        //
        //
        ArrayList<Double> list = new ArrayList<>();
        //
        int x = 0;
        //
        try {
            //
            rs.beforeFirst();
            //
            while (rs.next()) {
                if (x >= start && x <= end) {
                    double val = rs.getDouble(valueColName);
                    list.add(val);
                }
                x++;
            }

        } catch (SQLException ex) {
            Logger.getLogger(PolygonGraph.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        addDataH(list);
        //
    }

    @Override
    public void addData(ResultSet rs, String valueColName) {
        //
        this.resultSet = rs;
        this.valueColName = valueColName;
        //
        ArrayList<Double> list = new ArrayList<>();
        //
        try {
            //
            rs.beforeFirst();
            //
            double filterCoeff = HelpA.calc_Filter_Coeff(rs, valueColName);
            //
            rs.beforeFirst();
            //
            while (rs.next()) {
                //
                double val = rs.getDouble(valueColName);
                //
                if (val < (filterCoeff)) {
                    list.add(val);
                }
            }
            //
        } catch (SQLException ex) {
            Logger.getLogger(PolygonGraph.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        addDataH(list);
        //
    }

    private void addDataH(ArrayList<Double> list) {
        int steps = Integer.parseInt(Main.jTextFieldTest.getText());
        stepList = new ArrayList<>();
        //
        Collections.sort(list);
        double min = list.get(0);
        double step = calcStep(list, min, steps);
        stepList = defineSteps(min, step, steps, list);
        //
        addPoints();
        //
    }

    @Override
    public void addPoints() {
        //
        boolean diffMarkerPointsDeleteFlag = true;
        //
        xValuesList = new ArrayList<>();
        //
        myGraphXY.deleteAllPointsFromSerie(serie);
        //
        for (Step step : stepList) {
            //
            xValuesList.add("" + step.limLow + " -> " + step.limHigh);
            //
            MyPoint_HG p = new MyPoint_HG(step.ammount, step.ammount, step.limLow, step.limHigh);
            p.setDisplayValueX(step.limLow);
            this.addPointWithDiffMarkerPointsDelete(p, diffMarkerPointsDeleteFlag);
            //
            diffMarkerPointsDeleteFlag = false;
            //
        }
        //
        MyGraphXY_PG h = (MyGraphXY_PG) myGraphXY;
        h.setXValues(xValuesList);
        //
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
        System.out.println("step: " + step);
        //
        double x = defineExtra(min);
        //
        System.out.println("addCoeff: " + x);
        //
        return step + x;
    }

    private double defineExtra(double min) {
        if (min < 0.01) {
            rounding = "%2.4f";
            return 0.0015;
        } else if (min < 1) {
            rounding = "%2.3f";
            return 0.002;
        } else if (min < 5) {
            rounding = "%2.2f";
            return 0.01;
        } else {
            rounding = "%2.2f";
            return 0.1;
        }
    }

    private ArrayList<Step> defineSteps(double min, double step, int steps, ArrayList<Double> list) {
        ArrayList<Step> lst = new ArrayList<>();
        Step step_;
        double min_ = 0;
        for (int i = 0; i < steps; i++) {
            if (i == 0) {
                min_ = HelpA.roundDouble_(min + step, rounding);
                step_ = new Step(min, min_, list);
            } else {
                double lh = HelpA.roundDouble_(min_ + step, rounding);
                step_ = new Step(min_, lh, list);
                min_ = HelpA.roundDouble_(min_ + step, rounding);
            }
            lst.add(step_);
        }
        return lst;
    }

    class Step {

        ArrayList<Double> list;
        double limLow;
        double limHigh;
        double ammount;

        public Step(double limLow, double limHigh, ArrayList<Double> list) {
            this.limLow = limLow;
            this.limHigh = HelpA.roundDouble_(limHigh, rounding);
            this.list = list;
            count();
        }

        private void count() {
            for (Double val : list) {
                //
                if (val >= limLow && val < limHigh) {
                    ammount++;
                }
            }
        }
    }

    @Override
    public void initializeB() {
        serie = new MySerie_M(getTitle());
        //
        serie.setDrawPoints(true);
        serie.setPointThickness(1);
//        serie.setPointHighLightColor(Color.red);
//        serie.setPointColor(Color.red);

        serie.setDrawLines(false);
        serie.setLineThickness(1);
        serie.setLineDotted();
        serie.setCurveColor(Color.red);
        serie.setOverallScale(true);
        //
        this.addSerie(serie, false, this);
        //
        PointHighLighter.addSerieSingle(serie);
    }
}
