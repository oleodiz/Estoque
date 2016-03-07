package com.titan.estoque.estoquetitan.Objetos;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Leonardo on 26/03/2014.
 */
@XStreamAlias("ProdutoTamanhoIngrediente")
public class ProdutoTamanhoIngrediente {
    public Integer id_produto_tamanho;
    public Integer id_ingrediente;
    public Double quantidade_baixa;
    public Boolean obrigatorio;
    public Boolean incluso;

    public ProdutoTamanhoIngrediente(){}

    public ProdutoTamanhoIngrediente(Integer id_produto_tamanho,
                                     Integer id_ingrediente,
                                     Double quantidade_baixa,
                                     Boolean obrigatorio,
                                     Boolean incluso)
    {
        super();
        this.id_produto_tamanho = id_produto_tamanho;
        this.id_ingrediente = id_ingrediente;
        this.quantidade_baixa = quantidade_baixa;
        this.obrigatorio = obrigatorio;
        this.incluso = incluso;
    }
}
