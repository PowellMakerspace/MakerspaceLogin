package generalinfo.powellmakerspace.org.makerspacelogin.LogoutWindows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;
import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Member;
import generalinfo.powellmakerspace.org.makerspacelogin.Adapters.MemberAdapter;
import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.WelcomeWindow;
import generalinfo.powellmakerspace.org.makerspacelogin.R;
import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Visit;

public class LeavingVisitWindow extends AppCompatActivity {

    DatabaseHelper makerspaceDatabase;

    List<Visit> currentVisits;
    List<Member> presentMembers;
    ListView visitListView;
    MemberAdapter memberAdapter;

    Button cancelLeavingVisit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaving_visit_window);

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

                // Pass visit information to next activity
                Intent launchLeavingConfirm = new Intent(getApplicationContext(), LeavingVisitConfirmWindow.class);
                launchLeavingConfirm.putExtra("org.powellmakerspace.generalinfo.VISIT_ID", currentVisits.get(position).getVisitID());
                startActivity(launchLeavingConfirm);
                finish();
            }
        });

        // Initiate Cancel Button
        cancelLeavingVisit = (Button) findViewById(R.id.cancelLeavingVisit);
        cancelLeavingVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchWelcomeWindow = new Intent(getApplicationContext(), WelcomeWindow.class);
                startActivity(launchWelcomeWindow);
                finish();
            }
        });
    }
}
