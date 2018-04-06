package com.adyen.reportMerger.entities;

/**
 * Created by andrew on 9/21/16.
 */
public enum ReportTypes {

    SETTLEMENT_DETAIL (0, "settlement_detail_report","Settlement detail report", false),
    RECEIVED_PAYMENTS (1,"received_payments_report","Received payments report", false) ,
    PAYMENTS_ACCOUNTING (2, "payments_accounting_report","Payments accounting report", false),
    DISPUTE_REPORT (3, "dispute_report","Dispute report", false),
    MANUAL_REVIEW_REPORT(4, "manual_review_report", "Case management report", false),
    MARKETPLACE_REPORT(5, "marketplace_payments_accounting_report", "MarketPlace payment account report", false),
    EXTERNAL_SETTLEMENT_DETAILS_C_LEVEL(6, "external_settlement_detail_report", "External settlement detail (C)", false),
    EXTERNAL_SETTLEMENT_DETAILS_S_LEVEL_WITH_INFO(7, "external_settlement_detail_report_with_info_batch", "External settlement detail with info (S)", false);

//    ERROR_INVALID_CREDENTIALS (401,"Incorrect Credentials.Please, review the inserted details and try again.","Incorrect Credentials. Please, review the inserted details and try again.", true),
//    ERROR_NO_REPORTS_PERIOD (402, "There are no [Report_Type] available in the selected period of time. Please, select a new period.", "There are no [Report_Type] available in the selected period of time. Please, select a new period.", true),
//    ERROR_ENDDATE_BEFORE_STARTDATE (403, "Date. End date before start date.", "Date. End date before start date.", true),
//    ERROR_PAGE_NOT_FOUND (404, "Page not found.", "Page not found.", true),
//    ERROR_WRITTING_IN_FILE (405, "Problem editing the report. Please, notify adyen support about it and try again removing the created report in the path was choosen.", "Problem editing the report. Please, notify adyen support about it and try again removing the created report in the path was choosen.",true),
//    ERROR_DOWNLOADING_FILE (406, "Problem downloading the reports. Please, notify adyen support about it and try again removing the created reports in the path was choosen.","Problem downloading the reports. Please, notify adyen support about it and try again removing the created reports in the path was choosen.",true),
//    ERROR_NO_REPORTS_TYPE (407, "There are not [Report_Type] available for this account.", "There are not [Report_Type] available for this account.",true),
//    //Not used for now, done manually in ReportMergerGui
//    ERROR_SIZE_PASS (600,"Password incorrect. A password has to have 20 or more characters.","Password incorrect. A password has to have 20 or more characters.", true),
//    ERROR_SIZE_MER (601,"Merchant code incorrect. A merchant code has to have 4 or more characters.","Merchant code incorrect. A merchant code has to have 4 or more characters.", true),
//    ERROR_SIZE_COM (602,"Company code incorrect. A company code has to have 4 or more characters.","Company code incorrect. A company code has to have 4 or more characters.", true),
//    ERROR_SIZE_USER (603,"Report user name incorrect. A report user has to have 4 or more characters.","Report user name incorrect. A report user has to have 4 or more characters.", true),
//
//    ERROR_BLANK_SPACES_PASS (701,"Password incorrect. A password can not content blank spaces.","Password incorrect. A password can not content blank spaces.", true),
//    ERROR_BLANK_SPACES_MER (702,"Merchant code incorrect. A merchant code can not content blank spaces.","Merchant code incorrect. A merchant code can not content blank spaces.",true),
//    ERROR_BLANK_SPACES_COM (703, "Company code incorrect. A company code can not content blank spaces.","Company code incorrect. A company code can not content blank spaces.",true),
//    ERROR_BLANK_SPACES_USER (704, "Report user incorrect. A report user can not content blank spaces.","Report user incorrect. A report user can not content blank spaces.",true);

    public int guiValue;
    public String reportCode;
    public String reportDescription;
    public boolean isError;

    ReportTypes(int guiValues, String reportCode, String reportDescription, boolean isError) {
        this.guiValue = guiValues;
        this.reportCode = reportCode;
        this.reportDescription = reportDescription;
        this.isError = isError;
    }

    public int getGuiValue() {
        return guiValue;
    }

    public void setGuiValue(int guiValue) {
        this.guiValue = guiValue;
    }

    public String getReportCode() {
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public static ReportTypes getTypeFromDescription(String text) {
        if (text != null) {
            for (ReportTypes rt : ReportTypes.values()) {
                if (text.equalsIgnoreCase(rt.getReportDescription())) {
                    return rt;
                }
            }
        }
        return null;
    }
}
