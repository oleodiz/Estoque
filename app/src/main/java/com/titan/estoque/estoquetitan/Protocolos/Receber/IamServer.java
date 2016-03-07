package com.titan.estoque.estoquetitan.Protocolos.Receber;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Leonardo on 18/04/2014.
 */
@XStreamAlias("IamServer")
public class IamServer {
    final String IdProtocolo = "002";
    public String IpServer;
    public String IpHost;

    public IamServer() {

    }

    public IamServer(String IpHost, String IpServer)
    {
        super();
        this.IpHost = IpHost;
        this.IpServer = IpServer;
    }
}
