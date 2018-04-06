package com.adyen.reportMerger.gui;

import com.adyen.reportMerger.entities.ErrorTypes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by andrew on 9/27/16.
 */
public class ErrorScreen {

    private static JFrame errorFrame;
    private static JFrame previousInterface;

    private JPanel contentPane;
    private JPanel panel;
    private JPanel panel_1;
    private JPanel panel_2;
    private JPanel panel_3;
    private JPanel panel_3_1;
    private JPanel panel_3_2;

    private JButton backButton;
    private JButton exitButton;

    private String error;

    private JLabel errorHeader;
    private JLabel errorDescription;

    public ErrorScreen (JFrame previousInterface, ErrorTypes errorType) {
        this.previousInterface = previousInterface;
        Rectangle possition = previousInterface.getBounds();
        error = errorType.getDescription();
        initialize();
        errorFrame.setBounds(possition);
//        previousInterface.setVisible(false);
        errorFrame.setVisible(true);

    }


    private void initialize (){
        errorFrame = new JFrame();
        errorFrame.setTitle("AdyenReportMerger");
        errorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        errorFrame.setSize(new Dimension(1200, 400));
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        errorFrame.setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(4, 1, 0, 0));



		/*Creating the panels*/
        panel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        contentPane.add(panel);

        panel_1 = new JPanel();
        FlowLayout flowLayout_1 = (FlowLayout) panel.getLayout();
        flowLayout_1.setAlignment(FlowLayout.LEFT);
        contentPane.add(panel_1);

        panel_2 = new JPanel();
        FlowLayout flowLayout_2 = (FlowLayout) panel.getLayout();
        flowLayout_2.setAlignment(FlowLayout.LEFT);
        contentPane.add(panel_2);

        panel_3 = new JPanel();
        contentPane.add(panel_3);
        panel_3.setLayout(new GridLayout(1, 2, 0, 0));

        panel_3_1 = new JPanel();
        panel_3.add(panel_3_1);

        panel_3_2 = new JPanel();
        panel_3.add(panel_3_2);


		/*Adding content to the panels*/
        errorHeader = new JLabel("Unfortunately we have not been able to process your request. See the errors bellow and try again:");
        errorHeader.setVerticalAlignment(SwingConstants.TOP);
        panel.add(errorHeader);


        errorDescription = new JLabel(error);

        panel_1.add(errorDescription);
        panel_1.validate();

        backButton = new JButton("Back");
        panel_3_1.add(backButton);

        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Rectangle currentPossition = errorFrame.getBounds();
                errorFrame.setVisible(false);
                previousInterface.setVisible(true);
                previousInterface.setBounds(currentPossition);
            }
        });


        exitButton = new JButton("Exit");
        panel_3_2.add(exitButton);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static JFrame getErrorFrame() {
        return errorFrame;
    }
}
