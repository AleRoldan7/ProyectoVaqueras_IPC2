/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.ReporteUsuarioDBA;
import Excepciones.JasperException;
import Resources.ReporteUsuario.AnalisisBibliotecaDTO;
import Resources.ReporteUsuario.BibliotecaFamiliarUsoDTO;
import Resources.ReporteUsuario.CategoriaFavoritaDTO;
import Resources.ReporteUsuario.HistorialGastosDTO;
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
public class ReporteUsuarioService {

    private final ReporteUsuarioDBA reporte = new ReporteUsuarioDBA();

    public ArrayList<HistorialGastosDTO> historialGastos(int idUsuario) {
        return reporte.historialGastos(idUsuario);
    }

    public byte[] exportarPDFHistorial(int idUsuario) throws Exception, JasperException {

        List<HistorialGastosDTO> lista = reporte.historialGastos(idUsuario);

        if (lista == null || lista.isEmpty()) {
            throw new Exception("No hay datos para generar el reporte");
        }

        InputStream jasper = getClass().getResourceAsStream("/Reportes/ReporteHistorialGastos.jasper");
        if (jasper == null) {
            throw new Exception("No se encontró el archivo del reporte en: /Reportes/ReporteHistorialGastos.jasper");
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nickname", lista.get(0).getNickname());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);

        JasperPrint print = JasperFillManager.fillReport(jasper, parametros, dataSource);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, out);

        return out.toByteArray();
    }

    public List<AnalisisBibliotecaDTO> analisisBiblioteca(int idUsuario) {
        return reporte.obtenerAnalisisBiblioteca(idUsuario);
    }

    public byte[] exportarPDFAnalisisBiblioteca(int idUsuario) throws Exception, JasperException {

        List<AnalisisBibliotecaDTO> lista = reporte.obtenerAnalisisBiblioteca(idUsuario);

        if (lista == null || lista.isEmpty()) {
            throw new Exception("No hay datos para generar el reporte");
        }

        InputStream jasper = getClass().getResourceAsStream("/Reportes/ReporteBibliotecaUsuario.jasper");
        if (jasper == null) {
            throw new Exception("No se encontró el archivo del reporte en: /Reportes/ReporteBibliotecaUsuario.jasper");
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nickname", lista.get(0).getNickname());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);

        JasperPrint print = JasperFillManager.fillReport(jasper, parametros, dataSource);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, out);

        return out.toByteArray();
    }

    public List<CategoriaFavoritaDTO> categoriaFavorita(int idUsuario) {
        return reporte.obtenerCategoriasFavoritas(idUsuario);
    }

    public byte[] exportarPDFCategoriaFavorita(int idUsuario) throws Exception, JasperException {

        List<CategoriaFavoritaDTO> lista = reporte.obtenerCategoriasFavoritas(idUsuario);

        if (lista == null || lista.isEmpty()) {
            throw new Exception("No hay datos para generar el reporte");
        }

        InputStream jasper = getClass().getResourceAsStream("/Reportes/ReporteCategoriaFavorita.jasper");
        if (jasper == null) {
            throw new Exception("No se encontró el archivo del reporte en: /Reportes/ReporteCategoriaFavorita.jasper");
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nickname", lista.get(0).getNickname());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);

        JasperPrint print = JasperFillManager.fillReport(jasper, parametros, dataSource);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, out);

        return out.toByteArray();
    }

    public List<BibliotecaFamiliarUsoDTO> usoBiblioteca(int idUsuario) {
        return reporte.obtenerUsoBiblioteca(idUsuario);
    }

    public byte[] exportarPDFUsoBiblioteca(int idUsuario) throws Exception, JasperException {

        List<BibliotecaFamiliarUsoDTO> lista = reporte.obtenerUsoBiblioteca(idUsuario);

        if (lista == null || lista.isEmpty()) {
            throw new Exception("No hay datos para generar el reporte");
        }

        InputStream jasper = getClass().getResourceAsStream("/Reportes/ReporteBibliotecaFamiliar.jasper");
        if (jasper == null) {
            throw new Exception("No se encontró el archivo del reporte en: /Reportes/ReporteBibliotecaFamiliar.jasper");
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nickname", lista.get(0).getNickname());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);

        JasperPrint print = JasperFillManager.fillReport(jasper, parametros, dataSource);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, out);

        return out.toByteArray();
    }
}
