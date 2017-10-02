/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.DiffMarkerAction;
import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MyPoint;
import XYG_BASIC.PointHighLighter;
import java.awt.Color;
import java.sql.ResultSet;

/**
 *
 * @author KOCMOC
 */
public class GistoGraph extends HistograM implements GG{

    public GistoGraph(String title,MyGraphXY_H mgxyh, int displayMode) {
        super(title,mgxyh, displayMode);
    }

    @Override
    public String getRound() {
        return this.round;
    }

    @Override
    public void addDiffMarkersSetListener(DiffMarkerAction dma) {
        super.addDiffMarkersSetListener(dma); //To change body of generated methods, choose Tools | Templates.
    }
    

    @Override
    public void markersSet(MyGraphXY trigerInstance, MyPoint markerA, MyPoint markerB) {
        if (trigerInstance instanceof MyGraphXY_H == false) {
            rebuildData(resultSet, valueColName, round, markerA.getPointIndex(), markerB.getPointIndex());
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
        serie = new MySerieH(getTitle());
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
        this.addSerie(serie,true,this);
        //
        PointHighLighter.addSerieSingle(serie);
    }
}
