package generalinfo.powellmakerspace.org.makerspacelogin.LogoutWindows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;
import generalinfo.powellmakerspace.org.makerspacelogin.R;
import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Tour;
import generalinfo.powellmakerspace.org.makerspacelogin.Adapters.TourAdapter;

public class LeavingTourWindow extends AppCompatActivity {

    DatabaseHelper makerspaceDatabase;
    List<Tour> currentTours;
    ListView tourListView;
    TourAdapter tourAdapter;
    Button cancelButtonLeavingTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaving_tour_window);

        // Begin Database Helper
        makerspaceDatabase = new DatabaseHelper(this);

        // Get all active tours from the database
        currentTours = makerspaceDatabase.getActiveTours();

        cancelButtonLeavingTour = (Button) findViewById(R.id.cancelButtonLeavingTour);
        cancelButtonLeavingTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leavingTypeIntent = new Intent(getApplicationContext(), LeavingTypeWindow.class);
                startActivity(leavingTypeIntent);
                finish();
            }
        });


        // Initiate ListView
        tourListView = (ListView) findViewById(R.id.tourListView);
        tourAdapter = new TourAdapter(this, currentTours);
//        Log.i("Debug Tag","tourListView: " + tourListView==null? "null":"not null");
//        Log.i("Debug Tag","tourAdapter: " + tourAdapter==null? "null":"not null");

        tourListView.setAdapter(tourAdapter);

        tourListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                 Pass tour information to next activity
                Intent launchLeavingTourConfirmWindow = new Intent(getApplicationContext(), LeavingTourConfirmWindow.class);
                launchLeavingTourConfirmWindow.putExtra("org.powellmakerspace.generalinfo.TOUR_ID", currentTours.get(position).getTour_id());
                startActivity(launchLeavingTourConfirmWindow);
                finish();
            }
        });

    }
}