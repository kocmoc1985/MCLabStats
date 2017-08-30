/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.DiffMarkerAction;
import XYG_BASIC.DiffMarkerPoints;
import XYG_BASIC.MyPoint;
import XYG_BASIC.MySerie;
import XYG_HISTO.HistograM;
import XYG_HISTO.MyGraphXY_H;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class GistoGram extends HistograM implements DiffMarkerAction {

    private ResultSet resultSet;
    private String valueColName;
    private String round;

    public GistoGram(String title, MyGraphXY_H xY_H, int displayMode) {
        super(title, xY_H, displayMode);
        Gui.HistoPanel.add(getGraph());
//        mgxyh.setStepIdentifierX(2);
    }

    @Override
    public void markersSet(MyPoint markerA, MyPoint markerB) {
        rebuildData(resultSet, valueColName, round, markerA.getPointIndex(), markerB.getPointIndex());
    }

    private void clear() {
        getSerie().deleteAllPoints();
    }

    @Override
    public void addData(ResultSet rs, String valueColName, String round) {
        this.resultSet = rs;
        this.valueColName = valueColName;
        this.round = round;
        clear();
        try {
            while (rs.next()) {
                double val = rs.getDouble(valueColName);
                buildHistogramDataSet(val, histoMap, round);
            }

            addPoints();

        } catch (SQLException ex) {
            Logger.getLogger(HistograM.class.getName()).log(Level.SEVERE, null, ex);
            addPoints();
        }
    }

    private void rebuildData(ResultSet rs, String valueColName, String round, int start, int end) {
        //PAY ATTENTION HERE
        mgxyh = new MyGraphXY_H();
        mgxyh.addSerie(serie);
        //
        clear();
        histoMap.clear();
        int x = 0;
        try {
            rs.first();
            while (rs.next()) {
                if (x >= start && x <= end) {
                    double val = rs.getDouble(valueColName);
                    buildHistogramDataSet(val, histoMap, round);
                }
                x++;
            }
            addPoints();
        } catch (SQLException ex) {
            Logger.getLogger(GistoGram.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        serie = new MySerie(getTitle());
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
//        PointHighLighter.addSerie(serie);
    }

}
