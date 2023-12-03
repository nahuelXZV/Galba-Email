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
        try {
            this.validateCreate(params);
            if (this.respuesta != null) {
                return this.respuesta;
            }
            compraDetalleDato = new CompraDetalleDato(Integer.parseInt(params.get(0)), Float.parseFloat(params.get(1)),
                    Integer.parseInt(params.get(2)), Integer.parseInt(params.get(3)));
            if (compraDetalleDato.create()) {
                return compraDatos.getAll(Integer.parseInt(params.get(2)));
            }
            return "No se pudo crear, intente nuevamente.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error del sistema. Intente nuevamente.";
        }
    }

    public String delete(String id) {
        try {
            if (!Validate.isNumber(id)) {
                return "El id debe ser un numero";
            }
            if (!compraDetalleDato.exist(Integer.parseInt(id))) {
                return "El id del detalle de compra no existe.";
            }
            compraDetalleDato = new CompraDetalleDato();
            if (compraDetalleDato.delete(Integer.parseInt(id))) {
                return "Eliminado exitosamente.";
            }
            return "No se pudo eliminar, intente nuevamente.";
        } catch (Exception e) {
            return "Error del sistema. Intente nuevamente.";
        }
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
