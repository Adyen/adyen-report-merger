package com.adyen.reportMerger.Util;

import com.adyen.reportMerger.entities.LogFileHandler;
import com.adyen.reportMerger.entities.ReportLocation;
import com.adyen.reportMerger.gui.ProgressIndicatorScreen;
import com.adyen.reportMerger.gui.StartScreen;
import com.adyen.reportMerger.runners.Controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by andrew on 9/21/16.
 */
public final class CsvUtil {


    public static boolean validateCSV (String fileLocation) {

        return false;
    }

    public static File csvToJavaFile (String fileLocation) {

        File file = new File(fileLocation);

        return file;

    }

    public static List<List<String>> csvValuesToListStringList (File file) {
        BufferedReader br = null;
        String line ;

        List<List<String>> lineList = new ArrayList<>();
        try {

            br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {
                lineList.add(Arrays.asList(line.split("\\s*,\\s*")));

            }
        } catch (FileNotFoundException e1) {
            ProgressIndicatorScreen.addInfoToTextArea(file.getName() + "could not be found while parsing to StringList :" +e1.getMessage());

        } catch (IOException e) {
            ProgressIndicatorScreen.addInfoToTextArea(file.getName() + "caused an IOException while parsing to StringList :" + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    ProgressIndicatorScreen.addInfoToTextArea("An error orcurred while closing BufferedReader" + e.getMessage());
                }
            }
        }

        return lineList;

    }

    public static List<String> csvHeaderToStringList(File file){
//		csvHeaderReader reads the headers and makes a list of the headers";
        BufferedReader br = null;
        String line ;
        List <String> headers = new ArrayList<>();
        try {

            br = new BufferedReader(new FileReader(file));

            line = br.readLine();

            headers = Arrays.asList(line.split("\\s*,\\s*"));

            return 	headers;

        } catch (IOException e1) {
            ProgressIndicatorScreen.addInfoToTextArea(file.getName() + "could not be found while parsing to StringList :" +e1.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    ProgressIndicatorScreen.addInfoToTextArea("An error orcurred while closing BufferedReader" + e.getMessage());
                }
            }
        }

        return headers;

    }

    public static List<List<String>> csvEvenColumns (List<List<String>> valList){

        //ensure has same amount of columns troughout the file based on headerLine line
        int nrOfColumns = valList.get(0).size();
        List<List<String>> valListNew = new ArrayList<>();
        List<String> newList;
        for (List<String> list : valList ){
            newList = new ArrayList<>();
            int addEmpty =  nrOfColumns - list.size();
            newList.addAll(list);
            for (int x = 0; x<addEmpty ; x++) {
                newList.add("");
            }
            valListNew.add(newList);
        }
        return valListNew;
    }

    public static LinkedHashMap<String, List<String>> csvMapFile(List<List<String>> valList){

        int nrOfRows = valList.size();
        LinkedHashMap<String, List<String>> valMap = new LinkedHashMap<>();

        //add columns from file as Key for Map and an empty ArrayList as value
        int headerIndex = 0;
        for (String header : valList.get(0)){

            valMap.put(header, new ArrayList<>());
            for(int row = 1; row < nrOfRows ; row++){
                valMap.get(header).add(valList.get(row).get(headerIndex));
            }

            headerIndex++;
        }

        return valMap;
    }



    /**
     * create header in new scv file
     * @param headerList
     * @return
     */
    public static LinkedHashMap<String, List<String>> startCsvFileMerge(List <String> headerList){

        LinkedHashMap<String, List<String>> mergedCsvFileMapped = new LinkedHashMap<String, List<String>>();

        for (String header : headerList){
            mergedCsvFileMapped.put(header, new ArrayList<String>());
        }

        return mergedCsvFileMapped;
    }

    /**
     * map values for new csv file
     * todo give more explanation on what im doing here and make it simpler
     * Merge Map is a map with the found CSV headers and empty string list
     * The value map contains all the data
     */
    public static LinkedHashMap<String, List<String>> fillCsvFileMerge( LinkedHashMap<String, List<String>> mergeMap, LinkedHashMap<String, List<String>> valMap){
        //Null pointer for JoeAndtheJuice JoeAndTheJuice_NiceAirport
//        at com.adyen.reportMerger.Util.CsvUtil.fillCsvFileMerge(CsvUtil.java:180
        //report 11 changed the structure of the report  (the key is still there though..

        int nrOfRows =0;
        if (mergeMap == null) {
            return null;
        }
        if (mergeMap.isEmpty()) {
            return null;
        }
        if (valMap == null) {
            return null;
        }
        if (valMap.isEmpty()){
            return null;
        }
        for (String key : mergeMap.keySet()){
            if(valMap.get(key) != null){
                nrOfRows = valMap.get(key).size();

                for (String val :valMap.get(key)){
                    mergeMap.get(key).add(val);
                }

            }else{
                for (int row = 0; row < nrOfRows ; row++){
                    mergeMap.get(key).add("");
                }
            }

        }

        return mergeMap;

    }

    /**
     * write the new scv file
     */
    public static StringBuilder writeCsv (LinkedHashMap<String, List<String>> csvMap){

        StringBuilder sb = new StringBuilder();
        int nrOfRows = 0;

        for (String header : csvMap.keySet()){
            nrOfRows = csvMap.get(header).size();
            sb.append(header);
            sb.append(",");
        }

        sb.setLength(sb.length() - 1);
        sb.append("\n");

        for (int row = 0 ; row < nrOfRows ; row++){
            for (String header : csvMap.keySet()){

                sb.append(csvMap.get(header).get(row));
                sb.append(",");
            }

            sb.setLength(sb.length() - 1);
            sb.append("\n");
        }

        return sb;
    }
}
