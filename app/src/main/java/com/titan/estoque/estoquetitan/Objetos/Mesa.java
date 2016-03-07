package com.titan.estoque.estoquetitan.Objetos;

/**
 * Created by Leonardo on 21/03/14.
 */
public class Mesa {

    public int IdMesa;
    public Integer IdAmbiente;
    public Integer IdStatusAcessorio;
    public String Descricao;
    public Double Custo;

    public Mesa(){}

    public Mesa (int IdMesa,
                 Integer IdStatusAcessorio,
                 Integer IdAmbiente,
                 String Descricao,
                 Double Custo)
    {
        super();
        this.IdMesa = IdMesa;
        this.IdAmbiente = IdAmbiente;
        this.IdStatusAcessorio = IdStatusAcessorio;
        this.Descricao = Descricao;
        this.Custo = Custo;


    }
}
