package generalinfo.powellmakerspace.org.makerspacelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LeavingWindow extends AppCompatActivity {

    DatabaseHelper makerspaceDatabase;

    List<Visit> currentVisits;
    List<Member> presentMembers;
    ListView visitListView;
    MemberAdapter memberAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaving_window);

        // Begin Database Helper
        makerspaceDatabase = new DatabaseHelper(this);

        // Get all active visits from the database
        currentVisits = makerspaceDatabase.getActiveVisits();

        // Initiate present member list
        presentMembers = new ArrayList<>();

        // Generate an active member list from the active visits list
        for (int i=0; i<currentVisits.size(); i++){
            Visit visit = currentVisits.get(i); // Get Visit
            long member_id = visit.getMemberID(); // Get member id of visit
            Member member = makerspaceDatabase.getMember(member_id); // Get member
            presentMembers.add(member); // Add member to member list
        }

        // Initiate ListView
        visitListView = (ListView) findViewById(R.id.visitListView);
        memberAdapter = new MemberAdapter(this, presentMembers);
        visitListView.setAdapter(memberAdapter);

        visitListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // set current time to logout
                Visit visit = currentVisits.get(position);
                visit.setDepartureTime(System.currentTimeMillis()/1000);

                // update the database
                makerspaceDatabase.updateVisit(visit);

                Toast.makeText(getApplicationContext(),"Thank you for visiting!", Toast.LENGTH_LONG).show();

                Intent launchWelcomeWindow = new Intent(getApplicationContext(), WelcomeWindow.class);
                startActivity(launchWelcomeWindow);
            }
        });
    }
}
