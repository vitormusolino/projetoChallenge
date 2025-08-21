package br.com.estoque;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String USUARIO = "rm555012";
    private static final String SENHA = "020206";

    public static Connection getConnection(){
        try{
            Connection conexao = DriverManager.getConnection(URL,USUARIO,SENHA);
            System.out.println("Conexão estabelecida com sucesso!");
            return conexao;
        }catch (SQLException e){
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }
}
