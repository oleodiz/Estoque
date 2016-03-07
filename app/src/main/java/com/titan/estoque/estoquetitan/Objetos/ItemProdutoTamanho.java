package com.titan.estoque.estoquetitan.Objetos;

/**
 * Created by Leonardo on 09/04/2014.
 */
public class ItemProdutoTamanho {

    public int id_Venda;
    public int id_Item;
    public int id_produto_tamanho;
    public double porcentagem;
    public String descricao;


    public ItemProdutoTamanho() {

    }

    public ItemProdutoTamanho( int id_produto_tamanho, int id_Item, int id_Venda, double porcentagem, String descricao) {
        super();
        this.id_Venda = id_Venda;
        this.id_Item = id_Item;
        this.id_produto_tamanho = id_produto_tamanho;
        this.porcentagem = porcentagem;
        this.descricao = descricao;
    }
}
