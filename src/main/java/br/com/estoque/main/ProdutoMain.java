package br.com.estoque.main;


import br.com.estoque.dao.ProdutoDAO;
import br.com.estoque.model.Produto;

import java.time.LocalDate;
import java.util.List;

public class ProdutoMain {
    public static void main(String[] args) {

        Produto produto = new Produto("Tubos de exame de sangue", "Tubos para armazenamento de sangue", "0987654321123", LocalDate.of(2028,03,10), "CX", 1000);
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> lista = dao.listarTodos();



        for (Produto p : lista) {
            System.out.println(p.getIdProduto() + " - " + p.getNome() + " (" + p.getCodigoBarras() + ")");
        }

    }}
