package Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;

import Servicios.ConexionDB;

public class ProductoDato {
    private final ConexionDB conexion;

    private int id;
    private String nombre;
    private String imagen;
    private String tamaño;
    private float precio;
    private int cantidad;
    private String descripcion;
    private String categoria;

    public ProductoDato() {
        conexion = new ConexionDB();
    }

    public ProductoDato(int id, String nombre, String imagen, String tamaño, float precio, int cantidad,
            String descripcion, String categoria) {
        conexion = new ConexionDB();
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.tamaño = tamaño;
        this.precio = precio;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public ProductoDato(int id, String nombre, String imagen, String tamaño, float precio,
            String descripcion, String categoria) {
        conexion = new ConexionDB();
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.tamaño = tamaño;
        this.precio = precio;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    // Funciones
    public boolean createProducto() {
        String sql = "INSERT INTO producto (nombre , imagen, tamaño, precio, cantidad, descripcion, categoria) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, imagen);
            ps.setString(3, tamaño);
            ps.setFloat(4, precio);
            ps.setInt(5, cantidad);
            ps.setString(6, descripcion);
            ps.setString(7, categoria);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateProducto() {
        String sql = "UPDATE producto SET nombre = ?, imagen = ?, tamaño = ?, precio = ?, descripcion = ?, categoria = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, imagen);
            ps.setString(3, tamaño);
            ps.setFloat(4, precio);
            ps.setString(5, descripcion);
            ps.setString(6, categoria);
            ps.setInt(7, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateCantidadPrecio(int id, int cantidad, Float precio) {
        int anteriorCantidad = getCantidad(id);
        float anteriorPrecio = getPrecio(id);
        int nuevaCantidad = anteriorCantidad + cantidad;
        float nuevoPrecio = (anteriorPrecio + precio) / 2;
        String sql = "UPDATE producto SET cantidad = ?, precio = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nuevaCantidad);
            ps.setFloat(2, nuevoPrecio);
            ps.setInt(3, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateCantidad(int id, int cantidad) {
        int anteriorCantidad = getCantidad(id);
        int nuevaCantidad = anteriorCantidad + cantidad;
        String sql = "UPDATE producto SET cantidad = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nuevaCantidad);
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM producto WHERE id = ?";
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
        String sql = "SELECT * FROM producto WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public float getPrecio(int id) {
        String sql = "SELECT precio FROM producto WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getFloat(1);
            }
            return 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }

    }

    public int getCantidad(int id) {
        String sql = "SELECT cantidad FROM producto WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }

    }

    public String getAll(LinkedList<String> params) {
        String tabla = "";
        try {
            java.sql.Statement consulta;
            ResultSet resultado = null;
            tabla = "<h1>Lista de productos</h1>\n"
                    + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                    + "\n"
                    + "  <tr>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">NOMBRE</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">IMAGEN</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">TAMAÑO</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">PRECIO</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CANTIDAD</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">DESCRIPCION</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CATEGORIA</th>\n"
                    + "\n";
            String query = "";
            if (params.size() == 0)
                query = "SELECT id, nombre, imagen, tamaño, precio, cantidad, descripcion, categoria FROM producto";
            else
                query = "SELECT id, nombre, imagen, tamaño, precio, cantidad, descripcion, categoria FROM producto WHERE "
                        + params.get(0) + " ILIKE '%" + params.get(1) + "%'";
            Connection con = conexion.connect();
            consulta = con.createStatement();
            resultado = consulta.executeQuery(query);
            ResultSetMetaData rsmd = resultado.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();
            while (resultado.next()) {
                tabla = tabla + "  <tr>\n" + "\n";
                for (int i = 0; i < cantidadColumnas; i++) {
                    if (i == 2) {
                        tabla = tabla
                                + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\"><img src=\""
                                + resultado.getString(i + 1) + "\" width=\"100\" height=\"100\"></td>\n" + "\n";
                    } else {
                        tabla = tabla
                                + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">"
                                + resultado.getString(i + 1) + "</td>\n"
                                + "\n";
                    }
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
