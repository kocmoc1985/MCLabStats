/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.DiffMarkerAction;
import XYG_BASIC.HelpA;
import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MyPoint;
import XYG_BASIC.MyXYGB;
import XYG_HISTO.MyGraphXY_H;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class HistograM extends MyXYGB implements DiffMarkerAction {

    public TreeMap<Double, Integer> histoMap = new TreeMap();
    public ArrayList<String> xValuesList;
    public ResultSet resultSet;
    public String valueColName;
    public String round;

    public HistograM(String title, MyGraphXY_H xY_H, int displayMode) {
        super(title, xY_H, displayMode);
    }

    @Override
    public void markersSet(MyGraphXY trigerInstance,MyPoint markerA, MyPoint markerB) {
        rebuildData(resultSet, valueColName, round, markerA.getPointIndex(), markerB.getPointIndex());
    }

    @Override
    public void markersUnset(MyGraphXY trigerInstance) {
    }
    
    
    
    public void reset() {
        deleteAllPointsFromAllSeries();
        histoMap = new TreeMap<Double, Integer>();
    }

    public void refresh() {
        getGraph().repaint();
    }

    public void setStepIdentifierX(int x) {
        MyGraphXY_H h = (MyGraphXY_H) myGraphXY;
        h.setStepIdentifierX(x);
    }

    /**
     * Good to keep as example though
     * @deprecated 
     * @param rs 
     */
    public void addLimits(ResultSet rs) {
        try {
            double minLim = rs.getDouble("LSL");
            double maxLim = rs.getDouble("USL");
            setLimits(minLim, maxLim);
        } catch (SQLException ex) {
            Logger.getLogger(HistograM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addData(ResultSet rs, String valueColName, String round) {
        //
        this.resultSet = rs;
        this.valueColName = valueColName;
        this.round = round;
        //
        reset();
        refresh();
        //
        try {
            while (rs.next()) {
                double val = rs.getDouble(valueColName);
                buildHistogramDataSet(val, histoMap, round);
            }
            //
            addPoints();
            //
        } catch (SQLException ex) {
            Logger.getLogger(HistograM.class.getName()).log(Level.SEVERE, null, ex);
            addPoints();
        }
    }
    
    public void addData(double[] values, String round) {
        for (double val : values) {
            buildHistogramDataSet(val, histoMap, round);
        }

        addPoints();
    }

    public void addData(int[] values, String round) {
        for (int val : values) {
            buildHistogramDataSet(val, histoMap, round);
        }
        addPoints();
    }

    public void rebuildData(ResultSet rs, String valueColName, String round, int start, int end) {
        //
        System.out.println("rebuildData(...)");
        deleteAllPointsFromSerie(serie);
        histoMap.clear();
        //
        int x = 0;
        try {
            //
            rs.first();
            //
            while (rs.next()) {
                if (x >= start && x <= end) {
                    double val = rs.getDouble(valueColName);
                    buildHistogramDataSet(val, histoMap, round);
                }
                x++;
            }
            //
            addPoints();
            //
        } catch (SQLException ex) {
            Logger.getLogger(HistograM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void rebuildData(ArrayList<MyPoint> points,String round) {
        //
//        getSerie().deleteAllPoints();
        deleteAllPointsFromSerie(serie);
        histoMap.clear();
        //
        for (MyPoint myPoint : points) {
             buildHistogramDataSet(myPoint.y_Display, histoMap, round);
        }
        //
        addPoints();
    }
    
    

    public void addPoints() {
        //
        boolean diffMarkerPointsDeleteFlag = true;
        //
        Set set = histoMap.keySet();
        Iterator it = set.iterator();
        xValuesList = new ArrayList<String>();
        myGraphXY.X_MAX = 0;
        //
        while (it.hasNext()) {
            double key = (Double) it.next();
            int value = histoMap.get(key);
            xValuesList.add(""+key);
            MyPoint p = new MyPoint(value, value);
            p.setDisplayValueX(key);
            this.addPointWithDiffMarkerPointsDelete(p, diffMarkerPointsDeleteFlag);
//            this.addPointBySerie(p, serie);
            //
            diffMarkerPointsDeleteFlag = false;
            //
        }
        //
        MyGraphXY_H h = (MyGraphXY_H) myGraphXY;
        h.setXValues(xValuesList);
        //
    }

    public void buildHistogramDataSet(double key, TreeMap map, String round) {
        if (round != null) {
            key = HelpA.roundDouble(key, round);
        }
        if (map.containsKey(key)) {
            int val = (Integer) map.get(key);
            val++;
            updateMap(key, val, map);
        } else {
            map.put(key, 1);
        }
    }

    private void updateMap(Object key, Object value, TreeMap map) {
        map.remove(key);
        map.put(key, value);
    }
}
