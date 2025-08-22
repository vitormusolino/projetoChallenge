package br.com.estoque.main;


import br.com.estoque.dao.ProdutoDAO;
import br.com.estoque.model.Produto;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Produto produto = new Produto(
                "Papel higienico",
                "Papel higienico para limpeza",
                "1234567890123",
                LocalDate.of(2027,8,15),
                "CX",
                10
        );

        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.inserir(produto);
        //System.out.println(produtoDAO.consultarPorId());

        //boolean excluido = produtoDAO.excluirProduto(22);

        //if (excluido) {
        //    System.out.println("Produto excluído com sucesso!");
        //} else {
        //    System.out.println("Nenhum produto encontrado para excluir.");
        //}
    }
}
