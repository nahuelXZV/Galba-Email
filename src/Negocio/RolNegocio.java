package Negocio;

import java.util.LinkedList;

import Datos.RolDato;
import Utils.Validate;

public class RolNegocio {
    private RolDato rolDato;

    private String respuesta;

    public RolNegocio() {
        rolDato = new RolDato();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        rolDato = new RolDato(0, params.get(0), params.get(1));
        if (rolDato.create()) {
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
        rolDato = new RolDato(Integer.parseInt(params.get(0)), params.get(1), params.get(2));
        if (rolDato.update()) {
            respuesta = "Actualizado exitosamente.";
        } else {
            respuesta = "No se pudo actualizar.";
        }
        return respuesta;
    }

    public String delete(String id) {
        if (!Validate.isNumber(id)) {
            return "El id debe ser un numero";
        }
        rolDato = new RolDato();
        if (rolDato.delete(Integer.parseInt(id))) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return rolDato.getAll(params);
    }

    public boolean exist(int id) {
        return rolDato.exist(id);
    }

    private void validateCreate(LinkedList<String> params) {
        if (Validate.hasSize(params, 2)) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!Validate.isString(params.get(0))) {
            this.respuesta = "El nombre no puede ser vacio";
            return;
        }
        if (!Validate.isString(params.get(1))) {
            this.respuesta = "La descripcion no puede ser vacio";
            return;
        }
    }

    private void validateUpdate(LinkedList<String> params) {
        if (params.size() != 3) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!Validate.isNumber(params.get(0))) {
            this.respuesta = "El id debe ser un numero";
            return;
        }
        if (!Validate.isString(params.get(1))) {
            this.respuesta = "El nombre no puede ser vacio";
            return;
        }
        if (!Validate.isString(params.get(2))) {
            this.respuesta = "La descripcion no puede ser vacio";
            return;
        }
    }
}
