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
 * Created by mayank raj sinha on 10-03-2017.
 */

public class ObserverAdapter extends ArrayAdapter {

    public ObserverAdapter(Context context, ArrayList<observerclass> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ListItemView=convertView;
        if(ListItemView == null){
            ListItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.observer_listview, parent, false);
        }
        observerclass currentClass= (observerclass) getItem(position);
        TextView name= (TextView) ListItemView.findViewById(R.id.observer_name);
        name.setText(currentClass.getName());
        TextView pc=(TextView) ListItemView.findViewById(R.id.observer_level);
        pc.setText(currentClass.getPc_name());

        TextView number= (TextView) ListItemView.findViewById(R.id.observer_number);
        number.setText(currentClass.getNumber());
        return ListItemView;

    }
}