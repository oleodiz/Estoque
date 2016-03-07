package com.titan.estoque.estoquetitan.Objetos;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Leonardo on 09/04/2014.
 */
@XStreamAlias("Entrega")
public class Entrega {

    public int id_Venda;
    public int id_Item;
    public int id_Entrega;
    public Integer id_Funcionario;
    public Double quantidade;
    public String data_entrega;
    public Boolean cancelado;
    public Boolean Embalar;


    public Entrega() {

    }

    public Entrega(int id_Entrega, int id_Venda, int id_Item, Integer id_Funcionario,
                        Double quantidade,String data_entrega, Boolean cancelado, Boolean embalar) {
        super();

        this.id_Venda = id_Venda;
        this.id_Item = id_Item;
        this.id_Entrega = id_Entrega;
        this.id_Funcionario = id_Funcionario;
        this.quantidade = quantidade;
        this.data_entrega = data_entrega;
        this.cancelado = cancelado;
        this.Embalar = embalar;
    }
}
