/*
 *
 */

package com.goucorporation.android.orientateuni.presentation.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goucorporation.android.orientateuni.R;

import java.util.List;

public class ListMenuMapAdapter extends BaseExpandableListAdapter {

    private List<String> titles;
    private List<List<String>> contents;
    private LayoutInflater inflater;

    public ListMenuMapAdapter(Context context, List<String> titles, List<List<String>> contents) {
        this.titles = titles;
        this.contents = contents;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return titles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return contents.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return titles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return contents.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String titulo = (String) getGroup(groupPosition);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.group_item,null);
        }
        TextView title = convertView.findViewById(R.id.group_title);
        ImageView arrow_up = convertView.findViewById(R.id.group_up);
        ImageView arrow_down = convertView.findViewById(R.id.group_down);
        title.setText(titulo);
        if(isExpanded){
            arrow_down.setVisibility(View.GONE);
            arrow_up.setVisibility(View.VISIBLE);
        }else{
            arrow_down.setVisibility(View.VISIBLE);
            arrow_up.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String content = (String) getChild(groupPosition,childPosition);
        if(convertView == null){
            switch (childPosition){
                case 0:
                    convertView = inflater.inflate(R.layout.child_item_mayo,null);
                    break;
                case 1:
                    convertView = inflater.inflate(R.layout.child_item_map,null);
                    break;
                default:
                    convertView = inflater.inflate(R.layout.child_item,null);
                    break;
            }

        }
        TextView titulo = convertView.findViewById(R.id.child_title);
        titulo.setText(content);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
