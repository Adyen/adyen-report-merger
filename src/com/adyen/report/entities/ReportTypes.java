package com.adyen.report.entities;

/**
 * Enums for Report types and error handling.
 * 
 * 
 * @author cristina
 *
 */

public enum ReportTypes {
	SETTLEMENT_DETAIL (0, "settlement_detail_report","Settlement detail report", false), 
	RECEIVED_PAYMENTS (1,"received_payments_report","Received payments report", false) , 
	PAYMENTS_ACCOUNTING (2, "payments_accounting_report","Payments accounting report", false), 
	DISPUTE_REPORT (3, "dispute_report","Dispute report", false),
	SETTLEMENT_BATCH (4, "settlement_batch_report","Settlement batch report", false),
	
	ERROR_INVALID_CREDENTIALS (401,"Incorrect Credentials.Please, review the inserted details and try again.","Incorrect Credentials. Please, review the inserted details and try again.", true),
	ERROR_NO_REPORTS_PERIOD (402, "There are no [Report_Type] available in the selected period of time. Please, select a new period.", "There are no [Report_Type] available in the selected period of time. Please, select a new period.", true),
	ERROR_ENDDATE_BEFORE_STARTDATE (403, "Date. End date before start date.", "Date. End date before start date.", true),
	ERROR_PAGE_NOT_FOUND (404, "Page not found.", "Page not found.", true),
	ERROR_WRITTING_IN_FILE (405, "Problem editing the report. Please, notify adyen support about it and try again removing the created report in the path was choosen.", "Problem editing the report. Please, notify adyen support about it and try again removing the created report in the path was choosen.",true),
	ERROR_DOWNLOADING_FILE (406, "Problem downloading the reports. Please, notify adyen support about it and try again removing the created reports in the path was choosen.","Problem downloading the reports. Please, notify adyen support about it and try again removing the created reports in the path was choosen.",true),
	ERROR_NO_REPORTS_TYPE (407, "There are not [Report_Type] available for this account.", "There are not [Report_Type] available for this account.",true),
	//Not used for now, done manually in Interface
	ERROR_SIZE_PASS (600,"Password incorrect. A password has to have 20 or more characters.","Password incorrect. A password has to have 20 or more characters.", true),
	ERROR_SIZE_MER (601,"Merchant code incorrect. A merchant code has to have 4 or more characters.","Merchant code incorrect. A merchant code has to have 4 or more characters.", true),
	ERROR_SIZE_COM (602,"Company code incorrect. A company code has to have 4 or more characters.","Company code incorrect. A company code has to have 4 or more characters.", true),
	ERROR_SIZE_USER (603,"Report user name incorrect. A report user has to have 4 or more characters.","Report user name incorrect. A report user has to have 4 or more characters.", true),
	
	ERROR_BLANK_SPACES_PASS (701,"Password incorrect. A password can not content blank spaces.","Password incorrect. A password can not content blank spaces.", true),
	ERROR_BLANK_SPACES_MER (702,"Merchant code incorrect. A merchant code can not content blank spaces.","Merchant code incorrect. A merchant code can not content blank spaces.",true),
	ERROR_BLANK_SPACES_COM (703, "Company code incorrect. A company code can not content blank spaces.","Company code incorrect. A company code can not content blank spaces.",true),
	ERROR_BLANK_SPACES_USER (704, "Report user incorrect. A report user can not content blank spaces.","Report user incorrect. A report user can not content blank spaces.",true);
	
	private int guiValue;
	private String htmlFormatedReportName;
	private String reportFileNameOrErrorDescription;
	private boolean isError;
	
	public String getHtmlFormatedReportName() {
		return htmlFormatedReportName;
	}

	public void setHtmlFormatedReportName(String htmlFormatedReportName) {
		this.htmlFormatedReportName = htmlFormatedReportName;
	}

	private String selectedReportType;
	
	public String getReportType() {
		return selectedReportType;
	}

	public void setReportType(String reportType) {
		this.selectedReportType = reportType;
	}

	public String getReportFileNameOrErrorDescription() {
		return reportFileNameOrErrorDescription;
	}

	public void setReportFileName(String reportFileName) {
		this.reportFileNameOrErrorDescription = reportFileName;
	}

	public int getGuiValue() {
		return guiValue;
	}

	public void setGuiValue(int type) {
		this.guiValue = type;
	}

	private ReportTypes(int guiValue,String reportFileName, String htmlFormatedReportName, boolean isError) {
		this.guiValue = guiValue;
		this.reportFileNameOrErrorDescription = reportFileName;
		this.htmlFormatedReportName = htmlFormatedReportName;
		this.isError = isError;
	}
	
	public String toString (){
		return "The name of the report file is: "+reportFileNameOrErrorDescription;
	}
	
	public String toStringHTMLFormat (){
		return "<html><body>"+reportFileNameOrErrorDescription+"</body></html>";
	}
	
	public String toStringHTMLFormat (String reportType){
		String completedMessage = reportFileNameOrErrorDescription.replace("[Report_Type] ", reportType+"s ");
		String res = "<html><body>"+completedMessage+"</body></html>";

		return res;
	}
	
	public boolean isError(){
		return isError;
	}
	
	public void setError(boolean isError){
		this.isError = isError;
	}
	
	public static ReportTypes getReportTypePerNumber (int guiValue){
		ReportTypes reportType = ReportTypes.SETTLEMENT_DETAIL;
		ReportTypes [] reportTypesValues = ReportTypes.values();
		boolean founded = false;
		for (int i = 0; i < reportTypesValues.length && !founded; i++){
			reportType = reportTypesValues[i];
			if (reportType.getGuiValue() == guiValue){
				founded = true;
			}
		}

		return reportType;
	}

	public static String getReportNames (){
		String name = "";
		
		return name;
	}
}
