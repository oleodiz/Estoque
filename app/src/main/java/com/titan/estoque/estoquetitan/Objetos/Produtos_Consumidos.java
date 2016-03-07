package com.titan.estoque.estoquetitan.Objetos;

import java.util.List;

/**
 * Created by Leonardo on 04/04/2014.
 */
public class Produtos_Consumidos {

    public int id_item;
    public Integer Idproduto;
    public Integer IdprodutoTamanho;
    public boolean Impresso;
    public String produto;
    public String tamanho;
    public String sabor;
    public String segmento;
    public Double quantidade;
    public Double valor;
    public Double porcentagem;
    public String descricaoSub;
    public int id_impressora;
    public List<Integer> IdProdutos;
    public List<Integer> IdProdutoTamanho;
    public List<String> nomesProdutos;
    public List<Double> porcentagens;
    public List<String> descricoesSub;
    public List<ComSem> comSem;
    public List<Extra> extras;


    public Produtos_Consumidos(){}


}
