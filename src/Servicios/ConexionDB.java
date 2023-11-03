package Servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private final String DB = "db_grupo06sc";
    private final String USER = "grupo06sc";
    private final String PASSWORD = "grup006grup006";
    private final String URL = "jdbc:postgresql://tecnoweb.org.bo:5432/" + DB;
    private Connection conn;

    public Connection getConexion() {
        return conn;
    }

    public void close() {
        try {
            conn.close();
            System.out.println("Cerrando conexion");
        } catch (SQLException ex) {
            System.out.println("No se pudo cerrar la conexion");
        }
    }

    public Connection connect() {
        try {
            System.out.println("conectando a la DB...");
            Class.forName("org.postgresql.Driver");
            conn = (Connection) DriverManager.getConnection(this.URL, this.USER, this.PASSWORD);
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error al conectar" + ex);
        }
        return conn;
    }
}
