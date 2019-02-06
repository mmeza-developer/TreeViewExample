package com.example.treeviewexample;



import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class SecondLevelAdapter extends ExpandableListAdapter {

    private int count=0;

    public SecondLevelAdapter(Activity activity,Context context, List<Categoria> data) {
        super(activity,context,data);

    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {


        final ViewHolder viewHolder;
        final String headerTitle =  getGroup(groupPosition).getNombre();
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView = inflater.inflate(R.layout.second_row, null);
            viewHolder.nomTextView=(TextView) convertView.findViewById(R.id.rowSecondText);
            viewHolder.nomTextView.setText(headerTitle);
            viewHolder.treeDotImageView=(ImageView) convertView.findViewById(R.id.ivSecondParentMenuDots);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.nomTextView.setText(headerTitle);
        }


        viewHolder.treeDotImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(_context, v);
                popupMenu.inflate(R.menu.new_menu);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.add_menu:
                                alertDialogAddChildElement(groupPosition);
                                break;

                            case R.id.edit_menu:
                                alertDialogEditGroupElement(groupPosition,viewHolder);
                                break;

                            case R.id.delete_menu:
                                deleteGroupElement(getGroup(groupPosition));
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        return convertView;
    }



    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=new ViewHolder();
        final String headerTitle = getChild(groupPosition, childPosition).getNombre();
        if(convertView==null){

            convertView = inflater.inflate(R.layout.third_row, null);

            viewHolder.nomTextView=(TextView) convertView
                    .findViewById(R.id.rowThirdText);
            viewHolder.nomTextView.setText(headerTitle);

            viewHolder.treeDotImageView=(ImageView) convertView
                    .findViewById(R.id.ivChildMenuDots);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.nomTextView.setText(headerTitle);
        }


        viewHolder.treeDotImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(_context, v);
                popupMenu.inflate(R.menu.new_menu_child);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_menu1:
                                alertDialogEditChildElement(groupPosition,childPosition);
                                break;

                            case R.id.delete_menu1:
                                deleteChildElement(getChild(groupPosition, childPosition),groupPosition);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
       int size=0;
        if( this.listCategoria.get(groupPosition).getChildCategoria()!=null){
            size=this.listCategoria.get(groupPosition).getChildCategoria()
                .size();
        }
        return size;
    }

}
