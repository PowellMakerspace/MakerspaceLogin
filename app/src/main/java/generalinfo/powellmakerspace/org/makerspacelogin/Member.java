package generalinfo.powellmakerspace.org.makerspacelogin;

/**
 * Class defining a member object for easier database interactions
 */
public class Member {

    private long memberID;
    private String memberName;
    private String membershipType;

    // Constructors - three assigned with differing parameters, not yet sure why.
    public Member(){
    }

    public Member(String memberName, String membershipType){
        this.memberName = memberName;
        this.membershipType = membershipType;
    }

    public Member(long memberID, String memberName, String membershipType){
        this.memberID = memberID;
        this.memberName = memberName;
        this.membershipType = membershipType;
    }

    // Setters
    public void setMemberID(int memberID){
        this.memberID = memberID;
    }

    public void setMemberName(String memberName){
        this.memberName = memberName;
    }

    public void setMembershipType(String membershipType){
        this.membershipType = membershipType;
    }

    // Getters
    public long getMemberID(){
        return this.memberID;
    }

    public String getMemberName() {
        return this.memberName;
    }

    public String getMembershipType() {
        return this.membershipType;
    }
}
