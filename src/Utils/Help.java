package Utils;

public class Help {

    public static String getHelp() {
        return "<h1>FERRETERIA GALBA - COMANDOS DISPONIBLES</h1>"
                + "<p>Para poder utilizar los comandos, debe enviar un correo a la direccion: <b> grupo@mail.com </b> con el siguiente formato:</p>"
                + "<p> <b> Subject: [COMANDO] [PARAMETROS] </b>, los parametros deben ir entre comillas dobles (\"\") y separados por una coma </p>"
                + "<table style=\" border-collapse: collapse; width: 100%; border: 1px solid black; padding: 8px;\"> \n \n"
                + "<tr> \n \n"
                + "<th style=\"text-align: center; padding: 8px; background-color: #1C7293; color: white; border: 1px solid black;\"> FUNCIONALIDAD </th> \n \n"
                + "<th style=\"text-align: center; padding: 8px; background-color: #1C7293; color: white; border: 1px solid black;\"> COMANDO </th> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar usuario</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">ADDUSER [NOMBRE: STRING, CORREO: EMAIL, CONTRASEÑA: STRING, AREA: STRING, ROL_ID: NUMBER]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar usuario</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">EDITUSER [ID: NUMBER, NOMBRE: STRING, CORREO: EMAIL, CONTRASEÑA: STRING, AREA: STRING, ROL_ID: NUMBER]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar usuario</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELUSER [ID: NUMBER]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar usuarios</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LISTUSER [] || LISTUSER [KEY: STRING['usuario.*, rol.*'], VALOR: ANY]</td> \n \n"
                + "</tr> \n \n"
                + "</table>";
    }
}
