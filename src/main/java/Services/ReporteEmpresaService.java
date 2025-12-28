/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.ReporteEmpresaDBA;
import Excepciones.JasperException;
import Resources.ReporteEmpresa.FeedbackCalificacionDTO;
import Resources.ReporteEmpresa.FeedbackComentarioDTO;
import Resources.ReporteEmpresa.FeedbackPeorCalificacionDTO;
import Resources.ReporteEmpresa.TopVentaEmpresaDTO;
import Resources.ReporteEmpresa.VentaPropiaDTO;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author alejandro
 */
public class ReporteEmpresaService {

    private ReporteEmpresaDBA reporteEmpresaDBA = new ReporteEmpresaDBA();

    public ArrayList<VentaPropiaDTO> reporteVentasPropias(int idEmpresa) {
        return reporteEmpresaDBA.reporteVentaPropia(idEmpresa);
    }

    public byte[] generarPDFVentaPropia(Integer idEmpresa) throws Exception {

        List<VentaPropiaDTO> lista
                = new ReporteEmpresaDBA().reporteVentaPropia(idEmpresa);

        if (lista.isEmpty()) {
            throw new Exception("No hay datos para generar el reporte");
        }

        InputStream jasper = getClass()
                .getResourceAsStream("/Reportes/ReporteVentaPropia.jasper");

        JRBeanCollectionDataSource dataSource
                = new JRBeanCollectionDataSource(lista);

        Map<String, Object> params = new HashMap<>();
        params.put("TITLE", "Reporte de Ventas Propias");

        JasperPrint print = JasperFillManager.fillReport(
                jasper,
                params,
                dataSource
        );

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, out);

        return out.toByteArray();
    }

    public ArrayList<FeedbackCalificacionDTO> calificacionPromedio(int idEmpresa) {
        return reporteEmpresaDBA.obtenerCalificacionesPromedio(idEmpresa);
    }

    public byte[] generarPDFCalificaciones(Integer idEmpresa) throws Exception, JasperException {

        List<FeedbackCalificacionDTO> lista
                = reporteEmpresaDBA.obtenerCalificacionesPromedio(idEmpresa);

        if (lista.isEmpty()) {
            throw new Exception("No hay datos para generar el reporte");
        }

        InputStream jasper = getClass()
                .getResourceAsStream("/Reportes/ReporteCalificacionesPromedio.jasper");

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

    public ArrayList<FeedbackComentarioDTO> mejoresComentarios(int idEmpresa) {
        return reporteEmpresaDBA.obtenerMejoresComentarios(idEmpresa);
    }

    public byte[] generarPDFMejoresComentarios(int idEmpresa) throws Exception, JasperException {

        List<FeedbackComentarioDTO> lista = reporteEmpresaDBA.obtenerMejoresComentarios(idEmpresa);

        if (lista == null || lista.isEmpty()) {
            throw new Exception("No hay datos para generar el reporte");
        }

        InputStream jasper = getClass().getResourceAsStream("/Reportes/ReporteFeedbackComentarios.jasper");
        if (jasper == null) {
            throw new Exception("No se encontró el archivo del reporte en: /Reportes/ReporteFeedbackComentarios.jasper");
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("fecha", lista.get(0).getFecha());
        parametros.put("nombreEmpresa", lista.get(0).getNombreEmpresa());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);

        JasperPrint print = JasperFillManager.fillReport(jasper, parametros, dataSource);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, out);

        return out.toByteArray();
    }

    public ArrayList<FeedbackPeorCalificacionDTO> peoresCalificaciones(int idEmpresa) {
        return reporteEmpresaDBA.obtenerPeoresCalificaciones(idEmpresa);
    }

    public byte[] generarPDFPeorCalificacion(int idEmpresa) throws Exception, JasperException {

        List<FeedbackPeorCalificacionDTO> lista = reporteEmpresaDBA.obtenerPeoresCalificaciones(idEmpresa);

        if (lista == null || lista.isEmpty()) {
            throw new Exception("No hay datos para generar el reporte");
        }

        InputStream jasper = getClass().getResourceAsStream("/Reportes/ReporteFeedbackPeorCalificacion.jasper");
        if (jasper == null) {
            throw new Exception("No se encontró el archivo del reporte en: /Reportes/ReporteFeedbackPeorCalificacion.jasper");
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("fecha", lista.get(0).getFecha());
        parametros.put("nombreEmpresa", lista.get(0).getNombreEmpresa());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);

        JasperPrint print = JasperFillManager.fillReport(jasper, parametros, dataSource);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, out);

        return out.toByteArray();
    }

    public List<TopVentaEmpresaDTO> top5Juegos(int idEmpresa, String fechaInicio, String fechaFin) {
        return reporteEmpresaDBA.top5Juegos(idEmpresa, fechaInicio, fechaFin);
    }

    public byte[] generarPDFTop5(int idEmpresa, String fechaInicio, String fechaFin) throws Exception {

        List<TopVentaEmpresaDTO> lista = reporteEmpresaDBA.top5Juegos(idEmpresa, fechaInicio, fechaFin);

        if (lista == null || lista.isEmpty()) {
            throw new Exception("No hay datos para generar el reporte");
        }

        InputStream jasper = getClass().getResourceAsStream("/Reportes/ReporteTop5Ventas.jasper");
        if (jasper == null) {
            throw new Exception("No se encontró el archivo del reporte en: /Reportes/ReporteTop5Ventas.jasper");
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("fechaReporte", lista.get(0).getFechaReporte());
        parametros.put("nombreEmpresa", lista.get(0).getNombreEmpresa());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);

        JasperPrint print = JasperFillManager.fillReport(jasper, parametros, dataSource);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, out);

        return out.toByteArray();
    }

}
