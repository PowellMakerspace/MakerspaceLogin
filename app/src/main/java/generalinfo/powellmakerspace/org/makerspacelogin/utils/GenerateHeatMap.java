package generalinfo.powellmakerspace.org.makerspacelogin.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Visit;
import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;

public class GenerateHeatMap {

    private List<Visit> allVisits;
    private DatabaseHelper makerspaceDatabase;

    public GenerateHeatMap(Context context) {
        makerspaceDatabase = new DatabaseHelper(context);
        allVisits = makerspaceDatabase.getAllVisits();
    }

    public File create(){

        int[][] heatMap = new int[7][24];

        /**
         * Tabulate HeatMap
         */
        for (Visit visit : allVisits) {
            Calendar startTime = convertToDate(visit.getArrivalTime());
            Calendar endTime = convertToDate(visit.getDepartureTime());

            int dayOvWeek = startTime.get(Calendar.DAY_OF_WEEK) - 1;
            int startHour = startTime.get(Calendar.HOUR_OF_DAY);
            int endHour = endTime.get(Calendar.HOUR_OF_DAY);

            if (!visit.getVisitPurpose().equals("VISTA") && visit.getDepartureTime() != 0) {
                for (int i = startHour; i <= endHour; i++) {
                    heatMap[dayOvWeek][i]++;
                }
            }
        }

        /**
         * Create File for export.
         */
        File csvFile = new File(
                Environment.getExternalStorageDirectory(), "heatMap1.csv");
        try {
            CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(csvFile), CSVFormat.DEFAULT);
            for (int[] record : heatMap) {
                String[] stringRecord = new String[24];
                for (int j =  0;j < 24; j++){
                    stringRecord[j] = Integer.toString(record[j]);
                }
                csvPrinter.printRecord(stringRecord);
            }
            csvPrinter.close();
            Log.e("File Path: ", csvFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return csvFile;
    }

    private Calendar convertToDate(long timeAmount){

        Date date = new java.util.Date((long) timeAmount * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar;
    }
}
