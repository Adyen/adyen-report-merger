package com.adyen.reportMerger.entities;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andrew on 9/29/16.
 */
public class ReportLocationRenderer extends JPanel implements ListCellRenderer {
    JLabel name;
    JLabel account;
    JLabel size;

    public ReportLocationRenderer(){
        setLayout(new GridLayout(1,3));
        name = new JLabel();
        account = new JLabel();
        size = new JLabel();

    }


    public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) {

        name.setText(((ReportLocation) value).reportName);
        account.setText(((ReportLocation) value).accountCode);
        size.setText(((ReportLocation) value).size);

        setEnabled(list.isEnabled());
        setFont(list.getFont());

        return this;

    }

}
