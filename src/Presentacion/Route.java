package Presentacion;

import java.util.LinkedList;

import Negocio.CarritoDetalleNegocio;
import Negocio.CarritoNegocio;
import Negocio.CompraDetalleNegocio;
import Negocio.CompraNegocio;
import Negocio.IngresoDetalleNegocio;
import Negocio.IngresoNegocio;
import Negocio.PedidoNegocio;
import Negocio.ProductoNegocio;
import Negocio.ProveedorNegocio;
import Negocio.SalidaDetalleNegocio;
import Negocio.SalidaNegocio;
import Negocio.UsuarioNegocio;
import Utils.EmailHandler;

public class Route {

    public String routes(EmailHandler emailHandler) {
        UsuarioNegocio usuarioNegocio = new UsuarioNegocio();
        CarritoNegocio carritoNegocio = new CarritoNegocio();
        CarritoDetalleNegocio carritoDetalleNegocio = new CarritoDetalleNegocio();
        CompraNegocio compraNegocio = new CompraNegocio();
        CompraDetalleNegocio compraDetalleNegocio = new CompraDetalleNegocio();
        IngresoNegocio ingresoNegocio = new IngresoNegocio();
        IngresoDetalleNegocio ingresoDetalleNegocio = new IngresoDetalleNegocio();
        SalidaNegocio salidaNegocio = new SalidaNegocio();
        SalidaDetalleNegocio salidaDetalleNegocio = new SalidaDetalleNegocio();
        ProductoNegocio productoNegocio = new ProductoNegocio();
        ProveedorNegocio proveedorNegocio = new ProveedorNegocio();
        PedidoNegocio pedidoNegocio = new PedidoNegocio();

        String comando = emailHandler.getComando();
        LinkedList<String> parametros = emailHandler.getParametros();

        System.out.println("Comando: " + comando);
        System.out.println("Parametros: " + parametros);

        // PUBLICO
        /* Gestionar USUARIO */
        if (comando.equals("ADDCLIENT")) {
            return usuarioNegocio.createCliente(parametros, emailHandler.remitente);
        }

        if (comando.equals("EDITCLIENT")) {
            return usuarioNegocio.updateCliente(parametros, emailHandler.remitente);
        }

        if (comando.equals("SHOWPROFILE")) {
            return usuarioNegocio.show(emailHandler.remitente);
        }

        if (comando.equals("DELUSER")) {
            return usuarioNegocio.delete(emailHandler.remitente);
        }

        /* Gestionar Carrito */
        if (comando.equals("LISTCAR")) {
            return carritoNegocio.getAll(emailHandler.remitente);
        }

        if (comando.equals("NEWCAR")) {
            return carritoNegocio.create(emailHandler.remitente);
        }

        if (comando.equals("DELCAR")) {
            return carritoNegocio.delete(parametros.get(0));
        }

        /* Gestionar Carrito Detalle */
        if (comando.equals("ADDCARPROD")) {
            return carritoDetalleNegocio.create(parametros, emailHandler.remitente);
        }

        if (comando.equals("DELCARPROD")) {
            return carritoDetalleNegocio.delete(parametros.get(0));
        }

        /* Gestionar Pedido */
        if (comando.equals("CONFPEDIDO")) {
            return pedidoNegocio.create(emailHandler.remitente, parametros.get(0));
        }

        if (comando.equals("LISTPED")) {
            return pedidoNegocio.getAll(parametros, emailHandler.remitente);
        }

        if (comando.equals("SHOWPED")) {
            return pedidoNegocio.getOne(parametros.get(0), emailHandler.remitente);
        }

        /* Ver productos */
        if (comando.equals("LISTPROD")) {
            return productoNegocio.getAll(parametros);
        }

        // EMPLEADOS
        Boolean isEmpleado = usuarioNegocio.isPersonal(emailHandler.remitente);

        /* Gestionar productos */
        if (comando.equals("ADDPROD") && isEmpleado) {
            return productoNegocio.createProducto(parametros);
        }

        if (comando.equals("EDITPROD") && isEmpleado) {
            return productoNegocio.updateProducto(parametros);
        }

        if (comando.equals("DELPROD") && isEmpleado) {
            return productoNegocio.delete(parametros.get(0));
        }

        /* Gestionar proveedor */
        if (comando.equals("ADDPROV") && isEmpleado) {
            return proveedorNegocio.createProveedor(parametros);
        }

        if (comando.equals("EDITPROV") && isEmpleado) {
            return proveedorNegocio.updateProveedor(parametros);
        }

        if (comando.equals("DELPROV") && isEmpleado) {
            return proveedorNegocio.delete(parametros.get(0));
        }

        /* Gestionar Ingreso */
        if (comando.equals("LISTING") && isEmpleado) {
            return ingresoNegocio.getAllIng(parametros);
        }

        if (comando.equals("DETING") && isEmpleado) {
            return ingresoNegocio.getAll(parametros.get(0));
        }

        if (comando.equals("NEWING") && isEmpleado) {
            return ingresoNegocio.create(parametros.get(0));
        }

        if (comando.equals("DELING") && isEmpleado) {
            return ingresoNegocio.delete(parametros.get(0));
        }

        /* Gestionar Ingreso Detalle */
        if (comando.equals("LISTINGPROD") && isEmpleado) {
            return ingresoDetalleNegocio.create(parametros);
        }

        if (comando.equals("DELINGPROD") && isEmpleado) {
            return ingresoDetalleNegocio.delete(parametros.get(0));
        }
        /* Gestionar Salida */
        if (comando.equals("LISTSAL") && isEmpleado) {
            return salidaNegocio.getAllSal(parametros);
        }

        if (comando.equals("DETSAL") && isEmpleado) {
            return salidaNegocio.getAll(parametros.get(0));
        }

        if (comando.equals("NEWSAL") && isEmpleado) {
            return salidaNegocio.create(parametros.get(0));
        }

        if (comando.equals("DELSAL") && isEmpleado) {
            return salidaNegocio.delete(parametros.get(0));
        }

        /* Gestionar Ingreso Detalle */
        if (comando.equals("LISTSALPROD") && isEmpleado) {
            return salidaDetalleNegocio.create(parametros);
        }

        if (comando.equals("DELSALPROD") && isEmpleado) {
            return salidaDetalleNegocio.delete(parametros.get(0));
        }

        /* Gestionar Compra */
        if (comando.equals("LISTCOM") && isEmpleado) {
            return compraNegocio.getAllCom(parametros);

        }
        if (comando.equals("DETCOM") && isEmpleado) {
            return compraNegocio.getAll(parametros.get(0));
        }

        if (comando.equals("NEWCOM") && isEmpleado) {
            return compraNegocio.create(parametros.get(0));
        }

        if (comando.equals("DELCOM") && isEmpleado) {
            return compraNegocio.delete(parametros.get(0));
        }

        /* Gestionar Ingreso Detalle */
        if (comando.equals("LISTCOMPROD") && isEmpleado) {
            return compraDetalleNegocio.create(parametros);
        }

        if (comando.equals("DELCOMPROD") && isEmpleado) {
            return compraDetalleNegocio.delete(parametros.get(0));
        }

        /* Gestionar usuario */
        if (comando.equals("EDITUSER") && isEmpleado) {
            return usuarioNegocio.updatePersonal(parametros);
        }

        // ADMINISTRADOR
        Boolean isAdministrador = usuarioNegocio.isAdministrador(emailHandler.remitente);
        /* Gestionar usuario */
        if (comando.equals("LISTUSER") && isAdministrador) {
            return usuarioNegocio.getAll(parametros);
        }

        if (comando.equals("ADDUSER") && isAdministrador) {
            return usuarioNegocio.createPersonal(parametros);
        }

        return "Comando no reconocido, consulte el comando HELP para obtener ayuda.";
    }
}
