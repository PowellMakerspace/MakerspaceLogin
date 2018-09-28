package generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;

public class IntegerStatisticResult implements StatisticResult {

    private String fieldName;
    private int value;


    public IntegerStatisticResult(String fieldName, int value){
        this.fieldName = fieldName;
        this.value = value;
    }

    @Override
    public void toCsv(CSVPrinter csvPrinter) throws IOException {

        csvPrinter.printRecord(fieldName, value);
    }
}
