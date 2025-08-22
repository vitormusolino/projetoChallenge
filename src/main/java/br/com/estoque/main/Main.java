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
        System.out.println(produtoDAO.consultarPorId(23));
    }
}
