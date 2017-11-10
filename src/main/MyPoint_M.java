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
public class MyPoint_M extends MyPoint {

    private MyPoint LSL;
    private MyPoint USL;

    public MyPoint_M(double y, double y_, MyPoint LSL, MyPoint USL) {
        super(y, y_);
        this.LSL = LSL;
        this.USL = USL;
    }
    boolean minusValues = false;
    double y_max;
    double one_unit_y;
    boolean recalc_done = false;

    @Override
    protected void drawPoint(Graphics g, Color pointColor) {
        //
        Graphics2D g2d = (Graphics2D) g;
        //
        if (highLightSet == false) {
            POINT_COLOR = pointColor;
        }
        //
        if (POINT_COLOR_B != null) {
            POINT_COLOR = POINT_COLOR_B;
        }
        //
        g2d.setColor(POINT_COLOR);
        //
        if (DRAW_RECT) {
            g2d.fill3DRect((int) (x - POINT_D / 2), (int) (y - POINT_D / 2), POINT_D, POINT_D, true);
        } else {
            g2d.fillOval((int) (x - POINT_D / 2), (int) (y - POINT_D / 2), POINT_D, POINT_D);
        }
        //
        point_area = (int) 3.14 * (int) Math.pow(POINT_D / 2, 2);
        //==================================IMPORTNAT=============================
        //Sets the size of the component which reffers to this point
        this.setLocation((x - POINT_D / 2), (int) (y - POINT_D / 2));
        this.setSize(POINT_D, POINT_D);
    }


    public MyPoint getLSL() {
        return LSL;
    }

    public MyPoint getUSL() {
        return USL;
    }

    @Override
    public void deletePoint() {
        super.deletePoint(); //To change body of generated methods, choose Tools | Templates.
        LSL.deletePoint();
        USL.deletePoint();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
}
