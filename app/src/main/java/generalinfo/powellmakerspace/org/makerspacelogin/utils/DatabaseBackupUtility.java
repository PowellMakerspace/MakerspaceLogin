package generalinfo.powellmakerspace.org.makerspacelogin.utils;

import android.database.Cursor;
import android.util.Log;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;

public class DatabaseBackupUtility {

    private static String DEBUG_TAG = "DbExport";

    private DatabaseHelper databaseHelper;

    private File backupWorkingDirectory;

    private File csvZipFile;

    public DatabaseBackupUtility(DatabaseHelper databaseHelper, File backupWorkingDirectory) {
        this.databaseHelper = databaseHelper;
        this.backupWorkingDirectory = backupWorkingDirectory;
    }

    public void performBackup() {

        // Verify Directory exists and is empty
        backupWorkingDirectory.mkdirs();
        for (File file : backupWorkingDirectory.listFiles()){
            file.delete();
        }

        // Extract all tables into csv files
        for (String table : databaseHelper.getTableNames()) {
            try {
                convertTableToCsv(table, new File(backupWorkingDirectory, table + ".csv"));
            } catch (IOException e) {
                Log.w(DEBUG_TAG, "Failed to convert table " + table, e);
            }
        }

        compressCabDirectory();
    }

    public File getBackupFile() {
        return csvZipFile;
    }

    private void convertTableToCsv(String table, File csvFile) throws IOException {
        Log.i(DEBUG_TAG, "Exporting Table: " + table);

        Cursor curCSV = null;
        CSVPrinter csvPrinter = null;

        try {
            // Open new Database Cursor
            curCSV = databaseHelper.raw(table);
            String[] headers = curCSV.getColumnNames();

            // Open new Csv File
            csvPrinter = new CSVPrinter(new FileWriter(csvFile),
                    CSVFormat.DEFAULT.withHeader(headers));

            int recordCount = 0;
            while (curCSV.moveToNext()) {

                // Copy Data out of database cursor and in to the databaseRecord array
                String[] databaseRecord = new String[headers.length];
                for (int i = 0; i < headers.length; i++) {
                    databaseRecord[i] = curCSV.getString(i);
                }
                Log.d("DbExport", "Writing record: " + Arrays.toString(databaseRecord));

                // Write array to csv file
                csvPrinter.printRecord(databaseRecord);
                recordCount++;
            }
            Log.d(DEBUG_TAG, "Successfully exported table " + table + ". Total Records: " + recordCount);
        } finally {
            // Close any opened resources
            if (curCSV != null) {
                curCSV.close();
            }
            if (csvPrinter != null) {
                csvPrinter.close();
            }
        }
    }

    private void compressCabDirectory() {
        // Compress Directory to zip file
        File zippedCsvFiles = new File(backupWorkingDirectory.getParent(), backupWorkingDirectory.getName() + ".zip");

        // Buffer vars used during copying file to zip operations
        int len;
        byte[] buffer = new byte[2048];

        try (
                ZipOutputStream zipedCsvStream = new ZipOutputStream(new FileOutputStream(zippedCsvFiles))
        ) {
            for (File csvFile : backupWorkingDirectory.listFiles()) {
                // Create new entry to add to zip file
                ZipEntry zipEntry = new ZipEntry(backupWorkingDirectory.getName() + File.separator +
                        csvFile.getName());
                zipedCsvStream.putNextEntry(zipEntry);

                // Copy file into zip stream
                try (FileInputStream inputStream = new FileInputStream(csvFile)) {
                    while ((len = inputStream.read(buffer)) > 0) {
                        zipedCsvStream.write(buffer, 0, len);
                    }
                }
            }
        } catch (IOException e) {
            Log.e(DEBUG_TAG, "Failed to compress directory", e);
            csvZipFile = null;
        }
        csvZipFile = zippedCsvFiles;
    }
}