package com.titan.estoque.estoquetitan.Objetos;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Leonardo on 26/03/2014.
 */
@XStreamAlias("Tamanho")
public class Tamanho {
    public int id_segmento;
    public int id_tamanho;
    public String descricao;
    public Boolean ativo;
    public Integer id_imagem;

    public Tamanho(){}
}
