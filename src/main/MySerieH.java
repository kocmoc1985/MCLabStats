/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MySerie;

/**
 *
 * @author KOCMOC
 */
public class MySerieH extends MySerie {

    public MySerieH(String name) {
        super(name);
    }

    @Override
    public void setMyGraphXY(MyGraphXY myGraph) {
        this.myGraphXY = myGraph;
        if (this.DIFF_MARKER_POINTS == null) {
            this.DIFF_MARKER_POINTS = new DiffMarkerPointsH(this, myGraphXY);
        }
    }
}
