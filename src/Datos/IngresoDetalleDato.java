package Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import Servicios.ConexionDB;

public class IngresoDetalleDato {
    private final ConexionDB conexion;
    private ProductoDato productoDato;

    private int cantidad;
    private int ingreso_id;
    private int producto_id;

    public IngresoDetalleDato() {
        conexion = new ConexionDB();
    }

    public IngresoDetalleDato(int cantidad, int ingreso_id, int producto_id) {
        conexion = new ConexionDB();
        this.cantidad = cantidad;
        this.ingreso_id = ingreso_id;
        this.producto_id = producto_id;
    }

    // Funciones
    public boolean create() {
        String sql = "INSERT INTO ingreso_detalle (cantidad, ingreso_id, producto_id) VALUES (?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, this.cantidad);
            ps.setInt(2, this.ingreso_id);
            ps.setInt(3, this.producto_id);
            int rowsAffected = ps.executeUpdate();
            productoDato = new ProductoDato();
            if (rowsAffected > 0 && productoDato.updateCantidad(this.producto_id, this.cantidad)) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean update(int id, int cantidad) {
        int cantidad_anterior = getCantidad(id);
        String sql = "UPDATE ingreso_detalle SET cantidad = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cantidad_anterior + cantidad);
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM ingreso_detalle WHERE id = ?";
        int producto_id = getProductoIdByDetalleId(id);
        int cantidad = getCantidad(id);
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            productoDato = new ProductoDato();
            if (rowsAffected > 0 && productoDato.updateCantidad(producto_id, -cantidad)) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private int getProductoIdByDetalleId(int id) {
        int producto_id = 0;
        try {
            java.sql.Statement consulta;
            ResultSet resultado = null;
            String query = "";
            query = "SELECT producto_id FROM ingreso_detalle WHERE id = " + id;
            Connection con = conexion.connect();
            consulta = con.createStatement();
            resultado = consulta.executeQuery(query);
            while (resultado.next()) {
                producto_id = resultado.getInt(1);
            }
            consulta.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return producto_id;
    }

    public boolean deleteByingreso(int ingreso_id) {
        String sql = "DELETE FROM ingreso_detalle WHERE ingreso_id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ingreso_id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private int getCantidad(int id) {
        int cantidad = 0;
        try {
            java.sql.Statement consulta;
            ResultSet resultado = null;
            String query = "";
            query = "SELECT cantidad FROM ingreso_detalle WHERE id = " + id;
            Connection con = conexion.connect();
            consulta = con.createStatement();
            resultado = consulta.executeQuery(query);
            while (resultado.next()) {
                cantidad = resultado.getInt(1);
            }
            consulta.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cantidad;
    }

    public LinkedList<String> getIngresoDetalle(int ingreso_id) {
        String sql = "SELECT cantidad, motivo, producto_id FROM ingreso_detalle WHERE ingreso_id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ingreso_id);
            java.sql.ResultSet rs = ps.executeQuery();
            LinkedList<String> ingresoDetalle = new LinkedList<String>();
            while (rs.next()) {
                ingresoDetalle.add(rs.getInt("cantidad") + "," + rs.getString("motivo") + ","
                        + rs.getInt("producto_id"));
            }
            return ingresoDetalle;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public int getIdLastIngreso() {
        int id = 0;
        try {
            java.sql.Statement consulta;
            ResultSet resultado = null;
            String query = "";
            query = "SELECT MAX(id) FROM ingreso";
            Connection con = conexion.connect();
            consulta = con.createStatement();
            resultado = consulta.executeQuery(query);
            while (resultado.next()) {
                id = resultado.getInt(1);
            }
            consulta.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public boolean exist(int id) {
        String sql = "SELECT * FROM ingreso_detalle WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }
}