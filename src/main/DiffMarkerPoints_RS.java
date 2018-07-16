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

    public static final String CALC_STD_DEV = "STDEV";
    public static final String CALC_MEDIAN = "MEDIAN";
    public static final String CALC_CP = "CP";
    public static final String CALC_CPU = "CPU";
    public static final String CALC_CPL = "CPL";
    public static final String CALC_CPK = "CPK";
    public static final String CALC_SKEW = "SKEW";
    public static final String OUT_PUT_FORMAT = "%2.2f";
    public static ArrayList<String> CALC_LIST = new ArrayList<>();

    static {
        CALC_LIST.add(CALC_STD_DEV);
        CALC_LIST.add(CALC_AVERAGE);
        CALC_LIST.add(CALC_MEDIAN);
        CALC_LIST.add(CALC_CP);
        CALC_LIST.add(CALC_CPU);
        CALC_LIST.add(CALC_CPL);
        CALC_LIST.add(CALC_CPK);
        CALC_LIST.add(CALC_SKEW);
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
            calcAndShow(CALC_STD_DEV);
            calcAndShow(CALC_AVERAGE);
            calcAndShow(CALC_MEDIAN);
            calcAndShow(CALC_CP);
            calcAndShow(CALC_CPU);
            calcAndShow(CALC_CPL);
            calcAndShow(CALC_CPK);
            calcAndShow(CALC_SKEW);
        }
        //
        for (DiffMarkerAction diffMarkerAction : diffMarkerActionListeners) {
            diffMarkerAction.markersSet(myGraphXY, MARKER_POINT_A, MARKER_POINT_B);
        }
    }

    @Override
    public void calcAndShow(String name) {
        //
        ArrayList<Double> list = getList();
        //
        if (name.equals(CALC_STD_DEV)) {
            showOutPut(name, calcStandardDeviation(list), DEFAULT_OUT_PUT_FORMAT);
        } else if (name.equals(CALC_AVERAGE)) {
            showOutPut(name, calcAv(), DEFAULT_OUT_PUT_FORMAT);
        } else if (name.equals(CALC_MEDIAN)) {
            showOutPut(name, calcMedian(list), DEFAULT_OUT_PUT_FORMAT);
        } else if (name.equals(CALC_CP)) {
            showOutPut(name, calcCP(list), DEFAULT_OUT_PUT_FORMAT);
        } else if (name.equals(CALC_CPU)) {
            showOutPut(name, calcCPU(list), DEFAULT_OUT_PUT_FORMAT);
        } else if (name.equals(CALC_CPL)) {
            showOutPut(name, calcCPL(list), DEFAULT_OUT_PUT_FORMAT);
        } else if (name.equals(CALC_CPK)) {
            showOutPut(name, calcCPK(list), DEFAULT_OUT_PUT_FORMAT);
        } else if (name.equals(CALC_SKEW)) {
            showOutPut(name, calcSkew(list), DEFAULT_OUT_PUT_FORMAT);
        } else {
            System.out.println("NO SUCH CALC EXIST: " + name);
        }
    }

    public ArrayList<Double> getList() {
        ArrayList<Double> list = new ArrayList<>();
        if (bothExist()) {
            for (int i = MARKER_POINT_A.getPointIndex(); i <= MARKER_POINT_B.getPointIndex(); i++) {
                list.add(serie.getSerie().get(i).y_Display);
            }
        }
        //
        return list;
    }

    public double calcCP(ArrayList<Double> list) {
        //
        MyPoint_M point_a = (MyPoint_M) MARKER_POINT_A;
        MyPoint_M point_b = (MyPoint_M) MARKER_POINT_B;
        //
        double usl = point_a.getUSL().y_Display;
        double lsl = point_b.getLSL().y_Display;
        //
        double sixStdDev = calcStandardDeviation(list) * 6;
        //
        return (usl - lsl) * sixStdDev;
    }

    public double calcCPU(ArrayList<Double> list) {
        //
        MyPoint_M point_a = (MyPoint_M) MARKER_POINT_A;
        //
        double usl = point_a.getUSL().y_Display;
        //
        double mean = calcMean(list);
        //
        double threeStdDev = calcStandardDeviation(list) * 3;
        //
        return (usl - mean) / threeStdDev;
    }

    public double calcCPL(ArrayList<Double> list) {
        //
        MyPoint_M point_b = (MyPoint_M) MARKER_POINT_B;
        //
        double lsl = point_b.getLSL().y_Display;
        //
        double mean = calcMean(list);
        //
        double threeStdDev = calcStandardDeviation(list) * 3;
        //
        return (mean - lsl) / threeStdDev;
    }

    /**
     * Skew = SKp
     *
     * @param list
     * @return
     */
    public double calcSkew(ArrayList<Double> list) {
        return 3 * (calcMean(list) - calcMedian(list)) / calcStandardDeviation(list);
    }

    public double calcCPK(ArrayList<Double> list) {
        //
        double cpu = calcCPU(list);
        //
        double cpl = calcCPL(list);
        //
        return Math.min(cpu, cpl);
    }

    public double calcMedian(ArrayList<Double> list) {
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

    //==========================================================================
    public double calcStandardDeviation(ArrayList<Double> list) {
        return Math.sqrt(calcVariance(list));
    }

    public double calcVariance(ArrayList<Double> list) {
        //
        double mean = calcMean(list);
        double temp = 0;
        //
        for (Double val : list) {
            temp += (val - mean) * (val - mean);
        }
        //
        return temp / (list.size() - 1);
    }

    /**
     * Mean = Average
     *
     * @param list
     * @return
     */
    public double calcMean(ArrayList<Double> list) {
        //
        double sum = 0.0;
        //
        for (Double val : list) {
            sum += val;
        }
        //
        return sum / list.size();
    }

}
