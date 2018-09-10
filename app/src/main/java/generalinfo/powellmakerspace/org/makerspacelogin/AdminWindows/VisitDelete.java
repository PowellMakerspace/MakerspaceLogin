package generalinfo.powellmakerspace.org.makerspacelogin.AdminWindows;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Member;
import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Visit;
import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;
import generalinfo.powellmakerspace.org.makerspacelogin.R;

public class VisitDelete extends AppCompatActivity {

    EditText visitNumber;
    Button removeVisitButton;

    DatabaseHelper makerspaceDatabase;

    long memberId;
    Member member;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_delete);

        makerspaceDatabase = new DatabaseHelper(this);

        EditText visitNumber = (EditText) findViewById(R.id.visitNumber);

        Button removeVisitButton = (Button) findViewById(R.id.removeVisitButton);
//        removeVisitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                memberId = new Long(visitNumber.getText().toString());
//                member = makerspaceDatabase.getMember(memberId);
//                makerspaceDatabase.deleteMember(member,true);
//                Toast.makeText(getApplicationContext(),"Visit Deleted",Toast.LENGTH_LONG).show();
//            }
//        });






        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
