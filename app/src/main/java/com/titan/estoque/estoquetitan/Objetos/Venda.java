package com.titan.estoque.estoquetitan.Objetos;

/**
 * Created by Leonardo on 11/04/2014.
 */
public class Venda {

    public int id_Venda;
    public int id_Mesa;
    public int id_ambiente;
    public int id_Cliente;
    public int id_Endereco;
    public Integer id_Status_Venda;
    public int id_Funcionario;
    public Double valor_total;
    public Double valor_pago;
    public Double valor_desconto;
    public Double valor_credito;
    public String data_abertura;
    public String data_encerramento;
    public String descricao;
    public String tipo_venda;
    public String descricaoMesa;
    public String descricaoCartao;
    public int numero_contador;
    public boolean vincularServicos;



    public Venda() {

    }

    public Venda( Integer id_Venda,Integer id_Cliente, Integer id_Status_Venda, Integer id_Funcionario,
                      Double valor_total, Double valor_pago, Double valor_desconto,String data_abertura,
                      String data_encerramento,String descricao, String tipo_venda) {
        super();

        this.id_Venda = id_Venda;
        this.id_Cliente = id_Cliente;
        this.id_Status_Venda = id_Status_Venda;
        this.id_Funcionario = id_Funcionario;
        this.valor_total = valor_total;
        this.valor_pago = valor_pago;
        this.valor_desconto = valor_desconto;
        this.data_abertura = data_abertura;
        this.data_encerramento = data_encerramento;
        this.descricao = descricao;
        this.tipo_venda = tipo_venda;

    }
}
