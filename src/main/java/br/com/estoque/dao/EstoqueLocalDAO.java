package br.com.estoque.dao;

import br.com.estoque.conexao.ConexaoBD;
import br.com.estoque.model.EstoqueLocal;

import java.sql.*;

public class EstoqueLocalDAO {

    public boolean inserirOuSomar(EstoqueLocal estoqueLocal) {
        Connection conn = null;
        try {
            conn = ConexaoBD.getConnection();

            String selectSql = "SELECT ID_ESTOQUE, QUANTIDADE FROM ESTOQUE_LOCAL WHERE ID_PRODUTO = ? AND ID_LOCAL = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, estoqueLocal.getIdProduto());
            selectStmt.setInt(2, estoqueLocal.getIdLocal());
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int idEstoque = rs.getInt("ID_ESTOQUE");
                int quantidadeAtual = rs.getInt("QUANTIDADE");
                int novaQuantidade = quantidadeAtual + estoqueLocal.getQuantidade();

                String updateSql = "UPDATE ESTOQUE_LOCAL SET QUANTIDADE = ? WHERE ID_ESTOQUE = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, novaQuantidade);
                updateStmt.setInt(2, idEstoque);
                int linhas = updateStmt.executeUpdate();
                conn.commit();
                estoqueLocal.setIdEstoqueLocal(idEstoque);
                return linhas > 0;
            } else {
                String nextValSql = "SELECT SEQ_ESTOQUE_LOCAL.NEXTVAL FROM DUAL";
                PreparedStatement seqStmt = conn.prepareStatement(nextValSql);
                ResultSet seqRs = seqStmt.executeQuery();
                int novoId = 0;
                if (seqRs.next()) {
                    novoId = seqRs.getInt(1);
                }

                String insertSql = "INSERT INTO ESTOQUE_LOCAL (ID_ESTOQUE, ID_PRODUTO, ID_LOCAL, QUANTIDADE) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, novoId);
                insertStmt.setInt(2, estoqueLocal.getIdProduto());
                insertStmt.setInt(3, estoqueLocal.getIdLocal());
                insertStmt.setInt(4, estoqueLocal.getQuantidade());
                int linhas = insertStmt.executeUpdate();

                if (linhas > 0) {
                    conn.commit();
                    estoqueLocal.setIdEstoqueLocal(novoId);
                    return true;
                }
            }

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ignored) {}
            System.out.println("Erro no inserirOuSomar: " + e.getMessage());
        } finally {
            ConexaoBD.close(conn);
        }
        return false;
    }
}
