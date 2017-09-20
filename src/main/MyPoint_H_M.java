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

    private static boolean once = true;
    private static int step_x;

    public MyPoint_H_M(int y, double y_) {
        super(y, y_);
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

        g2d.setColor(POINT_COLOR);

        if (once && x > 0) {
            step_x = x;
            once = false;
        }

//        g2d.fill3DRect((int) (x - POINT_D / 2), (int) (y - POINT_D / 2), POINT_D, POINT_D, true);
        g2d.fill3DRect(x - step_x, y, step_x - 2, getSerie().myGraphXY.getHeight() - y, true);
        //==================================IMPORTNAT=============================
        //Sets the size of the component which reffers to this point
//        this.setLocation((x - POINT_D / 2), (int) (y - POINT_D / 2));
//        this.setSize(POINT_D, POINT_D);
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
