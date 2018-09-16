package generalinfo.powellmakerspace.org.makerspacelogin.utils;

import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import generalinfo.powellmakerspace.org.makerspacelogin.ExportDatabaseTests.CSVWriter;
import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;
import generalinfo.powellmakerspace.org.makerspacelogin.utils.reports.SqlIntegerStatisticGenerator;
import generalinfo.powellmakerspace.org.makerspacelogin.utils.reports.StatisticGenerator;
import generalinfo.powellmakerspace.org.makerspacelogin.utils.reports.StatisticResult;
import generalinfo.powellmakerspace.org.makerspacelogin.utils.reports.UniqueVisitsStatisticGenerator;


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

        StatisticGenerator[] statisticGenerators = new StatisticGenerator[]{
                new UniqueVisitsStatisticGenerator(databaseHelper.getReadableDatabase(), startDate, endDate)
        };


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
            CSVPrinter csvPrinter = new CSVPrinter();

            //Collect Report information
            for (StatisticGenerator statisticGenerator : statisticGenerators){
                StatisticResult statisticResult = statisticGenerator.generateStatistic();
                statisticResult.toCsv(csvPrinter);
            }

            // Close csv File
            csvPrinter.close();
        }
        catch (Exception sqlEx){
            Log.e("SQLite Error",sqlEx.getMessage(),sqlEx);
        }
    }

    public File getReportFile(){
        return csvReportFile;
    }

}



//    int totalVisits = databaseHelper.getTotalVisits(startDate, endDate);
//    int uniqueVisits = databaseHelper.getUniqueVisits(startDate, endDate);
//            Map<String,Integer> membershipTotals = databaseHelper.getMembershipTotals(startDate, endDate);
//            Map<String,Integer> purposeTimes = databaseHelper.getPurposeTimes(startDate, endDate);
