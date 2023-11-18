package Negocio;

import java.util.LinkedList;

import Datos.CarritoDato;
import Datos.PedidoDato;
import Datos.UsuarioDato;
import Utils.Validate;

public class PedidoNegocio {
    private String respuesta;

    private PedidoDato pedidoDato;
    private UsuarioDato usuarioDato;

    public PedidoNegocio() {
        pedidoDato = new PedidoDato();
        usuarioDato = new UsuarioDato();
    }

    public String create(String email, String nit) {
        int usuario_id = usuarioDato.idByEmail(email);
        if (usuario_id == -1) {
            return "El usuario no existe.";
        }
        pedidoDato = new PedidoDato(usuario_id);
        if (pedidoDato.create()) {
            int id = pedidoDato.getLastPedido(usuario_id);
            this.respuesta = pedidoDato.getQR(id, nit);
        } else {
            this.respuesta = "No se pudo crear.";
        }
        return this.respuesta;
    }

    public String getOne(String id, String email) {
        if (!Validate.isNumber(id)) {
            this.respuesta = "El id debe ser un numero";
            return this.respuesta;
        }
        int usuario_id = usuarioDato.idByEmail(email);
        if (usuario_id == -1) {
            return "El usuario no existe o ya tiene un carrito, por favor elimine el carrito para crear uno nuevo.";
        }
        if (pedidoDato.exist(Integer.parseInt(id), usuario_id)) {
            this.respuesta = pedidoDato.getOne(Integer.parseInt(id));
        } else {
            this.respuesta = "El pedido no existe.";
        }
        return this.respuesta;
    }

    public String getAll(LinkedList<String> params, String email) {
        int usuario_id = usuarioDato.idByEmail(email);
        if (usuario_id == -1) {
            return "El usuario no existe o ya tiene un carrito, por favor elimine el carrito para crear uno nuevo.";
        }
        return pedidoDato.getAll(params, usuario_id);
    }
}
