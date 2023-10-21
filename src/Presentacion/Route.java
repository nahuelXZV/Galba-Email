package Presentacion;

import java.util.LinkedList;

import Negocio.RolNegocio;
import Negocio.UsuarioNegocio;
import Servicios.SmtpService;
import Utils.EmailHandler;
import Utils.Help;

public class Route {

    public void routes(EmailHandler emailHandler) {
        SmtpService smtp = new SmtpService();
        UsuarioNegocio usuarioNegocio = new UsuarioNegocio();
        RolNegocio rolNegocio = new RolNegocio();

        String comando = emailHandler.getComando();
        LinkedList<String> parametros = emailHandler.getParametros();
        switch (comando) {
            case "HELP":
                smtp.sendEmail(Help.getHelp(), emailHandler.remitente);
                break;
            case "LISTROL":
                rolNegocio.getAll(parametros);
                break;
            case "ADDROL":
                rolNegocio.create(parametros);
                break;
            case "EDITROL":
                rolNegocio.update(parametros);
                break;
            case "DELROL":
                rolNegocio.delete(parametros.get(0));
                break;
            case "LISTUSER":
                usuarioNegocio.getAll(parametros);
                break;
            case "ADDUSER":
                usuarioNegocio.create(parametros);
                break;
            case "EDITUSER":
                usuarioNegocio.update(parametros);
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
