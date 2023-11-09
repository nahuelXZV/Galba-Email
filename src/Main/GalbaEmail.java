package Main;

import Presentacion.Route;
import Servicios.PopService;
import Servicios.SmtpService;
import Utils.Auth;
import Utils.EmailHandler;
import Utils.Help;

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
                    System.out.println("Email: " + emailHandler.remitente);
                    System.out.println("Subject: " + emailHandler.subject);
                    if (emailHandler.subject.equals("HELP") || emailHandler.subject.equals("help")) {
                        System.out.println("HELP");
                        smtp.sendEmail(Help.getHelp(), emailHandler.remitente);
                        System.out.println("**********************************************");
                        continue;
                    }
                    if (!emailHandler.isValidate()) {
                        System.out.println("Error: " + emailHandler.messageError);
                        smtp.sendEmailError("Error", emailHandler.messageError, emailHandler.remitente);
                        System.out.println("**********************************************");
                        continue;
                    }
                    if (!Auth.auth(emailHandler.remitente)) {
                        smtp.sendEmailError("Error", "Remitente no autorizado", emailHandler.remitente);
                        System.out.println("**********************************************");
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
