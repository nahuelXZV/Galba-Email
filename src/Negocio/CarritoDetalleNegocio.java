package Negocio;

import java.util.LinkedList;

import Datos.CarritoDato;
import Datos.CarritoDetalleDato;
import Datos.ProductoDato;
import Datos.UsuarioDato;
import Utils.Validate;

public class CarritoDetalleNegocio {
    private String respuesta;

    private CarritoDetalleDato carritoDetalleDato;
    private CarritoDato carritoDatos;
    private ProductoDato productoDatos;
    private UsuarioDato usuarioDato;

    public CarritoDetalleNegocio() {
        carritoDetalleDato = new CarritoDetalleDato();
        carritoDatos = new CarritoDato();
        productoDatos = new ProductoDato();
        usuarioDato = new UsuarioDato();
    }

    public String create(LinkedList<String> params, String email) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        int usuario_id = usuarioDato.idByEmail(email);
        if (usuario_id == -1) {
            return "El usuario no existe.";
        }
        Float precio = productoDatos.getPrecio(Integer.parseInt(params.get(1)));
        int carrito_id = carritoDatos.getIdCarritoByUser(usuario_id);
        carritoDetalleDato = new CarritoDetalleDato(Integer.parseInt(params.get(0)), precio,
                carrito_id, Integer.parseInt(params.get(1)));
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
        productoDatos = new ProductoDato();
        if (params.size() != 2) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!Validate.isNumber(params.get(0))) {
            this.respuesta = "La cantidad debe ser un numero";
            return;
        }
        if (!productoDatos.exist(Integer.parseInt(params.get(1)))) {
            this.respuesta = "El id del producto no existe";
            return;
        }
    }
}
