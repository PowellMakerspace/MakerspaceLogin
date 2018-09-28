package generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic;

import android.database.sqlite.SQLiteDatabase;

public class PurposeVisitsStatisticGenerator implements StatisticGenerator {

    // In theory, this should return purpose & the total time for each purpose
    // Question: Will I quickly run into overflow problems because SUM seems to return an int.
    private static String PURPOSE_TIME_VISITS =
            "SELECT " +
                    "Visits.visit_purpose, SUM(Visits.departure_time - Visits.arrival_time) " +
            "FROM " +
                    "Visits " +
            "WHERE " +
                    "Visits.arrival_time > ${arrival_time} and " +
                    "Visits.arrival_time < ${departure_time};";

    private SqlTimeStatisticGenerator wrappedSqlStatisticGenerator;

    public PurposeVisitsStatisticGenerator(SQLiteDatabase db, long startDate, long endDate){
        String sqlQuery = PURPOSE_TIME_VISITS
                .replace("${arrival_time}", Long.toString(startDate))
                .replace("${departure_time}", Long.toString(endDate));

        // If the theory above works, the SqlTimeStatistic Generator needs to be reworked quite a bit.
        wrappedSqlStatisticGenerator = new SqlTimeStatisticGenerator("Placeholder", sqlQuery, db);
    }

    @Override
    public StatisticResult generateStatistic(){
        return wrappedSqlStatisticGenerator.generateStatistic();
    }
}
