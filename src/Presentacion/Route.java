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
        switch (comando) {
            case "LISTUSER":
                response = usuarioNegocio.getAll(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "ADDUSER":
                response = usuarioNegocio.createPersonal(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "ADDCLIENT":
                response = usuarioNegocio.createCliente(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "EDITUSER":
                response = usuarioNegocio.updatePersonal(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "EDITCLIENT":
                response = usuarioNegocio.updateCliente(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "DELUSER":
                response = usuarioNegocio.delete(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;

            case "LISTCAR":
                response = carritoNegocio.getAll(emailHandler.remitente);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "NEWCAR":
                response = carritoNegocio.create(emailHandler.remitente);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "DELCAR":
                response = carritoNegocio.delete(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;

            case "ADDCARPROD":
                response = carritoDetalleNegocio.create(parametros, emailHandler.remitente);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "DELCARPROD":
                response = carritoDetalleNegocio.delete(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;

            case "CONFPEDIDO":
                response = pedidoNegocio.create(emailHandler.remitente, parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "LISTPED":
                response = pedidoNegocio.getAll(parametros, emailHandler.remitente);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "SHOWPED":
                response = pedidoNegocio.getOne(parametros.get(0), emailHandler.remitente);
                smtp.sendEmail(response, emailHandler.remitente);
                break;

            // Producto
            case "LISTPROD":
                response = productoNegocio.getAll(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "ADDPROD":
                response = productoNegocio.createProducto(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "EDITPROD":
                response = productoNegocio.updateProducto(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "DELPROD":
                response = productoNegocio.delete(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            default:
                smtp.sendEmailError(
                        "Error",
                        "Comando no reconocido, consulte el comando HELP para obtener ayuda.",
                        emailHandler.remitente);
                break;
        }
    }
}
