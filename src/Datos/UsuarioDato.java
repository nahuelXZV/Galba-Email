package Datos;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;

import Servicios.ConexionDB;

public class UsuarioDato {

    private int id;
    private String nombre;
    private String correo;
    private String contraseña;
    private String area;
    private int rol_id;

    private final ConexionDB conexion;

    public UsuarioDato() {
        conexion = new ConexionDB();
    }

    public UsuarioDato(int id, String nombre, String correo, String contraseña, String area, int rol_id) {
        conexion = new ConexionDB();
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.area = area;
        this.rol_id = rol_id;
    }

    // Funciones
    public boolean create() {
        String sql = "INSERT INTO usuario (nombre , correo, contraseña, area, rol_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, contraseña);
            ps.setString(4, area);
            ps.setInt(5, rol_id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean update() {
        String sql = "UPDATE usuario SET nombre = ?, correo = ?, contraseña = ?, area = ?, rol_id = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, contraseña);
            ps.setString(4, area);
            ps.setInt(5, rol_id);
            ps.setInt(6, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean exist(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean emailExist(String correo) {
        String sql = "SELECT * FROM usuario WHERE correo = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean validateRol(String correo, String rol) {
        String sql = "SELECT * FROM usuario, rol WHERE rol.id = usuario.rol_id AND usuario.correo = ? AND rol.nombre = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setString(2, rol);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public String getAll(LinkedList<String> params) {
        String tabla = "";
        Statement consulta;
        ResultSet resultado = null;
        tabla = "<h1>Lista de usuarios</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "\n"
                + "  <tr>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">NOMBRE</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CORREO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">AREA</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ROL</th>\n"
                + "\n";
        String query;
        if (params.size() == 0)
            query = "SELECT usuario.id, usuario.nombre, usuario.correo, usuario.area, rol.nombre as rol FROM usuario, rol WHERE usuario.rol_id = rol.id";
        else
            query = "SELECT usuario.id, usuario.nombre, usuario.correo, usuario.area, rol.nombre as rol FROM usuario, rol WHERE usuario.rol_id = rol.id AND "
                    + params.get(0) + " ILIKE '%" + params.get(1) + "%'";

        try {
            Connection con = conexion.connect();
            consulta = (Statement) con.createStatement();
            resultado = ((java.sql.Statement) consulta).executeQuery(query);
            ResultSetMetaData rsmd = resultado.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();
            while (resultado.next()) {
                tabla = tabla + "  <tr>\n" + "\n";
                for (int i = 0; i < cantidadColumnas; i++) {
                    tabla = tabla
                            + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">"
                            + resultado.getString(i + 1) + "</td>\n"
                            + "\n";
                }
                tabla = tabla + "  </tr>\n" + "\n";
            }
            tabla = tabla + "\n" + "</table>";
            ((Connection) consulta).close();
            con.close();
        } catch (SQLException e) {
            tabla = "No se pudieron listar los datos.";
        }
        return tabla;
    }

}
