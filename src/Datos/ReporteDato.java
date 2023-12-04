package Datos;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Servicios.ConexionDB;
import java.io.FileNotFoundException;

public class ReporteDato {
    private final ConexionDB conexion;

    private String tabla;
    private String key;
    private String valor;

    public ReporteDato(String tabla) {
        conexion = new ConexionDB();
        this.tabla = tabla;
        this.key = "";
        this.valor = "";
    }

    public ReporteDato(String tabla, String key, String valor) {
        conexion = new ConexionDB();
        this.tabla = tabla;
        this.key = key;
        this.valor = valor;
    }    

    public String GenerarReporte() throws FileNotFoundException {
        Document documento = new Document();
        try {
            String ruta = "../Reportes/" + "Prueba.pdf";
            PdfWriter.getInstance(documento, new FileOutputStream(ruta));
            documento.open();

            PdfPTable tabla = new PdfPTable(5);
            tabla.addCell("Nombre");
            tabla.addCell("Correo");
            tabla.addCell("Direccion");
            tabla.addCell("Telefono");
            tabla.addCell("Cargo");

            String sql = "SELECT nombre, correo, direccion, telefono, cargo FROM usuario";
            try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {                
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    tabla.addCell(rs.getString(1));
                    tabla.addCell(rs.getString(2));
                    tabla.addCell(rs.getString(3));
                }
                documento.add(tabla);
            }
            catch (SQLException e) {
                String mensaje = "No se pudieron listar los datos.";
                System.out.println(e.getMessage());
                return mensaje;
            }
            documento.close();
            return ruta;
        }
        catch (DocumentException err) {
            String mensaje = "No se pudo crear el reporte";
            System.out.println(err.getMessage());
            return mensaje;
        }        
    }
}
