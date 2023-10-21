package Presentacion;

import java.util.LinkedList;

public class EmailHandler {

    public String email;
    public String subject;
    public String remitente;
    public String messageError;
    public String comando;
    public String parametros;

    public EmailHandler(String email) {
        this.email = email;
        this.subject = this.getSubject();
        this.remitente = this.getRemitente();
    }

    // LISTPROD ["*"]
    // LISTPROD ["",""]
    // ADDPROD ["","","",""]
    // EDITPROD ["","","",""]
    // DELPROD ["","","",""]
    private boolean validateSubject() {
        String subject = this.subject;

        int parentesis1 = subject.indexOf("[");
        int parentesis2 = subject.indexOf("]");
        int espacio = subject.indexOf(" ");

        if (parentesis1 == -1 || parentesis2 == -1) {
            this.messageError = "No se reconoce el formato indicado. Verifique que está utilizando los corchetes( [] ) para realizar la petición.";
            return false;
        }
        if (parentesis1 > parentesis2) {
            this.messageError = "No se reconoce el formato indicado. Verifique que está utilizando los corchetes( [] ) de forma ordenada.";
            return false;
        }
        if (parentesis1 < 0) {
            this.messageError = "No se reconoce el formato indicado. Verifique que está enviando los datos dentro de un encabezado.";
            return false;
        }
        if (espacio == -1) {
            this.messageError = "No se reconoce el formato indicado. Verifique que exista un espacio entre el comando y los parametros.";
            return false;
        }
        if (espacio == -1 || parentesis1 == -1) {
            this.messageError = "No se reconoce el formato indicado. Verifique que exista un espacio entre el comando y los parametros.";
            return false;
        }
        if (parentesis1 < espacio) {
            this.messageError = "No se reconoce el formato indicado. Verifique que exista un espacio entre el comando y los parametros.";
            return false;
        }
        return true;
    }

    String getSubject() {
        int startIndex = this.email.indexOf("Subject:") + "Subject:".length();
        // posicion de In-Reply-To or To
        int endIndex = this.email.indexOf("To:");
        if (endIndex == -1 || endIndex < startIndex) {
            endIndex = this.email.indexOf("In-Reply-To:");
        }
        // obtener el texto entre Subject y In-Reply-To
        String subject = this.email.substring(startIndex, endIndex);

        // eliminar espacios en blanco al inicio y al final
        subject = subject.trim();
        // eliminar los \n
        subject = subject.replace("\n", "");
        // eliminar los \r
        subject = subject.replace("\r", "");
        return subject.toLowerCase().trim();
    }

    String getRemitente() {
        int startIndex = this.email.indexOf("From:") + "From:".length();
        int endIndex = this.email.indexOf("\n", startIndex);
        String subject = this.email.substring(startIndex, endIndex);
        // Nahuel Zalazar <zalazarnahuel43@gmail.com>
        String[] parts = subject.split("<");
        String part2 = parts[1]; // 034556
        part2 = part2.replace(">", "");
        part2 = part2.trim();
        return part2;
    }

    public boolean isValidate() {
        return validateSubject();
    }

    String getComando() {
        if (isValidate())
            return "";
        String subject = this.subject;
        int espacio = subject.indexOf(" ");
        String comando = subject.substring(0, espacio);
        return comando;
    }

    String getParametros() {
        if (isValidate())
            return "";
        String subject = this.subject;
        int parentesis1 = subject.indexOf("[");
        int parentesis2 = subject.indexOf("]");
        String parametros = subject.substring(parentesis1 + 1, parentesis2);
        return parametros;
    }

    public LinkedList<String> createList(String[] params) {
        LinkedList<String> list = new LinkedList<>();
        for (String param : params) {
            param = param.trim();
            list.add(param);
        }
        return list;
    }
}
