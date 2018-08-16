package generalinfo.powellmakerspace.org.makerspacelogin.Classes;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;

public class Report {
    List<Visit> visits;
    List<Tour> tours;

    int totalVisits;
    int uniqueVisits;
    int totalTours;
    int uniqueTours;

    DatabaseHelper makerspaceDatabase;

    Map<String, Integer> membershipTypeCount = new HashMap<>();

    public Report(Context context, long startDate, long endDate){
        makerspaceDatabase = new DatabaseHelper(context);
        
        visits = makerspaceDatabase.getVisitsFromRange(startDate, endDate);
        tours = makerspaceDatabase.getToursFromRange(startDate,endDate);

        this.totalVisits = visits.size();
        this.totalTours = tours.size();

        // Analyze the Visits
        for (int i=0; i<this.totalVisits; i++){

            long visitLength = visits.get(i).getDepartureTime() - visits.get(i).getArrivalTime();

        }
    }

}
