package generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic;

import android.database.sqlite.SQLiteDatabase;

public class TotalVisitsStatisticGenerator implements StatisticGenerator {

    private static String TOTAL_VISITS =
            "SELECT COUNT(*) " +
            "FROM " +
                    "Visits " +
            "WHERE " +
                    "Visits.arrival_time > ${arrival_time} and " +
                    "Visits.arrival_time < ${departure_time};";

    private SqlIntegerStatisticGenerator wrappedSqlStatisticsGenerator;

    public TotalVisitsStatisticGenerator(SQLiteDatabase db, long startDate, long endDate){
        String sqlQuery = TOTAL_VISITS
                .replace("${arrival_time}", Long.toString(startDate))
                .replace("${departure_time}",Long.toString(endDate));

        wrappedSqlStatisticsGenerator = new SqlIntegerStatisticGenerator("Total Visits", sqlQuery, db);
    }

    @Override
    public StatisticResult generateStatistic(){
        return wrappedSqlStatisticsGenerator.generateStatistic();
    }
}
