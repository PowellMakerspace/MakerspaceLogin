package generalinfo.powellmakerspace.org.makerspacelogin.Classes;

/**
 * Class defining a member object for easier database interactions
 */
public class Member {

    private long memberID;
    private String memberName;
    private String membershipType;
    private int punchPasses;

    // Constructors - three assigned with differing parameters, not yet sure why.
    public Member(){
    }

    public Member(String memberName, String membershipType, int punchPasses){
        this.memberName = memberName;
        this.membershipType = membershipType;
        this.punchPasses = punchPasses;
    }

    public Member(long memberID, String memberName, String membershipType, int punchPasses){
        this.memberID = memberID;
        this.memberName = memberName;
        this.membershipType = membershipType;
        this.punchPasses = punchPasses;
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

    public void setPunchPasses(int punchPasses){
        this.punchPasses = punchPasses;
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

    public int getPunchPasses(){
        return this.punchPasses;
    }
}
