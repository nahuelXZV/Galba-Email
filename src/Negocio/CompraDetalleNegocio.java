package Negocio;

import java.util.LinkedList;

import Datos.CompraDato;
import Datos.CompraDetalleDato;
import Datos.ProductoDato;
import Utils.Validate;

public class CompraDetalleNegocio {
    private String respuesta;

    private CompraDetalleDato compraDetalleDato;
    private CompraDato compraDatos;
    private ProductoDato productoDatos;

    public CompraDetalleNegocio() {
        compraDetalleDato = new CompraDetalleDato();
        compraDatos = new CompraDato();
        productoDatos = new ProductoDato();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        compraDetalleDato = new CompraDetalleDato(Integer.parseInt(params.get(0)), Float.parseFloat(params.get(1)),
                Integer.parseInt(params.get(2)), Integer.parseInt(params.get(3)));
        if (compraDetalleDato.create()) {
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
        compraDetalleDato = new CompraDetalleDato();
        if (compraDetalleDato.delete(Integer.parseInt(id))) {
            this.respuesta = "Eliminado exitosamente.";
        } else {
            this.respuesta = "No se pudo eliminar.";
        }
        return this.respuesta;
    }

    private void validateCreate(LinkedList<String> params) {
        compraDatos = new CompraDato();
        productoDatos = new ProductoDato();
        if (params.size() != 4) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!Validate.isNumber(params.get(0))) {
            this.respuesta = "La cantidad debe ser un numero";
            return;
        }
        if (!Validate.isFloat(params.get(1))) {
            this.respuesta = "El número no es válido, debe ser >=0 (0 | 0.0)";
            return;
        }
        if (!compraDatos.exist(Integer.parseInt(params.get(2)))) {
            this.respuesta = "El id de la compra no existe";
            return;
        }
        if (!productoDatos.exist(Integer.parseInt(params.get(3)))) {
            this.respuesta = "El id del producto no existe";
            return;
        }
    }
}
