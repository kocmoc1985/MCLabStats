/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.MyPoint;
import java.awt.event.MouseEvent;

/**
 *
 * @author KOCMOC
 */
public interface PointGraphListener {

    public void pointGraphHoverEvent(MouseEvent e, MyPoint point);
    public void pointGraphHoverOutEvent(MouseEvent e);
    
}
