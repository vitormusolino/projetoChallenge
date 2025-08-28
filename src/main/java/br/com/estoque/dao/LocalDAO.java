package br.com.estoque.dao;

import br.com.estoque.conexao.ConexaoBD;
import br.com.estoque.model.Local;
import br.com.estoque.model.Produto;

import java.sql.*;

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

    public Local consultarPorId(int idLocal){
        String sql = """
            SELECT ID_LOCAL, NOME, DESCRICAO
            FROM LOCAIS
            WHERE ID_LOCAL = ?
        """;

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idLocal);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Local l = new Local();
                    l.setIdLocal(rs.getInt("ID_LOCAL"));
                    l.setNomeLocal(rs.getString("NOME"));
                    l.setDescricao(rs.getString("DESCRICAO"));
                    return l;
                }
            }
        } catch (SQLException e){
            System.out.println("Erro ao consultar local: " + e.getMessage());
        }
        return null;
    }
}
