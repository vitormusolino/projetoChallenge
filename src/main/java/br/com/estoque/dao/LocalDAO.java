package br.com.estoque.dao;

import br.com.estoque.conexao.ConexaoBD;
import br.com.estoque.model.Local;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocalDAO {

    public boolean inserir(Local local) {
        if (consultarSeExiste(local.getNomeLocal())) {
            return false;
        }

        String sql = "INSERT INTO LOCAIS (NOME, DESCRICAO) VALUES (?, ?)";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, local.getNomeLocal());
            stmt.setString(2, local.getDescricao());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        local.setIdLocal(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir local: " + e.getMessage());
        }
        return false;
    }

    public Local consultarPorId(int idLocal) {
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
        } catch (SQLException e) {
            System.out.println("Erro ao consultar local: " + e.getMessage());
        }
        return null;
    }

    public Local consultarPorNome(String nomeLocal) {
        String sql = """
            SELECT ID_LOCAL, NOME, DESCRICAO
            FROM LOCAIS
            WHERE NOME = ?
        """;

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeLocal);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Local l = new Local();
                    l.setIdLocal(rs.getInt("ID_LOCAL"));
                    l.setNomeLocal(rs.getString("NOME"));
                    l.setDescricao(rs.getString("DESCRICAO"));
                    return l;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar local: " + e.getMessage());
        }
        return null;
    }

    public boolean excluirLocal(int idLocal) {
        String sql = "DELETE FROM LOCAIS WHERE ID_LOCAL = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idLocal);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir local: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(Local local) {
        String sql = "UPDATE LOCAIS SET NOME = ?, DESCRICAO = ? WHERE ID_LOCAL = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, local.getNomeLocal());
            stmt.setString(2, local.getDescricao());
            stmt.setInt(3, local.getIdLocal());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar local: " + e.getMessage());
            return false;
        }
    }

    public List<Local> listarTodos() {
        List<Local> locais = new ArrayList<>();

        String sql = "SELECT ID_LOCAL, NOME, DESCRICAO FROM LOCAIS";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Local l = new Local();
                l.setIdLocal(rs.getInt("ID_LOCAL"));
                l.setNomeLocal(rs.getString("NOME"));
                l.setDescricao(rs.getString("DESCRICAO"));
                locais.add(l);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar locais: " + e.getMessage());
        }
        return locais;
    }

    public boolean consultarSeExiste(String nomeLocal) {
        String sql = "SELECT COUNT(*) FROM LOCAIS WHERE NOME = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeLocal);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar existência do local: " + e.getMessage());
        }
        return false;
    }
}
