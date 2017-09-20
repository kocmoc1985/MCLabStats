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
public class MySerieH extends MySerie {

    public MySerieH(String name) {
        super(name);
        pointsHighlightColor = Color.ORANGE;
    }

    @Override
    public void setMyGraphXY(MyGraphXY myGraph) {
        this.myGraphXY = myGraph;
        if (this.DIFF_MARKER_POINTS == null) {
            this.DIFF_MARKER_POINTS = new DiffMarkerPointsH(this, myGraphXY);
        }
    }
    
    
}
