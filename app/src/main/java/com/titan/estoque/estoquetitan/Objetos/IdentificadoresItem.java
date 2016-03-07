package com.titan.estoque.estoquetitan.Objetos;

import java.util.List;

/**
 * Created by Leonardo on 08/04/2014.
 */
public class IdentificadoresItem {
    public Integer Id_Segmento;
    public Integer Id_Tamanho;
    public Integer Id_Divisor;
    public Integer Id_Sabor;
    public Integer Id_Impressora;
    public String nomesProdutos[];
    public Integer produtosSelecionados[];
    public Integer IdsProdutosTamanho[];
    public Double porcentagensItens[];
    public Double valoresItens[];
    public String descricaoSub[];
    public Boolean Embalar;
    public Boolean Imprimir;
    public Double Valor_Embalagem;
    public String Observacao;

    public List<Extra> extras;

    public String NomeProduto;
    public String NomeSegmento;
    public String NomeTamanho;
    public Double quantidade;
    public Double valor;
    public Double valorExtras;

    public IdentificadoresItem(){}

    public IdentificadoresItem(Integer Id_Segmento,
                              Integer Id_Tamanho,
                              Integer Id_Divisor,
                              double quantidade,
                              Integer produtosSelecionados[],
                              Double porcentagensItens[],
                              Double valoresItens[],
                               String descricaoSub[])
    {
        super();
        this.Id_Segmento = Id_Segmento;
        this.Id_Tamanho = Id_Tamanho;
        this.Id_Divisor = Id_Divisor;
        this.produtosSelecionados = produtosSelecionados;
        this.porcentagensItens = porcentagensItens;
        this.valoresItens = valoresItens;
        this.descricaoSub = descricaoSub;

        this.quantidade = quantidade;
        valor = 0.0;
    }

    public void setProdutosSelecionados(Integer[] produtosSelecionados) {
        this.produtosSelecionados = produtosSelecionados;
    }

    public Integer[] getProdutosSelecionados() {
        return produtosSelecionados;
    }

    public Integer[] getIdsProdutosTamanho() {
        return IdsProdutosTamanho;
    }

    public void setIdsProdutosTamanho(Integer[] idsProdutosTamanho) {
        IdsProdutosTamanho = idsProdutosTamanho;
    }

    public void setNomeProduto(String nomeProduto) {
        NomeProduto = nomeProduto;
    }

    public void setNomesegmento(String nomesegmento) {
        NomeSegmento = nomesegmento;
    }

    public String getNomeProduto() {
        return NomeProduto;
    }

    public String getNomesegmento() {
        return NomeSegmento;
    }

    public void setNomeTamanho(String nomeTamanho) {
        NomeTamanho = nomeTamanho;
    }

    public String getNomeTamanho() {
        return NomeTamanho;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
    public Double getQuantidade() {
        return quantidade;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    public Double getValor() {
        return valor;
    }
}
