package com.titan.estoque.estoquetitan.Protocolos.Enviar;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Leonardo on 09/04/2014.
 */
@XStreamAlias("SolicitarComanda")
public class SolicitarComanda {

    public String IdProtocolo = "09";
    public Integer IdEntidade;
    public Integer IdImpressora;
    public Integer IdFuncionario;
    public Integer IdVenda;

    public  SolicitarComanda()
    {

    }

    public SolicitarComanda( Integer IdEntidade,
                             Integer IdImpressora,
                             Integer IdFuncionario,
                             Integer IdVenda)
    {
        super();
        this.IdEntidade = IdEntidade;
        this.IdImpressora = IdImpressora;
        this.IdFuncionario = IdFuncionario;
        this.IdVenda = IdVenda;
    }
}
