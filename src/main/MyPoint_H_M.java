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
public class MyPoint_H_M extends MyPoint {

    private double rangeStart;
    private double rangeEnd;
    private static boolean once = true;
    private static int step_x;

    public MyPoint_H_M(int y, double y_,double rangeStart, double rangeEnd) {
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
        if (once && x > 0) {
            step_x = x;
            once = false;
        }
        //
        int x_ = (x - step_x);
        int y_ = y;
        int width = step_x - 2;
        int height = getSerie().myGraphXY.getHeight() - y;
        g2d.fill3DRect(x_, y_, width, height, true);
        //==================================IMPORTNAT=============================
        //Sets the size of the component which reffers to this point
        
        this.setLocation(x_, y_);
        this.setSize(width, height);
    }
    
    
    
    
    
    
//    @Override
//    protected void drawPoint(Graphics g, Color pointColor) {
//        Graphics2D g2d = (Graphics2D) g;
//        if (highLightSet == false) {
//            POINT_COLOR = pointColor;
//        }
//
//        if (POINT_COLOR_B != null) {
//            POINT_COLOR = POINT_COLOR_B;
//        }
//
//        g2d.setColor(POINT_COLOR);
//
////        System.out.println("y before: " + y + " / " + y_Real + " / " + y_Scaled + " / " + y_Display);
//        int x_= (int) (x - POINT_D / 2);
//        int y_ = (int) (y - POINT_D / 2);
//        System.out.println("x: " + x_ + " / y: " + y_);
//        g2d.fillOval(x_, y_, POINT_D, POINT_D);
////        System.out.println("y after: " + y + " / " + y_Real + " / " + y_Scaled + " / " + y_Display);
//
//        //==================================IMPORTNAT=============================
//        //Sets the size of the component which reffers to this point
//        this.setLocation((x - POINT_D / 2), (int) (y - POINT_D / 2));
//        this.setSize(POINT_D, POINT_D);
//    }
}
