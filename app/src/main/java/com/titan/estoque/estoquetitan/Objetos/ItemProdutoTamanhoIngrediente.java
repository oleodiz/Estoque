package com.titan.estoque.estoquetitan.Objetos;

/**
 * Created by Leonardo on 31/05/2014.
 */
public class ItemProdutoTamanhoIngrediente {

    public int id_Item;
    public int id_Venda;
    public int id_produto_tamanho;
    public int id_ingrediente;
    public double quantidade;
    public boolean com_sem;
    public boolean modificado;
    public String descricao;


    public ItemProdutoTamanhoIngrediente() {

    }

    public ItemProdutoTamanhoIngrediente( int id_Item, int id_Venda,int id_produto_tamanho, int id_ingrediente, double quantidade) {
        super();

        this.id_Venda = id_Venda;
        this.id_Item = id_Item;
        this.id_produto_tamanho = id_produto_tamanho;
        this.id_ingrediente = id_ingrediente;
        this.quantidade = quantidade;
    }
}
