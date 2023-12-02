package Negocio;

import java.util.LinkedList;

import Datos.SalidaDato;
import Datos.SalidaDetalleDato;
import Datos.ProductoDato;
import Utils.Validate;

public class SalidaDetalleNegocio {
    private String respuesta;

    private SalidaDetalleDato salidaDetalleDato;
    private SalidaDato salidaDatos;
    private ProductoDato productoDatos;

    public SalidaDetalleNegocio() {
        salidaDetalleDato = new SalidaDetalleDato();
        salidaDatos = new SalidaDato();
        productoDatos = new ProductoDato();
    }

    public String create(LinkedList<String> params) {
        try {
            this.validateCreate(params);
            if (this.respuesta != null) {
                return this.respuesta;
            }
            salidaDetalleDato = new SalidaDetalleDato(Integer.parseInt(params.get(0)),
                    Integer.parseInt(params.get(1)), Integer.parseInt(params.get(2)));
            if (salidaDetalleDato.create()) {
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
            salidaDetalleDato = new SalidaDetalleDato();
            if (salidaDetalleDato.delete(Integer.parseInt(id))) {
                this.respuesta = "Eliminado exitosamente.";
            }
            return "No se pudo eliminar, intente nuevamente.";
        } catch (Exception e) {
            return "Error del sistema. Intente nuevamente.";
        }
    }

    private void validateCreate(LinkedList<String> params) {
        salidaDatos = new SalidaDato();
        productoDatos = new ProductoDato();
        if (params.size() != 3) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!Validate.isNumber(params.get(0))) {
            this.respuesta = "La cantidad debe ser un numero";
            return;
        }
        if (!salidaDatos.exist(Integer.parseInt(params.get(1)))) {
            this.respuesta = "El id de la salida no existe";
            return;
        }
        if (!productoDatos.exist(Integer.parseInt(params.get(2)))) {
            this.respuesta = "El id del producto no existe";
            return;
        }
    }
}
