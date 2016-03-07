package com.titan.estoque.estoquetitan.Comunicação;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Leonardo on 12/03/14.
 */
public class AcaoEscutar implements Runnable {

    public Socket cliente;
    public AcaoEscutar(Socket cliente)
    {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        Scanner s = null;
        try {
            s = new Scanner(cliente.getInputStream());
            String entrada = "";
            //s.close();
            while (s.hasNextLine()) {
                entrada += s.nextLine();
            }

            new AcoesProtocolos().executaAcao(entrada);
            //Conexao c = new Conexao();
            //c.conectar();
            //c.PersisteSincronizeHistorico(new AcoesProtocolos().retornaSincronizeHistorico());

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (OutOfMemoryError o)
        {
            o.printStackTrace();
        }
        finally {
            try {
                cliente.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
