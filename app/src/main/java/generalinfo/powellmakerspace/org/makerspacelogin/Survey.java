package generalinfo.powellmakerspace.org.makerspacelogin;

public class Survey {

    private int surveyID;
    private String learnedAbout;

    // Constructors

    public Survey(){
    }

    public Survey(String learnedAbout){
        this.learnedAbout = learnedAbout;
    }

    // Setters
    public void setSurveyID(int surveyID) {
        this.surveyID = surveyID;
    }

    public void setLearnedAbout(String learnedAbout) {
        this.learnedAbout = learnedAbout;
    }

    // Getters
    public int getSurveyID() {
        return this.surveyID;
    }

    public String getLearnedAbout() {
        return this.learnedAbout;
    }
}
