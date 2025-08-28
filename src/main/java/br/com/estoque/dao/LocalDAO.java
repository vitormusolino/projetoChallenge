package br.com.estoque.dao;

import br.com.estoque.conexao.ConexaoBD;
import br.com.estoque.model.Local;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class LocalDAO {

    public void inserir(Local local) {
        String sql = "INSERT INTO LOCAIS (NOME,DESCRICAO)" +
                "VALUES(?,?)" ;

        try(Connection conn = ConexaoBD.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, local.getNomeLocal());
            stmt.setString(2, local.getDescricao());
            stmt.executeUpdate();

            System.out.println("Local inserido com sucesso!");
        }catch (SQLException e){
            System.out.println("Erro ao inserir local: " + e.getMessage());
        }
    }
}
