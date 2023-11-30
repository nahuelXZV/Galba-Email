package Negocio;

import Datos.CompraDato;
import Datos.ProveedorDato;
import Utils.Validate;

public class CompraNegocio {
    private String respuesta;

    private CompraDato compraDato;
    private ProveedorDato proveedorDato;

    public CompraNegocio() {
        compraDato = new CompraDato();
        proveedorDato = new ProveedorDato();
    }

    public String create(String id) {
        boolean proveedor_exist = proveedorDato.exist(Integer.parseInt(id));
        if (proveedor_exist) {
            return "El proveedor no existe.";
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
