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
        try {
            if (this.respuesta != null) {
                return this.respuesta;
            }
            salidaDato = new SalidaDato(motivo);
            if (salidaDato.create()) {
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
            salidaDato = new SalidaDato();
            if (salidaDato.delete(Integer.parseInt(id))) {
                return "Eliminado exitosamente.";
            }
            return "No se pudo eliminar, intente nuevamente.";
        } catch (Exception e) {
            return "Error del sistema. Intente nuevamente.";
        }
    }

    public String getAllSal(LinkedList<String> params) {
        try {
            return salidaDato.getAllSal(params);
        } catch (Exception e) {
            return "Error del sistema. Intente nuevamente.";
        }
    }

    public String getAll(String id) {
        try {
            if (!Validate.isNumber(id)) {
                return "El id debe ser un numero";
            }
            return salidaDato.getAll(Integer.parseInt(id));
        } catch (Exception e) {
            return "Error del sistema. Intente nuevamente.";
        }
    }
}
