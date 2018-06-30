package generalinfo.powellmakerspace.org.makerspacelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginMethodWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_method_window);

        // Launch QR Code Reader
        Button qrcodeButton = (Button) findViewById(R.id.qrcodeButton);
        qrcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchQRCodeReader = new Intent(getApplicationContext(), QRCodeWindow.class);
                startActivity(launchQRCodeReader);
                finish();
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
}
