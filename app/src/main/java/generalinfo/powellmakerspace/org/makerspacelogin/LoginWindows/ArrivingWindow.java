package generalinfo.powellmakerspace.org.makerspacelogin.LoginWindows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.zxing.integration.android.IntentIntegrator;

import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.WelcomeWindow;
import generalinfo.powellmakerspace.org.makerspacelogin.R;

public class ArrivingWindow extends AppCompatActivity {

    Button returningMemberButton;
    Button newMemberButton;
    Button tourButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arriving_window);

        // Launch the login method activity
        returningMemberButton = (Button) findViewById(R.id.returningMemberButton);
        returningMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchLoginMethod = new Intent(getApplicationContext(), LoginMethodWindow.class);
                startActivity(launchLoginMethod);
                finish();
            }
        });

        // Launch the new member activity
        newMemberButton = (Button) findViewById(R.id.newMemberButton);
        newMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchNewMember = new Intent(getApplicationContext(), NewMemberWindow.class);
                startActivity(launchNewMember);
                finish();
            }
        });

        // Launch the tour activity
        tourButton = (Button) findViewById(R.id.tourButton);
        tourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchTourWindow = new Intent(getApplicationContext(), TourWindow.class);
                startActivity(launchTourWindow);
                finish();
            }
        });

        // Launch the welcome window activity
        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchWelcomeWindow = new Intent(getApplicationContext(), WelcomeWindow.class);
                startActivity(launchWelcomeWindow);
                finish();
            }
        });
    }
}
