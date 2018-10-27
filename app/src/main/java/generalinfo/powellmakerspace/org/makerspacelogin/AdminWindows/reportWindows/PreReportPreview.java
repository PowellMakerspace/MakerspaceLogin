package generalinfo.powellmakerspace.org.makerspacelogin.AdminWindows.reportWindows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import generalinfo.powellmakerspace.org.makerspacelogin.Adapters.VisitsToReviewAdapter;
import generalinfo.powellmakerspace.org.makerspacelogin.AdminWindows.AdminMenu;
import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Visit;
import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;
import generalinfo.powellmakerspace.org.makerspacelogin.R;

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
//        List<Visit> visitsToReview = makerspaceDatabase.getVisitsFromRange(startDate, endDate);
        List<Visit> visitsToReview = makerspaceDatabase.getAllVisits();

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

            }
        });
    }
}
