package com.titan.estoque.estoquetitan.Activitys;

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.titan.estoque.estoquetitan.Classes_Especiais.AdapterIngredientes;
import com.titan.estoque.estoquetitan.Classes_Especiais.RecyclerItemClickListener;
import com.titan.estoque.estoquetitan.Objetos.Ingrediente;
import com.titan.estoque.estoquetitan.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class EstoqueActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<Ingrediente> estoque;
    Context context;
    RecyclerView rec_listaEstoque;
    LinearLayout lay_viewFlutuante ,lay_area_fora;
    View entra_sai_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estoque);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lay_viewFlutuante = (LinearLayout) findViewById(R.id.lay_viewFlutuante);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        entra_sai_layout = inflater.inflate(R.layout.lay_entrada_saida, lay_viewFlutuante, false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Processando... mentira", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rec_listaEstoque = (RecyclerView) findViewById(R.id.rec_listaEstoque);

        LinearLayoutManager llm = new LinearLayoutManager(context);
        rec_listaEstoque.setLayoutManager(llm);

        context = this;
        estoque = new ArrayList<Ingrediente>();

        LayoutTransition transition = new LayoutTransition();
        lay_viewFlutuante.setLayoutTransition(transition);

        lay_area_fora = (LinearLayout) entra_sai_layout.findViewById(R.id.lay_area_fora);
        lay_area_fora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lay_viewFlutuante.removeView(entra_sai_layout);

            }
        });

        new CarregaEstoque().executeOnExecutor(Executors.newFixedThreadPool(4));

    }













    private class CarregaEstoque extends AsyncTask<Void, Void, Void> {
        AdapterIngredientes adapterIngredientes;
        @Override
        protected Void doInBackground(Void... vendas) {

            estoque = LoginActivity.c.getEstoque();

            publishProgress();

            return null;
        }
        @Override
        protected void onProgressUpdate(Void... progress) {

            if (estoque != null && estoque.size() >0)
                adapterIngredientes = new AdapterIngredientes(estoque, context);

            rec_listaEstoque.setAdapter(adapterIngredientes);

            rec_listaEstoque.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (lay_viewFlutuante.getChildCount() > 0)
                        lay_viewFlutuante.removeAllViews();

                    lay_viewFlutuante.addView(entra_sai_layout);
                }
            }));
        }
        @Override
        protected void onPostExecute(Void result) {

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.estoque, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
