package br.com.estoque.main;

import br.com.estoque.dao.EstoqueLocalDAO;
import br.com.estoque.model.EstoqueLocal;

public class EstoqueLocalMain {
    public static void main(String[] args) {
        EstoqueLocal estoqueLocal = new EstoqueLocal(24,1,20);
        EstoqueLocalDAO estoqueLocalDAO = new EstoqueLocalDAO();

        boolean ok = estoqueLocalDAO.inserir(estoqueLocal);
        if(ok){
            System.out.println("Inserido com id = " + estoqueLocal.getIdEstoqueLocal());
        } else{
            System.out.println("Produto não inserido");
        }
    }
}
