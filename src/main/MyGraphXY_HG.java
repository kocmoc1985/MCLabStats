/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyPoint;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * MyGraphXY_HG = MyGraphXY for the HISTOGRAM GRAPH
 * @author KOCMOC
 */
public class MyGraphXY_HG extends MyGraphXY_PG {

    private final ArrayList<BarGraphListener> bg_listener_list = new ArrayList<>();

    @Override
    public void addPointInfo() {
        MyPoint_HG m = (MyPoint_HG) MARKER_POINT;
        MARKER_POINT.addPointInfo("serie", m.getSerieName());
        MARKER_POINT.addPointInfo("y", "" + (m.y_Display));
        MARKER_POINT.addPointInfo("x", "" + m.getRangeStart() + " -> " + m.getRangeEnd());
    }

    @Override
    public void addAdditionalControlsPopups() {
         if (MARKER_POINT.isDiffMarkerPoint() == false) {
            popup.add(menu_item_diff_marker_add);
        } else if (MARKER_POINT.isDiffMarkerPoint()) {
            popup.add(menu_item_diff_marker_remove);
        }
        //
//        popup.add(menu_item_delete_point);
    }
    
    

    public void addBarGraphListener(BarGraphListener bgl) {
        bg_listener_list.add(bgl);
    }

    @Override
    public void scaleX(Graphics2D g2) {
//        if (SCALE_X_AXIS) {
//            int j = 0; // step identifier
//
//            int vv = (int) (X_MAX);
//
//            if (vv > 500) {
//                j = 100;
//            } else if (vv > 200) {
//                j = 40;
//            } else if (vv > 100) {
//                j = 20;
//            } else if (vv > 30) {
//                j = 10;
//            } else {
//                j = 2;
//            }
//
//            if (STEP_IDENTIFIER_X_AXIS != -1) {
//                j = STEP_IDENTIFIER_X_AXIS;
//            }
//
//            int m = 1; // frequency regulator
//            for (int i = 1; i < getWidth(); i++) {
//                double X = i / ONE_UNIT_X; //!!!!!!!!! X = nr of one_unit_x per real pixel
//                if (X > (j * m) && X < (j * m) + ONE_UNIT_X) {
//                    if (SHOW_GRID) {
//                        g2.setPaint(GRID_COLOR);
//                        g2.drawRect(i, 0, 1, getHeight());
//                        //
//                        if (SHOW_GRID_AND_SCALE) {
//                            g2.drawString("" + (j * m), i - 10, (int) (getHeight() - 3 * COEFF_SMALL_GRID) - 1);
//
//                        }
//                        //
//                        m++;
//                    } else {
//                        g2.setPaint(GRID_COLOR);
//
//                        if (xValuesList != null) {
//                            try {
//                                g2.drawString("" + xValuesList.get((j * m) - 1), i - 10, (int) (getHeight() - 5 * COEFF_SMALL_GRID) - 5);
//                            } catch (IndexOutOfBoundsException ex) {
//                                g2.drawString("" + (j * m), i - 3, (int) (getHeight() - 5 * COEFF_SMALL_GRID) - 5);
//                            }
//                        } else {
//                            g2.drawString("" + (j * m), i - 3, (int) (getHeight() - 5 * COEFF_SMALL_GRID) - 5);
//                        }
//
//                        g2.drawRect(i, (int) (getHeight() - 5 * COEFF_SMALL_GRID), 1, (int) (5 * COEFF_SMALL_GRID));
//                        m++;
//                    }
//
//                }
//            }
//        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e); //To change body of generated methods, choose Tools | Templates.
        //
        if (e.getSource() instanceof MyPoint) {
            callEventWatchersHover(e);
        } else {
            callEventWatchersHoverOut(e);
        }

    }

    private void callEventWatchersHoverOut(MouseEvent e) {
        for (BarGraphListener bgl : bg_listener_list) {
            bgl.barGraphHoverOutEvent(e);
        }
    }

    private void callEventWatchersHover(MouseEvent e) {
        for (BarGraphListener bgl : bg_listener_list) {
            bgl.barGraphHoverEvent(e, (MyPoint_HG) MARKER_POINT);
        }
    }
}
