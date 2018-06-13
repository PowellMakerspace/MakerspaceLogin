package generalinfo.powellmakerspace.org.makerspacelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class LoginSearchWindow extends AppCompatActivity {

    DatabaseHelper makerspaceDatabase;
    ListView memberListView;
    MemberAdapter memberAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_search_window);

        // Begin Database Helper
        makerspaceDatabase = new DatabaseHelper(this);

        // Get all members from the database
        List<Member> allMembersList = makerspaceDatabase.getAllMembers();

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
            }
        });


    }
}
