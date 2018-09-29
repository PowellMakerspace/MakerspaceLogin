package generalinfo.powellmakerspace.org.makerspacelogin.utils;

import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import generalinfo.powellmakerspace.org.makerspacelogin.ExportDatabaseTests.CSVWriter;
import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;
import generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic.PurposeVisitsStatisticGenerator;
import generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic.StatisticGenerator;
import generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic.StatisticResult;
import generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic.TotalVisitsStatisticGenerator;
import generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic.TypeVisitsStatisticGenerator;
import generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic.UniqueVisitsStatisticGenerator;


public class GenerateReportUtility {

    private DatabaseHelper databaseHelper;
    private File reportWorkingDirectory;
    private File csvReportFile;
    long startDate, endDate;

    public GenerateReportUtility(DatabaseHelper databaseHelper, File reportWorkingDirectory){
    this.databaseHelper = databaseHelper;
    this.reportWorkingDirectory = reportWorkingDirectory;
    }


    public void generateReport(long startDate, long endDate) {

        StatisticGenerator[] statisticGenerators = new StatisticGenerator[]{
                new TotalVisitsStatisticGenerator(databaseHelper.getReadableDatabase(), startDate, endDate),
                new UniqueVisitsStatisticGenerator(databaseHelper.getReadableDatabase(), startDate, endDate),
                new PurposeVisitsStatisticGenerator(databaseHelper.getReadableDatabase(), startDate, endDate,"Class"),
                new PurposeVisitsStatisticGenerator(databaseHelper.getReadableDatabase(), startDate, endDate,"Teach"),
                new PurposeVisitsStatisticGenerator(databaseHelper.getReadableDatabase(), startDate, endDate,"Work on Project"),
                new PurposeVisitsStatisticGenerator(databaseHelper.getReadableDatabase(), startDate, endDate,"Business Work"),
                new PurposeVisitsStatisticGenerator(databaseHelper.getReadableDatabase(), startDate, endDate,"Meeting"),
                new PurposeVisitsStatisticGenerator(databaseHelper.getReadableDatabase(), startDate, endDate,"VISTA"),
                new TypeVisitsStatisticGenerator(databaseHelper.getReadableDatabase(), startDate, endDate, "CoWorkspace"),
                new TypeVisitsStatisticGenerator(databaseHelper.getReadableDatabase(), startDate, endDate,"Volunteer"),
                new TypeVisitsStatisticGenerator(databaseHelper.getReadableDatabase(), startDate, endDate,"Individual"),
                new TypeVisitsStatisticGenerator(databaseHelper.getReadableDatabase(), startDate, endDate,"NonMember"),
                new TypeVisitsStatisticGenerator(databaseHelper.getReadableDatabase(), startDate, endDate,"Family"),
                new TypeVisitsStatisticGenerator(databaseHelper.getReadableDatabase(), startDate, endDate,"Library Pass"),
                new TypeVisitsStatisticGenerator(databaseHelper.getReadableDatabase(), startDate, endDate,"NWC Student"),
                new TypeVisitsStatisticGenerator(databaseHelper.getReadableDatabase(), startDate, endDate,"Punch Pass"),
                new TypeVisitsStatisticGenerator(databaseHelper.getReadableDatabase(), startDate, endDate,"Kitchen Renter")
        };

        // Verify Directory exists and is empty
        reportWorkingDirectory.mkdir();
        for (File file : reportWorkingDirectory.listFiles()){
            file.delete();
        }

        // Initiate CSV Report
        csvReportFile = new File(reportWorkingDirectory,"Report.csv");

        // Try block to write the data
        try{
            csvReportFile.createNewFile();
            CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(csvReportFile),
                    CSVFormat.DEFAULT);

            // Collect Report Information
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
