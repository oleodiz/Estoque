package com.titan.estoque.estoquetitan.Objetos;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Leonardo on 12/03/2015.
 */
@XStreamAlias("Funcionario")
public class Funcionario {

    public int id_Funcionario;
    public int id_Funcao;
    public int id_Endereco;
    public int id_Imagem;
    public String nome;
    public String cpf;
    public String data_nascimento;
    public String email;
    public String pass;
    public boolean ativo;

    public Funcionario() {

    }
}