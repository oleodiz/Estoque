package com.titan.estoque.estoquetitan.Banco;

import android.content.Context;
import android.text.format.Time;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo on 04/02/2015.
 */
public class LogErro {
    String FILENAME = "log.txt";
    Context ctx;
    public LogErro(Context ctx)
    {
        this.ctx = ctx;
    }

    public void escreverLog(String classe, String erro)
    {

            try {
                if (!erro.contains("Nenhum resultado") && !erro.contains("chamar next")) {
                    File arquivo = new File(ctx.getFilesDir() + FILENAME);
                    Time today = new Time(Time.getCurrentTimezone());
                    today.setToNow();
                    if (!arquivo.exists()) {
                        arquivo.createNewFile();
                    }
                    //escreve no arquivo
                    FileWriter fw = new FileWriter(arquivo, true);

                    BufferedWriter bw = new BufferedWriter(fw);

                    bw.write(today.monthDay + "/" + today.month + " " + today.format("%k:%M") + "; " + classe + "; " + erro);

                    bw.newLine();
                    bw.close();
                    fw.close();
                }
            } catch (FileNotFoundException e) {
                Log.e("ERRO", "Problema ao escrever " + e.getMessage());

                e.printStackTrace();
            } catch (IOException e) {
                Log.e("ERRO", "Problema ao escrever " + e.getMessage());

                e.printStackTrace();
            }
            catch (NullPointerException e) {
                Log.e("ERRO", "Problema ao escrever " + e.getMessage());

                e.printStackTrace();
            }

    }

    public void limparLog()
    {
        try {
            File arquivo = new File(ctx.getFilesDir()+FILENAME);
            Time today = new Time(Time.getCurrentTimezone());
            today.setToNow();
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }
            //escreve no arquivo
            FileWriter fw = new FileWriter(arquivo);

            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("");
            bw.close();
            fw.close();

        } catch (FileNotFoundException e) {
            Log.e("ERRO", "Problema ao escrever "+ e.getMessage());

            e.printStackTrace();
        } catch (IOException e) {
            Log.e("ERRO", "Problema ao escrever "+ e.getMessage());

            e.printStackTrace();
        }
    }

    public List<String> lerLog() {

        List <String> linhas = new ArrayList<String>();
        try{
        File arquivo = new File(ctx.getFilesDir()+FILENAME);
        //faz a leitura do arquivo
        FileReader fr = new FileReader(arquivo);

        BufferedReader br = new BufferedReader(fr);
        while (br.ready()) {
//lê a proxima linha
           linhas.add(br.readLine());
        }

        br.close();
        fr.close();

    } catch (IOException ex) {
            Log.e("ERRO", "Problema ao ler "+ ex.getMessage());
        ex.printStackTrace();
    }

        return linhas;
    }

}
