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
import Servicios.SmtpService;
import Utils.EmailHandler;

public class Route {

    public void routes(EmailHandler emailHandler) {
        SmtpService smtp = new SmtpService();
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

            // Compra
            case "LISTCOM":
                response = compraNegocio.getAllCom(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "DETCOM":
                response = compraNegocio.getAll(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "NEWCOM":
                response = compraNegocio.create(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "DELCOM":
                response = compraNegocio.delete(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;

            case "ADDCOMPROD":
                response = compraDetalleNegocio.create(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "DELCOMPROD":
                response = compraDetalleNegocio.delete(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;

            // Proveedor
            case "LISTPROV":
                response = proveedorNegocio.getAll(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "ADDPROV":
                response = proveedorNegocio.createProveedor(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "EDITPROV":
                response = proveedorNegocio.updateProveedor(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "DELPROV":
                response = proveedorNegocio.delete(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;

            // ------- Inventario ------------
            // Ingreso
            case "LISTING":
                response = ingresoNegocio.getAllIng(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "DETING":
                response = salidaNegocio.getAll(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "NEWING":
                response = ingresoNegocio.create(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "DELING":
                response = ingresoNegocio.delete(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;

            case "ADDINGPROD":
                response = ingresoDetalleNegocio.create(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "DELINGPROD":
                response = ingresoDetalleNegocio.delete(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;

            // Salida
            case "LISTSAL":
                response = salidaNegocio.getAllSal(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "DETSAL":
                response = salidaNegocio.getAll(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "NEWSAL":
                response = salidaNegocio.create(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "DELSAL":
                response = salidaNegocio.delete(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;

            case "ADDSALPROD":
                response = salidaDetalleNegocio.create(parametros);
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            case "DELSALPROD":
                response = salidaDetalleNegocio.delete(parametros.get(0));
                smtp.sendEmail(response, emailHandler.remitente);
                break;
            // ---------- Fin Inventario --------------------
            default:
                smtp.sendEmailError(
                        "Error",
                        "Comando no reconocido, consulte el comando HELP para obtener ayuda.",
                        emailHandler.remitente);
                break;
        }
    }
}
