package generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic;

import android.util.Log;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TimeStatisticResult implements StatisticResult {

    private String fieldName;
    private int result;

    private int hours;
    private int minutes;
    private int seconds;

    public TimeStatisticResult(String fieldName, int result){
        this.fieldName = fieldName;
        this.result = result;
    }

    @Override
    public void toCsv(CSVPrinter csvPrinter) throws IOException{

        hours = (int) (result / 3600);
        minutes = (int) (result / 60) - (hours * 60);
        seconds = (int) (result - (hours * 3600) - (minutes * 60));

        csvPrinter.printRecord(fieldName, Integer.toString(hours) + ":" +
                Integer.toString(minutes) + ":" + Integer.toString(seconds));
    }
}
