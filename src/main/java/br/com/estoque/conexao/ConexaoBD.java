package br.com.estoque.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    private static final String HOST = "oracle.fiap.com.br";
    private static final String PORT = "1521";
    private static final String SERVICE_NAME = "orcl";
    private static final String URL = "jdbc:oracle:thin:@//" + HOST + ":" + PORT + "/" + SERVICE_NAME;

    private static final String USUARIO = "rm555012";
    private static final String SENHA = "020206";

    public static Connection getConnection() throws SQLException{
        Connection conn = DriverManager.getConnection(URL,USUARIO,SENHA);
        conn.setAutoCommit(false);
        return conn;
    }

    public static void commit(Connection conn) {
        if (conn != null) {
            try { conn.commit(); } catch (SQLException ignored) {}
        }
    }

    public static void rollback(Connection conn) {
        if (conn != null) {
            try { conn.rollback(); } catch (SQLException ignored) {}
        }
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try { conn.close(); } catch (SQLException ignored) {}
        }
    }

}
