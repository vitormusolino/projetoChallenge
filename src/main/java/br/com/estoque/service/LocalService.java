package br.com.estoque.service;

import br.com.estoque.dao.LocalDAO;
import br.com.estoque.model.Local;

import java.util.List;

public class LocalService {
    private final LocalDAO dao = new LocalDAO();

    public boolean salvar(Local local) {
        if (local.getNomeLocal() == null || local.getNomeLocal().isBlank()) {
            throw new IllegalArgumentException("Nome do local é obrigatório.");
        }
        return dao.inserir(local);
    }

    public Local buscarPorId(int id) {
        return dao.consultarPorId(id);
    }

    public Local buscarPorNome(String nome) {
        return dao.consultarPorNome(nome);
    }

    public boolean atualizar(Local local) {
        if (local.getIdLocal() == null || local.getIdLocal() <= 0) {
            throw new IllegalArgumentException("ID do local inválido.");
        }
        return dao.atualizar(local);
    }

    public boolean excluir(int id) {
        return dao.excluirLocal(id);
    }

    public List<Local> listarTodos() {
        return dao.listarTodos();
    }
}
