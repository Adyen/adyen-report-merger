package com.adyen.reportMerger.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;



/**
 * Created by andrew on 9/29/16.
 */
public final class DownloadResultScreen {

    //TextLabel showing what this screen is
    //TextLabel showing Result of Download
    //TextLabel showing Path of file
    //Optional Show finder to open file instantly


    public DownloadResultScreen (JFrame previousFrame, boolean result, String path) {

        JFrame resultScreen = new JFrame("Adyen Report Merger");
        resultScreen.setBounds(previousFrame.getBounds());
        resultScreen.setTitle(previousFrame.getTitle());
        resultScreen.addWindowListener(exitListener);

        previousFrame.setVisible(false);

        JPanel panel = new JPanel();
        JLabel mergeResult = new JLabel();
        panel.add(mergeResult);
        if (result) {
            mergeResult.setText("The files have been merged and are stored in the specified path");
        } else {
            mergeResult.setText("The files have NOT been merged! Please consult the lofFile in the specified path");
        }

        resultScreen.add(panel);

        JButton backButton = new JButton("Back");
        panel.add(backButton);
        JButton exitButton = new JButton("Exit");
        panel.add(exitButton);

        JButton button = new JButton("Open File");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {

                try {
                    Desktop.getDesktop().open(new File(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        panel.add(button);

        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Rectangle currentPossition = resultScreen.getBounds();
                resultScreen.setVisible(false);
                previousFrame.setVisible(true);
                previousFrame.setBounds(currentPossition);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });

        panel.validate();
        resultScreen.setVisible(true);
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
