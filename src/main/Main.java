/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import images.IconUrls;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import other.DemoRunner;
import other.HelpA;
import other.JComboBoxA;
import sql.SQL_Q;
import sql.ShowMessage;

/**
 *
 * @author KOCMOC
 */
public class Main extends javax.swing.JFrame implements ShowMessage, MouseListener {

    private Controller controller;
    private Properties p = HelpA.properties_load_properties("main.properties", false);
    public static Color INITIAL_BG_COLOR_COMBO;
    private ArrayList<JComboBox> JCOMBO_LIST = new ArrayList<>();
    private ArrayList<JComboBox> JCOMBO_OBLIGATORY_LIST = new ArrayList<>();
    public final static String DATE_FORMAT = "yy/MM/dd";
    public static final String VERSION = "1.01";
    private boolean BUILD_GRAPH_BTN_CLICKED = false;
    public static boolean LOG_CONNECTION_STRING = false;
    public static boolean RUNING_IN_NETBEANS = false;
    public final static String TAB_MAIN = "Main";
    public final static String TAB_LOG = "Log";
    //
    public final static boolean DEMO_MODE = true;

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        this.controller = new Controller(this, p);
        initOther();
        buildComboList();
        buildObligatoryComboList();
        addJComboListeners();
        setFontJBoxesTitleBorders();
        //
        if(DEMO_MODE){
             demo();
        }
       
    }
    
    private void demo(){
        //
        //Dont forget to look at "Controller.connect()" method
        //
        String quality = "1702860-ST110";
        jComboBoxQuality.addItem(quality);
        jComboBoxQuality.setSelectedItem(quality);
        //<#GFT-SPECIAL-DEMO>
//        jComboBoxQuality.setBackground(Color.BLACK);
        //</#GFT-SPECIAL-DEMO>
        String testCode = "10194";
        jComboBoxTestCode.addItem(testCode);
        jComboBoxTestCode.setSelectedItem(testCode);
        //
        String testName = "ML";
        jComboBoxTestName.addItem(testName);
        jComboBoxTestName.setSelectedItem(testName);
        //
        Thread x = new Thread(new DemoRunner(this,controller,1000));
        x.start();
    }

    private void initOther() {
        //
        datePicker1.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
        this.setTitle("MCLabStats " + VERSION);
        this.setIconImage(new ImageIcon(IconUrls.APP_ICON).getImage());
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.jLabelCursorHint.setVisible(false);
        //
        if (RUNING_IN_NETBEANS == false) {
            HelpA.hideTabByName(jTabbedPane1, TAB_LOG);
        } 
        //
    }

    public ArrayList<JComboBox> getJCOMBO_LIST() {
        return JCOMBO_LIST;
    }

    public boolean obligatoryBoxesFilled() {
        for (JComboBox jComboBox : JCOMBO_OBLIGATORY_LIST) {
            if (HelpA.getComboBoxSelectedValue_b(jComboBox) == null) {
                HelpA.showNotification("Obligatory fields not filled");
                return false;
            }
        }
        return true;
    }

    private void buildObligatoryComboList() {
        JCOMBO_OBLIGATORY_LIST.add(jComboBoxQuality);
        JCOMBO_OBLIGATORY_LIST.add(jComboBoxTestCode);
        JCOMBO_OBLIGATORY_LIST.add(jComboBoxTestName);
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
        //
        INITIAL_BG_COLOR_COMBO = jComboBoxQuality.getBackground();
        //
        for (JComboBox jComboBox : JCOMBO_LIST) {
            HelpA.addMouseListenerJComboBox(jComboBox, this);
            //
            JComboBoxA boxM = (JComboBoxA) jComboBox;
            boxM.setName(boxM.getPARAMETER());
        }
    }

    private void setFontJBoxesTitleBorders() {
        Font font = new Font("Arial", Font.PLAIN, 12);
        //
        for (JComboBox jComboBox : JCOMBO_LIST) {
            JPanel parent = (JPanel) jComboBox.getParent();
            TitledBorder border = (TitledBorder) parent.getBorder();
            border.setTitleFont(font);
//            border.setTitleColor(Color.GRAY);
        }
        //
        TitledBorder border = (TitledBorder) jPanel9.getBorder();
        border.setTitleFont(font);
        //
        TitledBorder border1 = (TitledBorder) jPanel17.getBorder();
        border1.setTitleFont(font);
    }

    @Override
    public void showMessage(String str) {
//        System.out.println("" + str);
        jTextArea1.append(HelpA.get_proper_date_time_same_format_on_all_computers() + "  " + str + "\n");
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
        HistoPanel = new javax.swing.JPanel();
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
        jPanelTableContainer = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableMain = new javax.swing.JTable();
        jTextFieldTest = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jComboBoxQuality = new other.JComboBoxA(SQL_Q.QUALITY,false);
        jPanel4 = new javax.swing.JPanel();
        jComboBoxOrder = new other.JComboBoxA(SQL_Q.ORDER,false);
        jPanel8 = new javax.swing.JPanel();
        jComboBoxBatch = new other.JComboBoxA(SQL_Q.BATCH,true);
        jPanel5 = new javax.swing.JPanel();
        jComboBoxTestCode = new other.JComboBoxA(SQL_Q.TEST_CODE,false);
        jPanel1 = new javax.swing.JPanel();
        jComboBoxTestName = new other.JComboBoxA(SQL_Q.TEST_NAME,false);
        jPanel10 = new javax.swing.JPanel();
        jComboBoxLSL = new other.JComboBoxA(SQL_Q.LSL,true);
        jPanel11 = new javax.swing.JPanel();
        jComboBoxUSL = new other.JComboBoxA(SQL_Q.USL,true);
        jPanel6 = new javax.swing.JPanel();
        jComboBoxDateA = new other.JComboBoxA(SQL_Q.TEST_DATE,false);
        jPanel9 = new javax.swing.JPanel();
        datePicker1 = new com.michaelbaranov.microba.calendar.DatePicker();
        jPanel17 = new javax.swing.JPanel();
        jButton_Prev_test_name = new javax.swing.JButton();
        jButton_Next_test_name = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jButtonFind = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButtonClear = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabelCursorHint = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        GraphPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        GraphPanel.setLayout(new java.awt.GridLayout(1, 1));

        HistoPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        HistoPanel.setLayout(new java.awt.GridLayout(1, 1));

        jPanelOutputContainer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelOutputContainer.setLayout(new java.awt.GridLayout(1, 3, 5, 5));

        jPanelSumContainer.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelSumContainer.setLayout(new java.awt.GridLayout(2, 1));

        jLabel1.setLabelFor(jTextFieldSumm);
        jLabel1.setText("<html><p style='margin-left: 5px;font-weight:bold;'>" + "Sum" + "</h3></p>");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelSumContainer.add(jLabel1);

        jTextFieldSumm.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelSumContainer.add(jTextFieldSumm);

        jPanelOutputContainer.add(jPanelSumContainer);

        jPanelAverageContainer.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelAverageContainer.setLayout(new java.awt.GridLayout(2, 1));

        jLabel3.setLabelFor(jTextFieldSumm);
        jLabel3.setText("<html><p style='margin-left: 5px;font-weight:bold;'>" + "Average" + "</h3></p>");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelAverageContainer.add(jLabel3);

        jTextFieldAverage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelAverageContainer.add(jTextFieldAverage);

        jPanelOutputContainer.add(jPanelAverageContainer);

        jPanelMeanContainer.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelMeanContainer.setLayout(new java.awt.GridLayout(2, 1));

        jLabel4.setLabelFor(jTextFieldSumm);
        jLabel4.setText("<html><p style='margin-left: 5px;font-weight:bold;'>" + "Median" + "</h3></p>");
        jLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelMeanContainer.add(jLabel4);

        jTextFieldMedian.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelMeanContainer.add(jTextFieldMedian);

        jPanelOutputContainer.add(jPanelMeanContainer);

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

        jTextFieldTest.setText("30");
        jTextFieldTest.setToolTipText("Histogram frequency regulator");
        jTextFieldTest.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jPanel12.setLayout(new java.awt.GridLayout(2, 4));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "QUALITY*", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), java.awt.Color.black)); // NOI18N
        jPanel7.setLayout(new java.awt.GridLayout(1, 1));
        jPanel7.add(jComboBoxQuality);

        jPanel12.add(jPanel7);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "ORDER", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), java.awt.Color.black)); // NOI18N
        jPanel4.setLayout(new java.awt.GridLayout(1, 1, 10, 0));
        jPanel4.add(jComboBoxOrder);

        jPanel12.add(jPanel4);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "BATCH", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), java.awt.Color.black)); // NOI18N
        jPanel8.setLayout(new java.awt.GridLayout(1, 1));
        jPanel8.add(jComboBoxBatch);

        jPanel12.add(jPanel8);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "TEST CODE*", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), java.awt.Color.black)); // NOI18N
        jPanel5.setLayout(new java.awt.GridLayout(1, 1));
        jPanel5.add(jComboBoxTestCode);

        jPanel12.add(jPanel5);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "TEST NAME*", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), java.awt.Color.black)); // NOI18N
        jPanel1.setLayout(new java.awt.GridLayout(1, 1));
        jPanel1.add(jComboBoxTestName);

        jPanel12.add(jPanel1);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "LSL", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), java.awt.Color.black)); // NOI18N
        jPanel10.setLayout(new java.awt.GridLayout(1, 0));
        jPanel10.add(jComboBoxLSL);

        jPanel12.add(jPanel10);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "USL", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), java.awt.Color.black)); // NOI18N
        jPanel11.setLayout(new java.awt.GridLayout(1, 0));
        jPanel11.add(jComboBoxUSL);

        jPanel12.add(jPanel11);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "DATE FROM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), java.awt.Color.black)); // NOI18N
        jPanel6.setLayout(new java.awt.GridLayout(1, 1));
        jPanel6.add(jComboBoxDateA);

        jPanel12.add(jPanel6);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "DATE TO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), java.awt.Color.black)); // NOI18N
        jPanel9.setLayout(new java.awt.GridLayout(1, 1));

        try {
            datePicker1.setDate(null);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        datePicker1.setShowNoneButton(false);
        jPanel9.add(datePicker1);

        jPanel12.add(jPanel9);

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "NAVIGATION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), java.awt.Color.black)); // NOI18N
        jPanel17.setLayout(new java.awt.GridLayout(1, 1));

        jButton_Prev_test_name.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton_Prev_test_name.setText("<");
        jButton_Prev_test_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Prev_test_nameActionPerformed(evt);
            }
        });
        jPanel17.add(jButton_Prev_test_name);

        jButton_Next_test_name.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton_Next_test_name.setText(">");
        jButton_Next_test_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Next_test_nameActionPerformed(evt);
            }
        });
        jPanel17.add(jButton_Next_test_name);

        jPanel12.add(jPanel17);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/undo_2.png"))); // NOI18N
        jButton5.setToolTipText("reset all graphs");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(204, 204, 204));
        jLabel46.setText("MCLabStats");

        jPanel13.setLayout(new java.awt.GridLayout(1, 0, 2, 0));

        jButtonFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/graph.png"))); // NOI18N
        jButtonFind.setToolTipText("show graph");
        jButtonFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFindActionPerformed(evt);
            }
        });
        jPanel13.add(jButtonFind);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/table_2.png"))); // NOI18N
        jButton1.setToolTipText("show result in table");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton1);

        jButtonClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/undo_2.png"))); // NOI18N
        jButtonClear.setToolTipText("reset all search fields");
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });
        jPanel13.add(jButtonClear);

        jPanel14.setLayout(new java.awt.GridLayout(1, 0, 2, 0));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bar-graph.png"))); // NOI18N
        jButton3.setToolTipText("show histogram");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton3);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/graph.png"))); // NOI18N
        jButton4.setToolTipText("show frequency polygon graph");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton4);

        jPanel15.setLayout(new java.awt.GridLayout(1, 0, 2, 0));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cursor.png"))); // NOI18N
        jButton2.setToolTipText("add markers to graph");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel15.add(jButton2);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cursor_clear.png"))); // NOI18N
        jButton6.setToolTipText("remove markers from graph");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel15.add(jButton6);

        jLabel2.setText("*Obligatory fields");

        jPanel16.setLayout(new java.awt.GridLayout(1, 0, 2, 0));

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cursor.png"))); // NOI18N
        jButton8.setToolTipText("add markers to graph");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel16.add(jButton8);

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cursor_clear.png"))); // NOI18N
        jButton9.setToolTipText("remove markers from graph");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel16.add(jButton9);

        jLabelCursorHint.setText("Move the cursor by clicking on it");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 943, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(0, 86, Short.MAX_VALUE))
                    .addComponent(jPanelTableContainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(GraphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelCursorHint)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(141, 141, 141)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(HistoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldTest, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanelOutputContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(22, 22, 22)))
                .addComponent(jPanelTableContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5))
                        .addGap(2, 2, 2))
                    .addComponent(jLabelCursorHint))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(GraphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HistoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelOutputContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addGap(5, 5, 5))
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
                .addContainerGap(368, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(531, Short.MAX_VALUE))
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
        buildGraphs();
    }//GEN-LAST:event_jButtonFindActionPerformed

    public void buildGraphs() {
        if (controller.buildGraphs()) {
            //
            jComboBoxQuality.setEnabled(false);
            jComboBoxTestCode.setEnabled(false);
            //
            BUILD_GRAPH_BTN_CLICKED = true;
        }
    }

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        controller.reset();
    }//GEN-LAST:event_jButtonClearActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        controller.buildTableByThread(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        controller.switchToHistogramBarGraph();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        controller.switchToFrequencyPolygonGraph();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        controller.resetGraphs();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        controller.addDiffMarkerPointsCommonGraph();
        jLabelCursorHint.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        controller.removeDiffMarkerPointsCommonGraph();
        jLabelCursorHint.setVisible(false);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        controller.addDiffMarkerPointsPolygonGraph();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        controller.removeDiffMarkerPointsPolygonGraph();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton_Prev_test_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Prev_test_nameActionPerformed
        controller.showTestItemOnBtnClick(false);
    }//GEN-LAST:event_jButton_Prev_test_nameActionPerformed

    private void jButton_Next_test_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Next_test_nameActionPerformed
        controller.showTestItemOnBtnClick(true);
    }//GEN-LAST:event_jButton_Next_test_nameActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        if (HelpA.runningInNetBeans("MCLabStats.jar")) {
            RUNING_IN_NETBEANS = true;
            LOG_CONNECTION_STRING = true;
        } else {
            HelpA.err_output_to_file();
            RUNING_IN_NETBEANS = false;
            
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
               Main main =  new Main();
               main.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JPanel GraphPanel;
    public static javax.swing.JPanel HistoPanel;
    public com.michaelbaranov.microba.calendar.DatePicker datePicker1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    public javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonFind;
    private javax.swing.JButton jButton_Next_test_name;
    private javax.swing.JButton jButton_Prev_test_name;
    public javax.swing.JComboBox jComboBoxBatch;
    public javax.swing.JComboBox jComboBoxDateA;
    public javax.swing.JComboBox jComboBoxLSL;
    public javax.swing.JComboBox jComboBoxOrder;
    public javax.swing.JComboBox jComboBoxQuality;
    public javax.swing.JComboBox jComboBoxTestCode;
    public javax.swing.JComboBox jComboBoxTestName;
    public javax.swing.JComboBox jComboBoxUSL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabelCursorHint;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
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
    public static javax.swing.JTextField jTextFieldTest;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseEntered(MouseEvent me) {
        if (me.getSource() instanceof JButton) {
            JButton button = (JButton) me.getSource();
            if (button.getParent() instanceof JComboBox) {
//                if (BUILD_GRAPH_BTN_CLICKED) {
//                    controller.reset();
//                    BUILD_GRAPH_BTN_CLICKED = false;
//                }
            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() instanceof JButton) {
            JButton button = (JButton) me.getSource();
            if (button.getParent() instanceof JComboBox) {
                //
                JComboBox parent = (JComboBox) button.getParent();
                //
                controller.fillComboStandard((JComboBoxA) parent);
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
