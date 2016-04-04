package com.titan.estoque.estoquetitan.Activitys;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.titan.estoque.estoquetitan.R;


import java.io.File;
import java.util.Collection;
import java.util.concurrent.Executors;


public class XMLActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);

        new CarregaXMLs().executeOnExecutor(Executors.newFixedThreadPool(4));

    }


    private class CarregaXMLs extends AsyncTask<Void, Boolean, Boolean> {
        protected Boolean doInBackground(Void... urls) {

           /* Collection<File> files = FileUtils.listFiles(
                    Environment.getExternalStorageDirectory(),
                    new RegexFileFilter(".xml|.html"),
                    TrueFileFilter.TRUE);

            for(File f : files){

            }
           //NFNota nota = new NotaParser().notaParaObjeto("");
            */

            return true;
        }

        protected void onProgressUpdate(Boolean... status) {


        }

        protected void onPostExecute(Boolean result) {
        }
    }

}
