package generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TimeStatisticResult implements StatisticResult {

    private String fieldName;
    private long value;

    public TimeStatisticResult(String fieldName, long value){
        this.fieldName = fieldName;
        this.value = value;
    }

    @Override
    public void toCsv(CSVPrinter csvPrinter) throws IOException{
        String resultValue = String.format("%d hr, %d min, %d sec",
                // Calculate Hours from Milliseconds
                TimeUnit.MILLISECONDS.toHours(value),
                // Calculate Minutes from Milliseconds, removing those accounted for in hours
                TimeUnit.MILLISECONDS.toMinutes(value) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(value)),
                // Calculate Seconds from Milliseconds, removing those accounted for in minutes and hours
                TimeUnit.MILLISECONDS.toSeconds(value) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(value) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(value))) -
                        TimeUnit.HOURS.toSeconds(TimeUnit.MILLISECONDS.toHours(value))
                );
        csvPrinter.printRecord(fieldName, resultValue);
    }
}
