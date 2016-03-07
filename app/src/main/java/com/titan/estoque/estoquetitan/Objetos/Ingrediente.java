package com.titan.estoque.estoquetitan.Objetos;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Leonardo on 26/03/2014.
 */
@XStreamAlias("Ingrediente")
public class Ingrediente {
    public int id_ingrediente;
    public String descricao;
    public Integer id_imagem;
    public Integer id_status_estoque;
    public Double estoque_minimo;
    public Double quantidade;
    public String unidade;
    public String status;
    public Boolean ativo;
}
