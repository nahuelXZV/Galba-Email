package Negocio;

import java.util.LinkedList;

import Datos.ProveedorDato;
import Utils.Validate;

public class ProveedorNegocio {
    private String respuesta;
    private ProveedorDato proveedorDato;

    public String createProveedor(LinkedList<String> params) {
        this.validateCreateProveedor(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        proveedorDato = new ProveedorDato(0, params.get(0), params.get(1), params.get(2),
                params.get(3), params.get(4));
        if (proveedorDato.createProveedor()) {
            this.respuesta = "Creado exitosamente.";
        } else {
            this.respuesta = "No se pudo crear.";
        }
        return this.respuesta;
    }

    public String updateProveedor(LinkedList<String> params) {
        validateUpdateProveedor(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        proveedorDato = new ProveedorDato(0, params.get(0), params.get(1), params.get(2),
                params.get(3), params.get(4));
        if (proveedorDato.updateProveedor()) {
            this.respuesta = "Actualizado exitosamente.";
        } else {
            this.respuesta = "No se pudo actualizar.";
        }
        return this.respuesta;
    }

    public String delete(String id) {
        if (!Validate.isNumber(id)) {
            this.respuesta = "El id debe ser un numero";
            return this.respuesta;
        }
        proveedorDato = new ProveedorDato();
        if (proveedorDato.delete(Integer.parseInt(id))) {
            this.respuesta = "Eliminado exitosamente.";
        } else {
            this.respuesta = "No se pudo eliminar.";
        }
        return this.respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return proveedorDato.getAll(params);
    }

    private void validateCreateProveedor(LinkedList<String> params) {
        proveedorDato = new ProveedorDato();
        if (params.size() != 5) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!Validate.isString(params.get(0))) {
            this.respuesta = "El nombre debe ser un string y no puede ser vacio";
            return;
        }
        if (!Validate.isEmail(params.get(1))) {
            this.respuesta = "El email no es valido";
            System.out.println(this.respuesta);
            return;
        }
        if (proveedorDato.emailExist(params.get(1))) {
            this.respuesta = "El email ya existe";
            return;
        }
        if (!Validate.isString(params.get(2))) {
            this.respuesta = "El numero telefono no puede ser vacío";
            return;
        }
        if (!Validate.isString(params.get(3))) {
            this.respuesta = "La direccion debe ser un string y no puede ser vacio";
            return;
        }
        if (!Validate.isCategoria(params.get(4).toLowerCase())) {
            this.respuesta = "El numero de NIT no puede ser vacio";
            return;
        }
    }

    private void validateUpdateProveedor(LinkedList<String> params) {
        proveedorDato = new ProveedorDato();
        if (Validate.hasSize(params, 5)) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!Validate.isNumber(params.get(0))) {
            this.respuesta = "El id debe ser un numero";
            return;
        }
        if (!proveedorDato.exist(Integer.parseInt(params.get(0)))) {
            this.respuesta = "El proveedor no existe";
            return;
        }
        if (!Validate.isString(params.get(1))) {
            this.respuesta = "El nombre debe ser un string y no puede ser vacio";
            return;
        }
        if (!Validate.isEmail(params.get(2))) {
            this.respuesta = "El email no es valido";
            return;
        }
        if (!Validate.isString(params.get(3))) {
            this.respuesta = "El numero de telefono no puede ser vacio";
            return;
        }
        if (!Validate.isString(params.get(4))) {
            this.respuesta = "La direccion no puede ser vacio";
            return;
        }
        if (!Validate.isString(params.get(5))) {
            this.respuesta = "El numero de NIT no puede ser vacio";
            return;
        }
    }

}
