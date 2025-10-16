package br.com.estoque.main;

import br.com.estoque.dao.EstoqueLocalDAO;
import br.com.estoque.model.EstoqueLocal;

import java.util.List;

public class EstoqueLocalMain {
    public static void main(String[] args) {
        EstoqueLocalDAO dao = new EstoqueLocalDAO();

        //EstoqueLocal novo = new EstoqueLocal(1, 1, 50);
        //boolean inseriu = dao.inserirOuSomar(novo);
        //System.out.println("InserirOuSomar -> " + inseriu + " | ID gerado: " + novo.getIdEstoqueLocal());

        //EstoqueLocal buscado = dao.buscarPorId(novo.getIdEstoqueLocal());
        //System.out.println("BuscarPorId -> " + buscado);

        //buscado.setQuantidade(100);
        //boolean atualizou = dao.atualizar(buscado);
        //System.out.println("Atualizar -> " + atualizou);

        //List<EstoqueLocal> todos = dao.listarTodos();
        //System.out.println("ListarTodos:");
        //todos.forEach(System.out::println);

        //List<EstoqueLocal> porProduto = dao.listarPorProduto(1);
        //System.out.println("ListarPorProduto:");
        //porProduto.forEach(System.out::println);

       // List<EstoqueLocal> porLocal = dao.listarPorLocal(1);
        //System.out.println("ListarPorLocal:");
        //porLocal.forEach(System.out::println);

        boolean transferiu = dao.transferir(1, 2, 1, 10);
        System.out.println("Transferir -> " + transferiu);
    
        //boolean removeu = dao.remover(novo.getIdEstoqueLocal());
        //System.out.println("Remover -> " + removeu);
    }
}
