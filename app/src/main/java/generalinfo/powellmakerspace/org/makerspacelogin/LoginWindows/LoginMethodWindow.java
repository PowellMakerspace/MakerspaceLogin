package generalinfo.powellmakerspace.org.makerspacelogin.LoginWindows;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import generalinfo.powellmakerspace.org.makerspacelogin.R;

public class LoginMethodWindow extends AppCompatActivity {

    long member_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_method_window);

        // Launch QR Code Reader
        Button qrcodeButton = (Button) findViewById(R.id.qrcodeButton);
        final Activity activity = this;
        qrcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan Code");
                integrator.setCameraId(1);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        // Launch Login Search Window
        Button manualButton = (Button) findViewById(R.id.manualButton);
        manualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchLoginSearch = new Intent(getApplicationContext(), LoginSearchWindow.class);
                startActivity(launchLoginSearch);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode,data);
        if (result != null){
            if (result.getContents()==null){
                Toast.makeText(this,"You cancelled the scanning",Toast.LENGTH_LONG).show();
            }
            else{
                String[] resultText = result.getContents().toString().split(": ");
                if(resultText[0].equals("MS ID")){
                    //Get and parse member ID from QR Code Reader
                    Toast.makeText(this, "Your ID is:" + resultText[1], Toast.LENGTH_LONG).show();
                    member_id = Long.parseLong(resultText[1]);

                    //Continue Login System using member ID
                    Intent launchMemberConfirmWindow = new Intent(getApplicationContext(), MemberConfirmWindow.class);
                    launchMemberConfirmWindow.putExtra("org.powellmakerspace.generalinfo.MEMBER_ID", member_id);
                    startActivity(launchMemberConfirmWindow);
                    finish();
                }
                else{
                    Toast.makeText(this,"Not a Makerspace Code",Toast.LENGTH_LONG).show();
                }
            }
        }
        else {
            super.onActivityResult(resultCode, resultCode, data);
        }


    }
}
