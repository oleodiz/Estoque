package com.titan.estoque.estoquetitan.Objetos;

/**
 * Created by Leonardo on 11/04/2014.
 */
public class VendaCartao {
    public int id_Venda;
    public int id_Cartao;
    public String data_vinculo;
    public String data_liberacao;

    public VendaCartao() {

    }

    public VendaCartao(int id_Venda, int id_Cartao, String data_vinculo, String data_liberacao) {
        super();
        this.id_Cartao = id_Cartao;
        this.id_Venda = id_Venda;
        this.data_vinculo = data_vinculo;
        this.data_liberacao = data_liberacao;
    }

}
