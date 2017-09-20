/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyPoint;
import XYG_HISTO.MyPointH;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author KOCMOC
 */
public class MyPoint_H_M extends MyPoint {

    private static boolean once = true;

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

//        g2d.fill3DRect((int) (x - POINT_D / 2), (int) (y - POINT_D / 2), POINT_D, POINT_D, true);
        g2d.fill3DRect(x, y, 100, 50, true);

        //==================================IMPORTNAT=============================
        //Sets the size of the component which reffers to this point
//        this.setLocation((x - POINT_D / 2), (int) (y - POINT_D / 2));
//        this.setSize(POINT_D, POINT_D);
    }
    
    
}
