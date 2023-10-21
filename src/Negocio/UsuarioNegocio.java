package Negocio;

import java.util.LinkedList;

import Datos.UsuarioDato;

public class UsuarioNegocio {

    private UsuarioDato usuarioDato;
    private String respuesta;
    // private rolController rol;

    public UsuarioNegocio() {
        usuarioDato = new UsuarioDato();
        // rol = new rolController();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        usuarioDato = new UsuarioDato(0, params.get(0), params.get(1), params.get(2), params.get(3),
                Integer.parseInt(params.get(4)));
        if (usuarioDato.create()) {
            respuesta = "Creado exitosamente.";
        } else {
            respuesta = "No se pudo crear.";
        }
        return respuesta;
    }

    public String update(LinkedList<String> params) {
        validateUpdate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        usuarioDato = new UsuarioDato(Integer.parseInt(params.get(0)), params.get(1), params.get(2), params.get(3),
                params.get(4), Integer.parseInt(params.get(5)));
        if (usuarioDato.update()) {
            respuesta = "Actualizado exitosamente.";
        } else {
            respuesta = "No se pudo actualizar.";
        }
        return respuesta;
    }

    public String delete(int id) {
        // if (!validatorUtils.validateNumber(String.valueOf(id))) {
        // return "El id debe ser un numero";
        // }
        usuarioDato = new UsuarioDato();
        if (usuarioDato.delete(id)) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return usuarioDato.getAll(params);
    }

    public boolean auth(String email) {
        // if (!validatorUtils.validateEmail(email) || !usuario.emailExist(email)) {
        // return false;
        // }
        return true;
    }

    public boolean validateRol(String email, String rol) {
        return usuarioDato.validateRol(email, rol);
    }

    private void validateCreate(LinkedList<String> params) {
        // usuario = new usuarioModel();
        // if (params.size() != 5) {
        // this.respuesta = "La cantidad de parametros es incorrecta";
        // return;
        // }
        // if (!validatorUtils.validateString(params.get(0))) {
        // this.respuesta = "El nombre no puede ser vacio";
        // return;
        // }
        // if (!validatorUtils.validateEmail(params.get(1))) {
        // this.respuesta = "El email no es valido";
        // return;
        // }
        // if (usuario.emailExist(params.get(1))) {
        // this.respuesta = "El email ya existe";
        // return;
        // }
        // if (!validatorUtils.validateString(params.get(2))) {
        // this.respuesta = "La contraseña no puede ser vacio";
        // return;
        // }
        // if (!validatorUtils.validateString(params.get(3))) {
        // this.respuesta = "El area no puede ser vacio";
        // return;
        // }
        // if (!validatorUtils.validateNumber(params.get(4))) {
        // this.respuesta = "El ID del rol debe ser un numero";
        // return;
        // }
        // if (!rol.exist(Integer.parseInt(params.get(4)))) {
        // this.respuesta = "El ID del rol no existe";
        // }
    }

    private void validateUpdate(LinkedList<String> params) {
        // usuario = new usuarioModel();
        // if (params.size() != 6) {
        // this.respuesta = "La cantidad de parametros es incorrecta";
        // return;
        // }
        // if (!validatorUtils.validateNumber(params.get(0))) {
        // this.respuesta = "El id debe ser un numero";
        // return;
        // }
        // if (!usuario.exist(Integer.parseInt(params.get(0)))) {
        // this.respuesta = "El usuario no existe";
        // return;
        // }
        // if (!validatorUtils.validateString(params.get(1))) {
        // this.respuesta = "El nombre no puede ser vacio";
        // return;
        // }
        // if (!validatorUtils.validateEmail(params.get(2))) {
        // this.respuesta = "El email no es valido";
        // return;
        // }
        // if (usuario.emailExist(params.get(2))) {
        // this.respuesta = "El email ya existe";
        // return;
        // }
        // if (!validatorUtils.validateString(params.get(3))) {
        // this.respuesta = "La contraseña no puede ser vacio";
        // return;
        // }
        // if (!validatorUtils.validateString(params.get(4))) {
        // this.respuesta = "El area no puede ser vacio";
        // return;
        // }
        // if (!validatorUtils.validateNumber(params.get(5))) {
        // this.respuesta = "El rol_id debe ser un numero";
        // return;
        // }
        // if (!rol.exist(Integer.parseInt(params.get(5)))) {
        // this.respuesta = "El ID del rol no existe";
        // }
    }

}
