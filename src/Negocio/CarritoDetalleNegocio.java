package Negocio;

import java.util.LinkedList;

import Datos.CarritoDato;
import Datos.CarritoDetalleDato;
import Utils.Validate;

public class CarritoDetalleNegocio {
    private String respuesta;

    private CarritoDetalleDato carritoDetalleDato;
    private CarritoDato carritoDatos;

    public CarritoDetalleNegocio() {
        carritoDetalleDato = new CarritoDetalleDato();
        carritoDatos = new CarritoDato();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        carritoDetalleDato = new CarritoDetalleDato(Integer.parseInt(params.get(0)), Float.parseFloat(params.get(1)),
                Integer.parseInt(params.get(2)), Integer.parseInt(params.get(3)));
        if (carritoDetalleDato.create()) {
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
        carritoDetalleDato = new CarritoDetalleDato();
        if (carritoDetalleDato.delete(Integer.parseInt(id))) {
            this.respuesta = "Eliminado exitosamente.";
        } else {
            this.respuesta = "No se pudo eliminar.";
        }
        return this.respuesta;
    }

    private void validateCreate(LinkedList<String> params) {
        carritoDatos = new CarritoDato();
        if (params.size() != 4) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!Validate.isNumber(params.get(0))) {
            this.respuesta = "La cantidad debe ser un numero";
            return;
        }
        if (!Validate.isFloat(params.get(1))) {
            this.respuesta = "El precio debe ser un numero";
            return;
        }
        if (carritoDatos.exist(Integer.parseInt(params.get(2)))) {
            this.respuesta = "El id del carrito no existe";
            return;
        }
        // if (carritoDatos.exist(Integer.parseInt(params.get(2)))) {
        // this.respuesta = "El id del carrito no existe";
        // return;
        // }
    }
}
