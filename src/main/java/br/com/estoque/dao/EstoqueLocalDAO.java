package br.com.estoque.dao;

import br.com.estoque.conexao.ConexaoBD;
import br.com.estoque.model.EstoqueLocal;

import java.sql.*;

public class EstoqueLocalDAO {

    public boolean inserirOuSomar(EstoqueLocal estoqueLocal){
        Connection conn = null;
        try {
            conn = ConexaoBD.getConnection();

            String selectSql = "SELECT ID_ESTOQUE, QUANTIDADE FROM ESTOQUE_LOCAL WHERE ID_PRODUTO = ? AND ID_LOCAL = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, estoqueLocal.getIdProduto());
            selectStmt.setInt(2, estoqueLocal.getIdLocal());
            ResultSet rs = selectStmt.executeQuery();

            if(rs.next()){
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
                String insertSql = "INSERT INTO ESTOQUE_LOCAL (ID_ESTOQUE, ID_PRODUTO, ID_LOCAL, QUANTIDADE) VALUES (SEQ_ESTOQUE.NEXTVAL, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                insertStmt.setInt(1, estoqueLocal.getIdProduto());
                insertStmt.setInt(2, estoqueLocal.getIdLocal());
                insertStmt.setInt(3, estoqueLocal.getQuantidade());
                int linhas = insertStmt.executeUpdate();

                if(linhas > 0){
                    ResultSet rsKeys = insertStmt.getGeneratedKeys();
                    if(rsKeys.next()){
                        estoqueLocal.setIdEstoqueLocal(rsKeys.getInt(1));
                    }
                    conn.commit();
                    return true;
                }
            }

        } catch (SQLException e) {
            try { if(conn != null) conn.rollback(); } catch (SQLException ignored){}
            System.out.println("Erro no inserirOuSomar: " + e.getMessage());
        } finally {
            ConexaoBD.close(conn);
        }
        return false;
    }
}
