package com.adyen.reportMerger.gui;

import com.adyen.reportMerger.Util.ContextMenuMouseListener;
import com.adyen.reportMerger.Util.Validate;
import com.adyen.reportMerger.entities.LogFileHandler;
import com.adyen.reportMerger.entities.ReportLevels;
import com.adyen.reportMerger.entities.ReportTypes;
import com.adyen.reportMerger.runners.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

/**
 * Created by andrew on 11/1/16.
 */
public class StartScreen implements ActionListener {

    public static JFrame frame;
    public static JComboBox levelCombo;
    public static JComboBox reportTypeCombo;
    public static JComboBox mergeByCombo;
    public static JTextField companyCode;
    public static JLabel companyCodeError;
    public static JLabel merchantCodeLabel;
    public static JTextField merchantCode;
    public static JLabel merchantCodeError;
    public static JTextField reportUserName;
    public static JLabel reportUserNameError;
    public static JPasswordField passwordField;
    public static JLabel passwordError;
    public static JButton pathButton;
    public static JButton continueButton;
    public static JButton exitButton;
    public static JLabel reportUserLabelEnd;
    public static JLabel pathError;

    public static String path;
    public static JFileChooser chooser;

    public static ReportTypes reportType;
    public static ReportLevels reportLevel;


    public StartScreen() {

        frame = new JFrame("Adyen Report Merger");
        frame.addWindowListener(exitListener);
        frame.setSize(new Dimension(1200, 400));

        JPanel jpanel = new JPanel();

        int i = 10;
        int j = 4;

        JPanel[][] panelHolder = new JPanel[i][j];
        jpanel.setLayout(new GridLayout(i,j));
        frame.add(jpanel);


        for(int m = 0; m < i; m++) {
            for(int n = 0; n < j; n++) {

                panelHolder[m][n] = new JPanel(new FlowLayout(FlowLayout.LEFT));

                jpanel.add(panelHolder[m][n]);
            }
        }

        JLabel descriptionLabel = new JLabel("Set below Values to find reports to merge");
        panelHolder[0][1].add(descriptionLabel);

        JLabel levelLabel = new JLabel("Report level: ");
        panelHolder[1][0].add(levelLabel);

        //get All report Levels from Enum and Add to combo box
        java.util.List<String> enumValues = new ArrayList<>();
        for (ReportLevels reportL : ReportLevels.values()) {
            enumValues.add(reportL.getReportLevelDescription());
        }
        String[] comboBoxArray = enumValues.toArray(new String[0]);

        levelCombo = new JComboBox(comboBoxArray);
        levelCombo.setSelectedIndex(0);
        panelHolder[1][1].add(levelCombo, BorderLayout.LINE_START);


        //get All report types from Enum and add to combo box
        JLabel reportTypeLabel = new JLabel("Report type: ");
        panelHolder[2][0].add(reportTypeLabel);

        enumValues.clear();
        for(ReportTypes reportType : ReportTypes.values()) {
            enumValues.add(reportType.getReportDescription());
        }
        comboBoxArray = enumValues.toArray(new String[0]);
        reportTypeCombo = new JComboBox<>(comboBoxArray);
        reportTypeCombo.setSelectedIndex(0);

        panelHolder[2][1].add(reportTypeCombo, BorderLayout.LINE_START);


        //Merge By Combo declare here so it can be changed by action listener
        mergeByCombo = new JComboBox<String>();
        mergeByCombo.addItem("Batch range");
        mergeByCombo.addItem("Date range");
        mergeByCombo.setSelectedIndex(0);

        //Add event listen to LevelComboBox
        levelCombo.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {

                //if Merchant Level
                if (levelCombo.getSelectedIndex() == 0) {
                    reportTypeCombo.removeAllItems();
                    reportTypeCombo.addItem("Settlement detail report");
                    reportTypeCombo.addItem("Received payments report");
                    reportTypeCombo.addItem("Payments accounting report");
                    reportTypeCombo.addItem("Dispute report");
                    reportTypeCombo.addItem("Case management report");
                    reportTypeCombo.addItem("MarketPlace payment account report");
                    reportTypeCombo.setSelectedIndex(0);

                    mergeByCombo.removeAllItems();
                    if (reportTypeCombo.getSelectedIndex() == 0) {
                        mergeByCombo.addItem("Batch range");
                        mergeByCombo.addItem("Date range");


                    } else {
                        mergeByCombo.addItem("Date range");
                    }
                    mergeByCombo.setSelectedIndex(0);

                    merchantCodeLabel.setText("Merchant Account Code");

                }
                //If company level
                if (levelCombo.getSelectedIndex() == 1) {
                    reportTypeCombo.removeAllItems();
                    reportTypeCombo.addItem("Dispute report");
                    reportTypeCombo.addItem("Received payments report");
                    reportTypeCombo.addItem("Case management report");

                    reportTypeCombo.setSelectedIndex(1);

                    mergeByCombo.removeAllItems();
                    mergeByCombo.addItem("Date range");

                    mergeByCombo.setSelectedIndex(0);

                    merchantCodeLabel.setText("Merchant Account Code");
                }
                //If Multi merchant level
                if (levelCombo.getSelectedIndex() == 2) {
                    reportTypeCombo.removeAllItems();
                    reportTypeCombo.addItem("Settlement detail report");
                    reportTypeCombo.addItem("Received payments report");
                    reportTypeCombo.addItem("Payments accounting report");
                    reportTypeCombo.addItem("Dispute report");
                    reportTypeCombo.addItem("MarketPlace payment account report");
                    reportTypeCombo.setSelectedIndex(0);

                    mergeByCombo.removeAllItems();
                    mergeByCombo.addItem("Date range");
                    mergeByCombo.setSelectedIndex(0);

                    merchantCodeLabel.setText("Merchant Account Codes comma separated");

                }
            }
        } );


        //Add event listen to ReportComboBox
        reportTypeCombo.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {

                //if Merchant Level
                if (levelCombo.getSelectedIndex() == 0) {

                    mergeByCombo.removeAllItems();
                    if (reportTypeCombo.getSelectedIndex() == 0) {
                        mergeByCombo.addItem("Batch range");
                        mergeByCombo.addItem("Date range");
                    } else {
                        mergeByCombo.addItem("Date range");
                    }
                    mergeByCombo.setSelectedIndex(0);

                    merchantCodeLabel.setText("Merchant Account Code");

                }
                //If company level
                if (levelCombo.getSelectedIndex() == 1) {

                    mergeByCombo.removeAllItems();
                    mergeByCombo.addItem("Date range");

                    mergeByCombo.setSelectedIndex(0);

                    merchantCodeLabel.setText("Merchant Account Code");
                }
                //If Multi merchant level
                if (levelCombo.getSelectedIndex() == 2) {
                    mergeByCombo.removeAllItems();
                    mergeByCombo.addItem("Date range");
                    mergeByCombo.setSelectedIndex(0);
                    merchantCodeLabel.setText("Merchant Account Codes comma separated");

                }
            }
        } );

        //Add Merge by
        panelHolder[3][0].add(new JLabel("Merge by: "));
        panelHolder[3][1].add(mergeByCombo);

        //Add Company Account TextField and label


        //Creates the right-click window for copy and paste. This is an awsome class from StackOverflow.
        MouseListener popupListener = new ContextMenuMouseListener();

        pathButton = new JButton("Choose path to save the reports");
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(System
                .getProperty("user.home")));
        path = chooser.getCurrentDirectory().getAbsolutePath();
        pathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                chooser.setDialogTitle("Choose path to save the file");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                chooser.setAcceptAllFileFilterUsed(false);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                    path = chooser.getSelectedFile().getAbsolutePath();
                    File f = new File(path);
                    if (f.exists()) {

                        pathError.setText(path);

                    }

                    else {
                        path = System.getProperty("user.home");
                        pathError
                                .setText("The path does not exist, using default path: "
                                        + path);
                    }
                }

            }
        });


        // Add Company account fields
        panelHolder[4][0].add(new JLabel("Company Account Code: "));
        companyCode = new JTextField();
        companyCode.addMouseListener(popupListener);
        companyCode.setText("");
        panelHolder[4][1].add(companyCode);
        companyCode.setColumns(10);

        FocusListener companyCodeListener = new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {

//				System.out.println("in companyCodeListener");
                reportUserLabelEnd.setText("@Company." + companyCode.getText());
            }

            @Override
            public void focusGained(FocusEvent e) {

            }
        };
        companyCode.addFocusListener(companyCodeListener);

        companyCodeError = new JLabel("");
        panelHolder[4][2].add(companyCodeError);


        //Add Merchant AccountCode Fields
        merchantCodeLabel = new JLabel("Merchant Account Code");
        panelHolder[5][0].add(merchantCodeLabel);
        merchantCode = new JTextField();
        merchantCode.addMouseListener(popupListener);
        merchantCode.setText("");
        merchantCode.setColumns(10);
        panelHolder[5][1].add(merchantCode);

        merchantCodeError = new JLabel("");
        panelHolder[5][2].add(merchantCodeError);


        //Add ReportUser Fields
        panelHolder[6][0].add(new JLabel("Report user: "));
        reportUserName = new JTextField();
        reportUserName.addMouseListener(popupListener);
        reportUserName.setText("");
        panelHolder[6][1].add(reportUserName);
        reportUserName.setColumns(10);

        reportUserLabelEnd = new JLabel("@Company." + companyCode.getText());
        panelHolder[6][1].add(reportUserLabelEnd);

        reportUserNameError = new JLabel("");
        panelHolder[6][2].add(reportUserNameError);


        //Add Password Field
        panelHolder[7][0].add(new JLabel("Report user password: "));

        passwordField = new JPasswordField();
        passwordField.addMouseListener(popupListener);
        panelHolder[7][1].add(passwordField);
        passwordField.setColumns(10);

        passwordError = new JLabel("");
        panelHolder[7][2].add(passwordError);

        //Add pathChooser
        panelHolder[1][3].add(pathButton);
        pathError = new JLabel(System.getProperty("user.home"));
        panelHolder[2][3].add(pathError);

        //Add continuew button
        // Panel 10
        continueButton = new JButton("Continue");
        panelHolder[8][1].add(continueButton);

        //ensure validate resets when errors are fixed
        ActionListener continueChecker = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ProgressIndicatorScreen progressIndicatorScreen = new ProgressIndicatorScreen();

                progressIndicatorScreen.addInfoToTextArea("Validating input..");

                if (!Validate.validateStartscreen()){
                    progressIndicatorScreen.addInfoToTextArea("input valid");
                    reportType = ReportTypes.getTypeFromDescription((reportTypeCombo.getSelectedItem().toString()));
                    reportLevel = ReportLevels.getLevelFromDescription(levelCombo.getSelectedItem().toString());
                    new Controller(StartScreen.this);
                } else {
                    ProgressIndicatorScreen.addInfoToTextArea("input error..");
                }
            }
        };

        continueButton.addActionListener(continueChecker);

        exitButton = new JButton("Exit");
        panelHolder[8][2].add(exitButton);

        exitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }
        });

        frame.setVisible(true);

    }


    //not sure why Main is here again while there is a main class. the main class is being used when clicking the jar
    public static void main(String[] args) {

        String java = System.getProperty("java.specification.version");
        double version = Double.valueOf(java);
        double reqVer = 1.6;
        if (version < reqVer) {
            //Show errorScreen with Wrong version msg
        }
        else javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StartScreenNewLayout();
            }
        });

    }

    public static String getPath() {
        return path;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    WindowListener exitListener = new WindowAdapter() {

        @Override
        public void windowClosing(WindowEvent e) {
            int confirm = JOptionPane.showOptionDialog(
                    null, "Are You Sure to Close Application?",
                    "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (confirm == 0) {
                System.exit(0);
            }
        }
    };
}
