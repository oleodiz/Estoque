package com.titan.estoque.estoquetitan.Activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.titan.estoque.estoquetitan.Banco.LogErro;
import com.titan.estoque.estoquetitan.Comunicação.EnviarMulticast;
import com.titan.estoque.estoquetitan.Comunicação.EnviarUnicast;
import com.titan.estoque.estoquetitan.Comunicação.ReceberUnicast;
import com.titan.estoque.estoquetitan.ConexaoExterna.Conexao;
import com.titan.estoque.estoquetitan.Objetos.Funcionario;
import com.titan.estoque.estoquetitan.Objetos.Imagem;
import com.titan.estoque.estoquetitan.Objetos.Ingrediente;
import com.titan.estoque.estoquetitan.Objetos.Usuario;
import com.titan.estoque.estoquetitan.Protocolos.Enviar.ConexaoRequest;
import com.titan.estoque.estoquetitan.Protocolos.Enviar.WhoServer;
import com.titan.estoque.estoquetitan.R;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    public static Context context;

    /**
     * The default email to populate the email field with.
     */
    public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // Values for email and password at the time of the login attempt.
    private String mUsuario;
    private String mPassword;
    boolean sairAplicacao = true;
    SharedPreferences mPrefs;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private View mLoginStatusView;
    private TextView mLoginStatusMessageView, txt_conexao;
    private ImageView iconeStatus;
    private LinearLayout lay_servidor;
    private ProgressBar pgb_login;

    public static List<Imagem> imagens;
    public static int id_Funcionario;
    public static Funcionario fun;
    public static Integer id_Bandeira;
    public static Integer id_Entidade;
    public static String IP_Entidade;
    public static String IP_Server = "";
    public static String ConexaoGerencia = "";
    public static String UsuarioGerencia;
    public static String SenhaGerencia;
    public static EstoqueActivity telaEstoque;

    public static Boolean logAtivo;
    public static Boolean vivo;

    public static Conexao c;
    public static LogErro erro;
    SharedPreferences sharedPrefs;
    private static Context sContext;
    private boolean emailValido =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        context = this;
        logAtivo= vivo = true;
        sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        mPrefs = getPreferences(MODE_PRIVATE);

        id_Entidade = Integer.parseInt(sharedPrefs.getString("Id_Entidade", "1"));
        IP_Entidade = getIPLocal();
        sContext = getApplicationContext();
        erro = new LogErro(sContext);
        imagens = new ArrayList<Imagem>();
        // Set up the login form.
        mUsuario = getIntent().getStringExtra(EXTRA_EMAIL);
        mEmailView = (EditText) findViewById(R.id.email);
        txt_conexao = (TextView) findViewById(R.id.txt_conexao);
        pgb_login = (ProgressBar) findViewById(R.id.pgb_login);
        lay_servidor = (LinearLayout) findViewById(R.id.lay_servidor);
        mEmailView.setText(mUsuario);
        txt_conexao.setVisibility(View.INVISIBLE);
        pgb_login.setVisibility(View.INVISIBLE);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mLoginStatusView = findViewById(R.id.login_status);
        iconeStatus = (ImageView)findViewById(R.id.img_status_servidor);
        mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        lay_servidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IP_Server == null || IP_Server.equals("")) {
                    LoginActivity.IP_Server = sharedPrefs.getString("IP_Server", "");

                    if (IP_Server == null || IP_Server.equals("")) {
                        Toast.makeText(getContext(), "Conexão não estabelecida!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        pgb_login.setVisibility(View.VISIBLE);
                        txt_conexao.setVisibility(View.VISIBLE);
                    }

                } else if (ConexaoGerencia != "" && ConexaoGerencia != null) {
                    Toast.makeText(getContext(), "Conectado!!", Toast.LENGTH_LONG).show();
                }
            }
        });

        new Thread(new ReceberUnicast(6004)).start();
        new Thread(new AtualizaConexao()).start();
        new AtualizaStatus().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        if (carregarDaMemoria() != null)
        {
            Intent intent = new Intent(LoginActivity.this, EstoqueActivity.class);
            startActivity(intent);

            sairAplicacao=false;
            finish();
        }
    }


    List<Ingrediente> carregarDaMemoria() {
        Gson gson = new Gson();
        String json = mPrefs.getString("Ingredientes", "");
        Type listType = new TypeToken<ArrayList<Ingrediente>>() {
        }.getType();
         List<Ingrediente> ing = gson.fromJson(json, listType);
        return ing;
    }
    public String getIPLocal()
    {
        WifiManager wim= (WifiManager) getSystemService(WIFI_SERVICE);
        List<WifiConfiguration> l =  wim.getConfiguredNetworks();
        try {
            WifiConfiguration wc = l.get(0);
            return Formatter.formatIpAddress(wim.getConnectionInfo().getIpAddress());
        }
        catch (Exception ex)
        {
            //Toast.makeText(LoginActivity.this,"Sem conexão com a rede", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private class AtualizaConexao implements Runnable {

        @Override
        public void run() {

            while(true) {

                if (IP_Entidade == null || IP_Entidade.equals("0.0.0.0"))
                {
                    IP_Entidade = getIPLocal();
                }

                try {

                    if (IP_Server == null || IP_Server.equals(""))
                    {
                        WhoServer whoServer = new WhoServer(IP_Entidade, 6004);
                        new Thread(new EnviarMulticast<WhoServer>(6000,"239.0.0.200",whoServer)).start();
                    }
                    else
                    if (ConexaoGerencia.equals("") || ConexaoGerencia == null) {

                        if (IP_Entidade == null || IP_Entidade.equals("") )
                            IP_Entidade = getIPLocal();

                        ConexaoRequest conexaoRequest = new ConexaoRequest(id_Entidade,IP_Entidade, IP_Server, 6004);
                        new Thread(new EnviarUnicast<ConexaoRequest>(IP_Server,6001,conexaoRequest)).start();

                        if(c == null)
                            c = new Conexao();
                    }
                    else {

                        if (c.conn == null || c.conn.isClosed())
                            c.conectar();
                    }

                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    if (LoginActivity.logAtivo)
                        LoginActivity.erro.escreverLog("Obter Dados Conexao", e.getMessage());
                    e.getMessage();
                } catch (NullPointerException e){
                    if (LoginActivity.logAtivo)
                        LoginActivity.erro.escreverLog("Obter Dados Conexao", e.getMessage());
                    e.getMessage();
                } catch (SQLException e) {
                    if (LoginActivity.logAtivo)
                        LoginActivity.erro.escreverLog("Obter Dados Conexao", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    private class AtualizaStatus extends AsyncTask<Void, Integer, Boolean> {
        protected Boolean doInBackground(Void... urls) {
            try {
                while (IP_Server != "a")
                {
                    if (IP_Server == null || IP_Server.equals("")) {
                        publishProgress(R.drawable.status_vermelho);
                    } else if (!ConexaoGerencia.equals("") && ConexaoGerencia != null) {
                        publishProgress(R.drawable.status_verde);
                    }
                    Thread.sleep(3000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        protected void onProgressUpdate(Integer... status) {
            iconeStatus.setImageResource(status[0]);

            iconeStatus.invalidate();

            if (status[0] == R.drawable.status_verde)
            {
                pgb_login.setVisibility(View.INVISIBLE);
                txt_conexao.setVisibility(View.INVISIBLE);
            }
        }

        protected void onPostExecute(Boolean result) {
        }
    }

    public static Imagem obterImagem(int id_imagem)
    {
        for (int i =0; i < imagens.size(); i++)
        {
            if (id_imagem == imagens.get(i).id_imagem)
                return  imagens.get(i);
        }
        return null;
    }

    public static Context getContext() {
        return sContext;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.acessorio, menu);

        //menu.getItem(2).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int id = item.getItemId();

        //if (id == R.id.action_log) {
            //Intent intent = new Intent(LoginActivity.this, ErrosActivity.class);
            //startActivity(intent);
           // return true;
        //}


        return super.onOptionsItemSelected(item);

    }

    @Override
    public void finish() {
        super.finish();

        if (sairAplicacao) {
            System.exit(0);
        }
    }



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {

        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        mUsuario = mEmailView.getText().toString().replace('\'','x').replace('\"','x').replace('\\','x');
        mPassword = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(mPassword)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (mPassword.length() < 4) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid name user.
        if (TextUtils.isEmpty(mUsuario)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
            showProgress(true);

            mAuthTask = new UserLoginTask();

            mAuthTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginStatusView.setVisibility(View.VISIBLE);
            mLoginStatusView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });

            mLoginFormView.setVisibility(View.VISIBLE);
            mLoginFormView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Integer, Integer> {
        @Override
        protected Integer doInBackground(Void... params) {
            try {
                Usuario usu = new Usuario();
                usu = c.verificaLogin(mUsuario, mPassword);

                if (usu.id_usuario == 0)
                    return 1;
                else
                {
                       if (id_Funcionario != 0)
                            fun = c.recuperarFuncionario(id_Funcionario);


                    return 0;
                }
            }
            catch (NullPointerException e)
            {
                return 2;
            }
        }


        @Override
        protected void onPostExecute(Integer success) {
            mAuthTask = null;
            showProgress(false);

            if (success == 0) {
                Intent intent = new Intent(LoginActivity.this, EstoqueActivity.class);
                startActivity(intent);

                sairAplicacao=false;
                finish();
            } if (success == 1) {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
            if (success == 2) {
                mEmailView.setError("Conexão não estabelecida!");
                mEmailView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
