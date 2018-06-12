package generalinfo.powellmakerspace.org.makerspacelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class NewMemberWindow extends AppCompatActivity {

    DatabaseHelper makerspaceDatabase;

    EditText memberNameEditText;
    RadioGroup membershipRadioGroup;
    RadioButton membershipRadioButton;
    String memberName, membershipType;

    Button createMemberButton;

    long memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_member_window);

        // Begin DatabaseHelper
        makerspaceDatabase = new DatabaseHelper(this);

        memberNameEditText = (EditText) findViewById(R.id.memberNameEditText);

        membershipRadioGroup = (RadioGroup) findViewById(R.id.membershipRadioGroup);

        createMemberButton = (Button) findViewById(R.id.createMemberButton);


        createMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Member Name
                memberName = memberNameEditText.getText().toString();

                // Find the selected RadioButton and get text
                int selectedId = membershipRadioGroup.getCheckedRadioButtonId();
                membershipRadioButton = (RadioButton) findViewById(selectedId);
                membershipType = membershipRadioButton.getText().toString();

                // Create new Member Object
                Member newMember = new Member(memberName, membershipType);

                // Add new member to the database
                memberId = makerspaceDatabase.createMember(newMember);

                // Create Intent
                Intent launchSurveyWindow = new Intent(getApplicationContext(), SurveyWindow.class);

                // Add info to be passed to next activity
                launchSurveyWindow.putExtra("org.powellmakerspace.makerspacelogin.MEMBER_ID", memberId);

                // Move to next activity
                startActivity(launchSurveyWindow);
            }
        });
    }

}
