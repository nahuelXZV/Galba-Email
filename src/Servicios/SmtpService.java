package Servicios;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SmtpService {

    /*
     * private final String SERVER = "mail.tecnoweb.org.bo";
     * private final String USER_EMISOR = "grupo06sc@tecnoweb.org.bo";
     */
    private final String SERVER = "nahuelxzv.pro";
    private final String USER_EMISOR = "nahuel.zalazar@nahuelxzv.pro";
    private final int PUERTO = 25;

    String line;
    String comando = "";

    public boolean sendEmail(String mensaje, String receptor) {
        try {
            Socket socket = new Socket(SERVER, PUERTO);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            if (socket != null && entrada != null && salida != null) {
                comando = "EHLO " + SERVER + "\r\n";
                salida.writeBytes(comando);

                comando = "MAIL FROM :" + USER_EMISOR + "\r\n";
                salida.writeBytes(comando);

                comando = "RCPT TO :" + receptor + "\r\n";
                salida.writeBytes(comando);

                comando = "DATA\r\n";
                salida.writeBytes(comando);

                comando = "SUBJECT : Respuesta a consulta" + "\r\n";
                comando += "Content-Type: text/html; charset=utf-8" + "\r\n"; // Agrega el encabezado Content-Type
                comando += mensaje + "\r\n";
                comando += ".\r\n";
                salida.writeBytes(comando);

                comando = "QUIT\r\n";
                salida.writeBytes(comando);
            }
            salida.close();
            entrada.close();
            socket.close();
            return true;
        } catch (Exception e) {
            System.out.println("[SMTP-SERVICE]" + e);
        }
        return false;
    }

    public boolean sendEmailError(String subject, String body, String receptor) {
        try {
            Socket socket = new Socket(SERVER, PUERTO);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            if (socket != null && entrada != null && salida != null) {
                comando = "EHLO " + SERVER + "\r\n";
                salida.writeBytes(comando);

                comando = "MAIL FROM :" + USER_EMISOR + "\r\n";
                salida.writeBytes(comando);

                comando = "RCPT TO :" + receptor + "\r\n";
                salida.writeBytes(comando);

                comando = "DATA\r\n";
                salida.writeBytes(comando);

                comando = "SUBJECT :" + subject + "\r\n";
                comando += body + "\r\n";
                comando += ".\r\n";
                salida.writeBytes(comando);

                comando = "QUIT\r\n";
                salida.writeBytes(comando);
            }
            salida.close();
            entrada.close();
            socket.close();
            return true;
        } catch (Exception e) {
            System.out.println("[SMTP-SERVICE]" + e);
        }
        return false;
    }

}
