/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.ReporteSistemaDBA;
import Resources.ReporteAdministrador.GananciaSistemaDTO;
import Resources.ReporteAdministrador.IngresoEmpresaDTO;
import Resources.ReporteAdministrador.RankingUsuarioDTO;
import Resources.ReporteAdministrador.TopBalanceDTO;
import Resources.ReporteAdministrador.TopCalidaDTO;
import Resources.ReporteAdministrador.TopVentaDTO;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author alejandro
 */
public class ReporteSistemaService {

    private ReporteSistemaDBA reporteSistemaDBA = new ReporteSistemaDBA();

    /*Service para reportes de administrador de sistema*/
    public GananciaSistemaDTO reporteGananciaGlobal() {
        return new ReporteSistemaDBA().obtenerReporteGanancia();
    }

    public byte[] generarPDFGanancias(GananciaSistemaDTO dto, String inicio, String fin) throws Exception {

        Map<String, Object> params = new HashMap<>();
        params.put("totalIngresos", dto.getTotalIngresos());
        params.put("totalEmpresas", dto.getTotalEmpresas());
        params.put("comisionPlataforma", dto.getComisionPlataforma());
        params.put("fechaInicio", inicio);
        params.put("fechaFin", fin);

        InputStream jasper = getClass().getResourceAsStream("/Reportes/ReporteGananciasGlobales.jasper");

        JasperPrint print = JasperFillManager.fillReport(
                jasper,
                params,
                new JREmptyDataSource()
        );

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, out);

        return out.toByteArray();
    }

    public List<TopVentaDTO> topVentas() {
        return new ReporteSistemaDBA().topVentas();
    }

    public List<TopCalidaDTO> topCalidad() {
        return new ReporteSistemaDBA().topCalidad();
    }

    public List<TopBalanceDTO> topBalance() {
        return new ReporteSistemaDBA().topBalance();
    }

    public List<TopVentaDTO> filtrarCategoria(String categoria) {
        return new ReporteSistemaDBA().filtrarPorCategoria(categoria);
    }

    public List<TopVentaDTO> filtrarClasificacion(String clasificacion) {
        return new ReporteSistemaDBA().filtrarPorClasificacion(clasificacion);
    }

    public byte[] generarReporteTopVentaPDF() throws Exception {

        List<TopVentaDTO> lista
                = reporteSistemaDBA.topVentas();

        if (lista == null || lista.isEmpty()) {
            throw new Exception("No hay datos para generar el reporte");
        }

        InputStream jasper = getClass()
                .getResourceAsStream("/Reportes/ReporteTopVentas.jasper");

        JRBeanCollectionDataSource dataSource
                = new JRBeanCollectionDataSource(lista);

        JasperPrint print = JasperFillManager.fillReport(
                jasper,
                null,
                dataSource
        );

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, out);

        return out.toByteArray();
    }

    public ArrayList<IngresoEmpresaDTO> reporteIngresosEmpresa(String fechaInicio, String fechaFin) {
        return new ReporteSistemaDBA().obtenerReporteIngresosEmpresa(fechaInicio, fechaFin);
    }

    public byte[] generarPDFIngresoEmpresaGlobal(String fechaInicio, String fechaFin) throws Exception {

        List<IngresoEmpresaDTO> lista
                = reporteSistemaDBA.obtenerReporteIngresosEmpresa(fechaInicio, fechaFin);

        if (lista == null || lista.isEmpty()) {
            throw new Exception("No hay datos para generar el reporte");
        }

        InputStream jasper = getClass()
                .getResourceAsStream("/Reportes/ReporteIngresoEmpresa.jasper");

        JRBeanCollectionDataSource dataSource
                = new JRBeanCollectionDataSource(lista);

        JasperPrint print = JasperFillManager.fillReport(
                jasper,
                null,
                dataSource
        );

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, out);

        return out.toByteArray();
    }

    public ArrayList<RankingUsuarioDTO> rankingUsuarios(String fechaInicio, String fechaFin) {
        return reporteSistemaDBA.obtenerRankingUsuarios(fechaInicio, fechaFin);
    }

    public byte[] exportarRankingPDF(String fechaInicio, String fechaFin) throws Exception {

        List<RankingUsuarioDTO> lista
                = reporteSistemaDBA.obtenerRankingUsuarios(fechaInicio, fechaFin);

        if (lista == null || lista.isEmpty()) {
            throw new Exception("No hay datos para generar el reporte");
        }

        InputStream jasper = getClass()
                .getResourceAsStream("/Reportes/ReporteRankingUsuario.jasper");

        JRBeanCollectionDataSource dataSource
                = new JRBeanCollectionDataSource(lista);

        JasperPrint print = JasperFillManager.fillReport(
                jasper,
                null,
                dataSource
        );

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, out);

        return out.toByteArray();
    }
}
