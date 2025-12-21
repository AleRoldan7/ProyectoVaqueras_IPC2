/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.ReporteEmpresaDBA;
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

    public ArrayList<FeedbackComentarioDTO> mejoresComentarios(int idEmpresa) {
        return reporteEmpresaDBA.obtenerMejoresComentarios(idEmpresa);
    }

    public ArrayList<FeedbackPeorCalificacionDTO> peoresCalificaciones(int idEmpresa) {
        return reporteEmpresaDBA.obtenerPeoresCalificaciones(idEmpresa);
    }

    public ArrayList<TopVentaEmpresaDTO> top5Juegos(int idEmpresa, String fechaInicio, String fechaFin) {
        return new ReporteEmpresaDBA().obtenerTop5Juegos(idEmpresa, fechaInicio, fechaFin);
    }

}
