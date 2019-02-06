package com.example.treeviewexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<Categoria> listCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();


        listAdapter = new ExpandableListAdapter(this,this, listCategorias);

        // setting list adapter
        expListView.setAdapter(listAdapter);



        //Register for Context Menu
        registerForContextMenu(expListView);

    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {

        listCategorias= new ArrayList<Categoria>();


        {
            Categoria categoria1 = new Categoria();
            categoria1.setNombre("Top 250");
            Categoria categoria2 = new Categoria();
            categoria2.setNombre("Now Showing");
            Categoria categoria3 = new Categoria();
            categoria3.setNombre("Coming Soon..");

            listCategorias.add(categoria1);
            listCategorias.add(categoria2);
            listCategorias.add(categoria3);
        }

        // Adding child data


        // Adding child data
        List<Categoria> top250 = new ArrayList<Categoria>();

        {
            Categoria categoria1 = new Categoria();
            categoria1.setNombre("The Shawshank Redemption");
            Categoria categoria2 = new Categoria();
            categoria2.setNombre("The Godfather");
            Categoria categoria3 = new Categoria();
            categoria3.setNombre("The Godfather: Part II");

            List<Categoria> actors=new ArrayList<Categoria>();

                Categoria categoria_1 = new Categoria();
                categoria_1.setNombre("Robert Duvall");
                Categoria categoria_2 = new Categoria();
                categoria_2.setNombre("Marlon Brandon");
                Categoria categoria_3 = new Categoria();
                categoria_3.setNombre("Al Pacino");


                actors.add(categoria_1);
                actors.add(categoria_2);
                actors.add(categoria_3);

            categoria2.setChildCategoria(actors);

            top250.add(categoria1);
            top250.add(categoria2);
            top250.add(categoria3);
        }


        List<Categoria> nowShowing = new ArrayList<Categoria>();


        {
            Categoria categoria1 = new Categoria();
            categoria1.setNombre("The Conjuring");
            Categoria categoria2 = new Categoria();
            categoria2.setNombre("Despicable Me 2");
            Categoria categoria3 = new Categoria();
            categoria3.setNombre("Turbo");


            List<Categoria> actors=new ArrayList<Categoria>();

            Categoria categoria_1 = new Categoria();
            categoria_1.setNombre("Sharon Stone");
            Categoria categoria_2 = new Categoria();
            categoria_2.setNombre("Jennifer Aniston");
            Categoria categoria_3 = new Categoria();
            categoria_3.setNombre("Brad Pit");


            actors.add(categoria_1);
            actors.add(categoria_2);
            actors.add(categoria_3);

            categoria1.setChildCategoria(actors);

            nowShowing.add(categoria1);
            nowShowing.add(categoria2);
            nowShowing.add(categoria3);
        }


        List<Categoria> comingSoon = new ArrayList<Categoria>();

        {
            Categoria categoria1 = new Categoria();
            categoria1.setNombre("2 Guns");
            Categoria categoria2 = new Categoria();
            categoria2.setNombre("The Smurfs 2");
            Categoria categoria3 = new Categoria();
            categoria3.setNombre("The Spectacular Now");


            List<Categoria> actors=new ArrayList<Categoria>();

            Categoria categoria_1 = new Categoria();
            categoria_1.setNombre("Sharon Stone");
            Categoria categoria_2 = new Categoria();
            categoria_2.setNombre("Jennifer Aniston");
            Categoria categoria_3 = new Categoria();
            categoria_3.setNombre("Brad Pit");


            actors.add(categoria_1);
            actors.add(categoria_2);
            actors.add(categoria_3);

            categoria1.setChildCategoria(actors);

            comingSoon.add(categoria1);
            comingSoon.add(categoria2);
            comingSoon.add(categoria3);
        }



        listCategorias.get(0).setChildCategoria(top250); // Header, Child data
        listCategorias.get(1).setChildCategoria(nowShowing); // Header, Child data
        listCategorias.get(2).setChildCategoria(comingSoon); // Header, Child data
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = this.getMenuInflater();

        inflater.inflate(R.menu.categoria_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.add_categoria_menu:
                addGroup();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addGroup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText editText = new EditText(this);
        editText.setHint("Ingrese Categoría");
        editText.setGravity(Gravity.CENTER);

        final TextView textView = new TextView(this);
        textView.setText("Añadir Categoría");
        textView.setTextSize(20.0f);
        textView.setTypeface(null,Typeface.BOLD);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);


        builder.setCustomTitle(textView).setView(editText);
        builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                final String name = editText.getText().toString();
                Categoria categoria=new Categoria();
                categoria.setNombre(name);
                listCategorias.add(categoria);
                listAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                di.cancel();
            }
        });
        builder.show();
    }


}
