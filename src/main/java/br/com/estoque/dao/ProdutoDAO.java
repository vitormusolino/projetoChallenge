package br.com.estoque.dao;

import br.com.estoque.conexao.ConexaoBD;
import br.com.estoque.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ProdutoDAO {
    public void inserir(Produto produto) {
        String sql = "INSERT INTO PRODUTOS (NOME,DESCRICAO,CODIGO_BARRAS,VALIDADE,UNIDADE,QUANTIDADE_MINIMA)" +
                     "VALUES(?,?,?,?,?,?)" ;

        try(Connection conn = ConexaoBD.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

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
}
