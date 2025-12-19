/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.GrupoFamiliarDBA;
import Dtos.GrupoFamiliar.NewGrupoRequest;
import ModeloEntidad.GrupoFamiliar;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class GrupoFamiliarService {

    private GrupoFamiliarDBA grupoDBA = new GrupoFamiliarDBA();

    public GrupoFamiliar crearGrupo(NewGrupoRequest newGrupoRequest) throws SQLException {
        GrupoFamiliar grupo = new GrupoFamiliar(newGrupoRequest.getNombreGrupo(), newGrupoRequest.getIdUsuarioDueño());
        grupoDBA.crearGrupo(grupo);
        return grupo;
    }

    public GrupoFamiliar obtenerGrupo(int idGrupo) throws SQLException {
        return grupoDBA.obtenerGrupo(idGrupo);
    }

    public List<GrupoFamiliar> listarGrupos() throws SQLException {
        return grupoDBA.listarGrupos();
    }

    public void actualizarGrupo(int idGrupo, String nombreNuevo) throws SQLException {
        GrupoFamiliar grupo = grupoDBA.obtenerGrupo(idGrupo);
        if (grupo != null) {
            grupo.setNombreGrupo(nombreNuevo);
            grupoDBA.actualizarGrupo(grupo);
        }
    }

    public void eliminarGrupo(int idGrupo) throws SQLException {
        grupoDBA.eliminarGrupo(idGrupo);
    }

    public void agregarUsuarioAlGrupo(int idGrupo, int idUsuario) throws SQLException {
        grupoDBA.agregarUsuarioAlGrupo(idGrupo, idUsuario);
    }

    public void eliminarUsuarioDelGrupo(int idGrupo, int idUsuario) throws SQLException {
        grupoDBA.eliminarUsuarioDelGrupo(idGrupo, idUsuario);
    }
}
