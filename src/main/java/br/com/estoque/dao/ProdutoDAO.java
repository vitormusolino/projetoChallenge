package br.com.estoque.dao;

import br.com.estoque.conexao.ConexaoBD;
import br.com.estoque.model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    public void inserir(Produto produto) {
        String sql = "INSERT INTO PRODUTOS (NOME,DESCRICAO,CODIGO_BARRAS,VALIDADE,UNIDADE,QUANTIDADE_MINIMA)" +
                     "VALUES(?,?,?,?,?,?)" ;

        try(Connection conn = ConexaoBD.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setString(3, produto.getCodigoBarras());
            if(produto.getValidade() != null){
                stmt.setDate(4, java.sql.Date.valueOf(produto.getValidade()));
            }else{
                stmt.setDate(4, null);
            }
            stmt.setString(5, produto.getUnidade());
            stmt.setInt(6, produto.getQuantidadeMinima() != null ? produto.getQuantidadeMinima() : 0);

            stmt.executeUpdate();
            System.out.println("Produto inserido com sucesso!");
        }catch (SQLException e){
            System.out.println("Erro ao inserir produto: " + e.getMessage());
        }
    }

    public Produto consultarPorId(int idProduto){
        String sql = """
            SELECT ID_PRODUTO, NOME, DESCRICAO, CODIGO_BARRAS, VALIDADE, UNIDADE, QUANTIDADE_MINIMA
            FROM PRODUTOS
            WHERE ID_PRODUTO = ?
        """;

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProduto);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Produto p = new Produto();
                    p.setIdProduto(rs.getInt("ID_PRODUTO"));
                    p.setNome(rs.getString("NOME"));
                    p.setDescricao(rs.getString("DESCRICAO"));
                    p.setCodigoBarras(rs.getString("CODIGO_BARRAS"));
                    java.sql.Date dt = rs.getDate("VALIDADE");
                    p.setValidade(dt != null ? dt.toLocalDate() : null);
                    p.setUnidade(rs.getString("UNIDADE"));
                    p.setQuantidadeMinima(rs.getInt("QUANTIDADE_MINIMA"));
                    return p;
                }
            }
        } catch (SQLException e){
            System.out.println("Erro ao consultar produto: " + e.getMessage());
        }
        return null;
    }

    public boolean excluirProduto(int idProduto){
        String sql = "DELETE FROM PRODUTOS WHERE ID_PRODUTO = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, idProduto);
            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;

        }catch (SQLException e){
            System.out.println("Erro ao excluir produto: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(Produto p){
        if (p.getIdProduto() == null || p.getIdProduto() <= 0) {
            throw new IllegalArgumentException("ID do produto inválido para atualização.");
        }

        String sql = """
            UPDATE PRODUTOS
               SET NOME = ?, DESCRICAO = ?, CODIGO_BARRAS = ?, VALIDADE = ?, UNIDADE = ?, QUANTIDADE_MINIMA = ?
             WHERE ID_PRODUTO = ?
        """;
        Connection conn = null;

        try{
            conn = ConexaoBD.getConnection();

            try(PreparedStatement stmt = conn.prepareStatement(sql)) {


                stmt.setString(1, p.getNome());
                stmt.setString(2, p.getDescricao());
                stmt.setString(3, p.getCodigoBarras());

                if (p.getValidade() != null) {
                    stmt.setDate(4, Date.valueOf(p.getValidade()));
                } else {
                    stmt.setNull(4, Types.DATE);
                }

                stmt.setString(5, p.getUnidade());
                stmt.setInt(6, p.getQuantidadeMinima());
                stmt.setInt(7, p.getIdProduto());

                int linhas = stmt.executeUpdate();

                if (!conn.getAutoCommit()) conn.commit();

                return linhas > 0;
            }

        }catch (SQLException e) {
            if (conn != null) {
                try { if (!conn.getAutoCommit()) conn.rollback(); } catch (SQLException ignored) {}
            }
            if (e.getErrorCode() == 1) {
                System.out.println("Código de barras já cadastrado para outro produto.");
            } else {
                System.out.println("Erro ao atualizar produto: " + e.getMessage());
            }
            return false;
        } finally {
            ConexaoBD.close(conn);
        }
    }

    public List<Produto> listarTodos(){
        ArrayList<Produto> produtos = new ArrayList<>();

        String sql = "SELECT ID_PRODUTO, NOME, DESCRICAO, CODIGO_BARRAS, VALIDADE, UNIDADE, QUANTIDADE_MINIMA FROM PRODUTOS";

        try(Connection conn = ConexaoBD.getConnection();
            PreparedStatement stmt= conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();){

            while(rs.next()){
                Produto p = new Produto();
                p.setIdProduto(rs.getInt("ID_PRODUTO"));
                p.setNome(rs.getString("NOME"));
                p.setDescricao(rs.getString("DESCRICAO"));
                p.setCodigoBarras(rs.getString("CODIGO_BARRAS"));

                Date validade = rs.getDate("VALIDADE");
                if (validade != null) {
                    p.setValidade(validade.toLocalDate());
                }

                p.setUnidade(rs.getString("UNIDADE"));
                p.setQuantidadeMinima(rs.getInt("QUANTIDADE_MINIMA"));

                produtos.add(p);
            }
        }catch (SQLException e){
            System.out.println("Erro ao listar: " + e.getMessage());
        }
        return produtos;
    }
}

