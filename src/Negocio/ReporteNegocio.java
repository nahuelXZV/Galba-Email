package Negocio;

import Datos.ReporteDato;

public class ReporteNegocio {
    private ReporteDato ReporteDato;

    public ReporteNegocio() {
        ReporteDato = new ReporteDato();
    }

    public String reporteCompras() {
        return ReporteDato.reporteCompras();
    }

    public String reporteVentas() {
        return ReporteDato.reporteVentas();
    }

    public String reporteInventario() {
        return ReporteDato.reporteInventario();
    }

}
