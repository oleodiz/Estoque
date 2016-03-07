package com.titan.estoque.estoquetitan.Protocolos.Enviar;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Leonardo on 25/03/2014.
 */
@XStreamAlias("WhoServer")
public class WhoServer {
    final String IdProtocolo = "001";
    public String IpHost;
    public Integer Porta;


    public WhoServer(String IpHost, Integer Porta)
    {
        super();
        this.IpHost = IpHost;
        this.Porta = Porta;
    }
}
