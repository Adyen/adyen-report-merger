package com.adyen.reportMerger.Util;

import com.adyen.reportMerger.entities.ReportLocation;
import com.adyen.reportMerger.entities.ReportTypes;
import com.adyen.reportMerger.runners.Controller;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andrew on 9/22/16.
 * Read the generated HTML file and Parse for CSV files of the chosen type
 * Add those to reportLocationList
 */
public final class ReadHtml {

    //Log File Handling
//    public static FileHandler fileHandler = Controller.getFileHandler();
    private final static Logger LOGGER = Logger.getLogger(ReadHtml.class.getName());

    public static List<ReportLocation> getReportDetails(File tmpHTML, URL urlBase, ReportTypes rt) {

        // Create the ArrayList to include all the objects
        List<ReportLocation> reportsLocationList = new ArrayList<>();

        // Open the file
        FileInputStream fileStream;
        try {

            fileStream = new FileInputStream(tmpHTML);

            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fileStream);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(in));

            String strLine;

            while ((strLine = bufferedReader.readLine()) != null ) {
                String info = getBatchInformation(strLine, rt);

                if (!info.equals("") && info.contains("csv")) {
                    //create new report
                    ReportLocation report = new ReportLocation(info, rt, urlBase);

                    //set batchNumber
                    try{
                        report.setBatchNumber( Integer.parseInt(info.replaceAll("[^\\d]", "") ) );

                    }catch (NumberFormatException n){
                        continue;
                    }

                    //Set accountCode
                    String s = urlBase.toString();
                    String m = "MerchantAccount";
                    String c = "CompanyAccount";
                    String accountCode = "";
                    if (s.contains(m)) {
                        int x = s.indexOf(m) + m.length() + 1;
                        accountCode = s.substring(x, s.length()-1);
                        report.setAccountCode( accountCode );
                    }

                    if (s.contains(c)) {
                        int x = s.indexOf(c) + c.length() + 1;
                        accountCode = s.substring(x, s.length()-1);
                        report.setAccountCode( accountCode );
                    }

                    report.setReportDownloadURL(new URL (s + report.getReportName() ));
                    //Set new Report Name used to ensure Filenames are Unique
                    String accountCodeForRenameFile = "";

                    if (report.getAccountCode() != null) {
                        accountCodeForRenameFile = "_" + report.getAccountCode();
                    }
                    report.setNewReportName( report.getReportName().substring(0, report.getReportName().length()-4)
                            + accountCodeForRenameFile
                            + ".csv");


                    //skip to next line to find modificationDate
                    String date = getReportDate(bufferedReader.readLine());
                    report.setModificationDate(date);

                    //skip to next line to find size
                    String size = getReportSize(bufferedReader.readLine());
                    report.setSize(size);


                    //add report
                    reportsLocationList.add(report);
                }
            }
            // Close the input stream
            in.close();
            tmpHTML.delete();
            // System.out.println("I can provissionary stop removing the file reports.html in: HTMLLineReader - getReportDetails - f.delete()");

        } catch (IOException e1) {
            e1.printStackTrace();
//            LOGGER.addHandler(fileHandler);
//            LOGGER.severe("IOException in getReportDetails while trying to connect: " + e1.getMessage());
        }

        return getReversed(reportsLocationList);
    }
    /**
     * Used to reverse the table, so that it's show in correct order in the GUI.
     */
    public static List<ReportLocation> getReversed(List<ReportLocation> original) {
            List<ReportLocation> copy = new ArrayList<>(original);
            Collections.reverse(copy);
            return copy;
    }

    private static String getReportDate(String htmlLine) {
        String reportDate = "";
        String aux;

        Pattern p = Pattern.compile("<td>(.*)</td>", Pattern.DOTALL);

        Matcher matcher = p.matcher(htmlLine);

        if (matcher.find()) {
            // I will split the string in several parts
            Pattern spliting1 = Pattern.compile(">", Pattern.LITERAL);
            String[] htmlWords1 = spliting1.split(htmlLine);
            aux = htmlWords1[1];
            spliting1 = Pattern.compile("<", Pattern.LITERAL);
            String[] htmlWords2 = spliting1.split(aux);
            reportDate = htmlWords2[0];

//             System.out.println("This batch date is: "+reportDate);
        }
        return reportDate;
    }

    private static String getReportSize(String htmlLine) {
        String reportSize = "";
        String aux ;

        Pattern p = Pattern.compile("<td (.*)</td>", Pattern.DOTALL);

        Matcher matcher = p.matcher(htmlLine);

        if (matcher.find()) {
            // I will split the string in several parts
            Pattern spliting1 = Pattern.compile(">", Pattern.LITERAL);
            String[] htmlWords1 = spliting1.split(htmlLine);
            aux = htmlWords1[1];
            spliting1 = Pattern.compile("<", Pattern.LITERAL);
            String[] htmlWords2 = spliting1.split(aux);
            reportSize = htmlWords2[0];

//             System.out.println("This batch size is: "+reportSize);
        }
        return reportSize;
    }

    public static String getBatchInformation(String htmlLine, ReportTypes rt) {
        String reportInfo = "";

        Pattern p = Pattern.compile("<td><a href=\"" + rt.getReportCode() + "(.*)\">"
                + rt.getReportCode() + "(.*)", Pattern.DOTALL);

        Matcher matcher = p.matcher(htmlLine);

        if (matcher.find()) {
            // I will split the string in several parts
            Pattern spliting = Pattern.compile("\"", Pattern.LITERAL);
            String[] htmlWords = spliting.split(htmlLine);
            reportInfo = htmlWords[1];

//			System.out.println("This report name is: " + reportInfo);
        }
        return reportInfo;
    }
}
