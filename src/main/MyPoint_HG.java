/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyPoint;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author KOCMOC
 */
public class MyPoint_HG extends MyPoint {

    private double rangeStart;
    private double rangeEnd;

    public MyPoint_HG(double y, double y_, double rangeStart, double rangeEnd) {
        super(y, y_);
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public double getRangeStart() {
        return rangeStart;
    }

    public double getRangeEnd() {
        return rangeEnd;
    }


    @Override
    protected void drawPoint(Graphics g, Color pointColor) {
        Graphics2D g2d = (Graphics2D) g;
        if (highLightSet == false) {
            POINT_COLOR = pointColor;
        }

        if (POINT_COLOR_B != null) {
            POINT_COLOR = POINT_COLOR_B;
        }
        //
        g2d.setColor(POINT_COLOR);
        //
        MyPoint prevPoint = getPreviousPoint();
        //
        int x_ = x;
        int width;
        //
        if(x > 0 && prevPoint == null){
             x_ -= x;
             width = x;
        }else if(prevPoint != null && x > 0){
             x_ = prevPoint.x;
             width = x - prevPoint.x;
        }else{
            return;
        }
        //
        int y_ = y;
        int height = getSerie().myGraphXY.getHeight() - y;
        g2d.fill3DRect(x_, y_, width, height, true);
        //==================================IMPORTNAT=============================
        //Sets the size of the component which reffers to this point
        //
        this.setLocation(x_, y_);
        this.setSize(width, height);
    }

    
    

    
    

}
