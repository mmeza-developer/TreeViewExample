package com.example.treeviewexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    protected Context _context;
    protected List<Categoria> listCategoria;
    protected LayoutInflater inflater;
    protected Activity activity;


    public ExpandableListAdapter(Activity activity,Context context, List<Categoria> listDataHeader
                                 ) {
        this._context = context;
        this.listCategoria = listDataHeader;
        this.activity=activity;
        this.inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Categoria getChild(int groupPosition, int childPosititon) {
        Categoria categoria=null;
        if(listCategoria.get(groupPosition)!=null) {
            if (listCategoria.get(groupPosition).getChildCategoria() != null) {
                categoria = this.listCategoria.get(groupPosition).getChildCategoria()
                        .get(childPosititon);
            }
        }
        return categoria;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, final View convertView, ViewGroup parent) {

        final SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(_context);



        secondLevelELV.setAdapter(new SecondLevelAdapter(activity,_context,listCategoria.get(groupPosition).getChildCategoria()));

        secondLevelELV.setGroupIndicator(null);


        return secondLevelELV;

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //Must be 1 because we create 1 instance of SecondLevelAdapter in  getChildView() method, and SecondLevelAdaper will
        //create his sub-elements or childs
        //If we change the number 1 to 2 then, the ExpandableListView will create 2 copies of the sub-element or childs
        return 1;
    }

    @Override
    public Categoria getGroup(int groupPosition) {
        Categoria group=null;
        if(this.listCategoria.get(groupPosition)!=null){
            group=this.listCategoria.get(groupPosition);
        }
        return group;
    }

    @Override
    public int getGroupCount() {

        int size = 0;
        if (this.listCategoria != null) {
            size=this.listCategoria.size();
        }
        return size;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
         final ViewHolder viewHolder;
        final String headerTitle =  getGroup(groupPosition).getNombre();
       if(convertView==null){
           viewHolder=new ViewHolder();
           convertView = inflater.inflate(R.layout.first_row, null);
           viewHolder.nomTextView=(TextView) convertView.findViewById(R.id.rowParentText);
           viewHolder.nomTextView.setText(headerTitle);
           viewHolder.treeDotImageView=(ImageView) convertView.findViewById(R.id.ivParentMenuDots);
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
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public void deleteGroupElement(final Categoria elemento){

        AlertDialog.Builder builder = new AlertDialog.Builder(_context);


        final TextView textView = new TextView(_context);
        textView.setText("Eliminar Categoría");
        textView.setTextSize(20.0f);
        textView.setTypeface(null,Typeface.BOLD);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);

        builder.setCustomTitle(textView).setMessage("¿Esta seguro de eliminar esta Categoría?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                listCategoria.remove(elemento);
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                di.cancel();
            }
        });
        builder.show();
    }
    public void deleteChildElement(final Categoria elemento, final int groupPosition){

        AlertDialog.Builder builder = new AlertDialog.Builder(_context);

        final TextView textView = new TextView(_context);
        textView.setText("Eliminar Categoría");
        textView.setTextSize(20.0f);
        textView.setTypeface(null,Typeface.BOLD);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);

        builder.setCustomTitle(textView).setMessage("¿Esta seguro de eliminar esta Categoría?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                listCategoria.get(groupPosition).getChildCategoria().remove(elemento);
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                di.cancel();
            }
        });
        builder.show();
    }


    public void alertDialogEditGroupElement(final int groupPosition,final ViewHolder viewHolder){
        AlertDialog.Builder builder = new AlertDialog.Builder(_context);
        final EditText editText = new EditText(_context);
        editText.setText(listCategoria.get(groupPosition).getNombre());


        final TextView textView = new TextView(_context);
        textView.setText("Editar Categoría");
        textView.setTextSize(20.0f);
        textView.setTypeface(null,Typeface.BOLD);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);

        builder.setCustomTitle(textView).setView(editText);
        builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                final String name = editText.getText().toString();
                editCategoriaListGroup(groupPosition,name,viewHolder);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                di.cancel();
            }
        });
         builder.show();

    }
    public void alertDialogEditChildElement(final int groupPosition,final int childPosition){
        AlertDialog.Builder builder = new AlertDialog.Builder(_context);
        final EditText editText = new EditText(_context);
        editText.setText(listCategoria.get(groupPosition).getChildCategoria().get(childPosition).getNombre());


        final TextView textView = new TextView(_context);
        textView.setText("Editar Categoría");
        textView.setTextSize(20.0f);
        textView.setTypeface(null,Typeface.BOLD);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);

        builder.setCustomTitle(textView).setView(editText);
        builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                final String name = editText.getText().toString();
                editCategoriaListChild(groupPosition,childPosition,name);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                di.cancel();
            }
        });
         builder.show();

    }
    public  void alertDialogAddGroupElement(final int groupPosition){
        AlertDialog.Builder builder = new AlertDialog.Builder(_context);
        final EditText editText = new EditText(_context);


        final TextView textView = new TextView(_context);
        textView.setText("Añadir Categoría");
        textView.setTextSize(20.0f);
        textView.setTypeface(null,Typeface.BOLD);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);

        builder.setCustomTitle(textView).setView(editText);
        builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                final String name = editText.getText().toString();
                addChildToCategoriaListGroup(groupPosition,name);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                di.cancel();
            }
        });
         builder.show();
    }

    public  void alertDialogAddChildElement(final int groupPosition){
        AlertDialog.Builder builder = new AlertDialog.Builder(_context);
        final EditText editText = new EditText(_context);


        final TextView textView = new TextView(_context);
        textView.setText("Añadir Categoría");
        textView.setTextSize(20.0f);
        textView.setTypeface(null,Typeface.BOLD);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);

        builder.setCustomTitle(textView).setView(editText);
        builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                final String name = editText.getText().toString();
                addChildToCategoriaListGroup(groupPosition,name);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                di.cancel();
            }
        });
         builder.show();
    }

    public void editCategoriaListGroup(int groupPosition,String nombre,ViewHolder viewHolder){
        this.listCategoria.get(groupPosition).setNombre(nombre);
        viewHolder.nomTextView.setText(nombre);
        notifyDataSetChanged();
    }
    public void editCategoriaListChild(int groupPosition,final int childPosition, String nombre){
        this.listCategoria.get(groupPosition).getChildCategoria().get(childPosition).setNombre(nombre);
        notifyDataSetChanged();
    }
    public void addChildToCategoriaListGroup(int groupPosition,String nombre){
        Categoria categoria=new Categoria();
        categoria.setNombre(nombre);
        if( this.listCategoria.get(groupPosition).getChildCategoria()!=null){
            this.listCategoria.get(groupPosition).getChildCategoria().add(categoria);
            notifyDataSetChanged();
        }else{
            ArrayList<Categoria> list=new ArrayList<Categoria>();
            list.add(categoria);
            this.listCategoria.get(groupPosition).setChildCategoria(list);
            notifyDataSetChanged();
        }


    }


}
