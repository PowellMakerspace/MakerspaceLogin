package generalinfo.powellmakerspace.org.makerspacelogin.Classes;

public class Tour {

    private long tour_id;
    private String tour_name;
    private int tour_visitor_number;
    private long tour_arrival_time;
    private long tour_departure_time;

    // Constructors
    public Tour(){
    }

    // Constructors
    public Tour(String tour_name, int tour_visitor_number, long tour_arrival_time, long tour_departure_time){
        this.tour_name = tour_name;
        this.tour_visitor_number = tour_visitor_number;
        this.tour_arrival_time = tour_arrival_time;
        this.tour_departure_time = tour_departure_time;
    }

    // Getters
    public long getTour_id() {
        return tour_id;
    }

    public String getTour_name() {
        return tour_name;
    }

    public int getTour_visitor_number() {
        return tour_visitor_number;
    }

    public long getTour_arrival_time(){
        return tour_arrival_time;
    }

    public long getTour_departure_time() {
        return tour_departure_time;
    }

    // Setters
    public void setTour_id(long tour_id) {
        this.tour_id = tour_id;
    }

    public void setTour_name(String tour_name) {
        this.tour_name = tour_name;
    }

    public void setTour_visitor_number(int tour_visitor_number) {
        this.tour_visitor_number = tour_visitor_number;
    }

    public void setTour_arrival_time(long tour_arrival_time) {
        this.tour_arrival_time = tour_arrival_time;
    }

    public void setTour_departure_time(long tour_departure_time) {
        this.tour_departure_time = tour_departure_time;
    }
}
