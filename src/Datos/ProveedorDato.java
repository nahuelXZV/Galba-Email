package Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;

import Servicios.ConexionDB;

public class ProveedorDato {
    private final ConexionDB conexion;

    private int id;
    private String nombre;
    private String correo;
    private String telefono;
    private String direccion;
    private String nit;

    public ProveedorDato() {
        conexion = new ConexionDB();
    }

    public ProveedorDato(int id, String nombre, String correo, String telefono, String direccion,
            String nit) {
        conexion = new ConexionDB();
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.nit = nit;
    }

    public boolean createProveedor() {
        String sql = "INSERT INTO proveedor (nombre, correo, telefono, direccion, nit) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, telefono);
            ps.setString(4, direccion);
            ps.setString(5, nit);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateProveedor() {
        String sql = "UPDATE proveedor SET nombre = ?, correo = ?, telefono = ?, direccion = ?, nit = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, telefono);
            ps.setString(4, direccion);
            ps.setString(5, nit);
            ps.setInt(6, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM proveedor WHERE id = ?";
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
        String sql = "SELECT * FROM proveedor WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean emailExist(String correo) {
        String sql = "SELECT * FROM proveedor WHERE correo = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            return rs.next();
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
            tabla = "<h1>Lista de proveedores</h1>\n"
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
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">TELEFONO</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">DIRECCION</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">NIT</th>\n"
                    + "\n";
            String query = "";
            if (params.size() == 0)
                query = "SELECT id, nombre, correo, telefono, direccion, nit FROM proveedor";
            else
                query = "SELECT id, nombre, correo, telefono, direccion, nit FROM proveedor WHERE "
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

}
