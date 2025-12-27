/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.GrupoFamiliarDBA;
import ConexionDBA.UsuarioDBA;
import Dtos.GrupoFamiliar.GrupoMiembro;
import Dtos.GrupoFamiliar.MiembroGrupoRequest;
import Dtos.GrupoFamiliar.NewGrupoRequest;
import ModeloEntidad.GrupoFamiliar;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author alejandro
 */
public class GrupoFamiliarService {

    private GrupoFamiliarDBA grupoDBA = new GrupoFamiliarDBA();

    public GrupoFamiliar crearGrupo(NewGrupoRequest request) throws SQLException {
        GrupoFamiliar grupo = new GrupoFamiliar();
        grupo.setNombreGrupo(request.getNombreGrupo());
        grupo.setIdUsuarioDueño(request.getIdUsuarioDueño());

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

        int miembros = grupoDBA.contarMiembrosGrupo(idGrupo);

        if (miembros >= 5) {
            throw new SQLException("El grupo ya alcanzó el máximo de miembros");
        }

        if (grupoDBA.usuarioPerteneceAGrupo(idGrupo, idUsuario)) {
            throw new SQLException("El usuario ya pertenece al grupo");
        }

        grupoDBA.agregarUsuarioAlGrupo(idGrupo, idUsuario);
    }

    public void eliminarMiembroGrupo(int idGrupo, int idUsuarioSolicitante, int idUsuarioEliminar)
            throws SQLException {

        if (!grupoDBA.esDuenoGrupo(idGrupo, idUsuarioSolicitante)) {
            throw new SQLException("Solo el dueño del grupo puede eliminar miembros");
        }

        grupoDBA.eliminarUsuarioDelGrupo(idGrupo, idUsuarioEliminar);
    }

    public List<GrupoFamiliar> listarGruposUsuario(int idUsuario) throws SQLException {
        return grupoDBA.listarGruposPorUsuario(idUsuario);
    }

    public List<GrupoMiembro> listarGruposCreadosConMiembros(int idUsuario) throws SQLException {

        List<GrupoFamiliar> grupos = grupoDBA.listarGruposPorUsuario(idUsuario);
        List<GrupoMiembro> respuesta = new ArrayList<>();

        for (GrupoFamiliar g : grupos) {

            List<MiembroGrupoRequest> miembros
                    = grupoDBA.obtenerMiembrosGrupo(g.getIdGrupo());

            GrupoMiembro dto = new GrupoMiembro();
            dto.setIdGrupo(g.getIdGrupo());
            dto.setNombreGrupo(g.getNombreGrupo());
            dto.setMiembros(miembros);
            dto.setTotalMiembros(miembros.size() + 1);
            dto.setCupoDisponible(miembros.size() < 5);

            respuesta.add(dto);
        }
        return respuesta;
    }

    public List<GrupoFamiliar> listarGruposMiembro(int idUsuario) throws SQLException {
        return grupoDBA.listarGruposDondeEsMiembro(idUsuario);
    }

    public void aceptarInvitacion(int idGrupo, int idUsuario) throws SQLException {
        if (grupoDBA.contarMiembrosGrupo(idGrupo) >= 5) {
            throw new SQLException("El grupo familiar está lleno (máximo 5 miembros)");
        }

        grupoDBA.responderInvitacion(idGrupo, idUsuario, "ACEPTADA");

        //sincronizarJuegosUsuarioConGrupo(idUsuario, idGrupo);
    }

    /*
    private void sincronizarJuegosUsuarioConGrupo(int idUsuario, int idGrupo) throws SQLException {
        List<Integer> juegosComprados = usuarioJuegoDBA.obtenerIdsJuegosComprados(idUsuario);

        for (Integer idVideojuego : juegosComprados) {
            grupoDBA.agregarJuegoAGrupo(idGrupo, idVideojuego);
        }
    }
     */
    public Map<String, List<GrupoFamiliar>> obtenerGruposDelUsuario(int idUsuario) throws SQLException {
        Map<String, List<GrupoFamiliar>> resultado = new HashMap<>();

        List<GrupoFamiliar> creados = grupoDBA.listarGruposPorUsuario(idUsuario);

        List<GrupoFamiliar> miembro = grupoDBA.listarGruposDondeEsMiembro(idUsuario);

        resultado.put("creadosComoDueno", creados);
        resultado.put("gruposDondeSoyMiembro", miembro);

        return resultado;
    }

    public List<GrupoFamiliar> listarTodosLosGruposDelUsuario(int idUsuario) throws SQLException {
        // Primero: grupos donde es dueño
        List<GrupoFamiliar> comoDueno = grupoDBA.listarGruposPorUsuario(idUsuario);

        // Segundo: grupos donde es miembro (pero no dueño, para evitar duplicados)
        List<GrupoFamiliar> comoMiembro = grupoDBA.listarGruposDondeEsMiembro(idUsuario);

        // Unir listas evitando duplicados por idGrupo
        Set<Integer> idsAgregados = new HashSet<>();
        List<GrupoFamiliar> todos = new ArrayList<>();

        for (GrupoFamiliar g : comoDueno) {
            todos.add(g);
            idsAgregados.add(g.getIdGrupo());
        }

        for (GrupoFamiliar g : comoMiembro) {
            if (!idsAgregados.contains(g.getIdGrupo())) {
                todos.add(g);
                idsAgregados.add(g.getIdGrupo());
            }
        }

        return todos;
    }
}
