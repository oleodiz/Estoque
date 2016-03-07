package com.titan.estoque.estoquetitan.Protocolos.Enviar;

/**
 * Created by Leonardo on 02/05/2014.
 */
public class ConexaoRequest {
    final String IdProtocolo = "007";
    public Integer IdEntidade;
    public String IpEntidade;
    public String IpServer;
    public Integer Porta;

    public  ConexaoRequest()
    {

    }
    public ConexaoRequest( Integer IdEntidade,
                              String IPEntidade,
                              String IPServer,
                              Integer Porta)
    {
        super();
        this.IdEntidade = IdEntidade;
        this.IpEntidade = IPEntidade;
        this.IpServer = IPServer;
        this.Porta = Porta;
    }
}
