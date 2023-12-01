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
        try {
            this.validateCreatePersonal(params);
            if (this.respuesta != null)
                return this.respuesta;

            usuarioDato = new UsuarioDato(0, params.get(0), params.get(1), params.get(2), params.get(3),
                    Boolean.parseBoolean(params.get(4)), Boolean.parseBoolean(params.get(5)));
            if (usuarioDato.createPersonal()) {
                return this.show(params.get(1));
            }
            return "No se pudo crear, intente nuevamente.";
        } catch (Exception e) {
            return "Error al crear usuario";
        }
    }

    public String createCliente(LinkedList<String> params, String email) {
        try {
            this.validateCreateCliente(params, email);
            if (this.respuesta != null) {
                return this.respuesta;
            }
            usuarioDato = new UsuarioDato(0, params.get(0), email, params.get(1), params.get(2), params.get(3));
            if (usuarioDato.createCliente()) {
                return this.show(email);
            }
            return "No se pudo crear, intente nuevamente.";
        } catch (Exception e) {
            return "Error al crear usuario";
        }
    }

    public String updatePersonal(LinkedList<String> params) {
        try {
            validateUpdatePersonal(params);
            if (this.respuesta != null) {
                return this.respuesta;
            }
            usuarioDato = new UsuarioDato(Integer.parseInt(params.get(0)), params.get(1), params.get(2), params.get(3),
                    params.get(4),
                    Boolean.parseBoolean(params.get(5)), Boolean.parseBoolean(params.get(6)));
            if (usuarioDato.updatePersonal()) {
                return "Actualizado exitosamente.";
            }
            return "No se pudo actualizar.";
        } catch (Exception e) {
            return "Error al actualizar usuario";
        }
    }

    public String updateCliente(LinkedList<String> params, String email) {
        try {
            validateUpdateCliente(params);
            if (this.respuesta != null) {
                return this.respuesta;
            }
            int usuario_id = usuarioDato.idByEmail(email);
            if (usuario_id == -1) {
                return "El usuario no existe";
            }
            usuarioDato = new UsuarioDato(
                    usuario_id, params.get(0), email, params.get(1), params.get(2), params.get(3));
            if (usuarioDato.updateCliente()) {
                return usuarioDato.show(usuario_id);
            }
            return "No se pudo actualizar.";
        } catch (Exception e) {
            return "Error al actualizar usuario";
        }
    }

    public String delete(String email) {
        try {
            usuarioDato = new UsuarioDato();
            int usuario_id = usuarioDato.idByEmail(email);
            if (usuario_id == -1) {
                return "El usuario no existe";
            }
            if (usuarioDato.delete(usuario_id)) {
                return "Eliminado exitosamente.";
            }
            return "No se pudo eliminar.";
        } catch (Exception e) {
            return "Error al eliminar usuario";
        }
    }

    public String show(String email) {
        try {
            usuarioDato = new UsuarioDato();
            int usuario_id = usuarioDato.idByEmail(email);
            if (usuario_id == -1) {
                return "El usuario no existe";
            }
            return usuarioDato.show(usuario_id);
        } catch (Exception e) {
            return "Error al obtener usuario";
        }
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
        if (params.size() != 6) {
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

    private void validateCreateCliente(LinkedList<String> params, String email) {
        usuarioDato = new UsuarioDato();
        if (params.size() != 4) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!Validate.isString(params.get(0))) {
            this.respuesta = "El nombre debe ser un string y no puede ser vacio";
            return;
        }
        if (!Validate.isString(params.get(1))) {
            this.respuesta = "La contraseña debe ser un string y debe ser mayor a 8 caracteres";
            return;
        }
        if (!Validate.isString(params.get(2))) {
            this.respuesta = "El campo direccion no puede ser vacio";
            return;
        }
        if (!Validate.isString(params.get(3))) {
            this.respuesta = "El campo telefono no puede ser vacio";
            return;
        }
        if (!Validate.isEmail(email)) {
            this.respuesta = "El email no es valido";
            return;
        }
        if (usuarioDato.emailExist(email)) {
            this.respuesta = "El email ya existe";
            return;
        }
    }

    private void validateUpdatePersonal(LinkedList<String> params) {
        usuarioDato = new UsuarioDato();
        if (params.size() != 7) {
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
        if (params.size() != 4) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!Validate.isString(params.get(0))) {
            this.respuesta = "El nombre debe ser un string y no puede ser vacio";
            return;
        }
        if (!Validate.isString(params.get(1))) {
            this.respuesta = "La contraseña debe ser un string y debe ser mayor a 8 caracteres";
            return;
        }
        if (!Validate.isString(params.get(2))) {
            this.respuesta = "El campo direccion no puede ser vacio";
            return;
        }
        if (!Validate.isString(params.get(3))) {
            this.respuesta = "El campo telefono no puede ser vacio";
            return;
        }
    }
}
