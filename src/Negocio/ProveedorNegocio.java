package Negocio;

import java.util.LinkedList;
import Datos.ProveedorDato;
import Utils.Validate;

public class ProveedorNegocio {
    private String respuesta;
    private ProveedorDato proveedorDato;

    public ProveedorNegocio() {
        proveedorDato = new ProveedorDato();
    }

    public String createProveedor(LinkedList<String> params) {
        try {
            this.validateCreateProveedor(params);
            if (this.respuesta != null) {
                return this.respuesta;
            }
            proveedorDato = new ProveedorDato(0, params.get(0), params.get(1), params.get(2),
                    params.get(3), params.get(4));
            if (proveedorDato.createProveedor()) {
                return "Creado exitosamente.";
            }
            return "No se pudo crear, intente nuevamente.";
        } catch (Exception e) {
            return "Error al crear proveedor";
        }
    }

    public String updateProveedor(LinkedList<String> params) {
        try {
            validateUpdateProveedor(params);
            if (this.respuesta != null) {
                return this.respuesta;
            }
            proveedorDato = new ProveedorDato(Integer.parseInt(params.get(0)), params.get(1), params.get(2),
                    params.get(3),
                    params.get(4), params.get(5));
            if (proveedorDato.updateProveedor()) {
                return "Actualizado exitosamente.";
            }
            return "No se pudo actualizar, intente nuevamente.";
        } catch (Exception e) {
            return "Error al actualizar proveedor";
        }
    }

    public String delete(String id) {
        try {
            if (!Validate.isNumber(id)) {
                return "El id debe ser un numero";
            }
            if (!proveedorDato.exist(Integer.parseInt(id))) {
                return "El proveedor no existe";
            }
            proveedorDato = new ProveedorDato();
            if (proveedorDato.delete(Integer.parseInt(id))) {
                return "Eliminado exitosamente.";
            }
            return "No se pudo eliminar, intente nuevamente.";
        } catch (Exception e) {
            return "Error al eliminar proveedor";
        }
    }

    public String getAll(LinkedList<String> params) {
        try {
            return proveedorDato.getAll(params);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error al obtener proveedores";
        }
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
        if (!Validate.isNumber(params.get(2))) {
            this.respuesta = "El numero telefono debe ser un numero y no puede ser vacio";
            return;
        }
        if (!Validate.isString(params.get(3))) {
            this.respuesta = "La direccion debe ser un string y no puede ser vacio";
            return;
        }
        if (!Validate.isString(params.get(4))) {
            this.respuesta = "El numero de NIT no puede ser vacio";
            return;
        }
    }

    private void validateUpdateProveedor(LinkedList<String> params) {
        proveedorDato = new ProveedorDato();
        if (params.size() != 6) {
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
        if (!Validate.isNumber(params.get(3))) {
            this.respuesta = "El numero de telefono debe ser un numero y no puede ser vacio";
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
