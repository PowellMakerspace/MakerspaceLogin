package generalinfo.powellmakerspace.org.makerspacelogin.AdminWindows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.WelcomeWindow;
import generalinfo.powellmakerspace.org.makerspacelogin.R;

public class AdminLogin extends AppCompatActivity {

    String USER_NAME = "Anthony Riesen";
    String PASSWORD = "Password";

    EditText userNameTextEdit;
    EditText passwordTextEdit;

    Button adminSubmitButton;
    Button cancelAccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Workbook wb = new HSSFWorkbook();
        } catch (Exception e){

        }

        setContentView(R.layout.activity_admin_login);

        userNameTextEdit = (EditText) findViewById(R.id.userNameTextEdit);
        passwordTextEdit = (EditText) findViewById(R.id.passwordTextEdit);

        adminSubmitButton = (Button) findViewById(R.id.adminSubmitButton);
        cancelAccess = (Button) findViewById(R.id.cancelAccess);

        adminSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userNameTextEdit.getText().toString().equals(USER_NAME)){
                    if (passwordTextEdit.getText().toString().equals(PASSWORD)){
                        Intent launchAdminMenu = new Intent(getApplicationContext(), AdminMenu.class);
                        startActivity(launchAdminMenu);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect Username or Password",
                                Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(getApplicationContext(), "Incorrect Username or Password",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchWelcomeWindow = new Intent(getApplicationContext(), WelcomeWindow.class);
                startActivity(launchWelcomeWindow);
                finish();
            }
        });
    }
}
