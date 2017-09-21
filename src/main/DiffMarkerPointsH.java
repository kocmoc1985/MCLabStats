/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.DiffMarkerAction;
import XYG_BASIC.DiffMarkerPoints;
import static XYG_BASIC.DiffMarkerPoints.CALC_AVERAGE;
import static XYG_BASIC.DiffMarkerPoints.CALC_SUMM;
import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MyPoint;
import XYG_BASIC.MySerie;
import java.util.ArrayList;

/**
 *
 * @author KOCMOC
 */
public class DiffMarkerPointsH extends DiffMarkerPoints {

    public static final String CALC_MEDIAN = "MEDIAN";

    public DiffMarkerPointsH(MySerie serie, MyGraphXY graphXY) {
        super(serie, graphXY);
    }

    @Override
    public void go() {
        addProperties();
        if (outPutMap.size() > 0) {
            calcAndShow(CALC_SUMM);
            calcAndShow(CALC_AVERAGE);
            calcAndShow(CALC_MEDIAN);
        }

        for (DiffMarkerAction diffMarkerAction : diffMarkerActionListeners) {
            diffMarkerAction.markersSet(myGraphXY, MARKER_POINT_A, MARKER_POINT_B);
        }
    }


    @Override
    public void calcAndShow(String name) {
        if (name.equals(CALC_SUMM)) {
            showOutPut(name, calcSum());
        } else if (name.equals(CALC_AVERAGE)) {
            showOutPut(name, calcAv());
        } else if (name.equals(CALC_MEDIAN)) {
            showOutPut(name, calcMedian());
        } else {
            System.out.println("NO SUCH CALC EXIST: " + name);
        }
    }

    public double calcMedian() {
        ArrayList<Double> list = new ArrayList<>();
        if (bothExist()) {
            for (int i = MARKER_POINT_A.getPointIndex(); i <= MARKER_POINT_B.getPointIndex(); i++) {
                list.add(serie.getSerie().get(i).y_Display);
            }
        }
        //
        return medianCalc(list);
    }

    private double medianCalc(ArrayList<Double> list) {
        Double[] arr = new Double[list.size()];
        list.toArray(arr);
        double median;
        //
        if (arr.length % 2 == 0) {
            median = ((double) arr[arr.length / 2] + (double) arr[arr.length / 2 - 1]) / 2;
        } else {
            median = (double) arr[arr.length / 2];
        }

        return median;
    }

    
}
