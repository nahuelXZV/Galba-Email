package Negocio;

import java.util.LinkedList;

import Datos.IngresoDato;
import Utils.Validate;

public class IngresoNegocio {
    private String respuesta;

    private IngresoDato ingresoDato;

    public IngresoNegocio() {
        ingresoDato = new IngresoDato();
    }

    public String create(String motivo) {
        if (this.respuesta != null) {
            return this.respuesta;
        }
        ingresoDato = new IngresoDato(motivo);
        if (ingresoDato.create()) {
            this.respuesta = "Creado exitosamente.";
        } else {
            this.respuesta = "No se pudo crear.";
        }
        return this.respuesta;
    }

    public String delete(String id) {
        if (!Validate.isNumber(id)) {
            this.respuesta = "El id debe ser un numero";
            return this.respuesta;
        }
        ingresoDato = new IngresoDato();
        if (ingresoDato.delete(Integer.parseInt(id))) {
            this.respuesta = "Eliminado exitosamente.";
        } else {
            this.respuesta = "No se pudo eliminar.";
        }
        return this.respuesta;
    }

    public String getAllIng(LinkedList<String> params) {
        return ingresoDato.getAllIng(params);
    }

    public String getAll(String id) {
        if (!Validate.isNumber(id)) {
            this.respuesta = "El id debe ser un numero";
            return this.respuesta;
        }
        return ingresoDato.getAll(Integer.parseInt(id));
    }
}
