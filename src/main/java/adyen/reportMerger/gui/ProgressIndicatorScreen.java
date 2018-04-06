package com.adyen.reportMerger.gui;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

/**
 * Created by andrew on 10/27/16.
 * this class is only to create a screen to indicate to the user something is happening in the background
 * I might add some time indicators
 */
public final class ProgressIndicatorScreen {

    public static JFrame progressIndicatorFrame = new JFrame("Adyen Report Merger Log Frame");
    public static JPanel whatHappensPanel = new JPanel();
    public static JLabel whatHappensLabel = new JLabel();
    public static JTextArea whatHappensTextArea = new JTextArea(18,24);

    public ProgressIndicatorScreen(){
        progressIndicatorFrame.setSize(new Dimension(600, 400));
        whatHappensPanel = new JPanel();
        whatHappensPanel.add(new JScrollPane(whatHappensTextArea), BorderLayout.CENTER);
        progressIndicatorFrame.add(whatHappensPanel);

        whatHappensTextArea.append("Starting Log Frame");
        DefaultCaret caret = (DefaultCaret) whatHappensTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

        progressIndicatorFrame.validate();
        progressIndicatorFrame.setVisible(true);

    }

    public static void addInfoToTextArea(String data){

        whatHappensTextArea.append("\n");
        whatHappensTextArea.append(data);
        progressIndicatorFrame.update(whatHappensTextArea.getGraphics());

    }

    public void setWhatHappensLabel(String text) {
        whatHappensLabel.setText(text);
        progressIndicatorFrame.getContentPane().add(whatHappensLabel);
        progressIndicatorFrame.setVisible(true);
    }

}
