package generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Tour;
import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;

public class TourStatisticGenerator implements StatisticGenerator {

    String TOUR_QUERY =
            "SELECT * FROM " +
                    "Tours " +
            "WHERE " +
                    "Tours.tour_arrival > ${arrival_time} and " +
                    "Tours.tour_departure < ${departure_time};";

    private SqlToursStatisticGenerator wrappedSqlStatisticsGenerator;

    public TourStatisticGenerator(SQLiteDatabase db, long startDate, long endDate){

        String sqlQuery = TOUR_QUERY
                .replace("${arrival_time}", Long.toString(startDate))
                .replace("${departure_time}", Long.toString(endDate));

        wrappedSqlStatisticsGenerator = new SqlToursStatisticGenerator(db, sqlQuery);
    }

    @Override
    public StatisticResult generateStatistic(){
        return wrappedSqlStatisticsGenerator.generateStatistic();
    }
}

