package com.titan.estoque.estoquetitan.Objetos;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Leonardo on 26/03/2014.
 */
@XStreamAlias("TamanhoDivisor")
public class TamanhoDivisor {
    public Integer id_tamanho;
    public Integer id_divisor;

    public TamanhoDivisor(){}

    public TamanhoDivisor(Integer id_tamanho,
                          Integer id_divisor)
    {
        super();
        this.id_tamanho = id_tamanho;
        this.id_divisor = id_divisor;
    }
}
