/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MySerie;
import java.awt.Color;

/**
 *
 * @author KOCMOC
 */
public class MySerie_M extends MySerie {

    public MySerie_M(String name) {
        super(name);
    }

    @Override
    public void setMyGraphXY(MyGraphXY myGraph, boolean createDiffMarkers) {
        this.myGraphXY = myGraph;
        if (this.DIFF_MARKER_POINTS == null) {
            this.DIFF_MARKER_POINTS = new DiffMarkerPoints_RS(this, myGraphXY);
        }
        //
        if (myGraphXY instanceof MyGraphXY_HG) {
            pointsHighlightColor = Color.MAGENTA;
        }
    }
    
    
}
