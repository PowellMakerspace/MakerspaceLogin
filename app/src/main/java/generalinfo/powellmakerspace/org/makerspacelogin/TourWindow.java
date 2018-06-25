package generalinfo.powellmakerspace.org.makerspacelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TourWindow extends AppCompatActivity {

    Button tourDoneButton;
    EditText tourNameEditText;
    EditText visitorNumberEditText;
    DatabaseHelper makerspaceDatabase;
    Tour tour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_window);

        // Initialize database and activity components
        makerspaceDatabase = new DatabaseHelper(this);
        tourDoneButton = (Button) findViewById(R.id.tourDoneButton);
        tourNameEditText = (EditText) findViewById(R.id.tourNameEditText);
        visitorNumberEditText = (EditText) findViewById(R.id.visitorNumberEditText);

        // Create button action listener
        tourDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create empty tour
                tour = new Tour();
                tour.setTour_name(tourNameEditText.getText().toString());
                tour.setTour_visitor_number(Integer.parseInt(visitorNumberEditText.getText().toString()));
                tour.setTour_arrival_time(System.currentTimeMillis()/1000);
                tour.setTour_departure_time(0);

                // Add tour to the database
                makerspaceDatabase.createTour(tour);

                // Display Message
                Toast.makeText(getApplicationContext(), "Welcome to the Powell Makerspace!", Toast.LENGTH_LONG).show();

                // Return to Welcome Window
                Intent launchWelcomeWindow = new Intent(getApplicationContext(), WelcomeWindow.class);
                startActivity(launchWelcomeWindow);
            }
        });
    }
}
