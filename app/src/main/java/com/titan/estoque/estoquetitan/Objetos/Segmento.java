package com.titan.estoque.estoquetitan.Objetos;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Leonardo on 21/03/14.
 */
@XStreamAlias("Segmento")
public class Segmento {
    public int id_segmento;
    public String descricao;
    public Boolean ativo;
    public Integer id_imagem;

    public Segmento(){}



}
