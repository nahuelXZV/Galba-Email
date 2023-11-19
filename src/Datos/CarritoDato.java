package Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import Servicios.ConexionDB;

public class CarritoDato {
    private final ConexionDB conexion;

    private int id;
    private float monto_total;
    private String fecha;
    private String hora;
    private int usuario_id;

    // Constructores
    public CarritoDato() {
        conexion = new ConexionDB();
    }

    public CarritoDato(int usuario_id) {
        conexion = new ConexionDB();
        this.fecha = new java.util.Date().toString().substring(8, 10) + "/"
                + new java.util.Date().toString().substring(4, 7) + "/"
                + new java.util.Date().toString().substring(24, 29);
        this.hora = new java.util.Date().toString().substring(11, 19);
        this.hora = (Integer.parseInt(this.hora.substring(0, 2)) - 1) + this.hora.substring(2, 8);
        this.usuario_id = usuario_id;
        this.monto_total = 0;
    }

    public CarritoDato(int id, float monto_total) {
        conexion = new ConexionDB();
        this.id = id;
    }

    // Funciones
    public boolean create() {
        String sql = "INSERT INTO carrito (monto_total , fecha, hora, usuario_id) VALUES (?, ?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setFloat(1, this.monto_total);
            ps.setString(2, this.fecha);
            ps.setString(3, this.hora);
            ps.setInt(4, this.usuario_id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean update(int carrito_id, Float monto) {
        String sql = "UPDATE carrito SET monto_total = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            Float monto_anterior = getMontoTotal(carrito_id);
            Float monto_total = monto_anterior + monto;
            ps.setFloat(1, monto_total);
            ps.setInt(2, carrito_id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        CarritoDetalleDato carritoDetalleDato = new CarritoDetalleDato();
        if (!carritoDetalleDato.deleteBycarrito(id)) {
            return false;
        }
        String sql = "DELETE FROM carrito WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean existByUser(int usuario_id) {
        String sql = "SELECT * FROM carrito WHERE usuario_id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuario_id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public int getIdCarritoByUser(int usuario_id) {
        String sql = "SELECT id FROM carrito WHERE usuario_id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuario_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public boolean exist(int id) {
        String sql = "SELECT * FROM carrito WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public float getMontoTotal(int id) {
        float monto_total = 0;
        String sql = "SELECT monto_total FROM carrito WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet resultado = null;
            resultado = ps.executeQuery();
            while (resultado.next()) {
                monto_total = resultado.getFloat(1);
            }
            return monto_total;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return monto_total;
    }

    public String getAll(int id) {
        String tabla = "";
        try {
            java.sql.Statement consulta;
            ResultSet resultado = null;
            String query = "";
            query = "SELECT id, fecha, hora, monto_total FROM carrito WHERE usuario_id = " + id;
            Connection con = conexion.connect();
            consulta = con.createStatement();
            resultado = consulta.executeQuery(query);
            ResultSetMetaData rsmd = resultado.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();
            while (resultado.next()) {
                this.id = resultado.getInt(1);
                this.fecha = resultado.getString(2);
                this.hora = resultado.getString(3);
                this.monto_total = resultado.getFloat(4);
            }
            tabla = "<h1>Detalle del carrito</h1>\n"
                    + "ID: " + this.id + ".<br>"
                    + "Fecha: " + this.fecha + ".<br>"
                    + "Hora: " + this.hora + ".<br>"
                    + "Monto Total: " + this.monto_total + "Bs. <br>"
                    + "<h2>Lista de productos</h2>\n"
                    + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                    + "\n"
                    + "  <tr>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">IMAGEN</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">NOMBRE</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CANTIDAD</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">PRECIO</th>\n"
                    + "\n";

            query = "SELECT carrito_detalle.id, producto.imagen, producto.nombre, carrito_detalle.cantidad, carrito_detalle.precio FROM  producto, carrito_detalle WHERE carrito_detalle.carrito_id = "
                    + this.id + " AND carrito_detalle.producto_id = producto.id";
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
