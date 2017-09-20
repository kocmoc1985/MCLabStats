/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_HISTO.MyGraphXY_H;
import java.awt.Graphics2D;

/**
 *
 * @author KOCMOC
 */
public class MyGraphXY_H_M extends MyGraphXY_H {
    
      @Override
    public void addPointInfo() {
        MyPoint_H_M m = (MyPoint_H_M)MARKER_POINT;
        MARKER_POINT.addPointInfo("serie", m.getSerieName());
        MARKER_POINT.addPointInfo("y", "" + (m.y_Display));
        MARKER_POINT.addPointInfo("x", "" + m.getRangeStart() + " -> " + m.getRangeEnd());
    }
    
    @Override
    public void scaleX(Graphics2D g2) {
        if (SCALE_X_AXIS) {
            int j = 0; // step identifier

            int vv = (int) (X_MAX);

            if (vv > 500) {
                j = 100;
            } else if (vv > 200) {
                j = 40;
            } else if (vv > 100) {
                j = 20;
            } else if (vv > 30) {
                j = 10;
            } else {
                j = 2;
            }


            if (STEP_IDENTIFIER_X_AXIS != -1) {
                j = STEP_IDENTIFIER_X_AXIS;
            }


            int m = 1; // frequency regulator
            for (int i = 1; i < getWidth(); i++) {
                double X = i / ONE_UNIT_X; //!!!!!!!!! X = nr of one_unit_x per real pixel
                if (X > (j * m) && X < (j * m) + ONE_UNIT_X) {
                    if (SHOW_GRID) {
                        g2.setPaint(GRID_COLOR);
                        g2.drawRect(i, 0, 1, getHeight());
                        //
                        if (SHOW_GRID_AND_SCALE) {
                            g2.drawString("" + (j * m), i - 10, (int) (getHeight() - 3 * COEFF_SMALL_GRID) - 1);

                        }
                        //
                        m++;
                    } else {
                        g2.setPaint(GRID_COLOR);

                        if (xValuesList != null) {
                            try {
                                g2.drawString("" + xValuesList.get((j * m) - 1), i - 10, (int) (getHeight() - 5 * COEFF_SMALL_GRID) - 5);
                            } catch (IndexOutOfBoundsException ex) {
                                g2.drawString("" + (j * m), i - 3, (int) (getHeight() - 5 * COEFF_SMALL_GRID) - 5);
                            }
                        } else {
                            g2.drawString("" + (j * m), i - 3, (int) (getHeight() - 5 * COEFF_SMALL_GRID) - 5);
                        }

                        g2.drawRect(i, (int) (getHeight() - 5 * COEFF_SMALL_GRID), 1, (int) (5 * COEFF_SMALL_GRID));
                        m++;
                    }

                }
            }
        }
    }

    
}
