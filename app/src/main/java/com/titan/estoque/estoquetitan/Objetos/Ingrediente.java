package com.titan.estoque.estoquetitan.Objetos;

import android.media.Image;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Leonardo on 26/03/2014.
 */
@XStreamAlias("Ingrediente")
public class Ingrediente {
    public int id_ingrediente;
    public String descricao;
    public Integer id_imagem;
    public Image imagem;
    public Integer id_status_estoque;
    public double estoque_minimo;
    public double quantidade;
    public String unidade;
    public String status;
    public Boolean ativo;

    public boolean entrada;
    public double quantidadeEntrada;
    public double valorEntrada;
    public int vencimentoDia;
    public int vencimentoMes;
    public int vencimentoAno;

    public boolean saida;
    public double quantidadeSaida;

}
