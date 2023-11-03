package Main;

import Presentacion.Route;
import Servicios.PopService;
import Servicios.SmtpService;
import Utils.Auth;
import Utils.EmailHandler;

public class GalbaEmail {

    public static void main(String[] args) {
        PopService pop = new PopService();
        SmtpService smtp = new SmtpService();
        Route route = new Route();
        System.out.println("Iniciando servidor...");
        int cantMails = pop.getCantidadEmails();
        System.out.println("Cantidad de emails: " + cantMails);
        while (true) {
            int newCantsMails = pop.getCantidadEmails();
            if (cantMails != newCantsMails) {
                cantMails = newCantsMails;
                System.out.println("*************NEW EMAIL**********************");
                try {
                    String email = pop.getMail();
                    EmailHandler emailHandler = new EmailHandler(email);
                    if (!emailHandler.isValidate()) {
                        smtp.sendEmailError("Error", emailHandler.remitente, emailHandler.messageError);
                        continue;
                    }
                    if (!Auth.auth(emailHandler.remitente)) {
                        smtp.sendEmailError("Error", emailHandler.remitente, "Remitente no autorizado");
                        continue;
                    }
                    route.routes(emailHandler);
                } catch (Exception e) {
                    System.out.println("Error al obtener emails");
                }
                System.out.println("**********************************************");
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                System.out.println("Error en el servidor.");
            }
        }
    }

}
