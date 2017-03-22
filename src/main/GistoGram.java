/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_HISTO.HistograM;
import XYG_HISTO.MyGraphXY_H;
import java.awt.Color;

/**
 *
 * @author KOCMOC
 */
public class GistoGram extends HistograM{
    
    public GistoGram(String title, MyGraphXY_H xY_H, int displayMode) {
        super(title, xY_H, displayMode);
        Gui.HistoPanel.add(getGraph());
//        mgxyh.setStepIdentifierX(10);
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

    
    
    
    
}