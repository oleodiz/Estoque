package com.titan.estoque.estoquetitan.Objetos;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo on 28/08/2014.
 */
@XStreamAlias("ItemImpressao")
public class ItemImpressao {
    public int IdImpressora;
    public int IdItem;
    public int IdVenda;
    public int IdFuncionario;

    public String Segmento;
    public String Tamanho;
    public String Sabor;

    @XStreamAlias("Extras")
    public List<String> Extras = new ArrayList<String>();

    @XStreamAlias("IdProdutos")
    public List<Integer> IdProdutos = new ArrayList<Integer>();

    @XStreamAlias("IdProdutoTamanho")
    public List<Integer> IdProdutoTamanho = new ArrayList<Integer>();

    @XStreamAlias("NomesProdutos")
    public List<String> NomesProdutos = new ArrayList<String>();

    @XStreamAlias("PorcentagemProdutos")
    public List<Double> PorcentagemProdutos = new ArrayList<Double>();

    @XStreamAlias("DescricaoSub")
    public List<String> DescricaoSub = new ArrayList<String>();

    @XStreamAlias("IngredientesObrigatorios")
    public List<String> IngredientesObrigatorios = new ArrayList<String>();

    @XStreamAlias("IngredientesInclusos")
    public List<String> IngredientesInclusos = new ArrayList<String>();

    @XStreamAlias("IngredientesExcluidos")
    public List<String> IngredientesExcluidos = new ArrayList<String>();

    public Double Quantidade;

    public Boolean Embalar;
    public String Observacao;

}
