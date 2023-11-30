package Negocio;

import Datos.CarritoDato;
import Datos.UsuarioDato;
import Utils.Validate;

public class CarritoNegocio {
    private String respuesta;

    private CarritoDato carritoDato;
    private UsuarioDato usuarioDato;

    public CarritoNegocio() {
        carritoDato = new CarritoDato();
        usuarioDato = new UsuarioDato();
    }

    public String create(String email) {
        int usuario_id = usuarioDato.idByEmail(email);
        if (usuario_id == -1) {
            return "El usuario no existe o ya tiene un carrito, por favor elimine el carrito para crear uno nuevo.";
        }
        carritoDato = new CarritoDato(usuario_id);
        if (carritoDato.create()) {
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
        carritoDato = new CarritoDato();
        if (carritoDato.delete(Integer.parseInt(id))) {
            this.respuesta = "Eliminado exitosamente.";
        } else {
            this.respuesta = "No se pudo eliminar.";
        }
        return this.respuesta;
    }

    public String getAll(String email) {
        int usuario_id = usuarioDato.idByEmail(email);
        if (usuario_id == -1) {
            return "El usuario no existe o no tiene un carrito.";
        }
        return carritoDato.getAll(usuario_id);
    }
}
