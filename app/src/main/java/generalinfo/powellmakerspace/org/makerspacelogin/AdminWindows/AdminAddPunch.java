package generalinfo.powellmakerspace.org.makerspacelogin.AdminWindows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Member;
import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;
import generalinfo.powellmakerspace.org.makerspacelogin.R;

public class AdminAddPunch extends AppCompatActivity {

    EditText memberNameEntryEditText;
    EditText numberPunchesEditText;

    DatabaseHelper makerspaceDatabase;

    Button addPunchesButton;
    Button cancelPunchesButton;

    String memberName;
    Member member;
    int punchesToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_punch);

        makerspaceDatabase = new DatabaseHelper(this);

        memberNameEntryEditText = (EditText) findViewById(R.id.memberNameEntryEditText);
        numberPunchesEditText = (EditText) findViewById(R.id.numberPunchesEditText);

        addPunchesButton = (Button) findViewById(R.id.addPunchButton);
        cancelPunchesButton = (Button) findViewById(R.id.cancelPunchesButton);

        addPunchesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    memberName = memberNameEntryEditText.getText().toString();
                    punchesToAdd = Integer.valueOf(numberPunchesEditText.getText().toString());
                    member = makerspaceDatabase.getMemberByName(memberName);
                    member.setPunchPasses(punchesToAdd);
                    makerspaceDatabase.updateMember(member);
                    Toast.makeText(getApplicationContext(),"Punches Added Successfully",Toast.LENGTH_LONG).show();

                    Intent adminMenuIntent = new Intent(getApplicationContext(), AdminMenu.class);
                    startActivity(adminMenuIntent);
                    finish();

                } catch (NullPointerException e){
                    Toast.makeText(getApplicationContext(),"Punch Adding Failed",Toast.LENGTH_LONG).show();
                }

            }
        });

        cancelPunchesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Canceled",Toast.LENGTH_LONG).show();
                Intent adminMenuIntent = new Intent(getApplicationContext(), AdminMenu.class);
                startActivity(adminMenuIntent);
                finish();
            }
        });
    }
}
