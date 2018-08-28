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
import com.goucorporation.android.orientateuni.presentation.models.Lugar;

import java.util.List;

/**
 * Adapter que muestra la lista de lugares con sus respectivos codigos
 */
public class CodigosArrayAdapter extends ArrayAdapter<Lugar> {

    private LayoutInflater inflater;

    public CodigosArrayAdapter(@NonNull Context context, @NonNull List<Lugar> objects) {
        super(context, 0, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Lugar item = getItem(position);

        //if(convertView == null){
            switch (position%3){
                case 0:
                    convertView = inflater.inflate(R.layout.list_item1,null);
                    break;
                case 1:
                    convertView = inflater.inflate(R.layout.list_item2,null);
                    break;
                case 2:
                    convertView = inflater.inflate(R.layout.list_item3,null);
                    break;
            }

       // }
        TextView codigo = convertView.findViewById(R.id.child_cod);
        TextView title = convertView.findViewById(R.id.child_title);

        codigo.setText(item.getCodigo());
        title.setText(item.getNombre());
        return convertView;
    }
}
