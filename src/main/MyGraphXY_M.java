/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MyPoint;
import java.awt.Graphics2D;

/**
 * MyGraphXY_M = MyGraphXY for the Common Point Graph which can display minus
 * values Range of -1 to -14 we turn to range from -14 to +14 which is the same
 * as 0 to 28 y = is the max scale on Y axis (28 in this case) x = is the value
 * which we find the value for. If x = -4 we must find how much it is in range
 * from 0 - 28 (y/2 - x) + 1 calculation of - values (y/2 + x) + 1 calculation
 * of + values
 *
 * @author KOCMOC
 */
public class MyGraphXY_M extends MyGraphXY {

    public boolean MINUS_VALUES_PRESENT = false;
    
    @Override
    public void defineMaxForXYAxis(MyPoint point) {
        if (point.x_Scaled > X_MAX / 1.05) {
            X_MAX = (int) ((point.x_Scaled));//1.2 Note this is important value!
            X_MAX *= 1.05;
        }
        //
        //#MINUS-VALUES
        if (Math.abs(point.y_Scaled) > Y_MAX / 1.2) {
            Y_MAX = Math.abs(point.y_Scaled);
            Y_MAX *= 1.2;
        }
        //#MINUS-VALUES
        if(point.y_Scaled < 0 && MINUS_VALUES_PRESENT == false){
            MINUS_VALUES_PRESENT = true;
            System.out.println("minus");
        }
    }

    @Override
    public void scaleY(Graphics2D g2) {
        if (SCALE_Y_AXIS) {
            //Nr of ONE_UNIT_Y per getHeight. Note that Y_MAX is not the same
            //but is the highest point in graph expressed in ONE_UNIT_Y
            double max_y_units = getHeight() / ONE_UNIT_Y;

            //Max nr of unreal points in the graph area,
            //unreal points is the values that should be displayed
            //but not the real pixels on graph
            double max_unreal_points = max_y_units / ALL_SERIES_COEFF;

            // how many unreal points there is in one real point/pixel
            double unreal_points_per_real = max_unreal_points / getHeight();
            //
            double jj; // step identifier
            //
            //#MINUS-VALUES
            if (MINUS_VALUES_PRESENT) {
                Y_MAX = Y_MAX * 2;
            }
            //
            double vvv = (Y_MAX / ALL_SERIES_COEFF);
            //
            if (vvv > 100000 && vvv < 1000000) {
                jj = 10000;
            } else if (vvv > 10000 && vvv < 100000) {
                jj = 1000;
            } else if (vvv > 1000 && vvv < 10000) {
                jj = 500;
            } else if (vvv > 100 && vvv < 1000) {
                jj = 50;
            } else if (vvv > 10 && vvv < 100) {
                jj = 5;
            } else if (vvv > 5 && vvv < 10) {
                jj = 1;
            } else if (vvv > 1 && vvv < 3) {
                jj = 0.5;
            } else if (vvv > 0 && vvv < 3) {
                jj = 0.1;
            } else {
                jj = 1;
            }
            //
            if (vvv < 1) {
                jj = 0.02;
            }
            //
//            System.out.println("JJ: " + jj + " / vvv: " + vvv + " / y_max: " + Y_MAX + " / coeff: " + ALL_SERIES_COEFF);
            //
            int mm = 1; // frequency regulator
            int fix_coef_2 = 1; // this coef is for fixing the scaling of y-axis
            for (int i = 1; i < getHeight(); i++) {
                double x = (i * unreal_points_per_real);
                if (x > (jj * mm) && x < (jj * mm) + unreal_points_per_real + 1) {
                    if (SHOW_GRID) {
                        g2.setPaint(GRID_COLOR);
                        g2.drawRect(0, (getHeight() - (i - fix_coef_2)), getWidth(), 1);
                        //
                        if (SHOW_GRID_AND_SCALE) {
                            g2.drawString("" + round_(jj * mm), (int) (1 * COEFF_SMALL_GRID), getHeight() - (i - fix_coef_2 + 5));
                        }
                        //
                        mm++;
                    } else {
                        g2.setPaint(GRID_COLOR);
                        g2.drawString("" + round_(jj * mm), (int) (7 * COEFF_SMALL_GRID), getHeight() - (i - fix_coef_2 - 3));
                        g2.drawRect(0, (getHeight() - (i - fix_coef_2)), (int) (5 * COEFF_SMALL_GRID), 1);
                        mm++;
                    }

                }
            }
        }
    }
}
