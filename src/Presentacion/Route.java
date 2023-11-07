package Presentacion;

import java.util.LinkedList;

import Negocio.UsuarioNegocio;
import Servicios.SmtpService;
import Utils.EmailHandler;
import Utils.Help;

public class Route {

    public void routes(EmailHandler emailHandler) {
        SmtpService smtp = new SmtpService();
        UsuarioNegocio usuarioNegocio = new UsuarioNegocio();

        String comando = emailHandler.getComando();
        LinkedList<String> parametros = emailHandler.getParametros();
        switch (comando) {
            case "HELP":
                smtp.sendEmail(Help.getHelp(), emailHandler.remitente);
                break;
            case "LISTUSER":
                usuarioNegocio.getAll(parametros);
                break;
            case "ADDUSER":
                usuarioNegocio.createPersonal(parametros);
                break;
            case "ADDCLIENT":
                usuarioNegocio.createCliente(parametros);
                break;
            case "EDITUSER":
                usuarioNegocio.updatePersonal(parametros);
                break;
            case "EDITCLIENT":
                usuarioNegocio.updateCliente(parametros);
                break;
            case "DELUSER":
                usuarioNegocio.delete(parametros.get(0));
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
