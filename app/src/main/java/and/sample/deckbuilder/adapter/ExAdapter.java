package and.sample.deckbuilder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import and.sample.deckbuilder.data.Armor;
import and.sample.deckbuilder.data.Body;
import and.sample.deckbuilder.data.Weapon;

public class ExAdapter extends BaseExpandableListAdapter {
    private ArrayList<PartsGroup> items = new ArrayList<>();

    public void add(String groupKey, int num, Context context) {
        PartsGroup groupItem = null;
        for (PartsGroup item : items) {
            if (item.groupTitle.equals(groupKey)) {
                groupItem = item;
                break;
            }
        }
        if (groupItem == null) {
            groupItem = new PartsGroup();
            groupItem.groupTitle = groupKey;
            items.add(groupItem);
        }


        if (groupKey.equals("body")) {
            Body child = new Body(context, num,null);
            groupItem.bodies.add(child);
        } else if (groupKey.equals("armor")) {
            Armor child = new Armor(context, num,null);
            groupItem.armors.add(child);
        } else if (groupKey.equals("weapon")) {
            Weapon child = new Weapon(context, num,null);
            groupItem.weapons.add(child);
        }


        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int size = 0;
        switch (groupPosition) {
            case 0:
                size = items.get(groupPosition).bodies.size();
                break;

            case 1:
                size = items.get(groupPosition).armors.size();
                break;

            case 2:
                size = items.get(groupPosition).weapons.size();
                break;
        }
        return size;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        switch (groupPosition) {
            case 0:
                return items.get(groupPosition).bodies.get(childPosition);
            case 1:
                return items.get(groupPosition).armors.get(childPosition);
            case 2:
                return items.get(groupPosition).weapons.get(childPosition);
        }
        return null;
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView tv;

        if (convertView == null) {
            tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, null);
            tv.setPadding(100, 0, 500, 0);
        } else {
            tv = (TextView) convertView;
        }
        tv.setText(items.get(groupPosition).groupTitle);
        return tv;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView tv;

        if (convertView == null) {
            tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, null);
        } else {
            tv = (TextView) convertView;
        }

        String temp = "";
        switch (groupPosition) {
            case 0:
                temp = items.get(groupPosition).bodies.get(childPosition).toString();
                tv.setTextSize(14);
                break;
            case 1:
                temp = items.get(groupPosition).armors.get(childPosition).toString();
                tv.setTextSize(14);
                break;
            case 2:
                temp = items.get(groupPosition).weapons.get(childPosition).toString();
                tv.setTextSize(14);
                break;
        }

        tv.setText(temp);
        return tv;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
