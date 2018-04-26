/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.DiffMarkerAction;
import XYG_BASIC.DiffMarkerPoints;
import static XYG_BASIC.DiffMarkerPoints.CALC_AVERAGE;
import static XYG_BASIC.DiffMarkerPoints.CALC_SUMM;
import static XYG_BASIC.DiffMarkerPoints.DEFAULT_OUT_PUT_FORMAT;
import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MyPoint;
import XYG_BASIC.MySerie;
import java.util.ArrayList;
import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public class DiffMarkerPoints_RS extends DiffMarkerPoints {

    public static final String CALC_MEDIAN = "MEDIAN";
    public static final String OUT_PUT_FORMAT = "%2.2f";
    public static ArrayList<String> CALC_LIST = new ArrayList<>();

    static {
        CALC_LIST.add(CALC_SUMM);
        CALC_LIST.add(CALC_AVERAGE);
        CALC_LIST.add(CALC_MEDIAN);
    }

    public DiffMarkerPoints_RS(MySerie serie, MyGraphXY graphXY) {
        super(serie, graphXY);
    }

    

    @Override
    public void markersUnset() {
        //
        super.markersUnset();
        //
        for (String str : CALC_LIST) {
            JTextField jtf = outPutMap.get(str);
            //
            if (jtf != null) {
                jtf.setText("");
            }
            //
        }
        //
    }

    @Override
    public void go() {
        //
        addProperties();
        //
        if (outPutMap.size() > 0) {
            calcAndShow(CALC_SUMM);
            calcAndShow(CALC_AVERAGE);
            calcAndShow(CALC_MEDIAN);
        }
        //
        for (DiffMarkerAction diffMarkerAction : diffMarkerActionListeners) {
            diffMarkerAction.markersSet(myGraphXY, MARKER_POINT_A, MARKER_POINT_B);
        }
    }

    @Override
    public void calcAndShow(String name) {
        if (name.equals(CALC_SUMM)) {
            showOutPut(name, calcSum(), DEFAULT_OUT_PUT_FORMAT);
        } else if (name.equals(CALC_AVERAGE)) {
            showOutPut(name, calcAv(), DEFAULT_OUT_PUT_FORMAT);
        } else if (name.equals(CALC_MEDIAN)) {
            showOutPut(name, calcMedian(), DEFAULT_OUT_PUT_FORMAT);
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