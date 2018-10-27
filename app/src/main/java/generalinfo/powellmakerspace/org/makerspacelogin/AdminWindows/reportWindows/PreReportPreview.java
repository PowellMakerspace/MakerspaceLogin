package generalinfo.powellmakerspace.org.makerspacelogin.AdminWindows.reportWindows;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import generalinfo.powellmakerspace.org.makerspacelogin.Adapters.VisitsToReviewAdapter;
import generalinfo.powellmakerspace.org.makerspacelogin.AdminWindows.AdminMenu;
import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Visit;
import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;
import generalinfo.powellmakerspace.org.makerspacelogin.R;
import generalinfo.powellmakerspace.org.makerspacelogin.utils.GenerateReportUtility;

public class PreReportPreview extends AppCompatActivity {

    DatabaseHelper makerspaceDatabase;
    ListView visitsToReviewView;
    VisitsToReviewAdapter visitsToReviewAdapter;
    Button cancelReportButton;
    Button runReportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_report_preview);

        // Begin Database Helper
        makerspaceDatabase = new DatabaseHelper(this);

        long startDate = getIntent().getLongExtra("org.powellmakerspace.generalinfo.ARRIVAL_TIME",-1);
        long endDate = getIntent().getLongExtra("org.powellmakerspace.generalinfo.DEPARTURE_TIME",-1);

        // Get all visits from the database
        List<Visit> visitsToReview = makerspaceDatabase.getVisitsWithDurationGreaterThan(startDate,
                endDate, 6);

        // Initiate Listview
        visitsToReviewView = (ListView) findViewById(R.id.visitsToReview);
        visitsToReviewAdapter = new VisitsToReviewAdapter(this, visitsToReview);
        visitsToReviewView.setAdapter(visitsToReviewAdapter);

        visitsToReviewView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent launchVisitEditWindow = new Intent(getApplicationContext(), VisitEditWindow.class);
                launchVisitEditWindow.putExtra("org.powellmakerspace.generalinfo.EDIT_VISIT_ID", visitsToReview.get(position).getVisitID());
                launchVisitEditWindow.putExtra("org.powellmakerspace.generalinfo.ARRIVAL_TIME",startDate);
                launchVisitEditWindow.putExtra("org.powellmakerspace.generalinfo.DEPARTURE_TIME",endDate);
                startActivity(launchVisitEditWindow);
            }
        });

        cancelReportButton = (Button) findViewById(R.id.cancelReportButton);
        cancelReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchAdminWindow = new Intent(getApplicationContext(), AdminMenu.class);
                startActivity(launchAdminWindow);
                finish();
            }
        });

        runReportButton = (Button) findViewById(R.id.runReportButton);
        runReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ExportReport(startDate, endDate).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
        });
    }

    public class ExportReport extends AsyncTask<String, Void, Boolean> {

        private final ProgressDialog dialog = new ProgressDialog(PreReportPreview.this);
        private File reportWorkingFile;
        private long startDate;
        private long endDate;


        public ExportReport (long startDate, long endDate){
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        protected void onPreExecute() {

            this.dialog.setMessage("Exporting Report...");
            this.dialog.show();
        }

        protected Boolean doInBackground(final String... args) {
            File exportDirectory = new File(
                    Environment.getExternalStorageDirectory(), "makerspaceReportExport");

            // Run Generate Report Utility
            DatabaseHelper makerspaceDatabase = new DatabaseHelper(PreReportPreview.this);
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
                Toast.makeText(getApplicationContext(),"Report Sent", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(PreReportPreview.this, "Export failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void shareFileCSV(File file){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        startActivity(Intent.createChooser(shareIntent,"Share CSV"));
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
}
