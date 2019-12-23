/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MyPoint;
import XYG_BASIC.MySerie;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * MyGraphXY_PG = MyGraphXY for the POLYGON GRAPH
 *
 * @author KOCMOC
 */
public class MyGraphXY_PG extends MyGraphXY {

    public ArrayList<String> xValuesList;
    public int STEP_IDENTIFIER_X_AXIS = -1;
    private final ArrayList<PointGraphListener> pointGraphListeners = new ArrayList<>();

    public void addPointGraphListener(PointGraphListener pgl) {
        pointGraphListeners.add(pgl);
    }

    public void setXValues(ArrayList<String> list) {
        xValuesList = list;
    }

    public void setStepIdentifierX(int x) {
        this.STEP_IDENTIFIER_X_AXIS = x;
    }

    @Override
    public void defineMaxForXYAxis(MyPoint point) {
        if (point.x_Scaled > X_MAX / 1.05) {
            X_MAX = (int) ((point.x_Scaled));//1.2 Note this is important value!
            X_MAX *= 1.05;
        }
        if (point.y_Scaled > Y_MAX / 1.2) {
            Y_MAX = (point.y_Scaled);
            Y_MAX *= 1.2;
        }
    }

    @Override
    public void DRAW(Graphics g) {
        //
        if (DRAW_MARKER) {
            drawMarkerWhenPointing(g);
        }
        //
        drawDiffMarkers(g);
        //
        if (SCALE_XY_AXIS) {
            scaleOfXYAxis(g);
        }
        //
        drawLines(g);
        //
        drawPointsFixedSize(g);
        //
        drawLimits(g);
    }

    @Override
    public void drawLimits(Graphics g) {
        //
        Graphics2D g2 = (Graphics2D) g;
        //
        if (getHeight() < 50) {//ONE_UNIT_Y < 1.1 || getHeight() < 50 || ONE_UNIT_Y > 20
            return;
        }
        // #TEST-DRAW-LIMITS-PG
//        LIMIT_MIN = 1.1;
//        LIMIT_MAX = 2.2;
        //
        if (LIMIT_MAX == 0 || LIMIT_MIN == 0 || ONE_UNIT_X == 1) {
            return;
        }
        //
        System.out.println("Y_MAX: " + Y_MAX);
        System.out.println("X_MAX: " + X_MAX);
        //
//        double unit_y = getWidth() / Y_MAX;
//        //
//        double scaled_max = (LIMIT_MAX * ALL_SERIES_COEFF);
//        int pixels_max = (int) Math.round(getWidth() - (unit_y * scaled_max));
//        int max = pixels_max;
//
//        double scaled_min = (LIMIT_MIN * ALL_SERIES_COEFF);
//        int pixels_min = (int) Math.round(getWidth() - (unit_y * scaled_min));
//        int min = pixels_min;
//
//        ORDINARY_STROKE = (BasicStroke) g2.getStroke();
//
//        g2.setStroke(GRID_STROKE);
//
//        //draw lim max
//        g2.setPaint(Color.RED);
////      g2.drawLine(x, max, getWidth(), max);
//        g2.drawLine(max, 0, max, getHeight());
//
//        //draw lim min
//        g2.setPaint(Color.RED);
//        g2.drawLine(min, 0, min, getHeight());
//
//        g2.setStroke(ORDINARY_STROKE);
//
//
//==============================================================================
//
//
        double coeff = getWidth() / X_MAX;
        //
        boolean limit_one_drawn = false;
        boolean limit_two_drawn = false;
        //
        for (int i = 1; i < getWidth(); i++) {
            //
            //
            if (i > LIMIT_MIN * coeff && !limit_one_drawn) {
                //
                limit_one_drawn = true;
                //
                g2.setPaint(Color.RED);
                //
//                g2.drawRect(i, (int) (getHeight() - 5 * COEFF_SMALL_GRID), 1, (int) (5 * COEFF_SMALL_GRID));
                //
                g2.drawLine(i, 0, i, getHeight());
            }

            if (i > LIMIT_MAX * coeff && !limit_two_drawn) {
                //
                limit_two_drawn = true;
                //
                g2.drawLine(i, 0, i, getHeight());
            }
        }

    }

    @Override
    public void scaleX(Graphics2D g2) {
        if (SCALE_X_AXIS) {
            //
            int j = 0; // step identifier
            //
            double vv = (X_MAX);
            //
            if (vv > 500) {
                j = 100;
            } else if (vv > 200) {
                j = 40;
            } else if (vv > 100) {
                j = 20;
            } else if (vv > 30) {
                j = 10;
            } else {
                j = 5;
            }
            //
            if (STEP_IDENTIFIER_X_AXIS != -1) {
                j = STEP_IDENTIFIER_X_AXIS;
            }
            //
            //
            int m = 1; // frequency regulator
            //
            System.out.println("getWidth(): " + getWidth());
            //
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
                        //
                        g2.setPaint(GRID_COLOR);
                        //
                        if (xValuesList != null) {
                            try {
                                String val = xValuesList.get((j * m) - 1);
                                g2.drawString("" + val, i - 10, (int) (getHeight() - 5 * COEFF_SMALL_GRID) - 5);
                                //
                                //FOR TEST DRAW LIMITS
//                                double cc = Double.parseDouble(xValuesList.get((j * m) - 1)) / i;
//                                System.out.println("CALC_PG_A: " + val + " /i: " + i + " /X: " + X);
                                //
                            } catch (IndexOutOfBoundsException ex) {
                                g2.drawString("" + (j * m), i - 3, (int) (getHeight() - 5 * COEFF_SMALL_GRID) - 5);
                            }
                        } else {
                            g2.drawString("" + (j * m), i - 3, (int) (getHeight() - 5 * COEFF_SMALL_GRID) - 5);
                        }
                        //
                        g2.drawRect(i, (int) (getHeight() - 5 * COEFF_SMALL_GRID), 1, (int) (5 * COEFF_SMALL_GRID));
                        m++;
                        //
                    }

                }
            }
            System.out.println("======================================");
        }
    }

    @Override
    public void addPointInfo() {
        MARKER_POINT.addPointInfo("serie", MARKER_POINT.getSerieName());
        MARKER_POINT.addPointInfo("y", "" + (MARKER_POINT.y_Display));
        MARKER_POINT.addPointInfo("x", "" + MARKER_POINT.x_Display);
    }

    @Override
    public void addAdditionalControlsPopups() {
        if (MARKER_POINT.isDiffMarkerPoint() == false) {
            popup.add(menu_item_diff_marker_add);
        } else if (MARKER_POINT.isDiffMarkerPoint()) {
            popup.add(menu_item_diff_marker_remove);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //
        super.mouseMoved(e); //To change body of generated methods, choose Tools | Templates.
        //
        if (e.getSource() instanceof MyPoint) {
            callEventWatchersHover(e);
        } else {
            callEventWatchersHoverOut(e);
        }

    }

    private void callEventWatchersHoverOut(MouseEvent e) {
        for (PointGraphListener pgl : pointGraphListeners) {
            pgl.pointGraphHoverOutEvent(e);
        }
    }

    private void callEventWatchersHover(MouseEvent e) {
        for (PointGraphListener pgl : pointGraphListeners) {
            pgl.pointGraphHoverEvent(e, MARKER_POINT);
        }
    }
}
