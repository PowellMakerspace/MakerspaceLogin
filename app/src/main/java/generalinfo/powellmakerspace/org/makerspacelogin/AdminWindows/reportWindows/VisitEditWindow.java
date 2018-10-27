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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        String[] modifiedArrivalDate = parseDate(arrivalDate);
        String[] modifiedDepartureDate = parseDate(departureDate);

        arrivalDatePicker.updateDate(Integer.valueOf(modifiedArrivalDate[7]),
                Integer.valueOf(modifiedArrivalDate[1]),
                Integer.valueOf(modifiedArrivalDate[2]));
        departureDatePicker.updateDate(Integer.valueOf(modifiedDepartureDate[7]),
                Integer.valueOf(modifiedDepartureDate[1]),
                Integer.valueOf(modifiedDepartureDate[2]));

        arrivalTimePicker.setCurrentHour(Integer.valueOf(modifiedArrivalDate[3]));
        arrivalTimePicker.setCurrentMinute(Integer.valueOf(modifiedArrivalDate[4]));

        departureTimePicker.setCurrentHour(Integer.valueOf(modifiedDepartureDate[3]));
        departureTimePicker.setCurrentMinute(Integer.valueOf(modifiedDepartureDate[4]));
    }

    private void updateFromPickers(){

        Date updatedArrivalTime;
        updatedArrivalTime = new Date(arrivalDatePicker.getYear(), arrivalDatePicker.getMonth(),
                arrivalDatePicker.getDayOfMonth(), arrivalTimePicker.getCurrentHour(),
                arrivalTimePicker.getCurrentHour());
        visit.setArrivalTime(updatedArrivalTime.getTime() / 1000);

        Date updatedDepartureTime;
        updatedDepartureTime = new Date(departureDatePicker.getYear(), departureDatePicker.getMonth(),
                departureDatePicker.getDayOfMonth(),departureTimePicker.getCurrentHour(),
                departureTimePicker.getCurrentMinute());
        visit.setDepartureTime(updatedDepartureTime.getTime() / 1000);

        int line = makerspaceDatabase.updateVisit(visit);
        Log.e("Updated? ", Integer.toString(line));
    }

    private String[] parseDate (Date date){

        String modifiedString = date.toString().replace(":"," ");

        String[] parts = modifiedString.split(" ");

        // While the day of the month and year are properly numbered with common parlance,
        // the month starts at 0 as is typical for computer programming.
        switch(parts[1]){
            case "Jan": parts[1] = "0";
                break;
            case "Feb": parts[1] = "1";
                break;
            case "Mar": parts[1] = "2";
                break;
            case "Apr": parts[1] = "3";
                break;
            case "May": parts[1] = "4";
                break;
            case "Jun": parts[1] = "5";
                break;
            case "Jul": parts[1] = "6";
                break;
            case "Aug": parts[1] = "7";
                break;
            case "Sep": parts[1] = "8";
                break;
            case "Oct": parts[1] = "9";
                break;
            case "Nov": parts[1] = "10";
                break;
            case "Dec": parts[1] = "11";
                break;
            default: parts[1] = "Invalid Month";
                break;
        }

        Log.e("Date",date.toString());
        Log.e("Day of week", parts[0]);
        Log.e("Month", parts[1]);
        Log.e("Day of month", parts[2]);
        Log.e("Hours", parts[3]);
        Log.e("Minutes", parts[4]);
        Log.e("Seconds", parts[5]);
        Log.e("Time Zone", parts[6]);
        Log.e("Year", parts[7]);

        return parts;
    }


}
