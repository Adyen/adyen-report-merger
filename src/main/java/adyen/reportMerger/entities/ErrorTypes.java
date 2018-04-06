package com.adyen.reportMerger.entities;

/**
 * Created by andrew on 9/21/16.
 */
public enum ErrorTypes {
    ERROR_NO_REPORTS_TYPE ("No Reports founds for these accounts"),
    ERROR_INCORRECT_DETAILS ("The provided login credentials are incorrect"),
    ERROR_INCORRECT_ACCOUNT ("The company or merchant account is incorrect"),
    ERROR_CONNECTION ("There was an Error in the Connection"),
    ERROR_GET_REPORTS ("These was an Error fetching the reports"),
    ERROR_DOWNLOAD_FILE ("There was an Error downloading the files");

    String description;

    ErrorTypes(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
