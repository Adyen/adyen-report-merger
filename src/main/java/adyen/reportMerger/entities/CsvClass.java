package com.adyen.reportMerger.entities;

import com.adyen.reportMerger.Util.ConnectionUtil;
import com.adyen.reportMerger.Util.CsvUtil;
import com.adyen.reportMerger.gui.DownloadResultScreen;
import com.adyen.reportMerger.gui.ProgressIndicatorScreen;
import com.adyen.reportMerger.gui.ShowAvailableReportScreen;
import com.adyen.reportMerger.gui.StartScreen;
import com.adyen.reportMerger.runners.Controller;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by andrew on 9/27/16.
 */
public class CsvClass {

    public File tmpCsv;
    public List<File> tmpFileList;
    public List<String> tmpHeaderList;
    public File mergedCsv;
    public List<ReportLocation> filesNotDownloaded ;
    public String tmpFilePath;
    public List<LinkedHashMap<String, List<String>>> tmpMapList;
    public LinkedHashMap<String, List<String>> mergedCsvMap;
    public StringBuilder mergedCsvToString;


    public CsvClass ( List<ReportLocation> rl, String path, String credentialString, boolean deleteLocalFiles , MergeTypes mt) {

        filesNotDownloaded = new ArrayList<>();
        tmpFileList = new ArrayList<>();
        tmpMapList = new ArrayList<>();

        ProgressIndicatorScreen.addInfoToTextArea("Start Download for the chosen reports..");

        for (ReportLocation r : rl) {

            ProgressIndicatorScreen.addInfoToTextArea("Set tmp File Path..");

            tmpFilePath = path + File.separator + r.getNewReportName();
            ProgressIndicatorScreen.addInfoToTextArea(tmpFilePath);


            //downloadCsv();
            ProgressIndicatorScreen.addInfoToTextArea("DownLoad : " + r.getNewReportName());

            if (ConnectionUtil.downloadFile(r.getReportDownloadURL(), credentialString ,r.getNewReportName() , path) ) {
                r.setDownloadSucceeded(true);
                ProgressIndicatorScreen.addInfoToTextArea("Download successful");

                //ReadCsv from Machine
                tmpCsv = CsvUtil.csvToJavaFile(tmpFilePath);

                //storeFileInList();
                tmpFileList.add(tmpCsv);

                //GetHeaderFromFile (actually only needed for Last ReportLocation)
                tmpHeaderList = CsvUtil.csvHeaderToStringList(tmpCsv);


            }else {
                filesNotDownloaded.add(r);
                continue;
            }
        }
        LinkedHashMap<String, List<String>> mergeList = CsvUtil.startCsvFileMerge(tmpHeaderList);

        for (File file : tmpFileList){
            ProgressIndicatorScreen.addInfoToTextArea("Merge file: "+ file.getName());

            List<List<String>> valList = CsvUtil.csvValuesToListStringList(file);
            List<List<String>> valListEven = CsvUtil.csvEvenColumns(valList);
            LinkedHashMap<String, List<String>> valListEvenMapped = CsvUtil.csvMapFile(valListEven);
            mergedCsvMap = CsvUtil.fillCsvFileMerge(mergeList, valListEvenMapped);

            if (deleteLocalFiles) {
                file.delete();
            }
        }

        //StringBuild the Csv
        mergedCsvToString = CsvUtil.writeCsv(mergedCsvMap);

        String filename = "";

        //Set fileName based on mergeType
        switch (mt) {
            case DATERANGE: {

                // (2) create a date "formatter" (the date format we want)
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

                // (3) create a new String using the date format we want
                String folderName = formatter.format(rl.get(rl.size()-1).getReportModificationDate());
                filename = rl.get(0).getReportType().getReportCode() + formatter.format(rl.get(0).getReportModificationDate() ) + "_" + formatter.format(rl.get(rl.size()-1).getReportModificationDate());
            }
            break;

            case BATCHRANGE: {
                filename = rl.get(0).getReportType().getReportCode() + "FROM" + rl.get(0).getBatchNumber() + "TO" + rl.get(rl.size()-1).getBatchNumber();
            }
        }

        //Create new file
        mergedCsv = new File(path +File.separator + filename + ".csv");

        if (mergedCsv.exists()) {
            mergedCsv.delete();
        }

        try {
            mergedCsv.createNewFile();
            FileWriter writer = new FileWriter(mergedCsv);
            writer.write(mergedCsvToString.toString());
            writer.flush();
            writer.close();

            new DownloadResultScreen(ShowAvailableReportScreen.showAvailableReportFrame,  true, mergedCsv.getAbsolutePath());
        } catch (IOException e) {
            new DownloadResultScreen(ShowAvailableReportScreen.showAvailableReportFrame, false, mergedCsv.getAbsolutePath());
            ProgressIndicatorScreen.addInfoToTextArea("an exeption was thrown while creating the Merged CSV File :" + e.getMessage());
        }
    }

}
