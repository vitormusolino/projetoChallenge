package br.com.estoque.main;


import br.com.estoque.dao.ProdutoDAO;
import br.com.estoque.model.Produto;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Produto produto = new Produto(
                "Agulha",
                "Agulhas para exame de sangue",
                "7891234567890",
                LocalDate.of(2027,02,01),
                "UN",
                5
        );

        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.inserir(produto);
    }
}
