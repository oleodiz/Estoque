package com.titan.estoque.estoquetitan.Objetos;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Leonardo on 26/03/2014.
 */
@XStreamAlias("SubDivisor")
public class SubDivisor {
    public int id_sub_divisor;
    public Integer id_divisor;
    public String descricao;
    public Double valor;
    public Double preco;

    public SubDivisor(){}

    public SubDivisor(int id_sub_divisor,
                      Integer id_divisor,
                      String descricao,
                      Double valor)
    {
        super();
        this.id_sub_divisor = id_sub_divisor;
        this.id_divisor = id_divisor;
        this.descricao = descricao;
        this.valor = valor;
    }
}
