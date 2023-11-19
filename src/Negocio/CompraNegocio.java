package Negocio;

import Datos.CompraDato;
import Utils.Validate;

public class CompraNegocio {
    private String respuesta;

    private CompraDato compraDato;

    public CompraNegocio() {
        compraDato = new CompraDato();
    }

    public String create(String id) {
        // Validar que es un id de un proveedor
        if (this.respuesta != null) {
            return this.respuesta;
        }
        compraDato = new CompraDato(0, Integer.parseInt(id));
        if (compraDato.create()) {
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
        compraDato = new CompraDato();
        if (compraDato.delete(Integer.parseInt(id))) {
            this.respuesta = "Eliminado exitosamente.";
        } else {
            this.respuesta = "No se pudo eliminar.";
        }
        return this.respuesta;
    }

    public String getAll(String id) {
        if (!Validate.isNumber(id)) {
            this.respuesta = "El id debe ser un numero";
            return this.respuesta;
        }
        return compraDato.getAll(Integer.parseInt(id));
    }
}
