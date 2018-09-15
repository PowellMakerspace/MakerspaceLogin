package generalinfo.powellmakerspace.org.makerspacelogin.utils;

import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import generalinfo.powellmakerspace.org.makerspacelogin.ExportDatabaseTests.CSVWriter;
import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;


public class GenerateReportUtility {

    private DatabaseHelper databaseHelper;

    private File reportWorkingDirectory;

    private File csvReportFile;

    long startDate, endDate;


    public GenerateReportUtility(DatabaseHelper databaseHelper, File reportWorkingDirectory){
        this.databaseHelper = databaseHelper;
        this.reportWorkingDirectory = reportWorkingDirectory;

    }

    public void generateReport(long startDate, long endDate){


        // Verify Directory exists and is empty
        reportWorkingDirectory.mkdirs();
        for (File file : reportWorkingDirectory.listFiles()){
            file.delete();
        }

        // Initiate CSV Report
        csvReportFile = new File(reportWorkingDirectory,"Report.csv");

        // Try block to write the data
        try{
            csvReportFile.createNewFile();
            CSVWriter csvWriter = new CSVWriter(new FileWriter(csvReportFile));

            //Collect Report information
            int totalVisits = databaseHelper.getTotalVisits(startDate, endDate);
            int uniqueVisits = databaseHelper.getUniqueVisits(startDate, endDate);
//            Map<String,Integer> membershipTotals = databaseHelper.getMembershipTotals(startDate, endDate);
//            Map<String,Integer> purposeTimes = databaseHelper.getPurposeTimes(startDate, endDate);

            //Write Information to file
            csvWriter.writeNext(new String[]{"Total Visits", Integer.toString(totalVisits)});
            csvWriter.writeNext(new String[]{"Unique Visits",Integer.toString(uniqueVisits)});

            // Close csv File
            csvWriter.close();
        }
        catch (Exception sqlEx){
            Log.e("SQLite Error",sqlEx.getMessage(),sqlEx);
        }
    }

    public File getReportFile(){
        return csvReportFile;
    }

}
