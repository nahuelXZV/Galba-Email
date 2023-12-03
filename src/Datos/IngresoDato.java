package Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;

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
        IngresoDetalleDato ingresoDetalleDato = new IngresoDetalleDato();
        if (!ingresoDetalleDato.deleteByingreso(id)) {
            return false;
        }
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

    public boolean exist(int id) {
        String sql = "SELECT * FROM ingreso WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public String getAllIng(LinkedList<String> params) {
        String tabla = "";
        try {
            java.sql.Statement consulta;
            ResultSet resultado = null;
            tabla = "<h1>Lista de ingresos</h1>\n"
                    + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                    + "\n"
                    + "  <tr>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">FECHA</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">HORA</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">MOTIVO</th>\n"
                    + "\n";
            String query = "";
            if (params.size() == 0)
                query = "SELECT id, fecha, hora, motivo FROM ingreso";
            else
                query = "SELECT id, fecha, hora, motivo FROM ingreso WHERE "
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

    public String getAll(int id) {
        String tabla = "";
        try {
            java.sql.Statement consulta;
            ResultSet resultado = null;
            String query = "";
            query = "SELECT id, fecha, hora, motivo FROM ingreso WHERE id = " + id;
            Connection con = conexion.connect();
            consulta = con.createStatement();
            resultado = consulta.executeQuery(query);
            ResultSetMetaData rsmd = resultado.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();
            while (resultado.next()) {
                this.id = resultado.getInt(1);
                this.fecha = resultado.getString(2);
                this.hora = resultado.getString(3);
                this.motivo = resultado.getString(4);
            }
            tabla = "<h1>Detalle del ingreso</h1>\n"
                    + "ID: " + this.id + ".<br>"
                    + "Fecha: " + this.fecha + ".<br>"
                    + "Hora: " + this.hora + ".<br>"
                    + "Motivo: " + this.motivo + "Bs. <br>"
                    + "<h2>Lista de productos</h2>\n"
                    + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                    + "\n"
                    + "  <tr>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">IMAGEN</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">PRODUCTO</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CANTIDAD</th>\n"
                    + "\n";

            query = "SELECT ingreso_detalle.id, producto.imagen, producto.nombre, ingreso_detalle.cantidad FROM  producto, ingreso_detalle WHERE ingreso_detalle.ingreso_id = "
                    + this.id + " AND ingreso_detalle.producto_id = producto.id";
            con = conexion.connect();
            consulta = con.createStatement();
            resultado = consulta.executeQuery(query);
            rsmd = resultado.getMetaData();
            cantidadColumnas = rsmd.getColumnCount();
            while (resultado.next()) {
                tabla = tabla + "  <tr>\n" + "\n";
                for (int i = 0; i < cantidadColumnas; i++) {
                    if (i == 1) {
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
