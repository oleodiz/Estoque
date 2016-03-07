package com.titan.estoque.estoquetitan.Objetos;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Leonardo on 21/03/14.
 */
@XStreamAlias("Produto")
public class Produto {
    public int id_produto;
    public int id_divisor;
    public int id_tamanho;
    public Integer id_segmento;
    public String descricao;
    public Boolean ativo;
    public Boolean acompanhamento;
    public Integer id_imagem;


    public Produto(){}

}
