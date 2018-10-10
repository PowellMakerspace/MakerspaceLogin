package generalinfo.powellmakerspace.org.makerspacelogin.AdminWindows;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import generalinfo.powellmakerspace.org.makerspacelogin.AdminWindows.reportWindows.preReportPreview;
import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Report;
import generalinfo.powellmakerspace.org.makerspacelogin.ExportDatabaseTests.CSVWriter;
import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;
import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.WelcomeWindow;
import generalinfo.powellmakerspace.org.makerspacelogin.R;
import generalinfo.powellmakerspace.org.makerspacelogin.utils.DatabaseBackupUtility;
import generalinfo.powellmakerspace.org.makerspacelogin.utils.GenerateReportUtility;

public class AdminMenu extends AppCompatActivity {

    Button logoutButton;
    Button fullReportButton;
    Button recentsButton;
    Button backupButton;
    Button addPunchesButton;
    Button deleteVisit;

    DatePicker startDatePicker;
    DatePicker endDatePicker;

    DatabaseHelper makerspaceDatabase;

    GenerateReportUtility generateReportUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        makerspaceDatabase = new DatabaseHelper(this);

//        generateReportUtility = new GenerateReportUtility(makerspaceDatabase);

        startDatePicker = (DatePicker) findViewById(R.id.startDatePicker);
        endDatePicker = (DatePicker) findViewById(R.id.endDatePicker);


        logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchWelcomeWindow = new Intent(getApplicationContext(), WelcomeWindow.class);
                startActivity(launchWelcomeWindow);
                finish();
            }
        });

        fullReportButton = (Button) findViewById(R.id.fullReportButton);
        fullReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchPreReportWindow = new Intent(getApplicationContext(), preReportPreview.class);
                launchPreReportWindow.putExtra("org.powellmakerspace.generalinfo.ARRIVAL_TIME",
                        convertToUnixTimeStamp(startDatePicker.getYear(),startDatePicker.getMonth(),
                                startDatePicker.getDayOfMonth(),0,0,0));
                launchPreReportWindow.putExtra("org.powellmakerspace.generalinfo.DEPARTURE_TIME",
                        convertToUnixTimeStamp(endDatePicker.getYear(),endDatePicker.getMonth(),
                                endDatePicker.getDayOfMonth(),23,59,59));
                startActivity(launchPreReportWindow);
                finish();
            }
        });

//        fullReportButton = (Button) findViewById(R.id.fullReportButton);
//        fullReportButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                long startDate = convertToUnixTimeStamp(startDatePicker.getYear(),startDatePicker.getMonth(),
//                        startDatePicker.getDayOfMonth(),0,0,0);
//                long endDate = convertToUnixTimeStamp(endDatePicker.getYear(),endDatePicker.getMonth(),
//                        endDatePicker.getDayOfMonth(),23,59,59);
//
//                new ExportReport().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//
//
//                Toast.makeText(getApplicationContext(),"IT WORKED", Toast.LENGTH_LONG).show();
//            }
//        });

        recentsButton = (Button) findViewById(R.id.recentsButton);
        recentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"View Recent Members",Toast.LENGTH_LONG).show();
            }
        });

        backupButton = (Button) findViewById(R.id.backupButton);
        backupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ExportDatabaseCSVTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });

        addPunchesButton = (Button) findViewById(R.id.addPunchesButtons);
        addPunchesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent addPunchIntent = new Intent(getApplicationContext(), AdminAddPunch.class);
                 startActivity(addPunchIntent);
                 finish();

            }
        });

        deleteVisit = (Button) findViewById(R.id.VisitDelete);
        deleteVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent VisitDeleteIntent = new Intent(getApplicationContext(), VisitDelete.class);
                startActivity(VisitDeleteIntent);
            }
        });
    }

    public long convertToUnixTimeStamp(int year, int month, int day, int hour, int minute, int second){

        Calendar myCalendar = Calendar.getInstance();
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, day);
        myCalendar.set(Calendar.HOUR, hour);
        myCalendar.set(Calendar.MINUTE, minute);
        myCalendar.set(Calendar.SECOND, second);

        return (long) (myCalendar.getTimeInMillis()/1000);
    }


    private void shareFileBackup(File file){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/zip");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        startActivity(Intent.createChooser(shareIntent,"Share CSV"));
    }

    private void shareFileCSV(File file){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        startActivity(Intent.createChooser(shareIntent,"Share CSV"));
    }

    public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean> {

        private final ProgressDialog dialog = new ProgressDialog(AdminMenu.this);
        private File databaseBackupFile;

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Exporting database...");
            this.dialog.show();
        }

        protected Boolean doInBackground(final String... args) {
            File exportDirectory = new File(
                    Environment.getExternalStorageDirectory(), "makerspaceDataExport");

            // Run Db Backup utility
            DatabaseHelper makerspaceDatabase = new DatabaseHelper(AdminMenu.this);
            DatabaseBackupUtility dbBackupUtil = new DatabaseBackupUtility(makerspaceDatabase, exportDirectory);
            dbBackupUtil.performBackup();

            // Get backup file
            databaseBackupFile = dbBackupUtil.getBackupFile();
            return databaseBackupFile != null;
        }

        protected void onPostExecute(final Boolean success) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (success) {
                shareFileBackup(databaseBackupFile);
            } else {
                Toast.makeText(AdminMenu.this, "Export failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class ExportReport extends AsyncTask<String, Void, Boolean> {

        private final ProgressDialog dialog = new ProgressDialog(AdminMenu.this);
        private File reportWorkingFile;

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Exporting Report...");
            this.dialog.show();
        }

        protected Boolean doInBackground(final String... args) {
            File exportDirectory = new File(
                    Environment.getExternalStorageDirectory(), "makerspaceReportExport");

            // Get Dates
            long startDate = convertToUnixTimeStamp(startDatePicker.getYear(),startDatePicker.getMonth(),
                    startDatePicker.getDayOfMonth(),0,0,0);
            long endDate = convertToUnixTimeStamp(endDatePicker.getYear(),endDatePicker.getMonth(),
                    endDatePicker.getDayOfMonth(),23,59,59);

            // Run Generate Report Utility
            DatabaseHelper makerspaceDatabase = new DatabaseHelper(AdminMenu.this);
            GenerateReportUtility generateReportUtility = new GenerateReportUtility(makerspaceDatabase, exportDirectory);
            generateReportUtility.generateReport(startDate, endDate);

            // Get backup file
            reportWorkingFile = generateReportUtility.getReportFile();
            return reportWorkingFile != null;
        }

        protected void onPostExecute(final Boolean success) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (success) {
                shareFileCSV(reportWorkingFile);
            } else {
                Toast.makeText(AdminMenu.this, "Export failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
