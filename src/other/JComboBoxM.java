/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import javax.swing.JComboBox;

/**
 *
 * @author KOCMOC
 */
public class JComboBoxM extends JComboBox {

    private long FLAG_WAIT;
    private String PARAMETER; // Column name in DB
    private boolean NUMBER;

    public JComboBoxM(String PARAMETER,boolean isNumber) {
        this.PARAMETER = PARAMETER;
        this.NUMBER = isNumber;
    }

    public boolean isNUMBER() {
        return NUMBER;
    }
    
    public String getPARAMETER() {
        return PARAMETER;
    }

    public void setPARAMETER(String PARAMETER) {
        this.PARAMETER = PARAMETER;
    }

    
    public long getFLAG_WAIT() {
        return FLAG_WAIT;
    }

    public void setFLAG_WAIT(long FLAG_WAIT) {
        this.FLAG_WAIT = FLAG_WAIT;
    }
}
