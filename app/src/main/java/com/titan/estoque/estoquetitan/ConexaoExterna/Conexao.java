package com.titan.estoque.estoquetitan.ConexaoExterna;

import android.util.Log;

import com.titan.estoque.estoquetitan.Activitys.LoginActivity;
import com.titan.estoque.estoquetitan.Objetos.Funcionario;
import com.titan.estoque.estoquetitan.Objetos.Imagem;
import com.titan.estoque.estoquetitan.Objetos.Ingrediente;
import com.titan.estoque.estoquetitan.Objetos.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo on 26/04/2014.
 */
public class Conexao {

    public Connection conn = null;
    public Conexao()
    {
    }

    public void conectar()
    {
        try
        {
            String url = LoginActivity.ConexaoGerencia;
            Class.forName("org.postgresql.Driver");
            //DriverManager.registerDriver(new org.postgresql.Driver());
            conn = DriverManager.getConnection(url, LoginActivity.UsuarioGerencia ,LoginActivity.SenhaGerencia);

            System.out.println ("Database connection established");

        }
        catch (Exception e)
        {
            if (LoginActivity.logAtivo)
                LoginActivity.erro.escreverLog("Conexao com banco", e.getMessage());
            e.printStackTrace();
            LoginActivity.IP_Server = "";
            LoginActivity.ConexaoGerencia = "";
        }

        try {
            Statement st = conn.createStatement();
            st.executeQuery("SET TIMEZONE = -3;");
        } catch (Exception e) {
            if (LoginActivity.logAtivo)
                LoginActivity.erro.escreverLog("Mudar Timezone", e.getMessage());
            e.printStackTrace();
        }
    }



    public List<Integer> getNumeroImagens()
    {
        List<Integer> img = new ArrayList<Integer>();
        Statement stmt;
        try {
            if (conn == null || conn.isClosed())
                conectar();

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet rs;

            String query = "SELECT MAX(ID_IMAGEM) AS MAX, Count(ID_IMAGEM) AS COUNT FROM imagensusadas ";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
               img.add(rs.getInt("MAX"));img.add(rs.getInt("COUNT"));

            }
            return img;
        }
        catch (SQLException e) {
            if (LoginActivity.logAtivo)
                LoginActivity.erro.escreverLog("Obter Count imagens", e.getMessage());
            e.printStackTrace();
        }

        return img;
    }

    public List<Imagem> getImagensIntervalo(int inicio, int fim)
    {
        List<Imagem> imagems = new ArrayList<Imagem>();

        Statement stmt;
        try {
            if (conn == null || conn.isClosed())
                conectar();

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet rs;

            String query = "SELECT ID_IMAGEM, DESCRICAO, IMAGEM FROM imagensusadas WHERE ID_IMAGEM >= "+inicio + " AND ID_IMAGEM < "+fim;
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Imagem img = new Imagem();
                img.id_imagem = rs.getInt("ID_IMAGEM");
                img.descricao = rs.getString("DESCRICAO");
                img.imagem = rs.getString("IMAGEM");

                imagems.add(img);
            }
            return imagems;
        }
        catch (SQLException e) {
            if (LoginActivity.logAtivo)
                LoginActivity.erro.escreverLog("Obter imagens", e.getMessage());
            e.printStackTrace();
        }

        return imagems;
    }

    public Imagem getImagem(int id_imagem)
    {
        Statement stmt;
        try {
            if (conn == null || conn.isClosed())
                conectar();

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet rs;

            String query = "SELECT ID_IMAGEM, DESCRICAO, IMAGEM FROM TB_IMAGEM WHERE ID_IMAGEM ="+id_imagem;
            rs = stmt.executeQuery(query);

            rs.first();

                Imagem img = new Imagem();
                img.id_imagem = rs.getInt("ID_IMAGEM");
                img.descricao = rs.getString("DESCRICAO");
                img.imagem = rs.getString("IMAGEM");


            return img;
        }
        catch (SQLException e) {
            if (LoginActivity.logAtivo)
                LoginActivity.erro.escreverLog("Obter imagem", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


    public List<Ingrediente> getEstoque()
    {
        List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();

        Statement stmt;
        try {
            if (conn == null || conn.isClosed())
                conectar();

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet rs;

            String query = "SELECT I.ID_INGREDIENTE, I.ID_IMAGEM, I.DESCRICAO, SUM(N.QUANTIDADE_ATUAL) AS QUANTIDADE_ATUAL," +
                    " I.ATIVO, I.ID_STATUS_ESTOQUE , S.DESCRICAO AS STATUS, I.ESTOQUE_MINIMO, I.UNIDADE FROM TB_INGREDIENTE I" +
                    " LEFT OUTER JOIN TB_ITEM_NOTA N ON (I.ID_INGREDIENTE = N.ID_INGREDIENTE  )  INNER JOIN TB_STATUS_ESTOQUE S" +
                    " ON I.ID_STATUS_ESTOQUE = S.ID_STATUS_ESTOQUE GROUP BY I.ID_INGREDIENTE, S.DESCRICAO ORDER BY I.DESCRICAO ASC ";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Ingrediente ing = new Ingrediente();
                ing.id_ingrediente = rs.getInt("ID_INGREDIENTE");
                ing.id_imagem = rs.getInt("ID_IMAGEM");
                ing.id_status_estoque = rs.getInt("ID_STATUS_ESTOQUE");
                ing.descricao = rs.getString("DESCRICAO");
                ing.unidade = rs.getString("UNIDADE");
                ing.status = rs.getString("STATUS");
                ing.estoque_minimo = rs.getDouble("ESTOQUE_MINIMO");
                ing.quantidade = rs.getDouble("QUANTIDADE_ATUAL");
                ing.ativo = rs.getBoolean("ATIVO");
                ingredientes.add(ing);
            }
            return ingredientes;
        }
        catch (SQLException e) {
            if (LoginActivity.logAtivo)
                LoginActivity.erro.escreverLog("Obter ingredientes", e.getMessage());
            e.printStackTrace();
        }

        return ingredientes;
    }


    public Funcionario recuperarFuncionario(int id_Funcionario) {

        Statement stmt;

        Funcionario fun = new Funcionario();
        try {

            if (conn == null || conn.isClosed())
                conectar();

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet rs;

            String query = "SELECT ID_FUNCIONARIO, ID_FUNCAO, ID_ENDERECO, ID_IMAGEM, NOME, CPF, DATA_NASCIMENTO, EMAIL, ATIVO FROM TB_FUNCIONARIO WHERE id_funcionario = "+ id_Funcionario;

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                fun.id_Funcionario = rs.getInt("ID_FUNCIONARIO");
                fun.id_Funcao = rs.getInt("ID_FUNCAO");
                fun.id_Endereco = rs.getInt("ID_ENDERECO");
                fun.id_Imagem = rs.getInt("ID_IMAGEM");
                fun.nome = rs.getString("NOME");
                fun.cpf = rs.getString("CPF");
                fun.data_nascimento = rs.getString("DATA_NASCIMENTO");
                fun.email = rs.getString("EMAIL");
                fun.ativo = rs.getBoolean("ATIVO");

            }
        }
        catch (SQLException e) {
            if (LoginActivity.logAtivo)
                LoginActivity.erro.escreverLog("Obter Fun", e.getMessage());
            e.printStackTrace();
        }

        return fun;
    }


    public Usuario verificaLogin(String usuario, String senha)
    {
        Usuario usu = new Usuario();

        Statement stmt = null;
        try {
            if (conn == null || conn.isClosed())
                conectar();

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet rs;

            String query = "SELECT ID_USUARIO, ID_FUNCIONARIO, LOGIN, SENHA, ATIVO FROM TB_USUARIO WHERE UPPER(LOGIN) = '" + usuario.toUpperCase() + "' AND UPPER(SENHA) = '"+ senha.toUpperCase()+"'";
            rs = stmt.executeQuery(query);

            rs.first();
            //rs.next();
            usu.id_usuario  = rs.getInt("ID_USUARIO");
            usu.id_funcionario = rs.getInt("ID_FUNCIONARIO");
            usu.login = rs.getString("LOGIN");
            usu.senha = rs.getString("SENHA");
            usu.ativo = rs.getBoolean("ATIVO");

            return usu;
        }
         catch (SQLException e) {
             if (LoginActivity.logAtivo)
                 LoginActivity.erro.escreverLog("Verificação Login", e.getMessage());
             e.printStackTrace();
         }
        return usu;
    }


 }
