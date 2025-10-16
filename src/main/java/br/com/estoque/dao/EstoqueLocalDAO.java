package br.com.estoque.dao;

import br.com.estoque.conexao.ConexaoBD;
import br.com.estoque.model.EstoqueLocal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public boolean atualizar(EstoqueLocal estoqueLocal) {
        Connection conn = null;
        try {
            conn = ConexaoBD.getConnection();
            String sql = "UPDATE ESTOQUE_LOCAL SET ID_PRODUTO = ?, ID_LOCAL = ?, QUANTIDADE = ? WHERE ID_ESTOQUE = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, estoqueLocal.getIdProduto());
                ps.setInt(2, estoqueLocal.getIdLocal());
                ps.setInt(3, estoqueLocal.getQuantidade());
                ps.setInt(4, estoqueLocal.getIdEstoqueLocal());
                int linhas = ps.executeUpdate();
                conn.commit();
                return linhas > 0;
            }
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ignored) {}
            System.out.println("Erro no atualizar: " + e.getMessage());
        } finally {
            ConexaoBD.close(conn);
        }
        return false;
    }

    public boolean remover(int idEstoque) {
        Connection conn = null;
        try {
            conn = ConexaoBD.getConnection();
            String sql = "DELETE FROM ESTOQUE_LOCAL WHERE ID_ESTOQUE = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idEstoque);
                int linhas = ps.executeUpdate();
                conn.commit();
                return linhas > 0;
            }
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ignored) {}
            System.out.println("Erro no remover: " + e.getMessage());
        } finally {
            ConexaoBD.close(conn);
        }
        return false;
    }

    public EstoqueLocal buscarPorId(int idEstoque) {
        Connection conn = null;
        try {
            conn = ConexaoBD.getConnection();
            String sql = "SELECT ID_ESTOQUE, ID_PRODUTO, ID_LOCAL, QUANTIDADE FROM ESTOQUE_LOCAL WHERE ID_ESTOQUE = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idEstoque);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new EstoqueLocal(
                                rs.getInt("ID_ESTOQUE"),
                                rs.getInt("ID_PRODUTO"),
                                rs.getInt("ID_LOCAL"),
                                rs.getInt("QUANTIDADE")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no buscarPorId: " + e.getMessage());
        } finally {
            ConexaoBD.close(conn);
        }
        return null;
    }

    public boolean transferir(int idProduto, int idOrigem, int idDestino, int quantidade) {
        Connection conn = null;
        try {
            conn = ConexaoBD.getConnection();

            String selOrigem = "SELECT ID_ESTOQUE, QUANTIDADE FROM ESTOQUE_LOCAL WHERE ID_PRODUTO = ? AND ID_LOCAL = ?";
            try (PreparedStatement psSelOrigem = conn.prepareStatement(selOrigem)) {
                psSelOrigem.setInt(1, idProduto);
                psSelOrigem.setInt(2, idOrigem);
                try (ResultSet rsOrigem = psSelOrigem.executeQuery()) {
                    if (!rsOrigem.next()) {
                        conn.rollback();
                        return false;
                    }
                    int idEstoqueOrigem = rsOrigem.getInt("ID_ESTOQUE");
                    int qtdAtualOrigem = rsOrigem.getInt("QUANTIDADE");
                    if (qtdAtualOrigem < quantidade) {
                        conn.rollback();
                        return false;
                    }
                    String updOrigem = "UPDATE ESTOQUE_LOCAL SET QUANTIDADE = ? WHERE ID_ESTOQUE = ?";
                    try (PreparedStatement psUpdOrig = conn.prepareStatement(updOrigem)) {
                        psUpdOrig.setInt(1, qtdAtualOrigem - quantidade);
                        psUpdOrig.setInt(2, idEstoqueOrigem);
                        int linhas = psUpdOrig.executeUpdate();
                        if (linhas == 0) {
                            conn.rollback();
                            return false;
                        }
                    }
                }
            }

            String selDestino = "SELECT ID_ESTOQUE, QUANTIDADE FROM ESTOQUE_LOCAL WHERE ID_PRODUTO = ? AND ID_LOCAL = ?";
            try (PreparedStatement psSelDst = conn.prepareStatement(selDestino)) {
                psSelDst.setInt(1, idProduto);
                psSelDst.setInt(2, idDestino);
                try (ResultSet rsDst = psSelDst.executeQuery()) {
                    if (rsDst.next()) {
                        int idEstoqueDst = rsDst.getInt("ID_ESTOQUE");
                        int qtdAtualDst = rsDst.getInt("QUANTIDADE");
                        String updDst = "UPDATE ESTOQUE_LOCAL SET QUANTIDADE = ? WHERE ID_ESTOQUE = ?";
                        try (PreparedStatement psUpdDst = conn.prepareStatement(updDst)) {
                            psUpdDst.setInt(1, qtdAtualDst + quantidade);
                            psUpdDst.setInt(2, idEstoqueDst);
                            int linhas = psUpdDst.executeUpdate();
                            if (linhas == 0) {
                                conn.rollback();
                                return false;
                            }
                        }
                    } else {
                        int novoId = 0;
                        String seqSql = "SELECT SEQ_ESTOQUE_LOCAL.NEXTVAL FROM DUAL";
                        try (PreparedStatement psSeq = conn.prepareStatement(seqSql);
                             ResultSet rsSeq = psSeq.executeQuery()) {
                            if (rsSeq.next()) {
                                novoId = rsSeq.getInt(1);
                            } else {
                                conn.rollback();
                                return false;
                            }
                        }
                        String insertDst = "INSERT INTO ESTOQUE_LOCAL (ID_ESTOQUE, ID_PRODUTO, ID_LOCAL, QUANTIDADE) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement psIns = conn.prepareStatement(insertDst)) {
                            psIns.setInt(1, novoId);
                            psIns.setInt(2, idProduto);
                            psIns.setInt(3, idDestino);
                            psIns.setInt(4, quantidade);
                            int linhas = psIns.executeUpdate();
                            if (linhas == 0) {
                                conn.rollback();
                                return false;
                            }
                        }
                    }
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try { if (conn != null && !conn.getAutoCommit()) conn.rollback(); } catch (SQLException ignored) {}
            System.out.println("Erro na transferência: " + e.getMessage());
            return false;
        } finally {
            ConexaoBD.close(conn);
        }
    }


    public List<EstoqueLocal> listarTodos() {
        List<EstoqueLocal> lista = new ArrayList<>();
        Connection conn = null;
        try {
            conn = ConexaoBD.getConnection();
            String sql = "SELECT ID_ESTOQUE, ID_PRODUTO, ID_LOCAL, QUANTIDADE FROM ESTOQUE_LOCAL";
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EstoqueLocal e = new EstoqueLocal(
                            rs.getInt("ID_ESTOQUE"),
                            rs.getInt("ID_PRODUTO"),
                            rs.getInt("ID_LOCAL"),
                            rs.getInt("QUANTIDADE")
                    );
                    lista.add(e);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no listarTodos: " + e.getMessage());
        } finally {
            ConexaoBD.close(conn);
        }
        return lista;
    }

    public List<EstoqueLocal> listarPorProduto(int idProduto) {
        List<EstoqueLocal> lista = new ArrayList<>();
        Connection conn = null;
        try {
            conn = ConexaoBD.getConnection();
            String sql = "SELECT ID_ESTOQUE, ID_PRODUTO, ID_LOCAL, QUANTIDADE FROM ESTOQUE_LOCAL WHERE ID_PRODUTO = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idProduto);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        EstoqueLocal e = new EstoqueLocal(
                                rs.getInt("ID_ESTOQUE"),
                                rs.getInt("ID_PRODUTO"),
                                rs.getInt("ID_LOCAL"),
                                rs.getInt("QUANTIDADE")
                        );
                        lista.add(e);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no listarPorProduto: " + e.getMessage());
        } finally {
            ConexaoBD.close(conn);
        }
        return lista;
    }

    public List<EstoqueLocal> listarPorLocal(int idLocal) {
        List<EstoqueLocal> lista = new ArrayList<>();
        Connection conn = null;
        try {
            conn = ConexaoBD.getConnection();
            String sql = "SELECT ID_ESTOQUE, ID_PRODUTO, ID_LOCAL, QUANTIDADE FROM ESTOQUE_LOCAL WHERE ID_LOCAL = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idLocal);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        EstoqueLocal e = new EstoqueLocal(
                                rs.getInt("ID_ESTOQUE"),
                                rs.getInt("ID_PRODUTO"),
                                rs.getInt("ID_LOCAL"),
                                rs.getInt("QUANTIDADE")
                        );
                        lista.add(e);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no listarPorLocal: " + e.getMessage());
        } finally {
            ConexaoBD.close(conn);
        }
        return lista;
    }
}
