package com.adyen.reportMerger.Util;

import com.adyen.reportMerger.entities.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import com.adyen.reportMerger.runners.Controller;
import org.apache.commons.codec.binary.Base64;


/**
 * Created by andrew on 9/21/16.
 */
public final class ConnectionUtil {


    private static final int BUFFER_SIZE = 4096;



    public static List<URL> getUrlListBasedOnReportLevel (ReportLevels rl, String accountCode) {

        List<URL> urlList = new ArrayList<>();
        URL url;

        switch (rl) {

            case MERCHANT: {

                urlList = new ArrayList<>();

                try {
                    url = new URL(new URL(
                            "https://ca-live.adyen.com/reports/download/MerchantAccount/"
                                    + accountCode + "/").toString());

                    urlList.add(url);

                } catch (MalformedURLException e) {
                    e.printStackTrace();

                }
            }

            break;

            case COMPANY: {

                urlList = new ArrayList<>();

                try {
                    url = new URL(new URL(
                            "https://ca-live.adyen.com/reports/download/Company/"
                                    + accountCode + "/").toString());

                    urlList.add(url);

                } catch (MalformedURLException e) {
                    e.printStackTrace();

                }
            }
            break;

            case MULTIMERCHANT: {
                urlList = new ArrayList<>();

                List<String>multipleAccountCodes = Arrays.asList(accountCode.toString().trim().split(","));

                for (String account : multipleAccountCodes) {
                    try {

                        url = new URL(new URL(
                                "https://ca-live.adyen.com/reports/download/MerchantAccount/"
                                        + account.trim() + "/").toString());

                        urlList.add(url);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }

        return  urlList;
    }

    //base64 Encoded credentialString
    public static String getCredentialString (String userName, char[] password) {
        String pass = new String (password);
        String credentialString = userName + ":" + pass;

        // Encoding
        byte[] encoding = Base64.encodeBase64(credentialString.getBytes());
        credentialString = new String(encoding);

        return credentialString;
    }

    //create Http connection
    public static HttpsURLConnection httpConnection(URL url, String credentialString) {
        HttpsURLConnection urlConnection = null;

        try {
            urlConnection = ((HttpsURLConnection) url.openConnection());

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        urlConnection.setRequestProperty("Authorization", "Basic "
                + credentialString);
        urlConnection.setConnectTimeout(600000);

        // Establishing the connection
        int code = 0;
        try {
            urlConnection.connect();
            code = urlConnection.getResponseCode();
            if (code == 200) {

                return urlConnection;

            }
            else if (code == 401) {
                //Send to contoller
                Controller.setErrorType(ErrorTypes.ERROR_INCORRECT_DETAILS) ;

            } else if (code == 404 ) {
                Controller.setErrorType(ErrorTypes.ERROR_INCORRECT_ACCOUNT) ;


            } else if (code == 403 ) {
                Controller.setErrorType(ErrorTypes.ERROR_INCORRECT_ACCOUNT) ;


            }

        } catch (IOException e) {
            //uh oh
        }

        return urlConnection;

    }

    //Find available Reports
    public static List<ReportLocation> getAllReportsForType (HttpsURLConnection http, ReportTypes rt, String path) {
        List<ReportLocation> reportDetails = new ArrayList<>();

        try {
            File tmpHTML = new File(path + "/reports.html");
            FileOutputStream writer = new FileOutputStream(tmpHTML);
            InputStream reader = http.getInputStream();

            byte[] buffer = new byte[153600];
            int bytesRead = 0;
            while ((bytesRead = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, bytesRead);
                buffer = new byte[153600];
            }
            writer.close();
            reportDetails = ReadHtml.getReportDetails(tmpHTML, http.getURL(), rt );

        } catch (IOException e) {
            e.printStackTrace();
        }

        return reportDetails;
    }


    public static boolean downloadFile(URL url, String credentialString, String newFileName, String savePath) {
        HttpURLConnection http;
        boolean success = false;
//        LOGGER.addHandler(fileHandler);

        try {
            http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Authorization", "Basic "
                    + credentialString);
            int responseCode = http.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = http.getInputStream();
                String saveFilePath = savePath + File.separator + newFileName;
                FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                int bytesRead;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();

                success = true;
            } else {
                Controller.errorType = ErrorTypes.ERROR_DOWNLOAD_FILE;
            }
            http.disconnect();

            } catch (IOException e1) {
                e1.printStackTrace();
        }

        return success;
    }

}
