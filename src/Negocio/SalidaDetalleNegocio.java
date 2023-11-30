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
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        salidaDetalleDato = new SalidaDetalleDato(Integer.parseInt(params.get(0)),
                Integer.parseInt(params.get(1)), Integer.parseInt(params.get(2)));
        if (salidaDetalleDato.create()) {
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
        salidaDetalleDato = new SalidaDetalleDato();
        if (salidaDetalleDato.delete(Integer.parseInt(id))) {
            this.respuesta = "Eliminado exitosamente.";
        } else {
            this.respuesta = "No se pudo eliminar.";
        }
        return this.respuesta;
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
