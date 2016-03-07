package com.titan.estoque.estoquetitan.Comunicação;

import android.util.Log;


import com.titan.estoque.estoquetitan.Activitys.LoginActivity;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Leonardo on 25/03/2014.
 */
public class ReceberUnicast implements Runnable{
    private int PORTA;


    public ReceberUnicast(int porta)
    {
        PORTA = porta;
    }

    public void escutar()
    {
        try
        {
            ServerSocket servidor = new ServerSocket(PORTA);
            Socket cliente;
            while (LoginActivity.vivo)
            {
                //Log.e("debug", "por aqui");
                cliente = servidor.accept();
                new Thread(new AcaoEscutar(cliente)).start();
            }

        }
        catch (IOException e)
        {
            Log.e("debug", e.getMessage());

        } finally
        {

        }
    }

    @Override
    public void run() {
        escutar();
    }
}
