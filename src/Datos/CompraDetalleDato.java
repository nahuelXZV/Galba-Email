package Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

import Servicios.ConexionDB;

public class CompraDetalleDato {
    private final ConexionDB conexion;
    private CompraDato compraDato;
    private ProductoDato productoDato;

    private int cantidad;
    private float precio;
    private int compra_id;
    private int producto_id;

    public CompraDetalleDato() {
        conexion = new ConexionDB();
        compraDato = new CompraDato();
        productoDato = new ProductoDato();
    }

    public CompraDetalleDato(int cantidad, float precio, int compra_id, int producto_id) {
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
            boolean idUpdated = rowsAffected > 0;
            boolean compraUpdated = compraDato.update(this.compra_id, monto);
            productoDato = new ProductoDato();
            boolean productoUpdated = productoDato.updateCantidadPrecio(this.producto_id, this.cantidad,
                    this.precio);
            return idUpdated && compraUpdated && productoUpdated;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM compra_detalle WHERE id = ?";
        int compra_id = this.getCompraIdByDetalleId(id);
        int producto_id = this.getProductoIdByDetalleId(id);
        int cantidad = this.getCantidadByDetalleId(id);
        float precio = this.getPrecioByDetalleId(id);
        Float monto = cantidad * precio;
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            boolean idUpdated = rowsAffected > 0;
            boolean compraUpdated = compraDato.update(compra_id, -monto);
            productoDato = new ProductoDato();
            boolean productoUpdated = productoDato.updateCantidadPrecio(producto_id, -cantidad,
                    precio);
            return idUpdated && compraUpdated && productoUpdated;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private int getProductoIdByDetalleId(int id) {
        String sql = "SELECT producto_id FROM compra_detalle WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("producto_id");
            }
            return 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    private float getPrecioByDetalleId(int id) {
        String sql = "SELECT precio FROM compra_detalle WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getFloat("precio");
            }
            return 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    private int getCantidadByDetalleId(int id) {
        String sql = "SELECT cantidad FROM compra_detalle WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("cantidad");
            }
            return 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    private int getCompraIdByDetalleId(int id) {
        String sql = "SELECT compra_id FROM compra_detalle WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("compra_id");
            }
            return 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    // private Float getMontoTotalDetalle(int id) {
    // String sql = "SELECT SUM(precio * cantidad) AS monto FROM compra_detalle
    // WHERE id = ?";
    // try (Connection con = conexion.connect(); PreparedStatement ps =
    // con.prepareStatement(sql)) {
    // ps.setInt(1, id);
    // java.sql.ResultSet rs = ps.executeQuery();
    // while (rs.next()) {
    // return rs.getFloat("monto");
    // }
    // return 0f;
    // } catch (SQLException e) {
    // System.out.println(e.getMessage());
    // return 0f;
    // }
    // }

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

    public boolean exist(int id) {
        String sql = "SELECT * FROM compra_detalle WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            java.sql.ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }
}