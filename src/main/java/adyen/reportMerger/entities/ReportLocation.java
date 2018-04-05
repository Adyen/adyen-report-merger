package com.adyen.reportMerger.entities;

import com.adyen.reportMerger.runners.Controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by andrew on 9/21/16.
 */
public class ReportLocation {

    public String reportName;
    public Date reportModificationDate;
    public URL reportDownloadURL;
    public String size;
    public int batchNumber;
    public ReportTypes reportType;
    public String accountCode;
    public String newReportName;
    public boolean downloadSucceeded;


    public ReportLocation() {
    }

    public ReportLocation(String reportName, ReportTypes reportType, URL reportDownloadURL) {
        this.reportName = reportName;
        this.reportType = reportType;
        this.reportDownloadURL = reportDownloadURL;
        this.reportModificationDate = new Date();

    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Date getReportModificationDate() {
        return reportModificationDate;
    }

    public void setReportModificationDate(Date reportModificationDate) {
        this.reportModificationDate = reportModificationDate;
    }

    public URL getReportDownloadURL() {
        return reportDownloadURL;
    }

    public void setReportDownloadURL(URL reportDownloadURL) {
        this.reportDownloadURL = reportDownloadURL;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }

    public ReportTypes getReportType() {
        return reportType;
    }

    public void setReportType(ReportTypes reportType) {
        this.reportType = reportType;
    }

    public void setModificationDate(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        try {
            this.reportModificationDate =  df.parse(date+" 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getNewReportName() {
        return newReportName;
    }

    public void setNewReportName(String newReportName) {
        this.newReportName = newReportName;
    }

    public boolean isDownloadSucceeded() {
        return downloadSucceeded;
    }

    public void setDownloadSucceeded(boolean downloadSucceeded) {
        this.downloadSucceeded = downloadSucceeded;
    }
}
