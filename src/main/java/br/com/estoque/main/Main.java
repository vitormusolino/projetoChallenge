package br.com.estoque.main;


import br.com.estoque.dao.ProdutoDAO;
import br.com.estoque.model.Produto;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {


        ProdutoDAO produtoDAO = new ProdutoDAO();
        System.out.println(produtoDAO.consultarPorId(24));

        ProdutoDAO dao = new ProdutoDAO();

        Produto p = dao.consultarPorId(24);
        if (p != null) {
            p.setNome("Papel para limpeza ( Atualizado )");
            p.setQuantidadeMinima(20);
            boolean ok = dao.atualizar(p);
            System.out.println(ok ? "Atualizado com sucesso!" : "Nada foi atualizado.");
        } else {
            System.out.println("Produto não encontrado.");
        }

        produtoDAO.consultarPorId(24);
}}
