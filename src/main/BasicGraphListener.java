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
import sql.Sql_B;

/**
 *
 * @author KOCMOC
 */
public interface BasicGraphListener extends DiffMarkerAction{
    
    public void addDiffMarkersSetListener(DiffMarkerAction dma);
    
    public void addData(Sql_B sql,String q, String valueColName);
    
    public String getRound();
    
    public void rebuildData(ArrayList<MyPoint> points,String round);
    
    public JComponent getGraph();
    
}
