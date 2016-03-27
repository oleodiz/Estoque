package com.titan.estoque.estoquetitan.Activitys;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.titan.estoque.estoquetitan.Classes_Especiais.AdapterIngredientes;
import com.titan.estoque.estoquetitan.Classes_Especiais.flatui.views.FlatButton;
import com.titan.estoque.estoquetitan.Objetos.Imagem;
import com.titan.estoque.estoquetitan.Objetos.Ingrediente;
import com.titan.estoque.estoquetitan.R;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;

import static com.titan.estoque.estoquetitan.Activitys.EstoqueActivity.Ordem.*;

public class EstoqueActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    enum Ordem {A_Z, Z_A, maiorEstoque, menorEstoque, filtrar}

    SharedPreferences mPrefs;

    List<Ingrediente> listaIngredientes;
    List<Ingrediente> listaIngredientesOrdenados;
    List<Ingrediente> listaIngredientesOrdenadosEFiltrados;
    Context context;
    FloatingActionButton btn_flutuanteProcessar;
    RecyclerView rec_listaEstoque;
    AdapterIngredientes adapterIngredientes;
    LinearLayout lay_viewFlutuante, lay_area_fora;
    View entra_sai_layout;
    ImageView img_ingredienteSelecionado;
    TextView txt_descricaoIngredienteSelecionado, txt_quantidadeIngredienteSelecionado;
    EditText edt_quantidadeIngredienteAdicionada;
    CurrencyEditText edt_valorIngredienteAdicionado;
    DatePicker dat_dataVencimentoIngredienteAdicionado;
    FlatButton btn_cancelar, btn_adiconarIngrediente;
    ScrollView scr_scroll;
    SearchView barraPesquisa;
    View view_ingredienteSelecionado;
    Ingrediente IngredienteSelecioando;
    Ordem ordem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estoque);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPrefs = getPreferences(MODE_PRIVATE);
        lay_viewFlutuante = (LinearLayout) findViewById(R.id.lay_viewFlutuante);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        entra_sai_layout = inflater.inflate(R.layout.lay_entrada, lay_viewFlutuante, false);

        btn_flutuanteProcessar = (FloatingActionButton) findViewById(R.id.fab);
        btn_flutuanteProcessar.setOnClickListener(new View.OnClickListener() {
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
        listaIngredientes = new ArrayList<Ingrediente>();
        listaIngredientesOrdenados = new ArrayList<Ingrediente>();
        listaIngredientesOrdenadosEFiltrados = new ArrayList<Ingrediente>();

        LayoutTransition transition = new LayoutTransition();
        lay_viewFlutuante.setLayoutTransition(transition);

        lay_area_fora = (LinearLayout) entra_sai_layout.findViewById(R.id.lay_area_fora);
        img_ingredienteSelecionado = (ImageView) entra_sai_layout.findViewById(R.id.img_ingrediente);
        txt_descricaoIngredienteSelecionado = (TextView) entra_sai_layout.findViewById(R.id.txt_descricao);
        txt_quantidadeIngredienteSelecionado = (TextView) entra_sai_layout.findViewById(R.id.txt_quantidadeAtual);
        edt_quantidadeIngredienteAdicionada = (EditText) entra_sai_layout.findViewById(R.id.edt_quantidadeEntrada);
        edt_valorIngredienteAdicionado = (CurrencyEditText) entra_sai_layout.findViewById(R.id.edt_valorEntrada);
        dat_dataVencimentoIngredienteAdicionado = (DatePicker) entra_sai_layout.findViewById(R.id.dat_dataVencimento);
        btn_cancelar = (FlatButton) entra_sai_layout.findViewById(R.id.btn_cancelar);
        btn_adiconarIngrediente = (FlatButton) entra_sai_layout.findViewById(R.id.btn_adicionar);
        scr_scroll = (ScrollView) entra_sai_layout.findViewById(R.id.scr_scroll);

        LoginActivity.telaEstoque = this;

        lay_area_fora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //lay_viewFlutuante.removeView(entra_sai_layout);

            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lay_viewFlutuante.removeView(entra_sai_layout);

            }
        });

        btn_adiconarIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (true) {
                    //VALIDAR DADOS
                } else {
                    //Exibir menssagem?

                    return;
                }


                IngredienteSelecioando.quantidadeEntrada = Double.parseDouble(edt_quantidadeIngredienteAdicionada.getText().toString().replace(",", "."));
                IngredienteSelecioando.valorEntrada = Double.parseDouble(edt_valorIngredienteAdicionado.getText().toString().replace("R$", "").replace(".", "").replace(",", "."));
                IngredienteSelecioando.vencimentoDia = dat_dataVencimentoIngredienteAdicionado.getDayOfMonth();
                IngredienteSelecioando.vencimentoMes = dat_dataVencimentoIngredienteAdicionado.getMonth() + 1;
                IngredienteSelecioando.vencimentoAno = dat_dataVencimentoIngredienteAdicionado.getYear();
                IngredienteSelecioando.entrada = IngredienteSelecioando.quantidadeEntrada > 0;

                edt_quantidadeIngredienteAdicionada = (EditText) entra_sai_layout.findViewById(R.id.edt_quantidadeEntrada);
                edt_valorIngredienteAdicionado = (CurrencyEditText) entra_sai_layout.findViewById(R.id.edt_valorEntrada);
                dat_dataVencimentoIngredienteAdicionado = (DatePicker) entra_sai_layout.findViewById(R.id.dat_dataVencimento);

                lay_viewFlutuante.removeView(entra_sai_layout);


                new OrdenaFiltraEstoque().executeOnExecutor(Executors.newFixedThreadPool(4), filtrar);

                salvaNaMemoria();
            }

        });

        new CarregaEstoque().executeOnExecutor(Executors.newFixedThreadPool(4));
    }


    List<Ingrediente> carregarDaMemoria() {
        Gson gson = new Gson();
        String json = mPrefs.getString("Ingredientes", "");
        Type listType = new TypeToken<ArrayList<Ingrediente>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }

    void salvaNaMemoria()
    {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listaIngredientes);
        prefsEditor.putString("Ingredientes", json);
        prefsEditor.apply();
    }

    @Override
    public void finish() {
        if (lay_viewFlutuante.getChildCount() > 0)
            lay_viewFlutuante.removeAllViews();
        else {
            LoginActivity.c = null;
            System.exit(0);
            super.finish();
        }
    }


    private class CarregaEstoque extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... vendas) {
            List<Ingrediente> ingredientesSessaoAnterior = carregarDaMemoria();
            try {
                if (LoginActivity.c == null || LoginActivity.c.conn.isClosed())
                    listaIngredientes = ingredientesSessaoAnterior;
                else {
                    listaIngredientes = LoginActivity.c.getEstoque();
                }
            } catch (Exception e) {
                return false;
            }

            if (listaIngredientes != null && listaIngredientes.size() >0)
                for (int i =0; i < ingredientesSessaoAnterior.size(); i++)
                {
                    for (int j =0; j < listaIngredientes.size(); j++)
                    {
                        if (ingredientesSessaoAnterior.get(i).id_ingrediente == listaIngredientes.get(j).id_ingrediente)
                        {
                            listaIngredientes.get(j).entrada = ingredientesSessaoAnterior.get(i).entrada;
                            listaIngredientes.get(j).quantidadeEntrada = ingredientesSessaoAnterior.get(i).quantidadeEntrada;
                            listaIngredientes.get(j).valorEntrada = ingredientesSessaoAnterior.get(i).valorEntrada;
                            listaIngredientes.get(j).vencimentoDia = ingredientesSessaoAnterior.get(i).vencimentoDia;
                            listaIngredientes.get(j).vencimentoMes = ingredientesSessaoAnterior.get(i).vencimentoMes;
                            listaIngredientes.get(j).vencimentoAno = ingredientesSessaoAnterior.get(i).vencimentoAno;

                            listaIngredientes.get(j).saida = ingredientesSessaoAnterior.get(i).saida;
                            listaIngredientes.get(j).quantidadeSaida = ingredientesSessaoAnterior.get(i).quantidadeSaida;
                            break;
                        }
                    }
                }
            listaIngredientesOrdenados.addAll(listaIngredientes);
            publishProgress();

            return true;
        }

        @Override
        protected void onProgressUpdate(Void... progress) {

            if (listaIngredientesOrdenados != null && listaIngredientesOrdenados.size() > 0)
                adapterIngredientes = new AdapterIngredientes(listaIngredientesOrdenados, context);

            rec_listaEstoque.setAdapter(adapterIngredientes);
            salvaNaMemoria();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result)
                Toast.makeText(context, "Sem conexao com o servidor", Toast.LENGTH_LONG).show();

        }
    }

    public void clickIngrediente(int id_ingrediente) {
        if (lay_viewFlutuante.getChildCount() > 0)
            lay_viewFlutuante.removeAllViews();
        btn_flutuanteProcessar.setVisibility(View.GONE);
        lay_viewFlutuante.addView(entra_sai_layout);

        Ingrediente ing = buscaIngrediente(id_ingrediente);

        if (ing != null) {

            Imagem img = LoginActivity.obterImagem(ing.id_imagem);

            if (img != null)
                img_ingredienteSelecionado.setImageBitmap(decodeBase64(img.imagem));
            txt_descricaoIngredienteSelecionado.setText(ing.descricao);
            txt_quantidadeIngredienteSelecionado.setText(ing.quantidade + "");

            edt_quantidadeIngredienteAdicionada.setText(ing.quantidadeEntrada + "");
            edt_valorIngredienteAdicionado.setText(ing.valorEntrada + "");
            if (ing.vencimentoAno != 0)
                dat_dataVencimentoIngredienteAdicionado.updateDate(ing.vencimentoAno, ing.vencimentoMes - 1, ing.vencimentoDia);
            scr_scroll.fullScroll(ScrollView.FOCUS_UP);

            //view_ingredienteSelecionado = view;
            IngredienteSelecioando = ing;
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


    public Ingrediente buscaIngrediente(int id_ingrediente) {
        for (Ingrediente i : listaIngredientes) {
            if (i.id_ingrediente == id_ingrediente)
                return i;
        }

        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.estoque, menu);


        //Pega o Componente.
        barraPesquisa = (SearchView) menu.findItem(R.id.search)
                .getActionView();
        //Define um texto de ajuda:
        barraPesquisa.setQueryHint("Filtrar");

        barraPesquisa.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                new OrdenaFiltraEstoque().executeOnExecutor(Executors.newFixedThreadPool(4), filtrar);
                return false;
            }
        });
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
            new OrdenaFiltraEstoque().executeOnExecutor(Executors.newFixedThreadPool(4), A_Z);

        } else if (id == R.id.nav_gallery) {
            new OrdenaFiltraEstoque().executeOnExecutor(Executors.newFixedThreadPool(4), Z_A);


        } else if (id == R.id.nav_slideshow) {
            new OrdenaFiltraEstoque().executeOnExecutor(Executors.newFixedThreadPool(4), maiorEstoque);


        } else if (id == R.id.nav_manage) {
            new OrdenaFiltraEstoque().executeOnExecutor(Executors.newFixedThreadPool(4), menorEstoque);


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    private class OrdenaFiltraEstoque extends AsyncTask<Ordem, Void, Void> {
        AdapterIngredientes adapterIngredientes;

        @Override
        protected Void doInBackground(Ordem... ordem) {
            //filtro = barraPesquisa.getQuery().toString();

            switch (ordem[0]) {
                case A_Z: {
                    listaIngredientesOrdenados = ordenarAZ();
                    break;
                }
                case Z_A: {
                    listaIngredientesOrdenados = ordenarZA();
                    break;
                }
                case maiorEstoque: {
                    listaIngredientesOrdenados = ordenarEstoqueMaior();
                    break;
                }
                case menorEstoque: {
                    listaIngredientesOrdenados = ordenarEstoqueMenor();
                    break;
                }
            }

            listaIngredientesOrdenadosEFiltrados.clear();
            listaIngredientesOrdenadosEFiltrados.addAll(listaIngredientesOrdenados);

            filtrarIngredientes(listaIngredientesOrdenadosEFiltrados);

            publishProgress();

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... progress) {

            if (listaIngredientesOrdenadosEFiltrados != null && listaIngredientesOrdenadosEFiltrados.size() > 0)
                adapterIngredientes = new AdapterIngredientes(listaIngredientesOrdenadosEFiltrados, context);

            rec_listaEstoque.setAdapter(adapterIngredientes);

        }

        @Override
        protected void onPostExecute(Void result) {

        }
    }

    List<Ingrediente> ordenarAZ() {
        List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
        ingredientes.addAll(listaIngredientes);

        Collections.sort(ingredientes, new Comparator<Ingrediente>() {
            public int compare(Ingrediente s1, Ingrediente s2) {
                return s1.descricao.compareToIgnoreCase(s2.descricao);
            }
        });

        return (ingredientes);
    }

    List<Ingrediente> ordenarZA() {
        List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
        ingredientes.addAll(listaIngredientes);

        Collections.sort(ingredientes, new Comparator<Ingrediente>() {
            public int compare(Ingrediente s1, Ingrediente s2) {
                return s2.descricao.compareToIgnoreCase(s1.descricao);
            }
        });

        return (ingredientes);
    }

    List<Ingrediente> ordenarEstoqueMaior() {
        List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
        ingredientes.addAll(listaIngredientes);

        Collections.sort(ingredientes, new Comparator<Ingrediente>() {
            public int compare(Ingrediente s1, Ingrediente s2) {
                return s1.quantidade < s2.quantidade ? 1 : -1;
            }
        });

        return (ingredientes);
    }

    List<Ingrediente> ordenarEstoqueMenor() {
        List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
        ingredientes.addAll(listaIngredientes);

        Collections.sort(ingredientes, new Comparator<Ingrediente>() {
            public int compare(Ingrediente s1, Ingrediente s2) {
                return s1.quantidade > s2.quantidade ? 1 : -1;
            }
        });

        return (ingredientes);
    }

    List<Ingrediente> filtrarIngredientes(List<Ingrediente> ingredientes) {
        String filtro = barraPesquisa.getQuery().toString().toUpperCase();

        if (!filtro.equals(""))
            for (int i = ingredientes.size() - 1; i >= 0; i--)
                if (!ingredientes.get(i).descricao.toUpperCase().contains(filtro))
                    ingredientes.remove(i);

        return ingredientes;
    }


}
