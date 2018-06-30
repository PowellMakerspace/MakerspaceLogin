package generalinfo.powellmakerspace.org.makerspacelogin.LoginWindows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;
import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Member;
import generalinfo.powellmakerspace.org.makerspacelogin.R;

public class UpdateWindow extends AppCompatActivity {

    DatabaseHelper makerspaceDatabase;
    EditText nameUpdateEditText;
    RadioGroup updateMembershipRadioGroup;
    RadioButton membershipTypeRadioButton;
    Button updateMemberButton;
    long member_id;
    Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_window);

        // Start Database Helper
        makerspaceDatabase = new DatabaseHelper(this);

        // Initiate member
        member_id = getIntent().getLongExtra("org.powellmakerspace.generalinfo.MEMBER_ID",-1);
        member = makerspaceDatabase.getMember(member_id);

        // Initialize user interface elements
        nameUpdateEditText = (EditText) findViewById(R.id.nameUpdateEditText);
        updateMembershipRadioGroup = (RadioGroup) findViewById(R.id.updateMembershipRadioGroup);
        updateMemberButton = (Button) findViewById(R.id.updateMemberButton);

        // Set interface elements by member info
        nameUpdateEditText.setText(member.getMemberName());
        updateMembershipRadioGroup.check(getMemberTypeIndex(member.getMembershipType()));

        // Initiate button and add action listener to update
        updateMemberButton = (Button) findViewById(R.id.updateMemberButton);
        updateMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Change Member Name
                member.setMemberName(nameUpdateEditText.getText().toString());

                // Find the selected RadioButton and get text
                int selectedId = updateMembershipRadioGroup.getCheckedRadioButtonId();
                membershipTypeRadioButton = (RadioButton) findViewById(selectedId);
                member.setMembershipType(membershipTypeRadioButton.getText().toString());

                // Update the Database
                int rowsAffected = makerspaceDatabase.updateMember(member);

                // Return to confirm window
                Intent launchMemberConfirmWindow = new Intent(getApplicationContext(), MemberConfirmWindow.class);
                launchMemberConfirmWindow.putExtra("org.powellmakerspace.generalinfo.MEMBER_ID", member_id);
                startActivity(launchMemberConfirmWindow);
                finish();
            }
        });
    }

    /**
     * Method designed to get the proper ID number for the Radiobutton of the current membership type.
     * @param membershipType String description of the membership type.
     * @return int identification number of the radiobutton in question
     */
    public int getMemberTypeIndex(String membershipType){
        switch(membershipType){
            case "Individual": return R.id.individualRadioButton;
            case "Family": return R.id.familyRadioButton;
            case "Volunteer": return R.id.volunteerRadioButton;
            case "CoWorkspace": return R.id.coWorkspaceRadioButton;
            case "Kitchen Renter": return R.id.kitchenRenterRadioButton;
            case "Library Pass": return R.id.libraryPassRadioButton;
            case "NWC Student": return R.id.nwcStudentRadioButton;
            case "NonMember": return R.id.nonMemberRadioButton;

            default: return -1;
        }
    }
}
