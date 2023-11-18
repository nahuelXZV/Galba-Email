package Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Servicios.ConexionDB;

public class IngresoDato {
    private final ConexionDB conexion;

    private int id;
    private String fecha;
    private String hora;
    private String motivo;

    // Constructores
    public IngresoDato() {
        conexion = new ConexionDB();
    }

    public IngresoDato(String motivo) {
        conexion = new ConexionDB();
        this.fecha = new java.util.Date().toString().substring(8, 10) + "/"
                + new java.util.Date().toString().substring(4, 7) + "/"
                + new java.util.Date().toString().substring(24, 29);
        this.hora = new java.util.Date().toString().substring(11, 19);
        this.hora = (Integer.parseInt(this.hora.substring(0, 2)) - 1) + this.hora.substring(2, 8);
        this.motivo = motivo;
    }

    public IngresoDato(int id, String motivo) {
        conexion = new ConexionDB();
        this.id = id;
        this.motivo = motivo;
    }

    // Funciones
    public boolean create() {
        String sql = "INSERT INTO ingreso (fecha, hora, motivo) VALUES (?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.fecha);
            ps.setString(2, this.hora);
            ps.setString(3, this.motivo);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean update(int ingreso_id, String motivo) {
        String sql = "UPDATE ingreso SET motivo = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, motivo);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM ingreso WHERE id = ?";
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
