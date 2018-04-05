package com.adyen.reportMerger.Util;

import com.adyen.reportMerger.entities.ReportLevels;
import com.adyen.reportMerger.gui.StartScreen;

/**
 * Created by andrew on 9/22/16.
 * Class to validate the startscreen inputs
 */
public final class Validate {

    public Validate() {

    }

    public static boolean validateStartscreen () {

        boolean errors = false;

        // Check if the path is correct
        if (StartScreen.path.equals(null)) {

            StartScreen.pathError.setText("The selected path: " + StartScreen.getPath()
                    + " is incorrect. Please, select a correct path.");
            errors = true;
        }

        if ((new String(StartScreen.passwordField.getPassword())).length() < 20) {
            StartScreen.passwordError
                    .setText("A password has to have 20 or more chars.");
            StartScreen.passwordField.moveCaretPosition(0);
            errors = true;
        }

        if (StartScreen.merchantCode.getText().isEmpty()
                || StartScreen.merchantCode.getText().length() < 4) {
            StartScreen.merchantCodeError
                    .setText("A merchant code has to have 4 or more chars.");
            StartScreen.merchantCode.moveCaretPosition(0);
            errors = true;
        }

        if (StartScreen.companyCode.getText().isEmpty()
                || StartScreen.companyCode.getText().length() < 4) {
            StartScreen.companyCodeError
                    .setText("A company code has to have 4 or more chars.");
            StartScreen.companyCode.moveCaretPosition(0);
            errors = true;
        }

        if (StartScreen.reportUserName.getText().isEmpty()
                || StartScreen.reportUserName.getText().length() < 4) {
            StartScreen.reportUserNameError
                    .setText("A report user has to have 4 or more chars.");
            StartScreen.reportUserName.moveCaretPosition(0);
            errors = true;
        }

//         Controlling blank spaces
        String passS = new String(StartScreen.passwordField.getPassword());
        if (passS.contains("\t")
                || passS.contains("\n")
                || passS.contains("\r")
                || passS.contains("\f")
                || passS.contains(" ")) {
//					System.out.println(passS);
            StartScreen.passwordError
                    .setText("A password can not content blank spaces");
            StartScreen.passwordField.moveCaretPosition(0);
            errors = true;
        }

        if (ReportLevels.getLevelFromDescription(StartScreen.levelCombo.getSelectedItem().toString() ) != ReportLevels.MULTIMERCHANT && (StartScreen.merchantCode.getText().contains("\t")
                || StartScreen.merchantCode.getText().contains("\n")
                || StartScreen.merchantCode.getText().contains("\r")
                || StartScreen.merchantCode.getText().contains("\f")
                || StartScreen.merchantCode.getText().contains(" "))) {
            StartScreen.merchantCodeError
                    .setText("A merchant code can have blank spaces on "
                            + StartScreen.levelCombo.getSelectedItem().toString() + ".");
            StartScreen.merchantCode.moveCaretPosition(0);
            errors = true;
        }

        if (StartScreen.companyCode.getText().contains(" ")
                || StartScreen.companyCode.getText().contains("\n")
                || StartScreen.companyCode.getText().contains("\r")
                || StartScreen.companyCode.getText().contains("\f")
                || StartScreen.companyCode.getText().contains(" ")) {
            StartScreen.companyCodeError
                    .setText("A company code can not have blank spaces.");
            StartScreen.companyCode.moveCaretPosition(0);
            errors = true;
        }

        if (StartScreen.reportUserName.getText().contains(" ")
                || StartScreen.reportUserName.getText().contains("\n")
                || StartScreen.reportUserName.getText().contains("\r")
                || StartScreen.reportUserName.getText().contains("\f")) {
            System.out.println(StartScreen.reportUserName.getText());
            StartScreen.reportUserNameError
                    .setText("A report user can not have blank spaces.");
            StartScreen.reportUserName.moveCaretPosition(0);
            errors = true;
        }

        return errors;

    }
}
