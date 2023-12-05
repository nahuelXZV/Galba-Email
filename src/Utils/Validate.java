package Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class Validate {

    public static boolean isNumber(String id) {
        return id.matches("[0-9]+");
    }

    public static boolean isFloat(String id) {
        return id.matches("[0-9]*\\.?[0-9]+");
    }

    public static boolean isString(String text) {
        if (text.length() == 0) {
            return false;
        }
        return true;
    }

    public static boolean isBoolean(String bool) {
        return bool.equals("true") || bool.equals("false");
    }

    public static boolean isEmail(String email) {
        try {
            String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            return Pattern.matches(regex, email);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isUrl(String url) {
        try {
            String regex = "^(http|https)://[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(:[0-9]{1,5})?(/.*)?$";
            return url.matches(regex);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isRol(String rol) {
        return rol.equals("admin") || rol.equals("user");
    }

    public static boolean isPassword(String password) {
        return password.length() >= 8 && isString(password);
    }

    public static boolean hasSize(LinkedList<String> list, int size) {
        return list.size() == size;
    }

    public static boolean isCategoria(String categoria) {
        switch (categoria) {
            case "herramientas":
                return true;
            case "iluminacion":
                return true;
            case "pegado y sellado":
                return true;
            case "abrasivos":
                return true;
            case "pinturas e impermeabilizantes":
                return true;
            case "seguridad industrial":
                return true;
            case "cementos y morteros":
                return true;
            case "escaleras y carretillas":
                return true;
            case "limpieza":
                return true;
            case "materiales electricos":
                return true;
            case "plomeria":
                return true;
            case "herrajes y cerraduras":
                return true;
            default:
                break;
        }
        ;
        return false;
    }
}
