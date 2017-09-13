/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import other.HelpA;
import other.HelpB;
import other.JComboBoxM;
import sql.SQL_Q;
import sql.ShowMessage;

/**
 *
 * @author KOCMOC
 */
public class Gui extends javax.swing.JFrame implements ShowMessage, MouseListener {
    
    private Controller controller;
    private Properties p = HelpB.properties_load_properties("main.properties", false);
    private static Color INITIAL_BG_COLOR_COMBO;
    private ArrayList<JComboBox> JCOMBO_LIST = new ArrayList<>();
    public final static String DATE_FORMAT = "yy/MM/dd";

    /**
     * Creates new form Gui
     */
    public Gui() {
        initComponents();
        this.controller = new Controller(this);
        initOther();
        buildComboList();
        addJComboListeners();
    }
    
    private void initOther() {
        datePicker1.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
    }
    
    public ArrayList<JComboBox> getJCOMBO_LIST() {
        return JCOMBO_LIST;
    }
    
    private void buildComboList() {
        JCOMBO_LIST.add(jComboBoxQuality);
        JCOMBO_LIST.add(jComboBoxOrder);
        JCOMBO_LIST.add(jComboBoxBatch);
        JCOMBO_LIST.add(jComboBoxTestCode);
        JCOMBO_LIST.add(jComboBoxTestName);
        JCOMBO_LIST.add(jComboBoxLSL);
        JCOMBO_LIST.add(jComboBoxUSL);
        JCOMBO_LIST.add(jComboBoxDateA);
    }
    
    private void addJComboListeners() {
        INITIAL_BG_COLOR_COMBO = jComboBoxQuality.getBackground();
        //
        for (JComboBox jComboBox : JCOMBO_LIST) {
            HelpA.addMouseListenerJComboBox(jComboBox, this);
        }
    }
    
    @Override
    public void showMessage(String str) {
//        System.out.println("" + str);
        jTextArea1.append(HelpB.get_proper_date_time_same_format_on_all_computers() + "  " + str + "\n");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        GraphPanel = new javax.swing.JPanel();
        jButtonFind = new javax.swing.JButton();
        HistoPanel = new javax.swing.JPanel();
        jComboBoxQuality = new JComboBoxM(SQL_Q.QUALITY,false);
        jComboBoxOrder = new JComboBoxM(SQL_Q.ORDER,false);
        jPanelOutputContainer = new javax.swing.JPanel();
        jPanelSumContainer = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldSumm = new javax.swing.JTextField();
        jPanelAverageContainer = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldAverage = new javax.swing.JTextField();
        jPanelMeanContainer = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldMedian = new javax.swing.JTextField();
        jComboBoxBatch = new JComboBoxM(SQL_Q.BATCH,true);
        jComboBoxTestCode = new JComboBoxM(SQL_Q.TEST_CODE,false);
        jComboBoxLSL = new JComboBoxM(SQL_Q.LSL,true);
        jComboBoxUSL = new JComboBoxM(SQL_Q.USL,true);
        jComboBoxTestName = new JComboBoxM(SQL_Q.TEST_NAME,false);
        jButtonClear = new javax.swing.JButton();
        jComboBoxDateA = new JComboBoxM(SQL_Q.TEST_DATE,false);
        datePicker1 = new com.michaelbaranov.microba.calendar.DatePicker();
        jPanelTableContainer = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableMain = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        GraphPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        GraphPanel.setLayout(new java.awt.GridLayout(1, 1));

        jButtonFind.setText("Graph");
        jButtonFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFindActionPerformed(evt);
            }
        });

        HistoPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        HistoPanel.setLayout(new java.awt.GridLayout(1, 1));

        jComboBoxQuality.setModel(new javax.swing.DefaultComboBoxModel());

        jComboBoxOrder.setModel(new javax.swing.DefaultComboBoxModel());

        jPanelOutputContainer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelOutputContainer.setLayout(new java.awt.GridLayout(1, 3, 5, 5));

        jPanelSumContainer.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelSumContainer.setLayout(new java.awt.GridLayout(2, 1));

        jLabel1.setLabelFor(jTextFieldSumm);
        jLabel1.setText("<html><p style='margin-left: 5px;font-weight:bold;'>" + "Sum" + "</h3></p>");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelSumContainer.add(jLabel1);

        jTextFieldSumm.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelSumContainer.add(jTextFieldSumm);

        jPanelOutputContainer.add(jPanelSumContainer);

        jPanelAverageContainer.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelAverageContainer.setLayout(new java.awt.GridLayout(2, 1));

        jLabel3.setLabelFor(jTextFieldSumm);
        jLabel3.setText("<html><p style='margin-left: 5px;font-weight:bold;'>" + "Average" + "</h3></p>");
        jLabel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelAverageContainer.add(jLabel3);

        jTextFieldAverage.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelAverageContainer.add(jTextFieldAverage);

        jPanelOutputContainer.add(jPanelAverageContainer);

        jPanelMeanContainer.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelMeanContainer.setLayout(new java.awt.GridLayout(2, 1));

        jLabel4.setLabelFor(jTextFieldSumm);
        jLabel4.setText("<html><p style='margin-left: 5px;font-weight:bold;'>" + "Median" + "</h3></p>");
        jLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelMeanContainer.add(jLabel4);

        jTextFieldMedian.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelMeanContainer.add(jTextFieldMedian);

        jPanelOutputContainer.add(jPanelMeanContainer);

        jComboBoxBatch.setModel(new javax.swing.DefaultComboBoxModel());

        jComboBoxTestCode.setModel(new javax.swing.DefaultComboBoxModel());

        jComboBoxLSL.setModel(new javax.swing.DefaultComboBoxModel());

        jComboBoxUSL.setModel(new javax.swing.DefaultComboBoxModel());

        jComboBoxTestName.setModel(new javax.swing.DefaultComboBoxModel());

        jButtonClear.setText("Clear");
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });

        jComboBoxDateA.setModel(new javax.swing.DefaultComboBoxModel());

        try {
            datePicker1.setDate(null);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        datePicker1.setShowNoneButton(false);

        jPanelTableContainer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelTableContainer.setLayout(new java.awt.BorderLayout());

        jTableMain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTableMain);

        jPanelTableContainer.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jButton1.setText("Table");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelOutputContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButtonFind, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jComboBoxLSL, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxBatch, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxQuality, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxTestCode, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxOrder, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(datePicker1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBoxDateA, 0, 139, Short.MAX_VALUE)
                                .addComponent(jComboBoxTestName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBoxUSL, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanelTableContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(GraphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(HistoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBoxQuality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxDateA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxBatch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxTestCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxTestName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxLSL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxUSL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonFind)
                            .addComponent(jButtonClear)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelTableContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(HistoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                    .addComponent(GraphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanelOutputContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        jTabbedPane1.addTab("Main", jPanel2);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 841, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(272, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(463, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Log", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFindActionPerformed
        controller.buildGraph();
    }//GEN-LAST:event_jButtonFindActionPerformed
    
    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        controller.clearComponents();
        controller.resetFlagWaits();
    }//GEN-LAST:event_jButtonClearActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        controller.buildTable();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Gui().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JPanel GraphPanel;
    public static javax.swing.JPanel HistoPanel;
    public com.michaelbaranov.microba.calendar.DatePicker datePicker1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonFind;
    public javax.swing.JComboBox jComboBoxBatch;
    public javax.swing.JComboBox jComboBoxDateA;
    public javax.swing.JComboBox jComboBoxLSL;
    public javax.swing.JComboBox jComboBoxOrder;
    public javax.swing.JComboBox jComboBoxQuality;
    public javax.swing.JComboBox jComboBoxTestCode;
    public javax.swing.JComboBox jComboBoxTestName;
    public javax.swing.JComboBox jComboBoxUSL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelAverageContainer;
    private javax.swing.JPanel jPanelMeanContainer;
    private javax.swing.JPanel jPanelOutputContainer;
    private javax.swing.JPanel jPanelSumContainer;
    private javax.swing.JPanel jPanelTableContainer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTable jTableMain;
    private javax.swing.JTextArea jTextArea1;
    public static javax.swing.JTextField jTextFieldAverage;
    public static javax.swing.JTextField jTextFieldMedian;
    public static javax.swing.JTextField jTextFieldSumm;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseEntered(MouseEvent me) {
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() instanceof JButton) {
            JButton button = (JButton) me.getSource();
            if (button.getParent() instanceof JComboBox) {
                JComboBox parent = (JComboBox) button.getParent();
                //
                controller.fillComboStandard((JComboBoxM) parent);
                //
                parent.setEditable(true);
                parent.setBackground(INITIAL_BG_COLOR_COMBO);
            }
        }
    }
    
    @Override
    public void mousePressed(MouseEvent me) {
        if (me.getSource() instanceof JButton) {
            JButton button = (JButton) me.getSource();
            if (button.getParent() instanceof JComboBox) {
                //
                JComboBox parent = (JComboBox) button.getParent();
                //
                parent.setBackground(Color.orange);
                //
                parent.hidePopup(); // OBS! IMPORTANT
                //
//                parent.invalidate();
//                parent.repaint();
//                parent.updateUI();
            }
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent me) {
    }
    
    @Override
    public void mouseExited(MouseEvent me) {
    }
}
