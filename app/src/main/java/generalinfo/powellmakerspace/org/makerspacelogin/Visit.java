package generalinfo.powellmakerspace.org.makerspacelogin;

import java.time.LocalDateTime;

public class Visit {

    private long visitID;
    private long memberID;
    private long arrivalTime;
    private long departureTime;
    private String visitPurpose;

    // Constructors
    public Visit(){
    }

    public Visit(long visitID, long memberID, long arrivalTime, long departureTime, String visitPurpose){
        this.visitID = visitID;
        this.memberID = memberID;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.visitPurpose = visitPurpose;
    }

    // Setters
    public void setVisitID(long visitID) {
        this.visitID = visitID;
    }

    public void setMemberID(long memberID){
        this.memberID = memberID;
    }

    public void setArrivalTime(long arrivalTime){
        this.arrivalTime = arrivalTime;
    }

    public void setDepartureTime(long departureTime){
        this.departureTime = departureTime;
    }

    public void setVisitPurpose(String visitPurpose){
        this.visitPurpose = visitPurpose;
    }

    // Getters
    public long getVisitID() {
        return this.visitID;
    }

    public long getMemberID(){
        return this.memberID;
    }

    public long getArrivalTime() {
        return this.arrivalTime;
    }

    public long getDepartureTime() {
        return this.departureTime;
    }

    public String getVisitPurpose() {
        return this.visitPurpose;
    }
}
