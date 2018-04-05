package com.adyen.reportMerger.runners;

/**
 * Created by andrew on 9/21/16.
 */
public class Main {

    public static void main(String[] args) {

        String java = System.getProperty("java.specification.version");
        double version = Double.valueOf(java);
        double reqVer = 1.6;
        if (version < reqVer) {
            //Show errorScreen with Wrong version msg
        }
        else javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Controller();
            }
        });

    }
}
