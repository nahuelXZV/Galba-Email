package Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;

import Servicios.ConexionDB;

public class PedidoDato {

    private final ConexionDB conexion;
    private final CarritoDato carritoDato;
    private final PedidoDetalleDato pedidoDetalleDato;

    private int id;
    private String fecha;
    private String hora;
    private Float monto_total;
    private String estado;
    private int usuario_id;
    private int carrito_id;

    // Constructores
    public PedidoDato() {
        conexion = new ConexionDB();
        carritoDato = new CarritoDato();
        pedidoDetalleDato = new PedidoDetalleDato();
    }

    public PedidoDato(int usuario_id) {
        conexion = new ConexionDB();
        carritoDato = new CarritoDato(usuario_id);
        pedidoDetalleDato = new PedidoDetalleDato();
        this.carrito_id = carritoDato.getIdCarritoByUser(usuario_id);
        this.fecha = new java.util.Date().toString().substring(8, 10) + "/"
                + new java.util.Date().toString().substring(4, 7) + "/"
                + new java.util.Date().toString().substring(24, 29);
        this.hora = new java.util.Date().toString().substring(11, 19);
        this.hora = (Integer.parseInt(this.hora.substring(0, 2)) - 1) + this.hora.substring(2, 8);
        this.usuario_id = usuario_id;
        this.monto_total = carritoDato.getMontoTotal(this.carrito_id);
        this.estado = "Pendiente";
    }

    // Funciones
    public boolean create() {
        System.out.println("Creando pedido...");
        String sql = "INSERT INTO pedido (fecha, hora, monto_total, estado, usuario_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.fecha);
            ps.setString(2, this.hora);
            ps.setFloat(3, this.monto_total);
            ps.setString(4, this.estado);
            ps.setInt(5, this.usuario_id);
            int rowsAffected = ps.executeUpdate();
            int pedido_id = getLastPedido(this.usuario_id);
            if (rowsAffected > 0 && pedidoDetalleDato.createByCarrito(this.carrito_id, pedido_id)) {
                carritoDato.delete(this.carrito_id);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public int getLastPedido(int usuario_id) {
        String sql = "SELECT id FROM pedido WHERE usuario_id = ? ORDER BY id DESC LIMIT 1";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuario_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public Boolean exist(int pedido_id, int usuario_id) {
        String sql = "SELECT * FROM pedido WHERE id = ? and usuario_id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, pedido_id);
            ps.setInt(2, usuario_id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public String getQR(int pedido_id, String nit) {
        try {
            java.sql.Statement consulta;
            ResultSet resultado = null;
            String query = "";
            query = "SELECT id, fecha, hora, monto_total FROM pedido WHERE id = " + pedido_id;
            Connection con = conexion.connect();
            consulta = con.createStatement();
            resultado = consulta.executeQuery(query);
            ResultSetMetaData rsmd = resultado.getMetaData();
            while (resultado.next()) {
                this.id = resultado.getInt(1);
                this.fecha = resultado.getString(2);
                this.hora = resultado.getString(3);
                this.monto_total = resultado.getFloat(4);
            }
            consulta.close();
            con.close();
            String qr = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" + nit;
            String response = "<h1>Gracias por su compra</h1>\n" + "<h2>Detalle del pedido</h2>\n" + "ID: " + this.id
                    + ".<br>"
                    + "Fecha: " + this.fecha + ".<br>" + "Hora: " + this.hora + ".<br>" + "Monto Total: "
                    + this.monto_total + " Bs. <br>" + "<h2>Lista de productos</h2>\n"
                    + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n" + "\n"
                    + "  <tr>\n" + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">IMAGEN</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">NOMBRE</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Cantidad</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">PRECIO</th>\n"
                    + "\n";
            query = "SELECT pedido_detalle.id, producto.imagen, producto.nombre, pedido_detalle.cantidad, pedido_detalle.precio FROM  producto, pedido_detalle WHERE pedido_detalle.pedido_id = "
                    + this.id + " AND pedido_detalle.producto_id = producto.id";
            con = conexion.connect();
            consulta = con.createStatement();
            resultado = consulta.executeQuery(query);
            rsmd = resultado.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();
            while (resultado.next()) {
                response = response + "  <tr>\n" + "\n";
                for (int i = 0; i < cantidadColumnas; i++) {
                    if (i == 1) {
                        response = response
                                + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\"><img src=\""
                                + resultado.getString(i + 1) + "\" width=\"100\" height=\"100\"></td>\n" + "\n";
                    } else {
                        response = response
                                + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">"
                                + resultado.getString(i + 1) + "</td>\n"
                                + "\n";
                    }
                }
                response = response + "  </tr>\n" + "\n";
            }
            response = response + "\n" + "</table> <br/>";
            response = response + "<h2>Para completar el pago escanee el codigo QR</h2>\n";
            response = response + "<img src=\"" + qr + "\" width=\"150\" height=\"150\">";
            consulta.close();
            con.close();
            return response;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public String getOne(int id) {
        String tabla = "";
        try {
            java.sql.Statement consulta;
            ResultSet resultado = null;
            String query = "";
            query = "SELECT id, fecha, hora, monto_total FROM pedido WHERE id = " + id;
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
            tabla = "<h1>Detalle del pedido</h1>\n"
                    + "ID: " + this.id + ".<br>"
                    + "Fecha: " + this.fecha + ".<br>"
                    + "Hora: " + this.hora + ".<br>"
                    + "Monto Total: " + this.monto_total + " Bs. <br>"
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
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Cantidad</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">PRECIO</th>\n"
                    + "\n";

            query = "SELECT pedido_detalle.id, producto.imagen, producto.nombre, pedido_detalle.cantidad, pedido_detalle.precio FROM  producto, pedido_detalle WHERE pedido_detalle.pedido_id = "
                    + this.id + " AND pedido_detalle.producto_id = producto.id";
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

    public String getAll(LinkedList<String> params, int usuario_id) {
        String tabla = "";
        try {
            java.sql.Statement consulta;
            ResultSet resultado = null;
            tabla = "<h1>Lista de pedidos</h1>\n"
                    + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                    + "\n"
                    + "  <tr>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Fecha</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Hora</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Monto Total</th>\n"
                    + "\n"
                    + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Estado</th>\n"
                    + "\n";
            String query = "";
            if (params.size() == 0)
                query = "SELECT id, fecha, hora, monto_total, estado FROM pedido WHERE usuario_id = " + usuario_id;
            else
                query = "SELECT id, fecha, hora, monto_total, estado FROM pedido WHERE usuario_id = " + usuario_id
                        + " AND " + params.get(0) + " ILIKE '%" + params.get(1) + "%'";
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
}
