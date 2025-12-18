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

/**
 *
 * @author alejandro
 */
public class ReporteSistemaService {

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

    public ArrayList<IngresoEmpresaDTO> reporteIngresosEmpresa() {
        return new ReporteSistemaDBA().obtenerReporteIngresosEmpresa();
    }

    public ArrayList<RankingUsuarioDTO> rankingUsuarios() {
        return new ReporteSistemaDBA().obtenerRankingUsuarios();
    }

}
