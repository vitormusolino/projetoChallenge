package br.com.estoque.main;

import br.com.estoque.dao.EstoqueLocalDAO;
import br.com.estoque.model.EstoqueLocal;

public class EstoqueLocalMain {
    public static void main(String[] args) {
        EstoqueLocal estoqueLocal = new EstoqueLocal(1, 2, 20);
        EstoqueLocalDAO dao = new EstoqueLocalDAO();

        System.out.println(dao.listarPorLocal(1));
        System.out.println(dao.listarPorLocal(2));
        System.out.println(dao.listarPorLocal(3));

        System.out.println(dao.listarTodos());

        System.out.println(dao.listarPorProduto(1));
        System.out.println(dao.listarPorProduto(61));


}}
