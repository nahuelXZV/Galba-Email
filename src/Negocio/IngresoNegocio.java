package Negocio;

import java.util.LinkedList;

import Datos.IngresoDato;
import Utils.Validate;

public class IngresoNegocio {
    private IngresoDato ingresoDato;

    public IngresoNegocio() {
        ingresoDato = new IngresoDato();
    }

    public String create(String motivo) {
        try {
            if (motivo == null) {
                return "El motivo no puede ser nulo.";
            }
            ingresoDato = new IngresoDato(motivo);
            if (ingresoDato.create()) {
                return "Creado exitosamente.";
            }
            return "No se pudo crear, intente nuevamente.";
        } catch (Exception e) {
            return "Error del sistema. Intente nuevamente.";
        }
    }

    public String delete(String id) {
        try {
            if (!Validate.isNumber(id)) {
                return "El id debe ser un numero";
            }
            ingresoDato = new IngresoDato();
            if (ingresoDato.delete(Integer.parseInt(id))) {
                return "Eliminado exitosamente.";
            }
            return "No se pudo eliminar, intente nuevamente.";
        } catch (Exception e) {
            return "Error del sistema. Intente nuevamente.";
        }
    }

    public String getAllIng(LinkedList<String> params) {
        try {
            return ingresoDato.getAllIng(params);
        } catch (Exception e) {
            return "Error del sistema. Intente nuevamente.";
        }
    }

    public String getAll(String id) {
        try {
            if (!Validate.isNumber(id)) {
                return "El id debe ser un numero";
            }
            if (!ingresoDato.exist(Integer.parseInt(id))) {
                return "El id del ingreso no existe";
            }
            return ingresoDato.getAll(Integer.parseInt(id));
        } catch (Exception e) {
            return "Error del sistema. Intente nuevamente.";
        }
    }
}
