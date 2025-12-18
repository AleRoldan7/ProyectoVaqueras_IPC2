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
import java.util.ArrayList;

/**
 *
 * @author alejandro
 */
public class ReporteEmpresaService {

    private ReporteEmpresaDBA reporteEmpresaDBA = new ReporteEmpresaDBA();

    public ArrayList<VentaPropiaDTO> reporteVentasPropias(int idEmpresa) {
        return reporteEmpresaDBA.reporteVentaPropia(idEmpresa);
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
