package Negocio;

import java.util.LinkedList;

import Datos.PedidoDato;
import Datos.UsuarioDato;
import Utils.Validate;

public class PedidoNegocio {
    private PedidoDato pedidoDato;
    private UsuarioDato usuarioDato;

    public PedidoNegocio() {
        pedidoDato = new PedidoDato();
        usuarioDato = new UsuarioDato();
    }

    public String create(String email, String nit) {
        try {
            int usuario_id = usuarioDato.idByEmail(email);
            if (usuario_id == -1) {
                return "El usuario no existe.";
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
