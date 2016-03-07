package com.titan.estoque.estoquetitan.Objetos;

/**
 * Created by Leonardo on 11/04/2014.
 */
public class VendaMesa {

    public int id_Venda;
    public int id_Mesa;
    public String data_abertura;
    public String data_fechamento;

    public VendaMesa() {

    }

    public VendaMesa(int id_Venda, int id_Mesa, String data_abertura, String data_fechamento) {
        super();
        this.id_Venda = id_Venda;
        this.id_Mesa = id_Mesa;
        this.data_abertura = data_abertura;
        this.data_fechamento = data_fechamento;
    }
}
