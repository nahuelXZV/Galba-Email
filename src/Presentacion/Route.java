package Presentacion;

import java.util.LinkedList;

import Negocio.UsuarioNegocio;
import Servicios.SmtpService;
import Utils.EmailHandler;

public class Route {

    public void routes(EmailHandler emailHandler) {
        SmtpService smtp = new SmtpService();
        UsuarioNegocio usuarioNegocio = new UsuarioNegocio();
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
            default:
                smtp.sendEmailError(
                        "Error",
                        emailHandler.remitente,
                        "Comando no reconocido, consulte el comando HELP para obtener ayuda.");
                break;
        }
    }
}
