/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MySerie;
import XYG_HISTO.HistograMM;
import XYG_HISTO.MyGraphXY_H;
import java.awt.Color;
import java.sql.ResultSet;

/**
 *
 * @author KOCMOC
 */
public class GistoGraph extends HistograMM {

    public GistoGraph(String title, MyGraphXY_H xY_H, int displayMode) {
        super(title, xY_H, displayMode);
    }

    /**
     * @deprecated 
     * @param rs 
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
