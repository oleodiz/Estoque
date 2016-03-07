package com.titan.estoque.estoquetitan.Protocolos.Receber;

/**
 * Created by Leonardo on 02/05/2014.
 */
public class ConexaoInfo {

    final String IdProtocolo = "008";
    public String IpServer;
    public String IpHost;
    public String Conexao;
    public Boolean PrecoMedio;

    public ConexaoInfo()
    {
    }

    public ConexaoInfo( String IpServer,
                        String IpHost,
                        String Conexao)
    {
        super();
        this.IpServer = IpServer;
        this.IpHost = IpHost;
        this.Conexao = Conexao;

    }
}
