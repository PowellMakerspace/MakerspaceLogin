package generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;

public interface StatisticResult {

    void toCsv(CSVPrinter csvPrinter) throws IOException;
}
