package com.titan.estoque.estoquetitan.Objetos;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Leonardo on 26/03/2014.
 */
@XStreamAlias("Usuario")
public class Usuario {
    public int id_usuario;
    public Integer id_funcionario;
    public String login;
    public String senha;
    public Boolean ativo;

    public Usuario(){}

    public Usuario (int id_usuario, Integer id_funcionario, String login, String senha, Boolean ativo)
    {
        super();
        this.id_usuario = id_usuario;
        this.id_funcionario = id_funcionario;
        this.login = login;
        this.senha = senha;
        this.ativo = ativo;

    }
}
