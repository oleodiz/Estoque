package com.titan.estoque.estoquetitan.Objetos;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Leonardo on 26/03/2014.
 */
@XStreamAlias("Divisor")
public class Divisor {
    public int id_tamanho;
    public int id_divisor;
    public String descricao;
    public Integer id_imagem;
    public Boolean ativo;

    public Divisor(){}


}
