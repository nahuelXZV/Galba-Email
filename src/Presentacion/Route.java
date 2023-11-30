package Presentacion;

import java.util.LinkedList;

import Negocio.CarritoDetalleNegocio;
import Negocio.CarritoNegocio;
import Negocio.PedidoNegocio;
import Negocio.ProductoNegocio;
import Negocio.UsuarioNegocio;
import Servicios.SmtpService;
import Utils.EmailHandler;

public class Route {

    public void routes(EmailHandler emailHandler) {
        SmtpService smtp = new SmtpService();
        UsuarioNegocio usuarioNegocio = new UsuarioNegocio();
        CarritoNegocio carritoNegocio = new CarritoNegocio();
        CarritoDetalleNegocio carritoDetalleNegocio = new CarritoDetalleNegocio();
        ProductoNegocio productoNegocio = new ProductoNegocio();
        PedidoNegocio pedidoNegocio = new PedidoNegocio();

        String response = "";
        String comando = emailHandler.getComando();
        LinkedList<String> parametros = emailHandler.getParametros();

        System.out.println("Comando: " + comando);
        System.out.println("Parametros: " + parametros);

        // PUBLICO
        /* Gestionar USUARIO */
        if (comando.equals("ADDCLIENT")) {
            response = usuarioNegocio.createCliente(parametros);
            smtp.sendEmail(response, emailHandler.remitente);
            return;
        }
        if (comando.equals("EDITCLIENT")) {
            response = usuarioNegocio.updateCliente(parametros);
            smtp.sendEmail(response, emailHandler.remitente);
            return;
        }
        /* Gestionar Carrito */
        if (comando.equals("LISTCAR")) {
            response = carritoNegocio.getAll(emailHandler.remitente);
            smtp.sendEmail(response, emailHandler.remitente);
            return;
        }
        if (comando.equals("NEWCAR")) {
            response = carritoNegocio.create(emailHandler.remitente);
            smtp.sendEmail(response, emailHandler.remitente);
            return;
        }
        if (comando.equals("DELCAR")) {
            response = carritoNegocio.delete(parametros.get(0));
            smtp.sendEmail(response, emailHandler.remitente);
            return;
        }
        /* Gestionar Carrito Detalle */
        if (comando.equals("LISTCARPROD")) {
            response = carritoDetalleNegocio.create(parametros, emailHandler.remitente);
            smtp.sendEmail(response, emailHandler.remitente);
            return;
        }
        if (comando.equals("LISTPROD")) {
            response = carritoDetalleNegocio.delete(parametros.get(0));
            smtp.sendEmail(response, emailHandler.remitente);
            return;
        }
        /* Gestionar Pedido */
        if (comando.equals("CONFPEDIDO")) {
            response = pedidoNegocio.create(emailHandler.remitente, parametros.get(0));
            smtp.sendEmail(response, emailHandler.remitente);
            return;
        }
        if (comando.equals("LISTPED")) {
            response = pedidoNegocio.getAll(parametros, emailHandler.remitente);
            smtp.sendEmail(response, emailHandler.remitente);
            return;
        }
        if (comando.equals("SHOWPED")) {
            response = pedidoNegocio.getOne(parametros.get(0), emailHandler.remitente);
            smtp.sendEmail(response, emailHandler.remitente);
            return;
        }
        /* Ver productos */
        if (comando.equals("LISTPROD")) {
            response = productoNegocio.getAll(parametros);
            smtp.sendEmail(response, emailHandler.remitente);
            return;
        }

        // EMPLEADOS
        Boolean isEmpleado = usuarioNegocio.validateRol(emailHandler.remitente, "isPersonal", "true");

        /* Gestionar productos */
        if (comando.equals("ADDPROD") && isEmpleado) {
            response = productoNegocio.createProducto(parametros);
            smtp.sendEmail(response, emailHandler.remitente);
            return;
        }
        if (comando.equals("EDITPROD") && isEmpleado) {
            response = productoNegocio.updateProducto(parametros);
            smtp.sendEmail(response, emailHandler.remitente);
            return;
        }
        if (comando.equals("DELPROD") && isEmpleado) {
            response = productoNegocio.delete(parametros.get(0));
            smtp.sendEmail(response, emailHandler.remitente);
            return;
        }
        /* Gestionar usuario */
        if (comando.equals("EDITUSER") && isEmpleado) {
            response = usuarioNegocio.updatePersonal(parametros);
            smtp.sendEmail(response, emailHandler.remitente);
            return;
        }

        // ADMINISTRADOR
        Boolean isAdministrador = usuarioNegocio.validateRol(emailHandler.remitente, "isAdministrador", "true");
        /* Gestionar usuario */
        if (comando.equals("LISTUSER") && isAdministrador) {
            response = usuarioNegocio.getAll(parametros);
            smtp.sendEmail(response, emailHandler.remitente);
            return;
        }
        if (comando.equals("ADDUSER") && isAdministrador) {
            response = usuarioNegocio.createPersonal(parametros);
            smtp.sendEmail(response, emailHandler.remitente);
            return;
        }
        if (comando.equals("DELUSER") && isAdministrador) {
            response = usuarioNegocio.delete(parametros.get(0));
            smtp.sendEmail(response, emailHandler.remitente);
            return;
        }

        smtp.sendEmailError(
                "Error",
                "Comando no reconocido, consulte el comando HELP para obtener ayuda.",
                emailHandler.remitente);
        // return;

        // switch (comando) {
        // case "LISTUSER":
        // response = usuarioNegocio.getAll(parametros);
        // smtp.sendEmail(response, emailHandler.remitente);
        // break;
        // case "ADDUSER":
        // response = usuarioNegocio.createPersonal(parametros);
        // smtp.sendEmail(response, emailHandler.remitente);
        // break;
        // case "ADDCLIENT":
        // response = usuarioNegocio.createCliente(parametros);
        // smtp.sendEmail(response, emailHandler.remitente);
        // break;
        // case "EDITUSER":
        // response = usuarioNegocio.updatePersonal(parametros);
        // smtp.sendEmail(response, emailHandler.remitente);
        // break;
        // case "EDITCLIENT":
        // response = usuarioNegocio.updateCliente(parametros);
        // smtp.sendEmail(response, emailHandler.remitente);
        // break;
        // case "DELUSER":
        // response = usuarioNegocio.delete(parametros.get(0));
        // smtp.sendEmail(response, emailHandler.remitente);
        // break;

        // case "LISTCAR":
        // response = carritoNegocio.getAll(emailHandler.remitente);
        // smtp.sendEmail(response, emailHandler.remitente);
        // break;
        // case "NEWCAR":
        // response = carritoNegocio.create(emailHandler.remitente);
        // smtp.sendEmail(response, emailHandler.remitente);
        // break;
        // case "DELCAR":
        // response = carritoNegocio.delete(parametros.get(0));
        // smtp.sendEmail(response, emailHandler.remitente);
        // break;

        // case "ADDCARPROD":
        // response = carritoDetalleNegocio.create(parametros, emailHandler.remitente);
        // smtp.sendEmail(response, emailHandler.remitente);
        // break;
        // case "DELCARPROD":
        // response = carritoDetalleNegocio.delete(parametros.get(0));
        // smtp.sendEmail(response, emailHandler.remitente);
        // break;

        // case "CONFPEDIDO":
        // response = pedidoNegocio.create(emailHandler.remitente, parametros.get(0));
        // smtp.sendEmail(response, emailHandler.remitente);
        // break;
        // case "LISTPED":
        // response = pedidoNegocio.getAll(parametros, emailHandler.remitente);
        // smtp.sendEmail(response, emailHandler.remitente);
        // break;
        // case "SHOWPED":
        // response = pedidoNegocio.getOne(parametros.get(0), emailHandler.remitente);
        // smtp.sendEmail(response, emailHandler.remitente);
        // break;

        // Producto
        // case "LISTPROD":
        // response = productoNegocio.getAll(parametros);
        // smtp.sendEmail(response, emailHandler.remitente);
        // break;
        // case "ADDPROD":
        // response = productoNegocio.createProducto(parametros);
        // smtp.sendEmail(response, emailHandler.remitente);
        // break;
        // case "EDITPROD":
        // response = productoNegocio.updateProducto(parametros);
        // smtp.sendEmail(response, emailHandler.remitente);
        // break;
        // case "DELPROD":
        // response = productoNegocio.delete(parametros.get(0));
        // smtp.sendEmail(response, emailHandler.remitente);
        // break;
        // default:
        // smtp.sendEmailError(
        // "Error",
        // "Comando no reconocido, consulte el comando HELP para obtener ayuda.",
        // emailHandler.remitente);
        // break;
        // }
    }
}
