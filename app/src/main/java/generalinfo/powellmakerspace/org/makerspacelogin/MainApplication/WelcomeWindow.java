package generalinfo.powellmakerspace.org.makerspacelogin.MainApplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import generalinfo.powellmakerspace.org.makerspacelogin.AdminWindows.AdminLogin;
import generalinfo.powellmakerspace.org.makerspacelogin.LoginWindows.ArrivingWindow;
import generalinfo.powellmakerspace.org.makerspacelogin.LogoutWindows.LeavingTypeWindow;
import generalinfo.powellmakerspace.org.makerspacelogin.R;

public class WelcomeWindow extends AppCompatActivity {

    Button arrivingButton;
    Button leavingButton;
    Button adminButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_window);

        // Launch arriving window
        arrivingButton = (Button) findViewById(R.id.arrivingButton);
        arrivingButton.setOnClickListener(new View.OnClickListener() {

              @Override
              public void onClick(View v) {
                  Intent launchArrivingWindow = new Intent(getApplicationContext(), ArrivingWindow.class);
                  startActivity(launchArrivingWindow);
                  finish();
              }
          });

        // Launch leaving window
        leavingButton = (Button) findViewById(R.id.leavingButton);
        leavingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchLeavingTypeWindow = new Intent(getApplicationContext(), LeavingTypeWindow.class);
                startActivity(launchLeavingTypeWindow);
                finish();
            }
        });

        // Launch admin login window
        adminButton = (Button) findViewById(R.id.adminButton);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchAdminLogin = new Intent(getApplicationContext(), AdminLogin.class);
                startActivity(launchAdminLogin);
                finish();
            }
        });
    }
}
