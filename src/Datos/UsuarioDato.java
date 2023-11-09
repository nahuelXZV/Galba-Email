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
    private final ConexionDB conexion;

    private int id;
    private String nombre;
    private String correo;
    private String contraseña;
    private String direccion;
    private String telefono;
    private String cargo;
    private boolean isCliente;
    private boolean isPersonal;
    private boolean isAdministrador;

    public UsuarioDato() {
        conexion = new ConexionDB();
    }

    public UsuarioDato(int id, String nombre, String correo, String contraseña, String cargo, boolean isPersonal,
            boolean isAdministrador) {
        conexion = new ConexionDB();
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.cargo = cargo;
        this.isPersonal = isPersonal;
        this.isAdministrador = isAdministrador;
    }

    public UsuarioDato(int id, String nombre, String correo, String contraseña, String direccion, String telefono,
            boolean isCliente) {
        conexion = new ConexionDB();
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.direccion = direccion;
        this.telefono = telefono;
        this.isCliente = isCliente;
    }

    // Funciones
    public boolean createPersonal() {
        String sql = "INSERT INTO usuario (nombre , correo, contraseña, cargo, isPersonal, isAdministrador) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, contraseña);
            ps.setString(4, cargo);
            ps.setBoolean(5, isPersonal);
            ps.setBoolean(6, isAdministrador);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean createCliente() {
        String sql = "INSERT INTO usuario (nombre , correo, contraseña, direccion, telefono, isCliente) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, contraseña);
            ps.setString(4, direccion);
            ps.setString(5, telefono);
            ps.setBoolean(6, isCliente);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updatePersonal() {
        String sql = "UPDATE usuario SET nombre = ?, correo = ?, contraseña = ?, cargo = ?, isPersonal = ?, isAdministrador = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, contraseña);
            ps.setString(4, cargo);
            ps.setBoolean(5, isPersonal);
            ps.setBoolean(6, isAdministrador);
            ps.setInt(7, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateCliente() {
        String sql = "UPDATE usuario SET nombre = ?, correo = ?, contraseña = ?, direccion = ?, telefono = ?, isCliente = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, contraseña);
            ps.setString(4, direccion);
            ps.setString(5, telefono);
            ps.setBoolean(6, isCliente);
            ps.setInt(7, id);
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

    public boolean validateRol(String correo, String rol, String atribute) {
        String sql = "SELECT * FROM usuario WHERE ? = ? AND correo = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, atribute);
            ps.setString(2, rol);
            ps.setString(3, correo);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public String getAll(LinkedList<String> params) {
        String tabla = "";
        try {
            Statement consulta;
            ResultSet resultado = null;
            tabla = "<h1>Lista de usuarios</h1>\n"
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
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CARGO</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">DIRECCION</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">TELEFONO</th>\n"
                    + "\n";
            String query = "";
            if (params.size() == 0)
                query = "SELECT id, nombre, correo, cargo, direccion, telefono FROM usuario";
            else
                query = "SELECT id, nombre, correo, cargo, direccion, telefono FROM usuario WHERE "
                        + params.get(0) + " ILIKE '%" + params.get(1) + "%'";
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
            System.out.println(e.getMessage());
        }
        return tabla;
    }

}
