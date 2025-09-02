package br.com.estoque.dao;

import br.com.estoque.conexao.ConexaoBD;
import br.com.estoque.model.EstoqueLocal;


import java.sql.*;

public class EstoqueLocalDAO {

    public boolean inserir(EstoqueLocal estoqueLocal){
        String checkSql = "SELECT ID_ESTOQUE FROM ESTOQUE_LOCAL WHERE ID_PRODUTO = ? AND ID_LOCAL = ?";
        String insertSql = "INSERT INTO ESTOQUE_LOCAL (ID_PRODUTO, ID_LOCAL, QUANTIDADE) VALUES (?, ?, ?)";

        Connection conn = null;
        try {
            conn = ConexaoBD.getConnection();

            // 1) Verifica se já existe registro para esse produto+local
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, estoqueLocal.getIdProduto());
                checkStmt.setInt(2, estoqueLocal.getIdLocal());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        return false; // já existe: quem chama decide se vai incrementar/atualizar
                    }
                }
            }

            // 2) Insere novo registro
            try (PreparedStatement stmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, estoqueLocal.getIdProduto());
                stmt.setInt(2, estoqueLocal.getIdLocal());
                stmt.setInt(3, estoqueLocal.getQuantidade() != null ? estoqueLocal.getQuantidade() : 0);

                int linhas = stmt.executeUpdate();

                if (linhas > 0) {
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            estoqueLocal.setIdEstoqueLocal(rs.getInt(1));
                        }
                    }
                    if (!conn.getAutoCommit()) conn.commit();
                    return true;
                } else {
                    if (!conn.getAutoCommit()) conn.rollback();
                    return false;
                }
            }

        } catch (SQLException e) {
            try { if (conn != null && !conn.getAutoCommit()) conn.rollback(); } catch (SQLException ignored) {}
            if (e.getErrorCode() == 1) {
                System.out.println("Erro: registro duplicado (produto + local).");
            } else {
                System.out.println("Erro ao inserir estoque: " + e.getMessage());
            }
            return false;
        } finally {
            ConexaoBD.close(conn);
        }

    }
}
