package com.adyen.reportMerger.entities;

import java.util.List;

/**
 * Created by andrew on 9/21/16.
 */
public enum ReportLevels {

    MERCHANT ("Merchant level"),
    COMPANY ("Company level"),
    MULTIMERCHANT ("Multiple Merchant Level");

    String reportLevelDescription;

    ReportLevels(String reportLevelDescription) {
        this.reportLevelDescription = reportLevelDescription;
    }

    public String getReportLevelDescription() {
        return reportLevelDescription;
    }

    public static ReportLevels getLevelFromDescription(String text) {
        if (text != null) {
            for (ReportLevels rl : ReportLevels.values()) {
                if (text.equalsIgnoreCase(rl.getReportLevelDescription())) {
                    return rl;
                }
            }
        }
        return null;
    }
}
