/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XYG_HISTO.HistograM;
import XYG_HISTO.MyGraphXY_H;

/**
 *
 * @author KOCMOC
 */
public class GistoGram extends HistograM{
    
    public GistoGram(String title, MyGraphXY_H xY_H, int displayMode) {
        super(title, xY_H, displayMode);
        Gui.HistoPanel.add(getGraph());
//        mgxyh.setStepIdentifierX(10);
    }
    
}
