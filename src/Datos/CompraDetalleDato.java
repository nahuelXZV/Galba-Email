package Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

import Servicios.ConexionDB;

public class CompraDetalleDato {
    private final ConexionDB conexion;
    private CompraDato compraDato;

    private int cantidad;
    private float precio;
    private int compra_id;
    private int producto_id;

    public CompraDetalleDato() {
        conexion = new ConexionDB();
        compraDato = new CompraDato();
    }

    public CompraDetalleDato(int cantidad, int precio, int compra_id, int producto_id) {
        conexion = new ConexionDB();
        compraDato = new CompraDato();
        this.cantidad = cantidad;
        this.precio = precio;
        this.compra_id = compra_id;
        this.producto_id = producto_id;
    }

    // Funciones
    public boolean create() {
        String sql = "INSERT INTO compra_detalle (cantidad, precio, compra_id, producto_id) VALUES (?, ?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, this.cantidad);
            ps.setFloat(2, this.precio);
            ps.setInt(3, this.compra_id);
            ps.setInt(4, this.producto_id);
            int rowsAffected = ps.executeUpdate();
            Float monto = this.precio * this.cantidad;
            if (rowsAffected > 0 && compraDato.update(this.compra_id, monto)) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM compra_detalle WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteBycompra(int ingreso_id) {
        String sql = "DELETE FROM compra_detalle WHERE compra_id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ingreso_id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public LinkedList<String> getCompraDetalle(int compra_id) {
        String sql = "SELECT cantidad, precio, producto_id FROM compra_detalle WHERE compra_id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, compra_id);
            java.sql.ResultSet rs = ps.executeQuery();
            LinkedList<String> compraDetalle = new LinkedList<String>();
            while (rs.next()) {
                compraDetalle.add(rs.getInt("cantidad") + "," + rs.getString("precio") + ","
                        + rs.getInt("producto_id"));
            }
            return compraDetalle;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}