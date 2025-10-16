package br.com.estoque.service;

import br.com.estoque.dao.EstoqueLocalDAO;
import br.com.estoque.model.EstoqueLocal;

import java.util.List;

public class EstoqueLocalService {
    private final EstoqueLocalDAO dao = new EstoqueLocalDAO();

    public boolean salvarOuSomar(EstoqueLocal estoqueLocal) {
        if (estoqueLocal.getQuantidade() == null || estoqueLocal.getQuantidade() < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa.");
        }
        return dao.inserirOuSomar(estoqueLocal);
    }

    public EstoqueLocal buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public boolean atualizar(EstoqueLocal estoqueLocal) {
        if (estoqueLocal.getIdEstoqueLocal() == null || estoqueLocal.getIdEstoqueLocal() <= 0) {
            throw new IllegalArgumentException("ID do estoque inválido.");
        }
        return dao.atualizar(estoqueLocal);
    }

    public boolean remover(int id) {
        return dao.remover(id);
    }

    public boolean transferir(int idProduto, int idOrigem, int idDestino, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        return dao.transferir(idProduto, idOrigem, idDestino, quantidade);
    }

    public List<EstoqueLocal> listarTodos() {
        return dao.listarTodos();
    }

    public List<EstoqueLocal> listarPorProduto(int idProduto) {
        return dao.listarPorProduto(idProduto);
    }

    public List<EstoqueLocal> listarPorLocal(int idLocal) {
        return dao.listarPorLocal(idLocal);
    }
}
