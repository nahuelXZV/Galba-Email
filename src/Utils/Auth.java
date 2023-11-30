package Utils;

import Datos.UsuarioDato;

public class Auth {

    public static boolean auth(String email) {
        UsuarioDato usuarioDato = new UsuarioDato();
        return usuarioDato.emailExist(email);
    }
}
