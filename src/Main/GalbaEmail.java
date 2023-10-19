package Main;

import Presentacion.Auth;
import Presentacion.SubjectHandler;
import Servicios.PopService;

public class GalbaEmail {

    public static void main(String[] args) {
        PopService pop = new PopService();
        int cantMails = pop.getCantidadEmails();
        while (true) {
            int newCantsMails = pop.getCantidadEmails();
            System.out.println("Escuchando EMAILS...");
            if (cantMails != newCantsMails) {
                cantMails = newCantsMails;
                try {
                    System.out.println("**********NEW EMAIL***************");
                    String email = pop.getMail();

                    // validate comando
                    
                    // auth
                    
                    // routing
                    
                    // send email
                    
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
