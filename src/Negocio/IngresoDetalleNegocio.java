package Negocio;

import java.util.LinkedList;

import Datos.IngresoDato;
import Datos.IngresoDetalleDato;
import Datos.ProductoDato;
import Utils.Validate;

public class IngresoDetalleNegocio {
    private String respuesta;

    private IngresoDetalleDato ingresoDetalleDato;
    private IngresoDato ingresoDatos;
    private ProductoDato productoDatos;

    public IngresoDetalleNegocio() {
        ingresoDetalleDato = new IngresoDetalleDato();
        ingresoDatos = new IngresoDato();
        productoDatos = new ProductoDato();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        ingresoDetalleDato = new IngresoDetalleDato(Integer.parseInt(params.get(0)),
                Integer.parseInt(params.get(1)), Integer.parseInt(params.get(2)));
        if (ingresoDetalleDato.create()) {
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
        ingresoDetalleDato = new IngresoDetalleDato();
        if (ingresoDetalleDato.delete(Integer.parseInt(id))) {
            this.respuesta = "Eliminado exitosamente.";
        } else {
            this.respuesta = "No se pudo eliminar.";
        }
        return this.respuesta;
    }

    private void validateCreate(LinkedList<String> params) {
        ingresoDatos = new IngresoDato();
        productoDatos = new ProductoDato();
        if (params.size() != 3) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!Validate.isNumber(params.get(0))) {
            this.respuesta = "La cantidad debe ser un numero";
            return;
        }
        if (!ingresoDatos.exist(Integer.parseInt(params.get(1)))) {
            this.respuesta = "El id del ingreso no existe";
            return;
        }
        if (!productoDatos.exist(Integer.parseInt(params.get(2)))) {
            this.respuesta = "El id del producto no existe";
            return;
        }
    }
}
