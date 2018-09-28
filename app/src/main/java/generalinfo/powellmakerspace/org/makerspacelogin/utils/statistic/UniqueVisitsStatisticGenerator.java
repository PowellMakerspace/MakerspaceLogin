package generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic;

import android.database.sqlite.SQLiteDatabase;

public class UniqueVisitsStatisticGenerator implements StatisticGenerator {

    private static String UNIQUE_VISITS =
            "SELECT DISTINCT " +
                    "VISITS.member_id " +
            "FROM " +
                    "Visits " +
            "WHERE " +
                    "Visits.arrival_time > ${arrival_time} and " +
                    "Visits.arrival_time < ${departure_time};";

    private SqlIntegerStatisticGenerator wrappedSqlStatisticsGenerator;

    public UniqueVisitsStatisticGenerator(SQLiteDatabase db, long startDate, long endDate){
        String sqlQuery = UNIQUE_VISITS
                .replace("${arrival_time}",Long.toString(startDate))
                .replace("${departure_time}",Long.toString(endDate));

        wrappedSqlStatisticsGenerator = new SqlIntegerStatisticGenerator("Unique Visits", sqlQuery, db);
    }

    @Override
    public StatisticResult generateStatistic(){
        return wrappedSqlStatisticsGenerator.generateStatistic();
    }
}
