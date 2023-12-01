package Presentacion;

import java.util.LinkedList;

import Negocio.CarritoDetalleNegocio;
import Negocio.CarritoNegocio;
import Negocio.PedidoNegocio;
import Negocio.ProductoNegocio;
import Negocio.UsuarioNegocio;
import Utils.EmailHandler;

public class Route {

    public String routes(EmailHandler emailHandler) {
        UsuarioNegocio usuarioNegocio = new UsuarioNegocio();
        CarritoNegocio carritoNegocio = new CarritoNegocio();
        CarritoDetalleNegocio carritoDetalleNegocio = new CarritoDetalleNegocio();
        ProductoNegocio productoNegocio = new ProductoNegocio();
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
            if (parametros.size() != 1)
                return "Debe ingresar el id del carrito a eliminar.";
            return carritoNegocio.delete(parametros.get(0));
        }

        /* Gestionar Carrito Detalle */
        if (comando.equals("LISTCARPROD")) {
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
        Boolean isEmpleado = usuarioNegocio.validateRol(emailHandler.remitente, "isPersonal", "true");

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
        /* Gestionar usuario */
        if (comando.equals("EDITUSER") && isEmpleado) {
            return usuarioNegocio.updatePersonal(parametros);
        }

        // ADMINISTRADOR
        Boolean isAdministrador = usuarioNegocio.validateRol(emailHandler.remitente, "isAdministrador", "true");
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
