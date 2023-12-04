package Datos;

import Servicios.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReporteDato {
    private final ConexionDB conexion;

    public ReporteDato() {
        conexion = new ConexionDB();
    }

    public String reporteCompras() {
        try {
            String url = "https://quickchart.io/chart?c=";
            String json = "{type:'bar',data:{labels:['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'], datasets:[{label:'Compras',data:";
            String data = getDataCompra();
            String grafica = url + json + data + "}]}}";
            String html = "<html><head><title>Reporte de compras</title></head><body><h1>Reporte de compras</h1><img src=\""
                    + grafica + "\"></body></html>";
            return html;
        } catch (Exception e) {
            return "Error en la generación del informe";
        }
    }

    private String getDataCompra() {
        String[] meses = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        String data = "[";
        for (int i = 0; i < 12; i++) {
            String sql = "SELECT SUM(monto_total) FROM compra WHERE fecha LIKE '%" + meses[i] + "/%'";
            try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    data += rs.getString(1) + ",";
                }
            } catch (SQLException e) {
                data += "0,";
            }
        }
        data = data.substring(0, data.length() - 1);
        data += "]";
        return data;
    }

    public String reporteVentas() {
        try {
            String url = "https://quickchart.io/chart?c=";
            String json = "{type:'bar',data:{labels:['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'], datasets:[{label:'Ventas',data:";
            String data = getDataVenta();
            String grafica = url + json + data + "}]}}";
            String html = "<html><head><title>Reporte de ventas</title></head><body><h1>Reporte de ventas</h1><img src=\""
                    + grafica + "\"></body></html>";
            return html;
        } catch (Exception e) {
            return "Error en la generación del informe";
        }
    }

    private String getDataVenta() {
        String[] meses = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        String data = "[";
        for (int i = 0; i < 12; i++) {
            String sql = "SELECT SUM(monto_total) FROM pedido WHERE fecha LIKE '%" + meses[i] + "/%'";
            try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    data += rs.getString(1) + ",";
                }
            } catch (SQLException e) {
                data += "0,";
            }
        }
        data = data.substring(0, data.length() - 1);
        data += "]";
        return data;
    }

    public String reporteInventario() {
        try {
            String url = "https://quickchart.io/chart?c=";
            String json = getMoreValueInventario();
            String grafica = url + json;
            String html = "<html><head><title>Reporte de valor del inventario</title></head><body><h1>Reporte de valor del inventario</h1><img src=\""
                    + grafica + "\"></body></html>";
            return html;
        } catch (Exception e) {
            return "Error en la generación del informe";
        }
    }

    private String getMoreValueInventario() {
        String sql = "SELECT nombre, (cantidad * precio) as valor FROM producto ORDER BY valor DESC;";
        String json = "{type:'doughnut',data:{labels:valueLabels, datasets:[{label:'Inventario',data:valueData}]},options:{plugins:{doughnutlabel:{labels:[{text: valueTotal,font:{size:20}},{text:'total'}]}}}}";
        String labels = "[";
        String data = "[";
        Float total = 0f;
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                labels += "'" + rs.getString(1) + "',";
                data += rs.getString(2) + ",";
                total += rs.getFloat(2);
            }
        } catch (SQLException e) {
            labels += "N/A,";
            data += "0,";
        }
        labels = labels.substring(0, labels.length() - 1);
        data = data.substring(0, data.length() - 1);
        labels += "]";
        data += "]";
        json = json.replace("valueLabels", labels);
        json = json.replace("valueData", data);
        json = json.replace("valueTotal", total.toString());
        return json;
    }

}
