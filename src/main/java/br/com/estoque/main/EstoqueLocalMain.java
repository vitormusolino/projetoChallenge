package br.com.estoque.main;

import br.com.estoque.dao.EstoqueLocalDAO;
import br.com.estoque.model.EstoqueLocal;

public class EstoqueLocalMain {
    public static void main(String[] args) {
        EstoqueLocal estoqueLocal = new EstoqueLocal(1, 2, 20);
        EstoqueLocalDAO dao = new EstoqueLocalDAO();


        dao.transferir(1, 1,2,20);
}}
