package com.titan.estoque.estoquetitan.Objetos;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo on 26/03/2014.
 */
@XStreamAlias("ProdutoTamanho")
public class ProdutoTamanho {
    public int id_produto_tamanho;
    public Integer id_produto;
    public List<ProdutoTamanhoImpressora> id_impressora;
    public Integer id_tamanho;
    public String descricao;
    public Double valor;
    public Double valor_embalagem;
    public Boolean impresso;
    public Boolean acompanhamento;

    public ProdutoTamanho(){

        id_impressora = new ArrayList<ProdutoTamanhoImpressora>();
    }

}
