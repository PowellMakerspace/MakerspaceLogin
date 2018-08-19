//package generalinfo.powellmakerspace.org.makerspacelogin.ExportDatabaseTests;
//
//import android.app.ProgressDialog;
//import android.database.Cursor;
//import android.os.AsyncTask;
//import android.os.Environment;
//import android.widget.Toast;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//
//import generalinfo.powellmakerspace.org.makerspacelogin.AdminWindows.AdminMenu;
//import generalinfo.powellmakerspace.org.makerspacelogin.ExportDatabaseTests.CSVWriter;
//import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;
//
//public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean> {
//
//    private final ProgressDialog dialog = new ProgressDialog(AdminMenu.getContext());
//    DatabaseHelper makerspaceDatabase;
//
//    @Override
//    protected void onPreExecute(){
//        this.dialog.setMessage("Exporting database...");
//        this.dialog.show();
//        makerspaceDatabase = new DatabaseHelper(AdminMenu.this);
//    }
//
//    protected Boolean doInBackground(final String... args){
//
//        File exportDir = new File(Environment.getExternalStorageDirectory(),"/codesss/");
//        if (!exportDir.exists()) { exportDir.mkdir(); }
//
//        File file = new File(exportDir, "makerspaceDataExport.csv");
//        try{
//            file.createNewFile();
//            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
//            Cursor curCSV = makerspaceDatabase.raw();
//            csvWrite.writeNext(curCSV.getColumnNames());
//            while(curCSV.moveToNext()){
//                String arrStr[] = null;
//                String[] mySecondStringArray = new String[curCSV.getColumnNames().length];
//                for(int i=0; i<curCSV.getColumnNames().length; i++){
//                    mySecondStringArray[i] = curCSV.getString(i);
//                }
//                csvWrite.writeNext(mySecondStringArray);
//            }
//            csvWrite.close();
//            curCSV.close();
//            return true;
//        } catch (IOException e){
//            return false;
//        }
//    }
//
//    protected void onPostExecute(final Boolean success){
//        if (this.dialog.isShowing()) { this.dialog.dismiss(); }
//        if (success) {
//            Toast.makeText(AdminMenu.this, "Export successful!", Toast.LENGTH_SHORT).show();
////            ShareGif();
//        } else{
//            Toast.makeText(AdminMenu.this,"Export failed", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//}
