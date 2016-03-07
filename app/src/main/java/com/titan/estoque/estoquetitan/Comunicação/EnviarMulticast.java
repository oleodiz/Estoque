package com.titan.estoque.estoquetitan.Comunicação;


import com.titan.estoque.estoquetitan.Activitys.LoginActivity;

import org.w3c.dom.Document;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Leonardo on 25/03/2014.
 */
public class EnviarMulticast <T> implements Runnable {
    private int         PORTA;
    private String      GRUPO;
    private T DICIONARIO;

    public EnviarMulticast (int porta, String grupo, T dicionario)
    {
        GRUPO = grupo;
        PORTA = porta;
        DICIONARIO = dicionario;
    }

    public <T> void falar(T dicionario)
    {
        Conversao converte = new Conversao();
        Document xmlDoc =  converte.parseToXML(dicionario);

        String xml = converte.getStringFromDocument(xmlDoc);

        enviaMulticast(xml);
    }


    private void enviaMulticast(String xml)
    {

        InetAddress addr = null;
        byte[] buff =xml.getBytes();
        try {
            addr = InetAddress.getByName(GRUPO);


            DatagramSocket ds = new DatagramSocket();

            DatagramPacket pkg = new DatagramPacket(buff, buff.length, addr, PORTA);

            ds.send(pkg);

        } catch (UnknownHostException e) {
            if (LoginActivity.logAtivo)
                LoginActivity.erro.escreverLog("Enviar Multicast", e.getMessage());
        } catch (SocketException e) {
            if (LoginActivity.logAtivo)
                LoginActivity.erro.escreverLog("Enviar Multicast", e.getMessage());
        } catch (IOException e) {
            if (LoginActivity.logAtivo)
                LoginActivity.erro.escreverLog("Enviar Multicast", e.getMessage());
        }
    }

    @Override
    public void run()
    {
        falar(DICIONARIO);
    }
}
