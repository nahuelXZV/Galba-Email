package Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    }

    public CarritoDetalleDato(int cantidad, float precio, int carrito_id, int producto_id) {
        conexion = new ConexionDB();
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
            if (rowsAffected > 0) {
                carritoDato.update(this.carrito_id, this.precio * this.cantidad);
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
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
