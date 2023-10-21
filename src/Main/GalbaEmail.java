package Main;

import Presentacion.Auth;
import Presentacion.EmailHandler;
import Presentacion.Route;
import Servicios.PopService;
import Servicios.SmtpService;

public class GalbaEmail {

    public static void main(String[] args) {
        PopService pop = new PopService();
        SmtpService smtp = new SmtpService();
        Route route = new Route();

        int cantMails = pop.getCantidadEmails();
        while (true) {
            int newCantsMails = pop.getCantidadEmails();
            System.out.println("Escuchando EMAILS...");
            if (cantMails != newCantsMails) {
                cantMails = newCantsMails;
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
                    System.out.println("***********************************");
                } catch (Exception e) {
                    System.out.println("Error al obtener emails");
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                System.out.println("Error en el servidor.");
            }
        }
    }

}
