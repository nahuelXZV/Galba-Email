package Servicios;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class PopService {

    // private final String SERVER = "mail.tecnoweb.org.bo";
    // private final String USUARIO = "grupo06sc";
    // private final String CONTRASEÑA = "grup006grup006";

    private final String SERVER = "nahuelxzv.pro";
    private final String USUARIO = "nahuel.zalazar";
    private final String CONTRASEÑA = "email2023";

    private final int PUERTO = 110;

    public int getCantidadEmails() {
        String number = "";
        String comando;
        try {
            Socket socket = new Socket(SERVER, PUERTO);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            if (socket != null && entrada != null && salida != null) {

                comando = "USER " + USUARIO + "\r\n";
                salida.writeBytes(comando);

                comando = "PASS " + CONTRASEÑA + "\r\n";
                salida.writeBytes(comando);

                comando = "LIST \r\n";
                salida.writeBytes(comando);
                number = getLastMail(entrada);

                comando = "QUIT\r\n";
                salida.writeBytes(comando);
            }
            salida.close();
            entrada.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("[POP-SERVICE] " + e);
        }
        return Integer.parseInt(number);
    }

    public String getLastMail(BufferedReader in) throws IOException {
        String number = "0";
        String line = "";
        String anteriorLine;
        while (true) {
            anteriorLine = line;
            line = in.readLine();
            if (line == null) {
                throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.equals(".")) {
                break;
            }
        }
        number = anteriorLine.substring(0, anteriorLine.indexOf(" "));
        number = number.trim();
        return number;
    }

    public String getMail() {
        String comando;
        String lastEmail;
        String email = "";
        try {
            Socket socket = new Socket(SERVER, PUERTO);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            if (socket != null && entrada != null && salida != null) {

                comando = "USER " + USUARIO + "\r\n";
                salida.writeBytes(comando);

                comando = "PASS " + CONTRASEÑA + "\r\n";
                salida.writeBytes(comando);

                comando = "LIST \r\n";
                salida.writeBytes(comando);
                lastEmail = getLastMail(entrada);

                comando = "RETR " + lastEmail + "\n";
                salida.writeBytes(comando);
                email = getMultiline(entrada);
                // System.out.println("Text: " + text);

                comando = "QUIT\r\n";
                salida.writeBytes(comando);
            }
            salida.close();
            entrada.close();
            socket.close();

            return email;
        } catch (UnknownHostException e) {
            System.out.println("[POP-SERVICE] " + e);
        } catch (IOException e) {
            System.out.println("[POP-SERVICE] " + e);
        }
        return null;
    }

    static protected String getMultiline(BufferedReader in) throws IOException {
        String lines = "";
        while (true) {
            String line = in.readLine();
            if (line == null) {
                throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.equals(".")) {
                break;
            }
            if ((line.length() > 0) && (line.charAt(0) == '.')) {
                line = line.substring(1);
            }
            lines = lines + "\n" + line;
        }
        return lines;
    }
}
