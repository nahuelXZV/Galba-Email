package Negocio;

import java.util.LinkedList;

import Datos.UsuarioDato;
import Utils.Validate;

public class UsuarioNegocio {

    private String respuesta;

    private UsuarioDato usuarioDato;

    public UsuarioNegocio() {
        usuarioDato = new UsuarioDato();
    }

    public String createPersonal(LinkedList<String> params) {
        this.validateCreatePersonal(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        usuarioDato = new UsuarioDato(0, params.get(0), params.get(1), params.get(2), params.get(3),
                Boolean.parseBoolean(params.get(4)), Boolean.parseBoolean(params.get(5)));
        if (usuarioDato.createPersonal()) {
            this.respuesta = "Creado exitosamente.";
        } else {
            this.respuesta = "No se pudo crear.";
        }
        return this.respuesta;
    }

    public String createCliente(LinkedList<String> params) {
        this.validateCreateCliente(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        usuarioDato = new UsuarioDato(0, params.get(0), params.get(1), params.get(2), params.get(3),
                params.get(4), Boolean.parseBoolean(params.get(5)));
        if (usuarioDato.createCliente()) {
            this.respuesta = "Creado exitosamente.";
        } else {
            this.respuesta = "No se pudo crear.";
        }
        return this.respuesta;
    }

    public String updatePersonal(LinkedList<String> params) {
        validateUpdatePersonal(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        usuarioDato = new UsuarioDato(0, params.get(0), params.get(1), params.get(2), params.get(3),
                Boolean.parseBoolean(params.get(4)), Boolean.parseBoolean(params.get(5)));
        if (usuarioDato.updatePersonal()) {
            this.respuesta = "Actualizado exitosamente.";
        } else {
            this.respuesta = "No se pudo actualizar.";
        }
        return this.respuesta;
    }

    public String updateCliente(LinkedList<String> params) {
        validateUpdateCliente(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        usuarioDato = new UsuarioDato(0, params.get(0), params.get(1), params.get(2), params.get(3),
                params.get(4), Boolean.parseBoolean(params.get(5)));
        if (usuarioDato.updateCliente()) {
            this.respuesta = "Actualizado exitosamente.";
        } else {
            this.respuesta = "No se pudo actualizar.";
        }
        return this.respuesta;
    }

    public String delete(String id) {
        if (!Validate.isNumber(id)) {
            this.respuesta = "El id debe ser un numero";
            return this.respuesta;
        }
        usuarioDato = new UsuarioDato();
        if (usuarioDato.delete(Integer.parseInt(id))) {
            this.respuesta = "Eliminado exitosamente.";
        } else {
            this.respuesta = "No se pudo eliminar.";
        }
        return this.respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return usuarioDato.getAll(params);
    }

    public boolean auth(String email) {
        if (!Validate.isEmail(email) || !usuarioDato.emailExist(email)) {
            return false;
        }
        return true;
    }

    public boolean validateRol(String email, String rol, String atribute) {
        return usuarioDato.validateRol(email, rol, atribute);
    }

    private void validateCreatePersonal(LinkedList<String> params) {
        usuarioDato = new UsuarioDato();
        if (Validate.hasSize(params, 6)) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!Validate.isString(params.get(0))) {
            this.respuesta = "El nombre debe ser un string y no puede ser vacio";
            return;
        }
        if (!Validate.isEmail(params.get(1))) {
            this.respuesta = "El email no es valido";
            return;
        }
        if (usuarioDato.emailExist(params.get(1))) {
            this.respuesta = "El email ya existe";
            return;
        }
        if (!Validate.isString(params.get(2))) {
            this.respuesta = "La contraseña debe ser un string y debe ser mayor a 8 caracteres";
            return;
        }
        if (!Validate.isString(params.get(3))) {
            this.respuesta = "El cargo no puede ser vacio";
            return;
        }
        if (!Validate.isBoolean(params.get(4))) {
            this.respuesta = "El isPersonal debe ser un boolean (true o false)";
            return;
        }
        if (!Validate.isBoolean(params.get(5))) {
            this.respuesta = "El isAdministrador debe ser un boolean (true o false)";
            return;
        }
    }

    private void validateCreateCliente(LinkedList<String> params) {
        usuarioDato = new UsuarioDato();
        if (Validate.hasSize(params, 6)) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!Validate.isString(params.get(0))) {
            this.respuesta = "El nombre debe ser un string y no puede ser vacio";
            return;
        }
        if (!Validate.isEmail(params.get(1))) {
            this.respuesta = "El email no es valido";
            return;
        }
        if (usuarioDato.emailExist(params.get(1))) {
            this.respuesta = "El email ya existe";
            return;
        }
        if (!Validate.isString(params.get(2))) {
            this.respuesta = "La contraseña debe ser un string y debe ser mayor a 8 caracteres";
            return;
        }
        if (!Validate.isString(params.get(3))) {
            this.respuesta = "El campo direccion no puede ser vacio";
            return;
        }
        if (!Validate.isString(params.get(4))) {
            this.respuesta = "El campo telefono no puede ser vacio";
            return;
        }
        if (!Validate.isBoolean(params.get(5))) {
            this.respuesta = "El isCliente debe ser un boolean (true o false)";
            return;
        }
    }

    private void validateUpdatePersonal(LinkedList<String> params) {
        usuarioDato = new UsuarioDato();
        if (Validate.hasSize(params, 7)) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!Validate.isNumber(params.get(0))) {
            this.respuesta = "El id debe ser un numero";
            return;
        }
        if (!usuarioDato.exist(Integer.parseInt(params.get(0)))) {
            this.respuesta = "El usuario no existe";
            return;
        }
        if (!Validate.isString(params.get(1))) {
            this.respuesta = "El nombre debe ser un string y no puede ser vacio";
            return;
        }
        if (!Validate.isEmail(params.get(2))) {
            this.respuesta = "El email no es valido";
            return;
        }
        if (usuarioDato.emailExist(params.get(2))) {
            this.respuesta = "El email ya existe";
            return;
        }
        if (!Validate.isString(params.get(3))) {
            this.respuesta = "La contraseña debe ser un string y debe ser mayor a 8 caracteres";
            return;
        }
        if (!Validate.isString(params.get(4))) {
            this.respuesta = "El cargo no puede ser vacio";
            return;
        }
        if (!Validate.isBoolean(params.get(5))) {
            this.respuesta = "El isPersonal debe ser un boolean (true o false)";
            return;
        }
        if (!Validate.isBoolean(params.get(6))) {
            this.respuesta = "El isAdministrador debe ser un boolean (true o false)";
            return;
        }
    }

    private void validateUpdateCliente(LinkedList<String> params) {
        usuarioDato = new UsuarioDato();
        if (Validate.hasSize(params, 7)) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!Validate.isNumber(params.get(0))) {
            this.respuesta = "El id debe ser un numero";
            return;
        }
        if (!usuarioDato.exist(Integer.parseInt(params.get(0)))) {
            this.respuesta = "El usuario no existe";
            return;
        }
        if (!Validate.isString(params.get(1))) {
            this.respuesta = "El nombre debe ser un string y no puede ser vacio";
            return;
        }
        if (!Validate.isEmail(params.get(2))) {
            this.respuesta = "El email no es valido";
            return;
        }
        if (usuarioDato.emailExist(params.get(2))) {
            this.respuesta = "El email ya existe";
            return;
        }
        if (!Validate.isString(params.get(3))) {
            this.respuesta = "La contraseña debe ser un string y debe ser mayor a 8 caracteres";
            return;
        }
        if (!Validate.isString(params.get(4))) {
            this.respuesta = "El campo direccion no puede ser vacio";
            return;
        }
        if (!Validate.isString(params.get(5))) {
            this.respuesta = "El campo telefono no puede ser vacio";
            return;
        }
        if (!Validate.isBoolean(params.get(6))) {
            this.respuesta = "El isCliente debe ser un boolean (true o false)";
            return;
        }
    }
}
