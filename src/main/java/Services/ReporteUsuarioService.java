/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.ReporteUsuarioDBA;
import Resources.ReporteUsuario.AnalisisBibliotecaDTO;
import Resources.ReporteUsuario.BibliotecaFamiliarUsoDTO;
import Resources.ReporteUsuario.CategoriaFavoritaDTO;
import Resources.ReporteUsuario.HistorialGastosDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class ReporteUsuarioService {
    
    private final ReporteUsuarioDBA reporte = new ReporteUsuarioDBA();

    public ArrayList<HistorialGastosDTO> historialGastos(int idUsuario) {
        return reporte.historialGastos(idUsuario);
    }
    
    public List<AnalisisBibliotecaDTO> analisisBiblioteca(int idUsuario) {
        return reporte.obtenerAnalisisBiblioteca(idUsuario);
    }
    
    public List<CategoriaFavoritaDTO> categoriaFavorita(int idUsuario) {
        return  reporte.obtenerCategoriasFavoritas(idUsuario);
    }
    
    public List<BibliotecaFamiliarUsoDTO> usoBiblioteca(int idUsuario) {
        return  reporte.obtenerUsoBiblioteca(idUsuario);
    }
}
