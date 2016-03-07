package com.titan.estoque.estoquetitan.Comunicação;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.titan.estoque.estoquetitan.Activitys.LoginActivity;
import com.titan.estoque.estoquetitan.Protocolos.Receber.ConexaoInfo;
import com.titan.estoque.estoquetitan.Protocolos.Receber.IamServer;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Leonardo on 22/03/14.
 */
public class AcoesProtocolos {
    public static Conversao c;

    public AcoesProtocolos() {
        c = new Conversao();
    }

    public int IdentificarProtocolo(String xml)
    {
        int id_protocolo = 0;

        try {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder docBuilder = dbf.newDocumentBuilder();

            Document doc = docBuilder.parse(new InputSource(new StringReader(xml)));
            NodeList tag = doc.getElementsByTagName("IdProtocolo");

            id_protocolo = Integer.parseInt(tag.item(0).getTextContent());


        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return id_protocolo;
    }

    public void executaAcao(String xml)
    {
        int idProtocolo = IdentificarProtocolo(xml);

        switch (idProtocolo)
        {

            case 2:
            {
                AcaoIamServer(xml);
                break;
            }

            case 8:
            {
                AcaoConexaoInfo(xml);
                break;
            }

        }
    }

    public boolean AcaoIamServer(String xml) {
        if (xml != null && xml != "") {
            IamServer iam;
            try {
                iam = c.XMLtoClass(new IamServer(), xml);
            }
            catch (Exception e)
            {
                return false;
            }


            if(iam != null) {
                LoginActivity.IP_Server = iam.IpServer;
                EditarIPServer(iam.IpServer);
            }
            else
                return false;
        }
        return true;
    }


    public void EditarIPServer(String ip)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("IP_Server", ip);
        editor.commit();
    }

    public void AcaoConexaoInfo (String xml)
    {
        if (xml != null && xml != "") {
            ConexaoInfo con = c.XMLtoClass(new ConexaoInfo(), xml);

            trataConexao(con.Conexao);
        }
    }

    public void trataConexao(String conexao){

       // conexao = "server=localhost;Port=5432;User=postgres;Password=canso;Database=db_empresa_gerencia";
        String partes[] = conexao.split(";");

        for (int i = 0; i < partes.length; i++)
        {
            partes[i] = partes[i].substring(partes[i].lastIndexOf("=")+1);
        }

        LoginActivity.ConexaoGerencia ="jdbc:postgresql://"+LoginActivity.IP_Server+":"+partes[1]+"/"+partes[4];
        LoginActivity.UsuarioGerencia = partes[2];
        LoginActivity.SenhaGerencia = partes[3];
    }

}
