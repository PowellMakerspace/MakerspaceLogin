package generalinfo.powellmakerspace.org.makerspacelogin.AdminWindows.reportWindows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Date;

import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Member;
import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Visit;
import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;
import generalinfo.powellmakerspace.org.makerspacelogin.R;

public class VisitEditWindow extends AppCompatActivity {

    TextView visitNameDisplay;
    TextView visitMembershipDisplay;
    TextView visitPurposeDisplay;

    TimePicker arrivalTimePicker;
    TimePicker departureTimePicker;

    DatePicker arrivalDatePicker;
    DatePicker departureDatePicker;

    Button cancelVisitUpdateButton;
    Button resetDateTimeButton;
    Button updateVisitButton;

    DatabaseHelper makerspaceDatabase;
    Member member;
    Visit visit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_edit_window);

        makerspaceDatabase = new DatabaseHelper(this);

        visit = makerspaceDatabase.getVisit(getIntent().getLongExtra("org.powellmakerspace.generalinfo.EDIT_VISIT_ID",-1));
        member = makerspaceDatabase.getMember(visit.getMemberID());

        visitNameDisplay = (TextView) findViewById(R.id.visitNameDisplay);
        visitMembershipDisplay = (TextView) findViewById(R.id.visitMembershipDisplay);
        visitPurposeDisplay = (TextView) findViewById(R.id.visitPurposeDisplay);

        arrivalTimePicker = (TimePicker) findViewById(R.id.arrivalTimePicker);
        departureTimePicker = (TimePicker) findViewById(R.id.departureTimePicker);

        arrivalDatePicker = (DatePicker) findViewById(R.id.arrivalDatePicker);
        departureDatePicker = (DatePicker) findViewById(R.id.departureDatePicker);

        visitNameDisplay.setText(member.getMemberName());
        visitMembershipDisplay.setText(member.getMembershipType());
        visitPurposeDisplay.setText(visit.getVisitPurpose());

        setPickers();

        cancelVisitUpdateButton = (Button) findViewById(R.id.cancelVisitUpdateButton);
        cancelVisitUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resetDateTimeButton = (Button) findViewById(R.id.resetDateTimeButton);
        resetDateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPickers();
            }
        });

        updateVisitButton = (Button) findViewById(R.id.updateVisitButton);
        updateVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_LONG).show();

                updateFromPickers();

                long startDate = getIntent().getLongExtra("org.powellmakerspace.generalinfo.ARRIVAL_TIME",-1);
                long endDate = getIntent().getLongExtra("org.powellmakerspace.generalinfo.DEPARTURE_TIME",-1);

                Intent launchPreReportPreview = new Intent(getApplicationContext(), PreReportPreview.class);
                launchPreReportPreview.putExtra("org.powellmakerspace.generalinfo.ARRIVAL_TIME",startDate);
                launchPreReportPreview.putExtra("org.powellmakerspace.generalinfo.DEPARTURE_TIME",endDate);

                startActivity(launchPreReportPreview);
                finish();
            }
        });
    }

    private void setPickers(){
        Date arrivalDate = new Date((long) visit.getArrivalTime() * 1000);
        Date departureDate = new Date((long) visit.getDepartureTime() * 1000);

        arrivalDatePicker.updateDate(arrivalDate.getYear() + 1900, arrivalDate.getMonth(),
                arrivalDate.getDate());
        arrivalTimePicker.setCurrentHour(arrivalDate.getHours());
        arrivalTimePicker.setCurrentMinute(arrivalDate.getMinutes());

        departureDatePicker.updateDate(departureDate.getYear() + 1900, departureDate.getMonth(),
                departureDate.getDate());
        departureTimePicker.setCurrentHour(departureDate.getHours());
        departureTimePicker.setCurrentMinute(departureDate.getMinutes());

    }

    private void updateFromPickers(){

        Date updatedArrivalTime;
        updatedArrivalTime = new Date(arrivalDatePicker.getYear() - 1900, arrivalDatePicker.getMonth(),
                arrivalDatePicker.getDayOfMonth(), arrivalTimePicker.getCurrentHour(),
                arrivalTimePicker.getCurrentMinute());
        visit.setArrivalTime(updatedArrivalTime.getTime() / 1000);

        Date updatedDepartureTime;
        updatedDepartureTime = new Date(departureDatePicker.getYear() - 1900, departureDatePicker.getMonth(),
                departureDatePicker.getDayOfMonth(),departureTimePicker.getCurrentHour(),
                departureTimePicker.getCurrentMinute());
        visit.setDepartureTime(updatedDepartureTime.getTime() / 1000);

        int line = makerspaceDatabase.updateVisit(visit);
        Log.e("Updated? ", Integer.toString(line));
    }

}
