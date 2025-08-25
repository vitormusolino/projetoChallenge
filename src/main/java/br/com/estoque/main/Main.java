package br.com.estoque.main;


import br.com.estoque.dao.ProdutoDAO;
import br.com.estoque.model.Produto;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> lista = dao.listarTodos();

        for (Produto p : lista) {
            System.out.println(p.getIdProduto() + " - " + p.getNome() + " (" + p.getCodigoBarras() + ")");
        }

    }}
