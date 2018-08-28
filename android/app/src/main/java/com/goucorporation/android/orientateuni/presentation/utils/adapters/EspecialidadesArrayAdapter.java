/*
 *
 */

package com.goucorporation.android.orientateuni.presentation.utils.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.goucorporation.android.orientateuni.R;
import com.goucorporation.android.orientateuni.presentation.models.Especialidad;

import java.util.List;

/**
 * Adapter que muestra la lista
 */
public class EspecialidadesArrayAdapter extends ArrayAdapter<Especialidad> {

    private LayoutInflater inflater;

    public EspecialidadesArrayAdapter(@NonNull Context context, @NonNull List<Especialidad> objects) {
        super(context, 0, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Especialidad item = getItem(position);

        //if(convertView == null){
            switch (position%3){
                case 0:
                    convertView = inflater.inflate(R.layout.list_item11,null);
                    break;
                case 1:
                    convertView = inflater.inflate(R.layout.list_item12,null);
                    break;
                case 2:
                    convertView = inflater.inflate(R.layout.list_item13,null);
                    break;
            }
        //}
        TextView title = convertView.findViewById(R.id.child_title);

        title.setText(item.getNombre());
        return convertView;
    }
}
