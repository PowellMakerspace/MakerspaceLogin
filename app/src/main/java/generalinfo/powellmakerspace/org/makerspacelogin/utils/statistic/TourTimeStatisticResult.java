package generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.util.List;

import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Tour;

public class TourTimeStatisticResult implements StatisticResult {


        private String fieldName;
        private List<Tour> tours;

        private long result;
        private long totalResult;

        private int hours;
        private int minutes;
        private int seconds;


        public TourTimeStatisticResult(String fieldName, List<Tour> tours){
            this.fieldName = fieldName;
            this.tours = tours;
        }

        @Override
        public void toCsv(CSVPrinter csvPrinter) throws IOException {

            for(Tour tour: tours) {
                result = tour.getTour_departure_time() - tour.getTour_arrival_time();

                totalResult += result * tour.getTour_visitor_number();

                hours = (int) (result / 3600);
                minutes = (int) (result / 60) - (hours * 60);
                seconds = (int) (result - (hours * 3600) - (minutes * 60));

                csvPrinter.printRecord(tour.getTour_name(), Integer.toString(hours) + ":" +
                        Integer.toString(minutes) + ":" + Integer.toString(seconds), "Visitors: " + tour.getTour_visitor_number());
            }

            hours = (int) (totalResult / 3600);
            minutes = (int) (totalResult / 60) - (hours * 60);
            seconds = (int) (totalResult - (hours * 3600) - (minutes * 60));

            csvPrinter.printRecord("Total Time",Integer.toString(hours) + ":" +
                    Integer.toString(minutes) + ":" + Integer.toString(seconds));
        }

}
