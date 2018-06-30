package generalinfo.powellmakerspace.org.makerspacelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ArrivingWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arriving_window);

        // Launch the login method activity
        Button returningMemberButton = (Button) findViewById(R.id.returningMemberButton);
        returningMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchLoginMethod = new Intent(getApplicationContext(), LoginMethodWindow.class);
                startActivity(launchLoginMethod);
                finish();
            }
        });

        // Launch the new member activity
        Button newMemberButton = (Button) findViewById(R.id.newMemberButton);
        newMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchNewMember = new Intent(getApplicationContext(), NewMemberWindow.class);
                startActivity(launchNewMember);
                finish();
            }
        });

        // Launch the tour activity
        Button tourButton = (Button) findViewById(R.id.tourButton);
        tourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchTourWindow = new Intent(getApplicationContext(), TourWindow.class);
                startActivity(launchTourWindow);
                finish();
            }
        });
    }
}
