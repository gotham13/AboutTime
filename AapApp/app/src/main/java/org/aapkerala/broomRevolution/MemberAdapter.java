package org.aapkerala.broomRevolution;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.aapkerala.broomRevolution.R;

import java.util.ArrayList;

/**
 * Created by mayank raj sinha on 26-02-2017.
 */

public class MemberAdapter extends ArrayAdapter {

    public MemberAdapter(Context context, ArrayList<MemberData> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ListItemView=convertView;
        if(ListItemView == null){
            ListItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.member_data_listview, parent, false);
        }
        MemberData currentMemberData= (MemberData) getItem(position);
        TextView name= (TextView) ListItemView.findViewById(R.id.name);
        name.setText(currentMemberData.getName());
       TextView sector= (TextView) ListItemView.findViewById(R.id.sector);
       sector.setText(currentMemberData.getSector());
        return ListItemView;
    }
}
