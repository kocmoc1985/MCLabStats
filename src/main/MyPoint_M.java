/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyPoint;

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
