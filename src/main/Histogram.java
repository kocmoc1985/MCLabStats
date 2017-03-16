/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyPoint;
import XYG_BASIC.MySerie;
import XYG_BASIC.MyXYGB;
import XYG_BASIC.PointHighLighter;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class Histogram extends MyXYGB {

    private TreeMap<Double, Integer> histoMap = new TreeMap<>();

    public Histogram(String title, int displayMode) {
        super(title, displayMode);
        Gui.HistoPanel.add(getGraph());
        this.setGraphTypeHistogram();// OBS! OBS! OBS!
    }

    @Override
    public void initializeB() {
        serie = new MySerie(getTitle());
        //
        serie.setDrawPoints(true);
        serie.setPointThickness(1);
//        serie.setPointHighLightColor(Color.red);
//        serie.setPointColor(Color.red);

        serie.setDrawLines(true);
        serie.setLineThickness(1);
//        serie.setLineDotted();
        serie.setCurveColor(Color.red);
        serie.setOverallScale(true);
        //
        this.addSerie(serie);
        //
        PointHighLighter.addSerie(serie);
    }

    public void addData(ResultSet rs, String valueColName) {
        try {
            while (rs.next()) {
                double val = rs.getDouble(valueColName);
                add(val, histoMap);
            }

            addPoints();

        } catch (SQLException ex) {
            Logger.getLogger(Histogram.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addPoints() {
        Set set = histoMap.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            double key = (Double) it.next();
            int value = histoMap.get(key);
            System.out.println("key: " + key + " /  value: " + value);
            MyPoint p = new MyPoint(value, value);
            addPoint(p);
        }
    }

    private void add(double key, TreeMap map) {
        if (map.containsKey(key)) {
            int val = (Integer) map.get(key);
            val++;
            update_value_hash_map(key, val, map);
        } else {
            map.put(key, 1);
        }
    }

    private void update_value_hash_map(Object key, Object value, TreeMap map) {
        map.remove(key);
        map.put(key, value);
    }
}
