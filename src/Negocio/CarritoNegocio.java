package Negocio;

import Datos.CarritoDato;
import Datos.UsuarioDato;
import Utils.Validate;

public class CarritoNegocio {
    private CarritoDato carritoDato;
    private UsuarioDato usuarioDato;

    public CarritoNegocio() {
        carritoDato = new CarritoDato();
        usuarioDato = new UsuarioDato();
    }

    public String create(String email) {
        try {
            int usuario_id = usuarioDato.idByEmail(email);
            if (usuario_id == -1) {
                return "El usuario no existe o ya tiene un carrito, por favor elimine el carrito para crear uno nuevo.";
            }
            carritoDato = new CarritoDato(usuario_id);
            if (carritoDato.create()) {
                return carritoDato.getAll(usuario_id);
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
            carritoDato = new CarritoDato();
            if (carritoDato.delete(Integer.parseInt(id))) {
                return "Eliminado exitosamente.";
            }
            return "No se pudo eliminar.";
        } catch (Exception e) {
            return "Error del sistema. Intente nuevamente.";
        }
    }

    public String getAll(String email) {
        try {
            int usuario_id = usuarioDato.idByEmail(email);
            if (usuario_id == -1) {
                return "El usuario no existe o no tiene un carrito.";
            }
            return carritoDato.getAll(usuario_id);
        } catch (Exception e) {
            return "Error del sistema. Intente nuevamente.";
        }
    }
}
