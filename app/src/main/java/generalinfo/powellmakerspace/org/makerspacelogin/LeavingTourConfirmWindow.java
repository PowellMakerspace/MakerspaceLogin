package generalinfo.powellmakerspace.org.makerspacelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LeavingTourConfirmWindow extends AppCompatActivity {

    long visit_id;
    DatabaseHelper makerspaceDatabase;
    TextView tourNameLearvingConfirmTextView;
    TextView numberLeavingConfirmTextView;
    Button confirmTourLeavingButton;
    Tour tour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaving_tour_confirm_window);

        if(getIntent().hasExtra(("org.powellmakerspace.generalinfo.TOUR_ID"))){
            visit_id = getIntent().getExtras().getLong("org.powellmakerspace.generalinfo.TOUR_ID");
        }
        else{
            Toast.makeText(getApplicationContext(),"ID Not Passed", Toast.LENGTH_LONG).show();
        }

        // Initialized database
        makerspaceDatabase = new DatabaseHelper(this);

        // Get Tour from database
        tour = makerspaceDatabase.getTour(visit_id);

        // Update Display
        tourNameLearvingConfirmTextView = (TextView) findViewById(R.id.tourNameLeavingConfirmTextView);
        tourNameLearvingConfirmTextView.setText(tour.getTour_name());
        numberLeavingConfirmTextView = (TextView) findViewById(R.id.numberLeavingConfirmTextView);
        numberLeavingConfirmTextView.setText(Integer.toString(tour.getTour_visitor_number()));

        // Setup Button
        confirmTourLeavingButton = (Button) findViewById(R.id.confirmTourLeavingButton);
        confirmTourLeavingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Set Tour Leaving Time
                tour.setTour_departure_time(System.currentTimeMillis()/1000);

                // Update Database
                makerspaceDatabase.updateTour(tour);

                // Display Message
                Toast.makeText(getApplicationContext(), "Thanks for Visiting!",Toast.LENGTH_LONG).show();

                // Launch Welcome Window
                Intent launchWelcomeWindow = new Intent(getApplicationContext(), WelcomeWindow.class);
                startActivity(launchWelcomeWindow);
                finish();
            }
        });

    }
}
