/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.PointHighLighter;
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
public class GistoGraphM extends GistoGraph implements GG {

    ArrayList<Step> stepList = new ArrayList<>();

    public GistoGraphM(String title, MyGraphXY_H mgxyh, int displayMode) {
        super(title, mgxyh, displayMode);
    }

    @Override
    public void addData(ResultSet rs, String valueColName, String round) {
        ArrayList<Double> list = new ArrayList<>();
        //
        int steps = Integer.parseInt(Gui.jTextFieldTest.getText());
        //
        stepList = new ArrayList<>();
        //
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
        myGraphXY.deleteAllPointsFromSerie(serie);
        //
        for (Step step : stepList) {
            //
            xValuesList.add("" + step.limLow + " -> " + step.limHigh);
            //
            MyPoint_H_M p = new MyPoint_H_M((int) step.ammount, step.ammount, step.limLow, step.limHigh);
            p.setDisplayValueX(step.limLow);
            this.addPointWithDiffMarkerPointsDelete(p, diffMarkerPointsDeleteFlag,"GistoGrapM -> addPoints()");
            //
            diffMarkerPointsDeleteFlag = false;
            //
        }
        //
        MyGraphXY_H h = (MyGraphXY_H) myGraphXY;
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
        return step + 0.01;
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
                if (val >= limLow && val < limHigh) {
                    ammount++;
                }
            }
        }
    }

    @Override
    public void initializeB() {
        serie = new MySerieH(getTitle());
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
        this.addSerie(serie);
        //
        PointHighLighter.addSerieSingle(serie);
    }

}
