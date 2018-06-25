package generalinfo.powellmakerspace.org.makerspacelogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MemberAdapter extends BaseAdapter {

    LayoutInflater mInflator;
    List<Member> allMemberList;

    public MemberAdapter(Context c, List<Member> allMemberList){
        this.allMemberList = allMemberList;
        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return allMemberList.size();
    }

    @Override
    public  Object getItem(int position){
        return allMemberList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View v = mInflator.inflate(R.layout.listview_member,null);

        TextView memberNameTextView = (TextView) v.findViewById(R.id.memberNameListTextView);
        TextView membershipTextView = (TextView) v.findViewById(R.id.membershipListTextView);

        memberNameTextView.setText(allMemberList.get(position).getMemberName());
        membershipTextView.setText(allMemberList.get(position).getMembershipType());

        return v;

    }
}


