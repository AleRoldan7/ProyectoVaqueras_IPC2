/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.ReporteUsuarioDBA;
import Resources.ReporteUsuario.HistorialGastosDTO;
import java.util.ArrayList;

/**
 *
 * @author alejandro
 */
public class ReporteUsuarioService {
    
    private final ReporteUsuarioDBA reporte = new ReporteUsuarioDBA();

    public ArrayList<HistorialGastosDTO> historialGastos(int idUsuario) {
        return reporte.historialGastos(idUsuario);
    }
}
