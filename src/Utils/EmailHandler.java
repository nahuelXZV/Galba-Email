package Utils;

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
        // this.remitente = "daniela.carrasco@nahuelxzv.pro";
    }

    private boolean validateSubject() {
        String subject = this.subject;
        int parentesis1 = subject.indexOf("[");
        int parentesis2 = subject.indexOf("]");
        int espacio = subject.indexOf(" ");
        // System.out.println("parentesis1: " + parentesis1);
        // System.out.println("parentesis2: " + parentesis2);
        // System.out.println("espacio: " + espacio);
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
        int startIndex = 0;
        int endIndex = 0;
        String subject = "";

        boolean isHotmail = this.email.indexOf("@hotmail.com") != -1;
        boolean isYahoo = this.email.indexOf("@yahoo.com") != -1;
        boolean isFicct = this.email.indexOf("@ficct.uagrm.edu.bo") != -1;

        if (isHotmail) {
            startIndex = this.email.indexOf("Subject:") + "Subject:".length();
            String emailRecortado = this.email.substring(startIndex, this.email.length());
            startIndex = emailRecortado.indexOf("Subject:") + "Subject:".length();
            emailRecortado = emailRecortado.substring(startIndex, emailRecortado.length());
            startIndex = emailRecortado.indexOf("Subject:") + "Subject:".length();
            endIndex = emailRecortado.indexOf("Thread-Topic:");
            subject = emailRecortado.substring(startIndex, endIndex);
        } else if (isYahoo) {
            startIndex = this.email.indexOf("Subject:") + "Subject:".length();
            String emailRecortado = this.email.substring(startIndex, this.email.length());
            startIndex = this.email.indexOf("Subject:") + "Subject:".length();
            emailRecortado = emailRecortado.substring(startIndex, emailRecortado.length());
            startIndex = emailRecortado.indexOf("Subject:") + "Subject:".length();
            emailRecortado = emailRecortado.substring(startIndex, emailRecortado.length());
            startIndex = emailRecortado.indexOf("Subject:") + "Subject:".length();
            endIndex = emailRecortado.indexOf("MIME-Version:");
            subject = emailRecortado.substring(startIndex, endIndex);
        } else if (isFicct) {
            startIndex = this.email.indexOf("Subject:") + "Subject:".length();
            String emailRecortado = this.email.substring(startIndex, this.email.length());
            startIndex = emailRecortado.indexOf("Subject:") + "Subject:".length();
            endIndex = emailRecortado.indexOf("Content-Type:");
            subject = emailRecortado.substring(startIndex, endIndex);
        } else {
            startIndex = this.email.indexOf("Subject:") + "Subject:".length();
            endIndex = this.email.indexOf("To:");
            if (endIndex == -1 || endIndex < startIndex) {
                endIndex = this.email.indexOf("In-Reply-To:");
            }
            subject = this.email.substring(startIndex, endIndex);
        }

        // eliminar espacios en blanco al inicio y al final
        subject = subject.trim();
        // eliminar los \n
        subject = subject.replace("\n", "");
        // eliminar los \r
        subject = subject.replace("\r", "");
        return subject.toLowerCase().trim();
    }

    String getRemitente() {
        int startIndex = 0;
        int endIndex = 0;
        boolean isHotmail = this.email.indexOf("@hotmail.com") != -1;
        boolean isYahoo = this.email.indexOf("@yahoo.com") != -1;
        boolean isFicct = this.email.indexOf("@ficct.uagrm.edu.bo") != -1;

        if (isHotmail || isYahoo || isFicct) {
            startIndex = this.email.indexOf("Return-Path:") + "Return-Path:".length();
            endIndex = this.email.indexOf("\n", startIndex);
        } else {
            startIndex = this.email.indexOf("From:") + "From:".length();
            endIndex = this.email.indexOf("\n", startIndex);
        }

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

    public String getComando() {
        String subject = this.subject;
        int espacio = subject.indexOf(" ");
        String comando = subject.substring(0, espacio);
        return comando.toUpperCase();
    }

    public LinkedList<String> getParametros() {
        String subject = this.subject;
        int espacio = subject.indexOf(" ");
        // int parentesis1 = subject.indexOf("[");
        int parentesis2 = subject.indexOf("]");
        String parametros = subject.substring(espacio + 1, parentesis2 + 1);
        // eliminar []
        parametros = parametros.replace("[", "");
        parametros = parametros.replace("]", "");

        // eliminar espacios en blanco al inicio y al final
        parametros = parametros.trim();

        // eliminar los \n
        parametros = parametros.replace("\n", "");

        // eliminar los \r
        parametros = parametros.replace("\r", "");

        // eliminar '
        parametros = parametros.replace("'", "");

        if (parametros.length() == 0) {
            return new LinkedList<>();
        }
        String[] parametrosArray = parametros.split(",");
        // eliminar las comillas
        for (int i = 0; i < parametrosArray.length; i++) {
            String param = parametrosArray[i];
            param = param.replace("\"", "");
            parametrosArray[i] = param.trim();
        }
        return this.createList(parametrosArray);
    }

    public LinkedList<String> createList(String[] params) {
        LinkedList<String> list = new LinkedList<>();
        for (String param : params) {
            list.add(param);
        }
        return list;
    }
}
