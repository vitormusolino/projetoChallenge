package br.com.estoque.main;

import br.com.estoque.dao.EstoqueLocalDAO;
import br.com.estoque.model.EstoqueLocal;

public class EstoqueLocalMain {
    public static void main(String[] args) {
        EstoqueLocal estoqueLocal = new EstoqueLocal(1, 1, 5);
        EstoqueLocalDAO dao = new EstoqueLocalDAO();

        System.out.println("Tentando inserir ou somar...");
        boolean ok = dao.inserirOuSomar(estoqueLocal);
        if(ok){
            System.out.println("Operação realizada com sucesso. ID_ESTOQUE = " + estoqueLocal.getIdEstoqueLocal());
        } else {
            System.out.println("Falha na operação.");
        }
    }
}
