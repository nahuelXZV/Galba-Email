package Presentacion;

import java.util.LinkedList;

import Negocio.CarritoDetalleNegocio;
import Negocio.CarritoNegocio;
import Negocio.UsuarioNegocio;
import Negocio.ProductoNegocio;
import Servicios.SmtpService;
import Utils.EmailHandler;

public class Route {

    public void routes(EmailHandler emailHandler) {
        SmtpService smtp = new SmtpService();
        UsuarioNegocio usuarioNegocio = new UsuarioNegocio();
        ProductoNegocio productoNegocio = new ProductoNegocio();
        CarritoNegocio carritoNegocio = new CarritoNegocio();
        CarritoDetalleNegocio carritoDetalleNegocio = new CarritoDetalleNegocio();
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
                response = carritoDetalleNegocio.create(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "DELCARPROD":
                response = carritoDetalleNegocio.delete(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;

            // Producto
            case "LISTPRODUCTO":
                response = productoNegocio.getAll(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "ADDPRODUCTO":
                response = productoNegocio.createProducto(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "EDITPRODUCTO":
                response = productoNegocio.updateProducto(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "DELPRODUCTO":
                response = productoNegocio.delete(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            default:
                smtp.sendEmailError(
                        "Error",
                        emailHandler.remitente,
                        "Comando no reconocido, consulte el comando HELP para obtener ayuda.");
                break;
        }
    }
}
