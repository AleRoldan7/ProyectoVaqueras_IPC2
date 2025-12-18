/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.ListaDBA;
import ConexionDBA.SistemaDBA;
import Dtos.Categoria.CategoriaVideojuegoPendiente;
import Excepciones.EntidadNotFound;
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

    public void aprobarCategoria(int id) throws EntidadNotFound {

        if (id <= 0) {
            throw new EntidadNotFound("ID inválido");
        }
        sistemaDBA.aprobarCategoria(id);
    }

    public void rechazarCategoria(int id) throws EntidadNotFound {

        if (id <= 0) {
            throw new EntidadNotFound("ID inválido");
        }
        sistemaDBA.rechazarCategoria(id);
    }

}
