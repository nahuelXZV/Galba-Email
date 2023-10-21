package Negocio;

import java.util.LinkedList;

import Datos.RolDato;
import Datos.UsuarioDato;
import Utils.Validate;

public class UsuarioNegocio {

    private String respuesta;

    private UsuarioDato usuarioDato;
    private RolDato rolDatos;

    public UsuarioNegocio() {
        usuarioDato = new UsuarioDato();
        rolDatos = new RolDato();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        usuarioDato = new UsuarioDato(0, params.get(0), params.get(1), params.get(2), params.get(3),
                Integer.parseInt(params.get(4)));
        if (usuarioDato.create()) {
            this.respuesta = "Creado exitosamente.";
        } else {
            this.respuesta = "No se pudo crear.";
        }
        return this.respuesta;
    }

    public String update(LinkedList<String> params) {
        validateUpdate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        usuarioDato = new UsuarioDato(Integer.parseInt(params.get(0)), params.get(1), params.get(2), params.get(3),
                params.get(4), Integer.parseInt(params.get(5)));
        if (usuarioDato.update()) {
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

    public boolean validateRol(String email, String rol) {
        return usuarioDato.validateRol(email, rol);
    }

    private void validateCreate(LinkedList<String> params) {
        usuarioDato = new UsuarioDato();
        if (Validate.hasSize(params, 5)) {
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
            this.respuesta = "El area no puede ser vacio";
            return;
        }
        if (!Validate.isNumber(params.get(4))) {
            this.respuesta = "El ID del rol debe ser un numero";
            return;
        }
        if (!rolDatos.exist(Integer.parseInt(params.get(4)))) {
            this.respuesta = "El ID del rol no existe";
        }
    }

    private void validateUpdate(LinkedList<String> params) {
        usuarioDato = new UsuarioDato();
        if (Validate.hasSize(params, 6)) {
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
            this.respuesta = "El nombre no puede ser vacio";
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
            this.respuesta = "La contraseña no puede ser vacio";
            return;
        }
        if (!Validate.isString(params.get(4))) {
            this.respuesta = "El area no puede ser vacio";
            return;
        }
        if (!Validate.isNumber(params.get(5))) {
            this.respuesta = "El rol_id debe ser un numero";
            return;
        }
        if (!rolDatos.exist(Integer.parseInt(params.get(5)))) {
            this.respuesta = "El ID del rol no existe";
        }
    }

}
