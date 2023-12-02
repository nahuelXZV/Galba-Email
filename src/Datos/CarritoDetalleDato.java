package Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import Servicios.ConexionDB;

public class CarritoDetalleDato {
    private final ConexionDB conexion;
    private CarritoDato carritoDato;

    private int cantidad;
    private float precio;
    private int carrito_id;
    private int producto_id;

    // Constructores
    public CarritoDetalleDato() {
        conexion = new ConexionDB();
        carritoDato = new CarritoDato();
    }

    public CarritoDetalleDato(int cantidad, float precio, int carrito_id, int producto_id) {
        conexion = new ConexionDB();
        carritoDato = new CarritoDato();
        this.cantidad = cantidad;
        this.precio = precio;
        this.carrito_id = carrito_id;
        this.producto_id = producto_id;
    }

    // Funciones
    public boolean create() {
        String sql = "INSERT INTO carrito_detalle (cantidad, precio, carrito_id, producto_id) VALUES (?, ?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, this.cantidad);
            ps.setFloat(2, this.precio);
            ps.setInt(3, this.carrito_id);
            ps.setInt(4, this.producto_id);
            int rowsAffected = ps.executeUpdate();
            Float monto = this.precio * this.cantidad;
            if (rowsAffected > 0 && carritoDato.update(this.carrito_id, monto)) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM carrito_detalle WHERE id = ?";
        Float monto = this.getMontoTotalDetalle(id);
        int carrito_id = this.getCarritoIdByDetalleId(id);
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0 && carritoDato.update(carrito_id, -monto)) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteBycarrito(int carrito_id) {
        String sql = "DELETE FROM carrito_detalle WHERE carrito_id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, carrito_id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public float getMontoTotalDetalle(int carrito_detalle_id) {
        String sql = "SELECT SUM(precio * cantidad) AS monto FROM carrito_detalle WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, carrito_detalle_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getFloat("monto");
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean exist(int carrito_detalle_id) {
        String sql = "SELECT * FROM carrito_detalle WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, carrito_detalle_id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }

    public int getCarritoIdByDetalleId(int carrito_detalle_id) {
        String sql = "SELECT carrito_id FROM carrito_detalle WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, carrito_detalle_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("carrito_id");
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public LinkedList<String> getCarritoDetalle(int carrito_id) {
        String sql = "SELECT cantidad, precio, producto_id FROM carrito_detalle WHERE carrito_id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, carrito_id);
            ResultSet rs = ps.executeQuery();
            LinkedList<String> carritoDetalle = new LinkedList<String>();
            while (rs.next()) {
                carritoDetalle.add(rs.getInt("cantidad") + "," + rs.getFloat("precio") + ","
                        + rs.getInt("producto_id"));
            }
            return carritoDetalle;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
