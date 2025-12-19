/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.ListaDBA;
import ConexionDBA.SistemaDBA;
import Dtos.Categoria.CategoriaVideojuegoPendiente;
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

    public void aprobarCategoria(int idCategoriaVideojuego) throws EntidadNotFound {
        try {
            boolean actualizado = sistemaDBA.aprobarCategoria(idCategoriaVideojuego);

            if (!actualizado) {
                throw new EntidadNotFound("La solicitud no existe o ya fue revisada");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rechazarCategoria(int idCategoriaVideojuego) throws EntidadNotFound {
        try {
            boolean actualizado = sistemaDBA.rechazarCategoria(idCategoriaVideojuego);

            if (!actualizado) {
                throw new EntidadNotFound("La solicitud no existe o ya fue revisada");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void agregarComision(int idEmpresa, double comision) {
        sistemaDBA.agregarComision(idEmpresa, comision);
    }

    public double obtenerComisionGlobal() throws SQLException {
        return sistemaDBA.obtenerComisionGlobal();
    }

    public void actualizarComisionGlobal(double nuevoPorcentaje) throws SQLException {
        sistemaDBA.actualizarComisionGlobal(nuevoPorcentaje);

        List<Integer> empresas = sistemaDBA.empresasConComisionMayor(nuevoPorcentaje);
        for (Integer idEmpresa : empresas) {
            sistemaDBA.actualizarComisionEspecifica(idEmpresa, nuevoPorcentaje);
        }
    }

    public double obtenerComisionEmpresa(int idEmpresa) throws SQLException {
        return sistemaDBA.obtenerComisionEspecifica(idEmpresa);
    }

    public void actualizarComisionEmpresa(int idEmpresa, double porcentaje) throws SQLException {
        double global = obtenerComisionGlobal();
        if (porcentaje > global) {
            throw new IllegalArgumentException("El porcentaje específico no puede ser mayor al global");
        }
        sistemaDBA.actualizarComisionEspecifica(idEmpresa, porcentaje);
    }
}
