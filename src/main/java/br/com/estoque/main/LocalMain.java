package br.com.estoque.main;

import br.com.estoque.dao.LocalDAO;
import br.com.estoque.model.Local;

public class LocalMain {

    public static void main(String[] args) {
        Local local = new Local("Sala de exame 3", "Sala para exames de sangue");
        LocalDAO localDAO = new LocalDAO();

        localDAO.inserir(local);

    }
}
