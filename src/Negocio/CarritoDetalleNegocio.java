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
        try {
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
                return carritoDatos.getAll(usuario_id);
            }
            return "No se pudo crear.";
        } catch (Exception e) {
            return "Error del sistema. Intente nuevamente.";
        }
    }

    public String delete(String id) {
        try {
            if (!Validate.isNumber(id)) {
                return "El id del carrito debe ser un numero";
            }
            if (!carritoDetalleDato.exist(Integer.parseInt(id))) {
                return "El id del carrito no existe";
            }
            carritoDetalleDato = new CarritoDetalleDato();
            if (carritoDetalleDato.delete(Integer.parseInt(id))) {
                return "Eliminado exitosamente.";
            }
            return "No se pudo eliminar.";
        } catch (Exception e) {
            return "Error del sistema. Intente nuevamente.";
        }
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
