package generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic;

import android.database.sqlite.SQLiteDatabase;

public class TypeVisitsStatisticGenerator implements StatisticGenerator {

    private static String TYPE_VISITS =
            "SELECT COUNT(*) " +
            "FROM " +
                    "Visits " +
            "INNER JOIN " +
                    "Members ON Visits.member_id = Members.member_id " +
            "WHERE " +
                    "Members.membership_type = ${membership_type} and " +
                    "Visits.arrival_time > ${arrival_time} and " +
                    "Visits.arrival_time < ${departure_time};";

    private SqlIntegerStatisticGenerator wrappedSqlStatisticsGenerator;

    public TypeVisitsStatisticGenerator(SQLiteDatabase db, long startDate, long endDate, String membershipType){
        String sqlQuery = TYPE_VISITS
                .replace("${membership_type}", "'" + membershipType + "'")
                .replace("${arrival_time}",Long.toString(startDate))
                .replace("${departure_time}",Long.toString(endDate));

        wrappedSqlStatisticsGenerator = new SqlIntegerStatisticGenerator(membershipType, sqlQuery, db);
    }

    @Override
    public StatisticResult generateStatistic(){
        return wrappedSqlStatisticsGenerator.generateStatistic();
    }
}
