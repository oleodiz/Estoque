package com.titan.estoque.estoquetitan.Objetos;

/**
 * Created by Leonardo on 11/04/2014.
 */
public class Item {

    public int id_venda;
    public int id_item;
    public int id_bandeira;
    public Integer id_sabor;
    public Double valor_venda;
    public Double valor_compra;
    public Boolean impresso;
    public Boolean cancelado;

    public Item() {
    }

    public Item(int id_venda, int id_item, Double valor_venda, Double valor_compra, Boolean impresso, Boolean cancelado) {

        this.id_venda = id_venda;
        this.id_item = id_item;
        this.valor_venda = valor_venda;
        this.valor_compra = valor_compra;
        this.impresso = impresso;
        this.cancelado = cancelado;
    }
}
