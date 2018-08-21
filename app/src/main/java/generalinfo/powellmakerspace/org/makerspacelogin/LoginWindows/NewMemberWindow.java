package generalinfo.powellmakerspace.org.makerspacelogin.LoginWindows;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;
import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Member;
import generalinfo.powellmakerspace.org.makerspacelogin.R;

public class NewMemberWindow extends AppCompatActivity {

    DatabaseHelper makerspaceDatabase;

    EditText memberNameEditText;
    RadioGroup membershipRadioGroup;
    RadioButton membershipRadioButton;
    String memberName, membershipType;

    Button nextButton;
    Button createMemberButton;
    ImageView QRDisplayImageView;

    long memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_member_window);

        // Begin DatabaseHelper
        makerspaceDatabase = new DatabaseHelper(this);

        memberNameEditText = (EditText) findViewById(R.id.memberNameEditText);

        membershipRadioGroup = (RadioGroup) findViewById(R.id.membershipRadioGroup);

        nextButton = (Button) findViewById(R.id.nextButton);
        createMemberButton = (Button) findViewById(R.id.createMemberButton);

        QRDisplayImageView = (ImageView) findViewById(R.id.QRDisplayImageView);


        createMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Member Name
                memberName = memberNameEditText.getText().toString();

                // Find the selected RadioButton and get text
                int selectedId = membershipRadioGroup.getCheckedRadioButtonId();
                membershipRadioButton = (RadioButton) findViewById(selectedId);
                membershipType = membershipRadioButton.getText().toString();

                // Create new Member Object
                Member newMember = new Member(memberName, membershipType);

                // Add new member to the database
                memberId = makerspaceDatabase.createMember(newMember);

                // Create and display QR Code

                if (memberId > -1) {
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode("MS ID: " + memberId, BarcodeFormat.QR_CODE, 500, 500);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        QRDisplayImageView.setImageBitmap(bitmap);

                        // Attempt at converting bitmap to file for emailing

                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.putExtra(Intent.EXTRA_EMAIL,"r.anthony1961@hotmail.com");
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Powell Makerspace QR Code");
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT," ");

                        /*
                        Important part where the files is created and saved
                         */
                        emailIntent.setType("image/"); // accept any image
                        File qrFile = new File(getCacheDir(),"qrCodeFile.png");

                        try{
                            boolean fileCreated = qrFile.createNewFile();
                            if (fileCreated){
                                // write bitmap to that file
                                FileOutputStream outputStream = new FileOutputStream(qrFile);
                                bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
                                outputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("SAVE FAILED","Could not save file");
                        }

                        emailIntent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(qrFile));
                        startActivity(Intent.createChooser(emailIntent,"Send your email in:"));

//                        //-Create a file to write bitmap data
//                        File qrFile = new File(getCacheDir(),"qrCodeFile.png");
//                        qrFile.createNewFile();
//
//                        //-Convert bitmap to byte array
//                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
//                        byte[] bitmapdata = bos.toByteArray();
//
//                        //-Write the bytes in file
//                        FileOutputStream fos = new FileOutputStream(qrFile);
//                        fos.write(bitmapdata);
//                        fos.flush();
//                        fos.close();
//
//                        shareFile(qrFile);
//
                    } catch (WriterException e) {
                        e.printStackTrace();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
                    }
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Intent
                Intent launchSurveyWindow = new Intent(getApplicationContext(), SurveyWindow.class);

                // Add info to be passed to next activity
                launchSurveyWindow.putExtra("org.powellmakerspace.generalinfo.MEMBER_ID", memberId);

                // Move to next activity
                startActivity(launchSurveyWindow);
                finish();
            }
        });
    }
    private void shareFile(File file){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/zip");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        startActivity(Intent.createChooser(shareIntent,"Share QR"));
    }

}
