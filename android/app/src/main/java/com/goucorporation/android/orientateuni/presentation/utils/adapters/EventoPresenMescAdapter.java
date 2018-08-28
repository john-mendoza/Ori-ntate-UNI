/*
 *
 */

package com.goucorporation.android.orientateuni.presentation.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.goucorporation.android.orientateuni.R;
import com.goucorporation.android.orientateuni.presentation.models.EventoPresenc;

import java.util.List;

public class EventoPresenMescAdapter extends BaseExpandableListAdapter {

    private List<EventoPresenc> mItems;
    private LayoutInflater inflater;
    private OnViewsClicked mListener;

    public EventoPresenMescAdapter(Context context, List<EventoPresenc> items, OnViewsClicked listener){
        mItems = items;
        mListener = listener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return mItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mItems.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mItems.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        EventoPresenc item = mItems.get(groupPosition);
        if(convertView == null){
            switch (groupPosition%4){
                case 0:
                    convertView = inflater.inflate(R.layout.group_evento_item1,null);
                    break;
                case 1:
                    convertView = inflater.inflate(R.layout.group_evento_item2,null);
                    break;
                case 2:
                    convertView = inflater.inflate(R.layout.group_evento_item3,null);
                    break;
                case 3:
                    convertView = inflater.inflate(R.layout.group_evento_item4,null);
                    break;
            }
        }
        TextView title = convertView.findViewById(R.id.group_title);
        TextView fecha = convertView.findViewById(R.id.group_fecha);
        title.setText(item.getTitulo());
        fecha.setText(item.getFecha());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final EventoPresenc item = mItems.get(groupPosition);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.event_presenc_expanded,null);
        }
        TextView descr = convertView.findViewById(R.id.event_descr);
        TextView fecha = convertView.findViewById(R.id.event_fecha);
        TextView hora = convertView.findViewById(R.id.event_hora);
        TextView lugar = convertView.findViewById(R.id.event_lugar);
        Button bt_inscrip  = convertView.findViewById(R.id.bt_inscrip);

        descr.setText(item.getDescripcion());
        fecha.setText(item.getFecha());
        hora.setText(item.getHora());
        lugar.setText(item.getLugar());

        if(item.getEnlace()!=null){
            bt_inscrip.setVisibility(View.VISIBLE);
            bt_inscrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onLinkClicked(item.getEnlace());
                }
            });
        }else{
            bt_inscrip.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public interface OnViewsClicked{
        void onLinkClicked(String url);
    }
}
