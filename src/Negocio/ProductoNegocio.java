package Negocio;

import java.util.LinkedList;

import Datos.ProductoDato;
import Utils.Validate;

public class ProductoNegocio {

    private String respuesta;

    private ProductoDato productoDato;

    public ProductoNegocio() {
        productoDato = new ProductoDato();
    }

    public String createProducto(LinkedList<String> params) {
        this.validateCreateProducto(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        productoDato = new ProductoDato(0, params.get(0), params.get(1), params.get(2), Float.parseFloat(params.get(3)),
                Integer.parseInt(params.get(4)), params.get(5), params.get(6));
        if (productoDato.createProducto()) {
            this.respuesta = "Creado exitosamente.";
        } else {
            this.respuesta = "No se pudo crear.";
        }
        return this.respuesta;
    }

    public String updateProducto(LinkedList<String> params) {
        validateUpdateProducto(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        productoDato = new ProductoDato(0, params.get(0), params.get(1), params.get(2), Float.parseFloat(params.get(3)),
                Integer.parseInt(params.get(4)), params.get(5), params.get(6));
        if (productoDato.updateProducto()) {
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
        productoDato = new ProductoDato();
        if (productoDato.delete(Integer.parseInt(id))) {
            this.respuesta = "Eliminado exitosamente.";
        } else {
            this.respuesta = "No se pudo eliminar.";
        }
        return this.respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return productoDato.getAll(params);
    }

    private void validateCreateProducto(LinkedList<String> params) {
        productoDato = new ProductoDato();
        if (params.size() != 7) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!Validate.isString(params.get(0))) {
            this.respuesta = "El nombre debe ser un string y no puede ser vacio";
            return;
        }
        if (!Validate.isUrl(params.get(1))) {
            this.respuesta = "Debe ingresar una URL de la imagen";
            return;
        }
        if (!Validate.isString(params.get(2))) {
            this.respuesta = "El tamaño debe ser un string y no puede ser vacío";
            return;
        }
        if (!Validate.isFloat(params.get(3))) {
            this.respuesta = "El número no es válido, debe ser >=0 (0 | 0.0)";
            return;
        }
        if (!Validate.isNumber(params.get(4))) {
            this.respuesta = "El número no es válido, debe ser >=0";
            return;
        }
        if (!Validate.isString(params.get(5))) {
            this.respuesta = "La descripción debe ser un string y no puede ser vacío";
            return;
        }
        if (!Validate.isCategoria(params.get(6).toLowerCase())) {
            this.respuesta = "Categoría no válida, debe corresponder a los siguientes: herramientas, iluminacion, pegado y sellado, abrasivos, pinturas e impermeabilizantes, seguridad industrial, cementos y morteros, escaleras y carretillas, limpieza, materiales electricos, plomeria, herrajes y cerraduras";
            return;
        }
    }

    private void validateUpdateProducto(LinkedList<String> params) {
        productoDato = new ProductoDato();
        if (Validate.hasSize(params, 7)) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!Validate.isNumber(params.get(0))) {
            this.respuesta = "El id debe ser un numero";
            return;
        }
        if (!productoDato.exist(Integer.parseInt(params.get(0)))) {
            this.respuesta = "El usuario no existe";
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
            this.respuesta = "La contraseña debe ser un string y debe ser mayor a 8 caracteres";
            return;
        }
        if (!Validate.isString(params.get(4))) {
            this.respuesta = "El cargo no puede ser vacio";
            return;
        }
        if (!Validate.isBoolean(params.get(5))) {
            this.respuesta = "El isPersonal debe ser un boolean (true o false)";
            return;
        }
        if (!Validate.isBoolean(params.get(6))) {
            this.respuesta = "El isAdministrador debe ser un boolean (true o false)";
            return;
        }
    }

}
