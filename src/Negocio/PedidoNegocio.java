package Negocio;

import java.util.LinkedList;

import Datos.CarritoDato;
import Datos.PedidoDato;
import Datos.UsuarioDato;
import Utils.Validate;

public class PedidoNegocio {
    private PedidoDato pedidoDato;
    private UsuarioDato usuarioDato;
    private CarritoDato carritoDato;

    public PedidoNegocio() {
        pedidoDato = new PedidoDato();
        usuarioDato = new UsuarioDato();
        carritoDato = new CarritoDato();
    }

    public String create(String email, String nit) {
        try {
            int usuario_id = usuarioDato.idByEmail(email);
            if (usuario_id == -1) {
                return "El usuario no existe.";
            }
            int carrito_id = carritoDato.getIdCarritoByUser(usuario_id);
            System.out.println("carrito_id: " + carrito_id);
            String validate_response = carritoDato.validateStock(carrito_id);
            if (validate_response != "") {
                return validate_response;
            }
            pedidoDato = new PedidoDato(usuario_id);
            if (pedidoDato.create()) {
                int pedido_id = pedidoDato.getLastPedido(usuario_id);
                return pedidoDato.getQR(pedido_id, usuario_id, nit);
            }
            return "No se pudo crear.";
        } catch (Exception e) {
            return "Error del sistema. Intente nuevamente.";
        }
    }

    public String getOne(String id, String email) {
        try {
            if (!Validate.isNumber(id)) {
                return "El id debe ser un numero";
            }
            int usuario_id = usuarioDato.idByEmail(email);
            if (usuario_id == -1) {
                return "El usuario no existe.";
            }
            if (pedidoDato.exist(Integer.parseInt(id), usuario_id)) {
                return pedidoDato.getOne(Integer.parseInt(id));
            }
            return "El pedido no existe.";
        } catch (Exception e) {
            return "Error del sistema. Intente nuevamente.";
        }
    }

    public String getAll(LinkedList<String> params, String email) {
        try {
            int usuario_id = usuarioDato.idByEmail(email);
            if (usuario_id == -1) {
                return "El usuario no existe.";
            }
            return pedidoDato.getAll(params, usuario_id);
        } catch (Exception e) {
            return "Error del sistema. Intente nuevamente.";
        }
    }
}
