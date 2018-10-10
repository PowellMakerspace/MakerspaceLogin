package generalinfo.powellmakerspace.org.makerspacelogin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Visit;
import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;
import generalinfo.powellmakerspace.org.makerspacelogin.R;
import generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic.TimeStatisticResult;

public class VisitsToReviewAdapter extends BaseAdapter{

    DatabaseHelper makerspaceDatabase;

    LayoutInflater mInflater;
    List<Visit> visitsToReview;

    public VisitsToReviewAdapter(Context c, List<Visit> visitsToReview){
        this.visitsToReview = visitsToReview;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Initiate Database
        makerspaceDatabase = new DatabaseHelper(c);
    }

    @Override
    public int getCount() {
        return visitsToReview.size();
    }

    @Override
    public Object getItem(int position) {
        return visitsToReview.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = mInflater.inflate(R.layout.listview_visits,null);

        TextView nameDisplay = (TextView) v.findViewById(R.id.visitNameDisplay);
        TextView arrivalTimeDisplay = (TextView) v.findViewById(R.id.arrivalTimeDisplay);
        TextView departureTimeDisplay = (TextView) v.findViewById(R.id.departureTimeDisplay);
        TextView totalTimeDisplay = (TextView) v.findViewById(R.id.totalTimeDesplay);
        TextView purposeDisplay = (TextView) v.findViewById(R.id.purposeDisplay);

        nameDisplay.setText(makerspaceDatabase.getMember(visitsToReview.get(position).getMemberID()).getMemberName());
        arrivalTimeDisplay.setText(convertToDate(visitsToReview.get(position).getArrivalTime()));
        departureTimeDisplay.setText(convertToDate(visitsToReview.get(position).getDepartureTime()));
        totalTimeDisplay.setText(convertToReadable(
                visitsToReview.get(position).getDepartureTime() - visitsToReview.get(position).getArrivalTime()));
        purposeDisplay.setText(visitsToReview.get(position).getVisitPurpose());

        return v;
    }

    private String convertToReadable(long timeAmount){

        int hours = (int) (timeAmount / 3600);
        int minutes = (int) (timeAmount / 60) - (hours * 60);
        int seconds = (int) (timeAmount - (hours * 3600) - (minutes * 60));

        return Integer.toString(hours) + ":" + Integer.toString(minutes) + ":" + Integer.toString(seconds);
    }

    private String convertToDate(long timeAmount){

        java.util.Date date = new java.util.Date((long) timeAmount * 1000);

        return date.toString();
    }
}
