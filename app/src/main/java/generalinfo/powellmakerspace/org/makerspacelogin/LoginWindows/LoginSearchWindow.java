package generalinfo.powellmakerspace.org.makerspacelogin.LoginWindows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;
import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Member;
import generalinfo.powellmakerspace.org.makerspacelogin.Adapters.MemberAdapter;
import generalinfo.powellmakerspace.org.makerspacelogin.R;

public class LoginSearchWindow extends AppCompatActivity {

    DatabaseHelper makerspaceDatabase;
    ListView memberListView;
    MemberAdapter memberAdapter;
    Button cancelManualEntryButton;

    EditText memberSearchBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_search_window);

        // Begin Database Helper
        makerspaceDatabase = new DatabaseHelper(this);

        // Get all members from the database
        List<Member> allMembersList = makerspaceDatabase.getAllMembers();

        // Initiate Filter Text Bar
        memberSearchBox = (EditText) findViewById(R.id.memberSearchBox);
        memberSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<Member> filteredMembersList = makerspaceDatabase.filterMembersByName(s.toString());
                allMembersList.clear();
                for (Member member : filteredMembersList){
                    allMembersList.add(member);
                }
                MemberAdapter filteredAdapter = new MemberAdapter(getApplicationContext(), allMembersList);
                memberListView.setAdapter(filteredAdapter);
            }
        });

        // Initiate Listview
        memberListView = (ListView) findViewById(R.id.memberListView);
        memberAdapter = new MemberAdapter(this, allMembersList);
        memberListView.setAdapter(memberAdapter);

        memberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent launchMemberConfirmWindow = new Intent(getApplicationContext(), MemberConfirmWindow.class);
                launchMemberConfirmWindow.putExtra("org.powellmakerspace.generalinfo.MEMBER_ID", allMembersList.get(position).getMemberID());
                startActivity(launchMemberConfirmWindow);
                finish();
            }
        });

        cancelManualEntryButton = (Button) findViewById(R.id.cancelManualEntryButton);
        cancelManualEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchLoginMethod = new Intent(getApplicationContext(),LoginMethodWindow.class);
                startActivity(launchLoginMethod);
                finish();
            }
        });


    }
}
