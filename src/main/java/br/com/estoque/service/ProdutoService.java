package br.com.estoque.service;

import br.com.estoque.dao.ProdutoDAO;
import br.com.estoque.model.Produto;

import java.util.List;

public class ProdutoService {
    private final ProdutoDAO dao = new ProdutoDAO();

    public boolean salvar(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório.");
        }
        if (produto.getCodigoBarras() == null || produto.getCodigoBarras().isBlank()) {
            throw new IllegalArgumentException("Código de barras é obrigatório.");
        }
        return dao.inserir(produto);
    }

    public Produto buscarPorId(int id) {
        return dao.consultarPorId(id);
    }

    public boolean atualizar(Produto produto) {
        if (produto.getIdProduto() == null || produto.getIdProduto() <= 0) {
            throw new IllegalArgumentException("ID do produto inválido.");
        }
        return dao.atualizar(produto);
    }

    public boolean excluir(int id) {
        return dao.excluirProduto(id);
    }

    public List<Produto> listarTodos() {
        return dao.listarTodos();
    }
}
