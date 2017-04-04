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
 * Created by mayank raj sinha on 28-02-2017.
 */

public class Member_Lac_Adapter extends ArrayAdapter {
    public Member_Lac_Adapter(Context context, ArrayList<Member_lac_data> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ListItemView=convertView;
        if(ListItemView == null){
            ListItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.member_lac_listview, parent, false);
        }
        Member_lac_data currentMemberData= (Member_lac_data) getItem(position);
        TextView name= (TextView) ListItemView.findViewById(R.id.textview_name);
        name.setText(currentMemberData.getName());
        TextView sector= (TextView) ListItemView.findViewById(R.id.textview_sector);
        sector.setText(currentMemberData.getSector());
        TextView lac= (TextView) ListItemView.findViewById(R.id.textview_lac);
        lac.setText(currentMemberData.getLac());
        TextView id= (TextView) ListItemView.findViewById(R.id.member_id);
        id.setText(currentMemberData.getId());
        return ListItemView;
    }
}
