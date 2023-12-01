package Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    private boolean es_cliente;
    private boolean es_personal;
    private boolean es_administrador;

    public UsuarioDato() {
        conexion = new ConexionDB();
    }

    public UsuarioDato(int id, String nombre, String correo, String contraseña, String cargo, boolean es_personal,
            boolean es_administrador) {
        conexion = new ConexionDB();
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = this.hashPassword(contraseña);
        this.cargo = cargo;
        this.es_personal = es_personal;
        this.es_administrador = es_administrador;
    }

    public UsuarioDato(int id, String nombre, String correo, String contraseña, String direccion, String telefono) {
        conexion = new ConexionDB();
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = this.hashPassword(contraseña);
        this.direccion = direccion;
        this.telefono = telefono;
        this.es_cliente = true;
    }

    // Funciones
    public boolean createPersonal() {
        String sql = "INSERT INTO usuario (nombre , correo, contraseña, cargo, es_personal, es_administrador) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.nombre);
            ps.setString(2, this.correo);
            ps.setString(3, this.contraseña);
            ps.setString(4, this.cargo);
            ps.setBoolean(5, this.es_personal);
            ps.setBoolean(6, this.es_administrador);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean createCliente() {
        String sql = "INSERT INTO usuario (nombre , correo, contraseña, direccion, telefono, es_cliente) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.nombre);
            ps.setString(2, this.correo);
            ps.setString(3, this.contraseña);
            ps.setString(4, this.direccion);
            ps.setString(5, this.telefono);
            ps.setBoolean(6, this.es_cliente);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updatePersonal() {
        String sql = "UPDATE usuario SET nombre = ?, correo = ?, contraseña = ?, cargo = ?, es_personal = ?, es_administrador = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.nombre);
            ps.setString(2, this.correo);
            ps.setString(3, this.contraseña);
            ps.setString(4, this.cargo);
            ps.setBoolean(5, this.es_personal);
            ps.setBoolean(6, this.es_administrador);
            ps.setInt(7, this.id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateCliente() {
        String sql = "UPDATE usuario SET nombre = ?, correo = ?, contraseña = ?, direccion = ?, telefono = ?, es_cliente = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.nombre);
            ps.setString(2, this.correo);
            ps.setString(3, this.contraseña);
            ps.setString(4, this.direccion);
            ps.setString(5, this.telefono);
            ps.setBoolean(6, this.es_cliente);
            ps.setInt(7, this.id);
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

    public int idByEmail(String email) {
        String sql = "SELECT id FROM usuario WHERE correo = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("id");
            else
                return -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public boolean emailExist(String correo) {
        String sql = "SELECT * FROM usuario WHERE correo = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return true;
            else
                return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public String show(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String tabla = "<h1>Perfil</h1>\n"
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
                        + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">DIRECCION</th>\n"
                        + "\n"
                        + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">TELEFONO</th>\n"
                        + "\n"
                        + "</tr>\n";

                tabla = tabla + "  <tr>\n" + "\n";
                tabla = tabla
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">"
                        + rs.getString("id") + "</td>\n"
                        + "\n";
                tabla = tabla
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">"
                        + rs.getString("nombre") + "</td>\n"
                        + "\n";
                tabla = tabla
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">"
                        + rs.getString("correo") + "</td>\n"
                        + "\n";
                tabla = tabla
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">"
                        + rs.getString("direccion") + "</td>\n"
                        + "\n";
                tabla = tabla
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">"
                        + rs.getString("telefono") + "</td>\n"
                        + "\n";
                tabla = tabla + "  </tr>\n" + "\n";
                tabla = tabla + "\n" + "</table>";
                return tabla;
            } else
                return "No se encontro el usuario.";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "Error al obtener usuario";
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
            java.sql.Statement consulta;
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
            consulta = con.createStatement();
            resultado = consulta.executeQuery(query);
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
            consulta.close();
            con.close();
        } catch (SQLException e) {
            tabla = "No se pudieron listar los datos.";
            System.out.println(e.getMessage());
        }
        return tabla;
    }

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
