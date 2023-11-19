package Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import Servicios.ConexionDB;

public class SalidaDetalleDato {
    private final ConexionDB conexion;

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
        String sql = "INSERT INTO ingreso_detalle (cantidad, ingreso_id, producto_id) VALUES (?, ?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, this.cantidad);
            ps.setInt(2, this.ingreso_id);
            ps.setInt(3, this.producto_id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean update(int ingreso_id, int cantidad) {
        int cantidad_anterior = getCantidad(ingreso_id);
        String sql = "UPDATE carrito SET monto_total = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setFloat(1, cantidad_anterior + cantidad);
            ps.setInt(2, ingreso_id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM ingreso_detalle WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
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
}