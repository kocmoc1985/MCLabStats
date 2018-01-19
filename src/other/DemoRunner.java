/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import java.util.logging.Level;
import java.util.logging.Logger;
import main.Controller;
import main.Main;

/**
 *
 * @author KOCMOC
 */
public class DemoRunner implements Runnable {

    private Main main;
    private int DELAYED_START_MILLIS = 1000;
    private Controller controller;
    
    public DemoRunner(Main main,Controller cont, int delayedStartMillis) {
        this.main = main;
        this.DELAYED_START_MILLIS = delayedStartMillis;
        this.controller = cont;
    }

    @Override
    public void run() {
        //
        wait_(DELAYED_START_MILLIS);
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                main.buildGraphs();
                controller.fillComboStandard((JComboBoxA)main.jComboBoxTestName);
//                main.jComboBoxTestName.setEnabled(true);
//                main.jComboBoxTestName.setEditable(true);
            }
        });

    }

    private synchronized void wait_(int millis) {
        try {
            wait(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(DemoRunner.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
