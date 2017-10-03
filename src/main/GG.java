/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_BASIC.DiffMarkerAction;
import XYG_BASIC.MyPoint;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 *
 * @author KOCMOC
 */
public interface GG extends DiffMarkerAction{
    
    public void addDiffMarkersSetListener(DiffMarkerAction dma);
    
    public void addData(ResultSet rs, String valueColName);
    
    public String getRound();
    
    public void rebuildData(ArrayList<MyPoint> points,String round);
    
    public JComponent getGraph();
    
}
