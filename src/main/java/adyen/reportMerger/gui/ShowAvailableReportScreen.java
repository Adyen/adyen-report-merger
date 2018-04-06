package com.adyen.reportMerger.gui;


import com.adyen.reportMerger.Util.ConnectionUtil;
import com.adyen.reportMerger.entities.CsvClass;
import com.adyen.reportMerger.entities.LogFileHandler;
import com.adyen.reportMerger.entities.MergeTypes;
import com.adyen.reportMerger.entities.ReportLocation;

import com.adyen.reportMerger.runners.Controller;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * Created by andrew on 9/22/16.
 * This class is used to show the user a list of all available reports
 * It allows the user to make a selection in the reports and merge those reports
 */
public class ShowAvailableReportScreen {

    //used to go back to startscreen with same values
    public static StartScreen startScreen;
    //Take startScreen frame to copy properties from
    public static JFrame startScreenFrame;

    //ShowAvailableReport general Elements elements
    public static JFrame showAvailableReportFrame ;
    public JTextArea pageDescription;
    public JPanel searchParameterPanel;
    public JScrollPane searchResultPanel;
    public JPanel buttonPanel;
    private JButton mergeButton;
    private JButton backButton;



    //ShowAvailableReport batchSearch Elements
    private JComboBox<String> startBatchComboBox;
    private JComboBox<String> endBatchComboBox;


    //ShowAvailableReports dateSearch Elements
    private JDateChooser startDate;
    private JDateChooser endDate;


    //Initial list off all reports, used to reset filers
    public List<ReportLocation> reportLocationList;

    //New list of reports based on selection of user
    public JList<ReportLocation> selectReportLocationJList;
    public List<ReportLocation> selectReportLocationList;


    public ShowAvailableReportScreen() {

    }

    //TODO show selectedReports with Name Date and size
    //TODO make sure users can either Merge all visible Reports or specific selection
    //TODO add validation to ensure startBatch < endBatch and startDate < endDate

    public ShowAvailableReportScreen( JFrame frame, List<ReportLocation> reportLocationList, StartScreen startScreen ) {

        this.startScreen = startScreen;
        this.startScreenFrame = frame;
        this.reportLocationList = reportLocationList;


        MergeTypes mergeType = MergeTypes.getMergeTypeFromDescription(startScreen.mergeByCombo.getSelectedItem().toString());

        //Initialize new JFrame
        showAvailableReportFrame = new JFrame();
        //Copy some properties from startScreen Frame
        showAvailableReportFrame.setBounds(startScreenFrame.getBounds());
        showAvailableReportFrame.setTitle(startScreenFrame.getTitle());
        showAvailableReportFrame.addWindowListener(exitListener);

        //set Description
        String pageDescriptionText = "";
        switch (mergeType) {
            case BATCHRANGE: {
                pageDescriptionText = "This page allows you to merge reports by batch range. \n"
                        +"Please select the batch range of the reports you want to include in the new report";
            }
                break;

            case DATERANGE: {
                pageDescriptionText = "This page allows you to merge reports by date range. \n"
                        +"Please select the date range of the reports you want to include in the new report. \n"
                        +"Keep in mind the dates are referring to the generation date of the report.";
            }

        }

        pageDescription = new JTextArea();
        pageDescription.setEditable(false);
        pageDescription.setText(pageDescriptionText);
        showAvailableReportFrame.getContentPane().add(pageDescription);


        //Add search parameter panel
        searchParameterPanel = new JPanel();
        showAvailableReportFrame.getContentPane().add(searchParameterPanel);

        switch (mergeType) {
            case BATCHRANGE : {
                //Set Elements for BatchRange
                //Find available batch numbers in reportLocationList
                reportLocationList
                        .stream()
                        .sorted((object1, object2) -> Integer.toString(object1.getBatchNumber()).compareTo(Integer.toString(object2.getBatchNumber())));

                Collections.sort(reportLocationList, (o1, o2) -> {
                    if (o1.getBatchNumber() < 0  || o2.getReportModificationDate() == null)
                        return 0;
                    return o1.getReportModificationDate().compareTo(o2.getReportModificationDate());
                });

                startBatchComboBox = new JComboBox<>();
                for (ReportLocation r : reportLocationList) {
                    startBatchComboBox.addItem(Integer.toString(r.batchNumber));
                }


                searchParameterPanel.add(startBatchComboBox);

                // This reverts the List.
                Collections.reverse(reportLocationList);
                endBatchComboBox = new JComboBox<>();
                for (ReportLocation r : reportLocationList) {
                    endBatchComboBox.addItem(Integer.toString(r.batchNumber));
                }

                searchParameterPanel.add(endBatchComboBox);

                //Revert the list back
                Collections.reverse(reportLocationList);

                //Add search to startbatchComboBox
                startBatchComboBox.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectReportLocationList  = new ArrayList<>();
                        // Set selected list to only show reports between startBatch and endBatch
                        for (ReportLocation r : reportLocationList) {

                            if ((r.getBatchNumber()  > Integer.parseInt(startBatchComboBox.getSelectedItem().toString() ) -1)
                                    && (r.getBatchNumber()  < Integer.parseInt(endBatchComboBox.getSelectedItem().toString() ) +1)
                                    ) {
                                selectReportLocationList.add(r);
                            }
                        }

                        setSearchResultJList();

                    }
                });

                //Add search to endbatchComboBox
                endBatchComboBox.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectReportLocationList  = new ArrayList<>();
                        // Set selected list to only show reports between startBatch and endBatch
                        for (ReportLocation r : reportLocationList) {

                            if ((r.getBatchNumber()  > Integer.parseInt(startBatchComboBox.getSelectedItem().toString() ) -1)
                                    && (r.getBatchNumber()  < Integer.parseInt(endBatchComboBox.getSelectedItem().toString() ) +1)
                                    ) {
                                selectReportLocationList.add(r);
                            }
                        }

                        setSearchResultJList();

                    }
                });

            }
                break;

            case DATERANGE : {
                //Set elements for DateRange
                reportLocationList
                        .stream()
                        .sorted((object1, object2) -> object1.getReportModificationDate().compareTo(object2.getReportModificationDate()));

                Collections.sort(reportLocationList, (o1, o2) -> {
                    if (o1.getReportModificationDate() == null || o2.getReportModificationDate() == null)
                        return 0;
                    return o1.getReportModificationDate().compareTo(o2.getReportModificationDate());
                });

                startDate = new JDateChooser();
                startDate.setMinSelectableDate(reportLocationList.get(0).getReportModificationDate());
                startDate.setMaxSelectableDate(reportLocationList.get(reportLocationList.size()-1).getReportModificationDate());
                startDate.setDateFormatString("dd-MM-yyyy");
                startDate.setDate(reportLocationList.get(0).getReportModificationDate());

                searchParameterPanel.add(startDate);


                endDate = new JDateChooser();
                endDate.setMinSelectableDate(reportLocationList.get(0).getReportModificationDate());
                endDate.setMaxSelectableDate(reportLocationList.get(reportLocationList.size()-1).getReportModificationDate());
                endDate.setDateFormatString("dd-MM-yyyy");
                endDate.setDate(reportLocationList.get(reportLocationList.size()-1).getReportModificationDate());

                searchParameterPanel.add(endDate);

                //Add search to startDateComboBox
                startDate.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        selectReportLocationList  = new ArrayList<>();
                        // Set selected list to only show reports between startBatch and endBatch
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(startDate.getDate());
                        cal.add(Calendar.DATE, -1);
                        Date startDateMin1 = cal.getTime();

                        Calendar cal2 = Calendar.getInstance();
                        cal2.setTime(endDate.getDate());
                        cal2.add(Calendar.DATE, +1);
                        Date endDatePlus1 = cal2.getTime();

                        for (ReportLocation r : reportLocationList) {
                            if (r.getReportModificationDate().after(startDateMin1)
                                    && r.getReportModificationDate().before(endDatePlus1)) {
                                selectReportLocationList.add(r);
                            }
                        }

                        setSearchResultJList();
                    }

                });


                //Add search to endDateComboBox
                endDate.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        selectReportLocationList  = new ArrayList<>();
                        // Set selected list to only show reports between startBatch and endBatch
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(startDate.getDate());
                        cal.add(Calendar.DATE, -1);
                        Date startDateMin1 = cal.getTime();

                        Calendar cal2 = Calendar.getInstance();
                        cal2.setTime(endDate.getDate());
                        cal2.add(Calendar.DATE, +1);
                        Date endDatePlus1 = cal2.getTime();

                        for (ReportLocation r : reportLocationList) {
                            if (r.getReportModificationDate().after(startDateMin1)
                                    && r.getReportModificationDate().before(endDatePlus1)) {
                                selectReportLocationList.add(r);
                            }
                        }

                        setSearchResultJList();
                    }

                });
            }
        }


        //Initialise Buttons
        buttonPanel = new JPanel();
        showAvailableReportFrame.getContentPane().add(buttonPanel);
        backButton = new JButton("Go back to start screen");
        mergeButton = new JButton("Merge the selected reports");

        //Add buttons to Frame
        buttonPanel.add(backButton);
        buttonPanel.add(mergeButton);

        // Set SearchResultList and ensure we only show new Report name
        this.selectReportLocationList = reportLocationList;

        selectReportLocationJList = new JList(new Vector<>(selectReportLocationList));

        //Testing customRenderer  not working yet
//        selectReportLocationJList.setCellRenderer(new ReportLocationRenderer());


        //Working renderer
        selectReportLocationJList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof ReportLocation) {
                    ((JLabel) renderer).setText(((ReportLocation) value).getNewReportName());
                }
                return renderer;
            }
        });

        this.searchResultPanel = new JScrollPane(selectReportLocationJList);
        showAvailableReportFrame.getContentPane().add(searchResultPanel);




        // Set LayOut
        GridLayout myLayout = new GridLayout(4, 1);
        showAvailableReportFrame.getContentPane().setLayout(myLayout);



        //Add functionality to buttons

        //Add back function to back Button
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showAvailableReportFrame.setVisible(false);
                Rectangle currentPosition = showAvailableReportFrame.getBounds();
                startScreen.frame.setVisible(true);
                startScreen.frame.setBounds(currentPosition);
            }
        });


        //Add functionality to Merge Button
        mergeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ProgressIndicatorScreen.progressIndicatorFrame.toFront();


                String userName = startScreen.reportUserName.getText() + "@Company."+startScreen.companyCode.getText();
                char [] passWord = startScreen.passwordField.getPassword();
                String credentialString = ConnectionUtil.getCredentialString(userName, passWord);


                //Order by BatchNumber
                Collections.sort(selectReportLocationList, (o1, o2) -> {
                    if (o1.getBatchNumber() < 0  || o2.getReportModificationDate() == null)
                        return 0;
                    return o1.getReportModificationDate().compareTo(o2.getReportModificationDate());
                });

                //Order by MerchantName
                Collections.sort(selectReportLocationList, (o1, o2) -> {
                    if (o1.getAccountCode() == null  || o2.getAccountCode() == null)
                        return 0;
                    return o1.getAccountCode().compareTo(o2.getAccountCode());
                });



                new CsvClass( selectReportLocationList, startScreen.path, credentialString, true, mergeType);


            }
        });





        //Show Frame
        showAvailableReportFrame.setVisible(true);

    }


    //remove the searchlist panel and content and readd
    public void setSearchResultJList () {

        showAvailableReportFrame.getContentPane().remove(searchResultPanel);
        searchResultPanel.remove(selectReportLocationJList);

        selectReportLocationJList = new JList(new Vector<>(selectReportLocationList));
        selectReportLocationJList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof ReportLocation) {
                    ((JLabel) renderer).setText(((ReportLocation) value).getNewReportName());
                }
                return renderer;
            }
        });

        this.searchResultPanel = new JScrollPane(selectReportLocationJList);
        this.showAvailableReportFrame.getContentPane().add(searchResultPanel);

        showAvailableReportFrame.validate();
    }

    WindowListener exitListener = new WindowAdapter() {

        @Override
        public void windowClosing(WindowEvent e) {
            int confirm = JOptionPane.showOptionDialog(
                    null, "Are You Sure to Close Application?",
                    "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (confirm == 0) {
//                Controller.fileHandler.close();
//                LogFileHandler.deleteEmptyLogFile(Controller.getPath());
                System.exit(0);
            }
        }
    };

}
