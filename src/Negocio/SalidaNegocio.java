package Negocio;

import java.util.LinkedList;

import Datos.SalidaDato;
import Utils.Validate;

public class SalidaNegocio {
    private String respuesta;

    private SalidaDato salidaDato;

    public SalidaNegocio() {
        salidaDato = new SalidaDato();
    }

    public String create(String motivo) {
        if (this.respuesta != null) {
            return this.respuesta;
        }
        salidaDato = new SalidaDato(motivo);
        if (salidaDato.create()) {
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
        salidaDato = new SalidaDato();
        if (salidaDato.delete(Integer.parseInt(id))) {
            this.respuesta = "Eliminado exitosamente.";
        } else {
            this.respuesta = "No se pudo eliminar.";
        }
        return this.respuesta;
    }

    public String getAllSal(LinkedList<String> params) {
        return salidaDato.getAllSal(params);
    }

    public String getAll(String id) {
        if (!Validate.isNumber(id)) {
            this.respuesta = "El id debe ser un numero";
            return this.respuesta;
        }
        return salidaDato.getAll(Integer.parseInt(id));
    }
}
