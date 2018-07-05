package generalinfo.powellmakerspace.org.makerspacelogin.AdminWindows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Report;
import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;
import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.WelcomeWindow;
import generalinfo.powellmakerspace.org.makerspacelogin.R;

public class AdminMenu extends AppCompatActivity {

    Button logoutButton;
    Button fullReportButton;
    Button recentsButton;

    DatePicker startDatePicker;
    DatePicker endDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

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
                long startDate = convertToUnixTimeStamp(startDatePicker.getYear(),startDatePicker.getMonth(),
                        startDatePicker.getDayOfMonth(),0,0,0);
                long endDate = convertToUnixTimeStamp(endDatePicker.getYear(),endDatePicker.getMonth(),
                        endDatePicker.getDayOfMonth(),0,0,0);
                Report report = new Report(getApplicationContext(), startDate, endDate);
                Toast.makeText(getApplicationContext(),"IT WORKED", Toast.LENGTH_LONG).show();
            }
        });

        recentsButton = (Button) findViewById(R.id.recentsButton);
        recentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"View Recent Members",Toast.LENGTH_LONG).show();
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
}
