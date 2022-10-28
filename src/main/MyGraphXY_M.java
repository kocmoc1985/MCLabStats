/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MyPoint;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.util.List;
import other.HelpA;
import sql.SQL_Q;
import udp.Client_UDP;

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

    public MenuItem menu_show_in_table = new MenuItem("Show in table");
    public MenuItem menu_item_open_in_browser = new MenuItem("Open in browser");
    public boolean MINUS_VALUES_PRESENT = false;
    public double OFFSET;
    private Client_UDP cudp = new Client_UDP("127.0.0.1", 10001);
    private Controller cont;

    public MyGraphXY_M(Controller controller) {
        this.cont = controller;
    }

    @Override
    public void addAdditionalControlsPopups() {
        super.addAdditionalControlsPopups(); //To change body of generated methods, choose Tools | Templates.
        //#TAG-TEMP-A-01#
        menu_item_open_in_browser.addActionListener(this);
        menu_show_in_table.addActionListener(this);
        popup.add(menu_item_open_in_browser);
        popup.add(menu_show_in_table);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        super.actionPerformed(ae); //To change body of generated methods, choose Tools | Templates.
        //#TAG-TEMP-A-01#
        //

        //
        if (ae.getSource() == menu_item_open_in_browser) {
            String batch = MARKER_POINT.getPointInfo().getOrDefault("Batch", "0");
            String order = MARKER_POINT.getPointInfo().getOrDefault("Order", "A");
//            System.out.println("Open in Browser");
//            System.out.println("Batch: " + batch);
//            System.out.println("Order: " + order);
            //#MC-LAB-STATS-OPEN-BATCH-IN-BROWSER#
            cudp.prepareAndSendDatagram("#showbatch#" + order + ";" + batch.replace(".0", ""));
//            cudp.prepareAndSendDatagram("#showbatch#" + "WO16102022_MIX002_131" + ";" + "10");
        } else if (ae.getSource() == menu_show_in_table) {
            HelpA.markGivenRow(Main.jTableMain, MARKER_POINT.getPointIndex());
            this.cont.showCurrTableEntryOnGraph();
        }

    }

    @Override
    public void defineMaxForXYAxis(MyPoint point) {
        //
        if (point.x_Scaled > X_MAX / 1.05) {
            X_MAX = (int) ((point.x_Scaled));//1.2 Note this is important value!
            X_MAX *= 1.05;
        }
        //
        //#MINUS-VALUES
        //
        if (point.y_Scaled < 0 && MINUS_VALUES_PRESENT == false) {
            MINUS_VALUES_PRESENT = true;
//            System.out.println("minus");
        }
        //
        if (Math.abs(point.y_Scaled) > Y_MAX / 1.2) {
            //
            Y_MAX = Math.abs(point.y_Scaled);
            //
            if (MINUS_VALUES_PRESENT) {
                Y_MAX *= 4;
            } else {
                Y_MAX *= 1.2;
            }
            //
//            System.out.println("Y_MAX: " + Y_MAX);
            //
        }
        //
    }

//    @Override
//    public void addPointInfo() {
//        //
//        super.addPointInfo(); //To change body of generated methods, choose Tools | Templates.
//        //
//        if (MINUS_VALUES_PRESENT) {
//            MARKER_POINT.addPointInfo("y_Real", "" + (MARKER_POINT.y_Real) + " (" + (Math.abs(Y_MAX) - Math.abs(MARKER_POINT.y_Real)) + ")");
//            MARKER_POINT.addPointInfo("y_Max", "" + (Y_MAX));
//        }
//    }
    @Override
    public synchronized void recalc(Dimension dim) {

        for (int x = 0; x < SERIES.size(); x++) {
            //
            List<MyPoint> act_serie = SERIES.get(x).getSerie();
            //
            for (int i = 0; i < act_serie.size(); i++) {
                //
                int x_static = act_serie.get(i).x_Scaled;
                double y_static = act_serie.get(i).y_Scaled;
                //
                y_static = Math.abs(y_static);
                //
                countUnit();
                //
                act_serie.get(i).x = (int) (Math.round(ONE_UNIT_X * x_static));
                //
                if (MINUS_VALUES_PRESENT) {
                    //
                    double y_scaled = act_serie.get(i).y_Scaled;
                    //
                    if (y_scaled < 0) {
                        double d = ((Y_MAX / 2) + Math.abs(y_scaled)) + 1;
                        double dd = Y_MAX - d;
                        OFFSET = dd - (y_scaled);
                        act_serie.get(i).y_Real = d;
                        act_serie.get(i).y = (int) (ONE_UNIT_Y * d);
                    } else if (y_scaled > 0) {
                        double d = ((Y_MAX / 2) - Math.abs(y_scaled)) + 1;
                        double dd = Y_MAX - d;
                        OFFSET = dd - (y_scaled);
                        act_serie.get(i).y_Real = d;
                        act_serie.get(i).y = (int) (ONE_UNIT_Y * d);
                    }
                } else {
                    act_serie.get(i).y = (int) Math.round((getHeight() - (ONE_UNIT_Y * y_static)));
                }
            }
        }
        //
        //OBS! OBS!
        repaint();
    }

    @Override
    public void countUnit() {
        //
        if (getHeight() < 100) {
            return;
        }
        //
        ONE_UNIT_X = (double) (getWidth() / X_MAX);
        //
        ONE_UNIT_Y = Math.round(getHeight() / Y_MAX);
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
                        double dbl = (jj * mm);
                        if (MINUS_VALUES_PRESENT) {
                            g2.drawString("" + round_(transform(dbl)), (int) (7 * COEFF_SMALL_GRID), getHeight() - (i - fix_coef_2 - 3));
//                            g2.drawString("" + round_(dbl), (int) (7 * COEFF_SMALL_GRID), getHeight() - (i - fix_coef_2 - 3));
                        } else {
                            g2.drawString("" + round_(dbl), (int) (7 * COEFF_SMALL_GRID), getHeight() - (i - fix_coef_2 - 3));
                        }
                        //
                        g2.drawRect(0, (getHeight() - (i - fix_coef_2)), (int) (5 * COEFF_SMALL_GRID), 1);
                        mm++;
                    }

                }
            }
        }
    }

    /**
     * OBS! This is very important
     *
     * @param dbl
     * @return
     */
    private double transform(double dbl) {
        return dbl - OFFSET;
    }

    @Override
    public void drawLimits(Graphics g) {
        // It's correct
    }

}
