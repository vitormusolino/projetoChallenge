package br.com.estoque.main;

import br.com.estoque.dao.LocalDAO;
import br.com.estoque.model.Local;

public class LocalMain {

    public static void main(String[] args) {
        Local local = new Local("Sala de exame 3", "Sala para exames de sangue");
        LocalDAO localDAO = new LocalDAO();

        System.out.println(localDAO.consultarPorId(1));
        System.out.println(localDAO.consultarPorId(2));
        System.out.println(localDAO.consultarPorId(3));

        Local localAtt = new Local();
        localAtt.setIdLocal(23);
        localAtt.setNomeLocal("Sala de exame 3");
        localAtt.setDescricao("Sala para exames de Ultrassom");

        localDAO.atualizar(localAtt);
        System.out.println(localDAO.listarTodos());


        System.out.println(localDAO.consultarPorNome("Almoxarifado"));
    }
}
