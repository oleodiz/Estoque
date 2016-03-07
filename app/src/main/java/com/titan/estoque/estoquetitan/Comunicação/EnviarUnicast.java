package com.titan.estoque.estoquetitan.Comunicação;


import com.titan.estoque.estoquetitan.Activitys.LoginActivity;

import org.w3c.dom.Document;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Leonardo on 25/03/2014.
 */
public class EnviarUnicast<T> implements Runnable {
    private int PORTA;
    private String IP;
    private T DICIONARIO;
    //private PrintStream out = null;

    public EnviarUnicast(String ip, int porta, T dicionario)
    {
        IP = ip;
        PORTA = porta;
        DICIONARIO = dicionario;
    }

    public <T> void falar(T dicionario)
    {
        Conversao converte = new Conversao();
        Document xmlDoc =  converte.parseToXML(dicionario);

        String xml = converte.getStringFromDocument(xmlDoc);
        //Ambiente a = converte.XMLtoClass(new Ambiente(), xml);
        enviaUnicast(xml);
    }
    private void enviaUnicast(String texto)
    {
        DataOutputStream envia;
        Socket conexao;
        try {
            conexao = new Socket(IP, PORTA);
            //out = new PrintStream(conexao.getOutputStream(), true);
            envia = new DataOutputStream(conexao.getOutputStream());
            //out.write(texto.getBytes());
            envia.writeBytes(texto);

            envia.close();
            conexao.close();
        } catch (IOException e) {
            if (LoginActivity.logAtivo)
                LoginActivity.erro.escreverLog("Enviar Unicast", e.getMessage());
        }

    }

    @Override
    public void run() {
        falar(DICIONARIO);
    }
}
