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
    private int salida_id;
    private int producto_id;

    public SalidaDetalleDato() {
        conexion = new ConexionDB();
    }

    public SalidaDetalleDato(int cantidad, int salida_id, int producto_id) {
        conexion = new ConexionDB();
        this.cantidad = cantidad;
        this.salida_id = salida_id;
        this.producto_id = producto_id;
    }

    // Funciones
    public boolean create() {
        String sql = "INSERT INTO salida_detalle (cantidad, salida_id, producto_id) VALUES (?, ?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, this.cantidad);
            ps.setInt(2, this.salida_id);
            ps.setInt(3, this.producto_id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean update(int id, int cantidad) {
        int cantidad_anterior = getCantidad(salida_id);
        String sql = "UPDATE salida_detalle SET cantidad = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cantidad_anterior - cantidad);
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM salida_detalle WHERE id = ?";
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
            query = "SELECT cantidad FROM salida_detalle WHERE id = " + id;
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