package generalinfo.powellmakerspace.org.makerspacelogin;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TourAdapter extends BaseAdapter{

    LayoutInflater mInflater;
    List<Tour> allTourList;
    Context c;

    public TourAdapter(Context c, List<Tour> allTourList){
        this.c = c;
        this.allTourList = allTourList;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return allTourList.size(); }

    @Override
    public Object getItem(int position) { return allTourList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = mInflater.inflate(R.layout.listview_tour,null);

        TextView tourNameListTextView = (TextView) v.findViewById(R.id.tourNameListTextView);
        TextView tourVisitorNumberListTextView = (TextView) v.findViewById(R.id.tourVisitorNumberTextView);

        tourNameListTextView.setText(allTourList.get(position).getTour_name());
        tourVisitorNumberListTextView.setText(Integer.toString(allTourList.get(position).getTour_visitor_number()));

        return v;

    }
}
