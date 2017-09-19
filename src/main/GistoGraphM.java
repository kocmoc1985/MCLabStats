/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyPoint;
import XYG_HISTO.MyGraphXY_H;
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
public class GistoGraphM extends GistoGraph implements GG{

    ArrayList<Step> stepList = new ArrayList<>();

    public GistoGraphM(String title, MyGraphXY_H xY_H, int displayMode) {
        super(title, xY_H, displayMode);
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
            stepList = defineSteps(min, step, steps, list);
            //
            addPoints();
            //
        } catch (SQLException ex) {
            Logger.getLogger(GistoGraph.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addPoints() {
        //
        boolean diffMarkerPointsDeleteFlag = true;
        //
        xValuesList = new ArrayList<>();
        //
        for (Step step : stepList) {
            //
            xValuesList.add("" + step.limLow + " -> " + step.limHigh);
            //
            MyPoint p = new MyPoint((int) step.ammount, step.ammount);
            p.setDisplayValueX(step.limLow);
            this.addPointWithDiffMarkerPointsDelete(p, diffMarkerPointsDeleteFlag);
            //
            diffMarkerPointsDeleteFlag = false;
            //
        }
        //
        MyGraphXY_H h = (MyGraphXY_H) my_xy_graph;
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
        return step;
    }

    private ArrayList<Step> defineSteps(double min, double step, int steps, ArrayList<Double> list) {
        ArrayList<Step> lst = new ArrayList<>();
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
        }
    }
}
