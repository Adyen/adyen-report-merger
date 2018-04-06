package com.adyen.reportMerger.runners;

import com.adyen.reportMerger.Util.ConnectionUtil;
import com.adyen.reportMerger.entities.ErrorTypes;
import com.adyen.reportMerger.entities.ReportLocation;
import com.adyen.reportMerger.gui.ErrorScreen;
import com.adyen.reportMerger.gui.ProgressIndicatorScreen;
import com.adyen.reportMerger.gui.ShowAvailableReportScreen;
import com.adyen.reportMerger.gui.StartScreen;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;




/**
 * Created by andrew on 9/21/16.
 */
public class Controller {

    //initiate startScreen
    StartScreen startScreen;
    ShowAvailableReportScreen showAvailableReportScreen;
    private static List<URL> urlList;
    private static List<HttpsURLConnection> httpsURLConnectionList;
    private List<ReportLocation> reportLocations;
    private static String accountCode;
    private static String credentialString;
    String userName;
    char [] passWord;

    private static String path ;
    public static ErrorTypes errorType;

    public Controller() {
        startScreen = new StartScreen();
    }

    public Controller(StartScreen screen) {
        startScreen = screen;
        this.path = screen.getPath();


        switch (screen.reportLevel) {

            case MERCHANT: {
                accountCode = screen.merchantCode.getText();
            }
                break;

            case COMPANY: {
                accountCode = screen.companyCode.getText();
            }
                break;

            case MULTIMERCHANT: {
                accountCode = screen.merchantCode.getText();
            }
                break;
        }

        urlList = ConnectionUtil.getUrlListBasedOnReportLevel( screen.reportLevel, accountCode );
        ProgressIndicatorScreen.addInfoToTextArea("Create URL List..");
        for (URL url : urlList) {
            ProgressIndicatorScreen.addInfoToTextArea(url.toString());

        }


        //Set Login Credentials for httpUrlConnection
        userName = screen.reportUserName.getText() + "@Company."+screen.companyCode.getText();
        passWord = screen.passwordField.getPassword();
        credentialString = ConnectionUtil.getCredentialString(userName, passWord);

        //Create 1 or more Connections
        httpsURLConnectionList = new ArrayList<>();
        for (URL url : urlList) httpsURLConnectionList.add( ConnectionUtil.httpConnection(url, credentialString ) );
        ProgressIndicatorScreen.addInfoToTextArea("Connect to URL..");

        validateForError();


        ProgressIndicatorScreen.addInfoToTextArea("Fetching report locations..");


        //Get report Details from the connections
        reportLocations = new ArrayList<>();
        for (HttpsURLConnection http : httpsURLConnectionList) {
            ProgressIndicatorScreen.addInfoToTextArea(http.toString());

            reportLocations.addAll( ConnectionUtil.getAllReportsForType( http, screen.reportType, screen.path ) );
        }

        //Show new Window with reports to DownLoad?
        //Check if there is any report of this type for this Merchant/Company account

        if (reportLocations.isEmpty()){
            ProgressIndicatorScreen.addInfoToTextArea("** No Reports Available for type " + screen.reportType + " **");
        } else {
            StartScreen.frame.setVisible(false);
            showAvailableReportScreen = new ShowAvailableReportScreen( screen.frame, reportLocations, screen );
        }

    }

    public void validateForError () {
        if (errorType != null) {
            new ErrorScreen( StartScreen.frame, errorType );
        }
    }

    public static void setErrorType(ErrorTypes errorType) {
        new ErrorScreen( StartScreen.frame, errorType );

    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        Controller.path = path;
    }

    //TODO more info fields in selected reports ScrollBar
    //TODO TextFile in location with merchant codes to use
    //TODO Look into static mess...
    //TODO Allow users to use selection in selectedReportResult
    //TODO received payment detail order by date
    //TODO date in dispute report seems to not be working
    //TODO disable date / batch based on type of report
}
