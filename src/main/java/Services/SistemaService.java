/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.ListaDBA;
import ConexionDBA.SistemaDBA;
import Dtos.Categoria.CategoriaVideojuegoPendiente;
import Dtos.Empresa.NotificacionResponse;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class SistemaService {

    private SistemaDBA sistemaDBA = new SistemaDBA();
    private ListaDBA listaDBA = new ListaDBA();

    public List<CategoriaVideojuegoPendiente> obtenerCategoriasPendientes() {
        return listaDBA.listarCategoriasPendientes();
    }

    public void aprobarCategoria(int idCategoriaVideojuego) throws EntidadNotFound, SQLException {
        boolean actualizado = sistemaDBA.aprobarCategoria(idCategoriaVideojuego);
        if (!actualizado) {
            throw new EntidadNotFound("La solicitud no existe o ya fue procesada");
        }
    }

    public void rechazarCategoria(int idCategoriaVideojuego) throws EntidadNotFound, SQLException {
        boolean actualizado = sistemaDBA.rechazarCategoria(idCategoriaVideojuego);
        if (!actualizado) {
            throw new EntidadNotFound("La solicitud no existe o ya fue procesada");
        }
    }

    public void agregarComision(int idEmpresa, double porcentajeHumano) throws DatosInvalidos, SQLException {
        validarPorcentaje(porcentajeHumano);
        double valorDecimal = porcentajeHumano / 100.0;
        sistemaDBA.agregarOActualizarComision(idEmpresa, valorDecimal);
    }

    public double obtenerComisionGlobal() throws SQLException {
        return sistemaDBA.obtenerComisionGlobal() * 100.0;
    }

    public void actualizarComisionGlobal(double porcentajeHumano) throws DatosInvalidos, SQLException {
        validarPorcentaje(porcentajeHumano);
        double valorDecimal = porcentajeHumano / 100.0;
        sistemaDBA.actualizarComisionGlobalYAdjustarEmpresas(valorDecimal, porcentajeHumano);
    }

    public double obtenerComisionEmpresa(int idEmpresa) throws SQLException {
        return sistemaDBA.obtenerComisionEspecifica(idEmpresa) * 100.0;
    }

    public void actualizarComisionEmpresa(int idEmpresa, double porcentajeHumano) throws DatosInvalidos, SQLException {
        validarPorcentaje(porcentajeHumano);
        double valorDecimal = porcentajeHumano / 100.0;
        double globalDecimal = sistemaDBA.obtenerComisionGlobal();

        if (valorDecimal > globalDecimal) {
            throw new DatosInvalidos(
                    String.format("La comisión específica (%.2f%%) no puede superar la global (%.2f%%)",
                            porcentajeHumano, globalDecimal * 100.0)
            );
        }

        sistemaDBA.actualizarComisionEspecifica(idEmpresa, valorDecimal);
    }

    private void validarPorcentaje(double porcentaje) throws DatosInvalidos {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new DatosInvalidos("El porcentaje debe estar entre 0 y 100");
        }
    }

    public List<NotificacionResponse> obtenerNotificacionesUsuario(int idUsuario) throws SQLException {
        return sistemaDBA.obtenerNotificaciones(idUsuario);
    }

    public void marcarComoLeida(int idNotificacion, int idUsuario) throws SQLException {
        boolean success = sistemaDBA.marcarComoLeida(idNotificacion, idUsuario);
        if (!success) {
            throw new RuntimeException("Notificación no encontrada o no pertenece al usuario");
        }
    }
}
