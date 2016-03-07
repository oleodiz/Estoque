package com.titan.estoque.estoquetitan.Objetos;

/**
 * Created by Leonardo on 21/03/14.
 */
public class Card {
    public int id_cartao;
    public Integer id_status_acessorio;
    public String descricao;
    public Double custo;
    public String codigo_barra;


    public Card(){}

    public Card (int IdCard,
                 Integer IdStatusCard,
                 String Descricao,
                 Double Custo,
                 String CodigoBarra)
    {
        super();
        this.id_cartao = IdCard;
        this.id_status_acessorio = IdStatusCard;
        this.descricao = Descricao;
        this.custo = Custo;
        this.codigo_barra = CodigoBarra;
    }
}
