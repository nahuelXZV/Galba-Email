package Negocio;

import java.util.LinkedList;

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
        try {
            if (!Validate.isNumber(id)) {
                return "El id debe ser un numero";
            }
            boolean proveedor_exist = proveedorDato.exist(Integer.parseInt(id));
            if (!proveedor_exist) {
                return "El proveedor no existe.";
            }
            compraDato = new CompraDato(Integer.parseInt(id));
            if (compraDato.create()) {
                int id_last = compraDato.getIdLastCompra();
                return compraDato.getAll(id_last);
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
            compraDato = new CompraDato();
            if (compraDato.delete(Integer.parseInt(id))) {
                return "Eliminado exitosamente.";
            }
            return "No se pudo eliminar, intente nuevamente.";
        } catch (Exception e) {
            return "Error del sistema. Intente nuevamente.";
        }
    }

    public String getAllCom(LinkedList<String> params) {
        try {
            return compraDato.getAllCom(params);
        } catch (Exception e) {
            return "Error del sistema. Intente nuevamente.";
        }
    }

    public String getAll(String id) {
        if (!Validate.isNumber(id)) {
            this.respuesta = "El id debe ser un numero";
            return this.respuesta;
        }
        return compraDato.getAll(Integer.parseInt(id));
    }
}
