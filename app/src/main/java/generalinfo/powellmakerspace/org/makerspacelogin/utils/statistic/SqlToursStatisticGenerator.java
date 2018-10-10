package generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Tour;

public class SqlToursStatisticGenerator implements StatisticGenerator{

    private SQLiteDatabase db;
    private String sqlQuery;
    private String fieldName;

    public SqlToursStatisticGenerator(SQLiteDatabase db, String sqlQuery){
        this.db = db;
        this.sqlQuery = sqlQuery;
    }

    @Override
    public StatisticResult generateStatistic(){

        // Create Tour List
        List<Tour> tours = new ArrayList<>();

        //Query the Database
        try (Cursor c = db.rawQuery(sqlQuery, null)){
            if (c.moveToFirst()){
                do{
                    // Create new tour object
                    Tour tour = new Tour();
                    tour.setTour_id(c.getLong(c.getColumnIndex("tour_id")));
                    tour.setTour_name(c.getString(c.getColumnIndex("tour_name")));
                    tour.setTour_visitor_number(c.getInt(c.getColumnIndex("tour_visitor_number")));
                    tour.setTour_arrival_time(c.getLong(c.getColumnIndex("tour_arrival")));
                    tour.setTour_departure_time(c.getLong(c.getColumnIndex("tour_departure")));

                    // Add tour object to the database
                    tours.add(tour);
                // Move to next item
                } while (c.moveToNext());
            }
        }

        return new TourTimeStatisticResult("Tours", tours);

    }
}
