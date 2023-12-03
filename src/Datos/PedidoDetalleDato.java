package Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

import Servicios.ConexionDB;

public class PedidoDetalleDato {
    private final ConexionDB conexion;
    private final CarritoDetalleDato carritoDetalleDato;
    private ProductoDato productoDato;

    private int cantidad;
    private float precio;
    private int pedido_id;
    private int producto_id;

    // Constructores
    public PedidoDetalleDato() {
        conexion = new ConexionDB();
        carritoDetalleDato = new CarritoDetalleDato();
        productoDato = new ProductoDato();
    }

    public PedidoDetalleDato(int cantidad, float precio, int pedido_id, int producto_id) {
        conexion = new ConexionDB();
        carritoDetalleDato = new CarritoDetalleDato();
        this.cantidad = cantidad;
        this.precio = precio;
        this.pedido_id = pedido_id;
        this.producto_id = producto_id;
    }

    // Funciones
    public boolean create() {
        String sql = "INSERT INTO pedido_detalle (cantidad, precio, pedido_id, producto_id) VALUES (?, ?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, this.cantidad);
            ps.setFloat(2, this.precio);
            ps.setInt(3, this.pedido_id);
            ps.setInt(4, this.producto_id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean createByCarrito(int carrito_id, int pedido_id) {
        try {
            this.pedido_id = pedido_id;
            LinkedList<String> carritoDetalle = carritoDetalleDato.getCarritoDetalle(carrito_id);
            for (int i = 0; i < carritoDetalle.size(); i++) {
                String[] detalle = carritoDetalle.get(i).split(",");
                this.cantidad = Integer.parseInt(detalle[0]);
                this.precio = Float.parseFloat(detalle[1]);
                this.producto_id = Integer.parseInt(detalle[2]);
                boolean isCreated = this.create();
                boolean isUpdatedProducto = productoDato.updateCantidad(this.producto_id, -this.cantidad);
                if (!isCreated || !isUpdatedProducto) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
