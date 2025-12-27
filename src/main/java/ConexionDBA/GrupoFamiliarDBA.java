/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Dtos.GrupoFamiliar.InvitacionGrupo;
import Dtos.GrupoFamiliar.MiembroGrupoRequest;
import ModeloEntidad.GrupoFamiliar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class GrupoFamiliarDBA {

    private static final String CREAR_GRUPO_QUERY = "INSERT INTO grupo_familiar (nombre_grupo, id_usuario_dueño) VALUES (?, ?)";
    private static final String OBTENER_GRUPO_QUERY = "SELECT * FROM grupo_familiar WHERE id_grupo = ?";
    private static final String LISTAR_GRUPOS_QUERY = "SELECT * FROM grupo_familiar";
    private static final String ACTUALIZAR_GRUPO_QUERY = "UPDATE grupo_familiar SET nombre_grupo = ? WHERE id_grupo = ?";
    private static final String ELIMINAR_GRUPO_QUERY = "DELETE FROM grupo_familiar WHERE id_grupo = ?";

    private static final String AGREGAR_USUARIO_GRUPO_QUERY = "INSERT INTO grupo_familiar_usuario (id_grupo, id_usuario) VALUES (?, ?)";
    private static final String ELIMINAR_USUARIO_GRUPO_QUERY = "DELETE FROM grupo_familiar_usuario WHERE id_grupo = ? AND id_usuario = ?";

    private static final String LISTA_INVITACIONES_RECIBIDAS_QUERY
            = "SELECT "
            + " i.id_invitacion,"
            + " g.id_grupo,  "
            + " g.nombre_grupo, "
            + " i.id_usuario, "
            + " i.estado, "
            + " i.fecha  "
            + "FROM invitacion_grupo i "
            + "JOIN grupo_familiar g ON g.id_grupo = i.id_grupo "
            + "WHERE i.id_usuario = ? "
            + "AND i.estado = 'PENDIENTE'";

    private static final String LISTAR_GRUPOS_POR_USUARIO
            = "SELECT id_grupo, nombre_grupo, id_usuario_dueño "
            + "FROM grupo_familiar "
            + "WHERE id_usuario_dueño = ?";

    private static final String GRUPOS_USUARIO_QUERY = "SELECT id_grupo FROM grupo_familiar_usuario WHERE id_usuario = ?";

    private static final String INSERT_GRUPO_VIDEOJUEGO = "INSERT IGNORE INTO grupo_videojuego (id_grupo, id_videojuego) VALUES (?, ?)";

    private static final String OBTENER_MIEMBROS_GRUPO
            = "SELECT u.id_usuario, u.nombre, u.nickname "
            + "FROM grupo_familiar_usuario gfu "
            + "JOIN usuario u ON u.id_usuario = gfu.id_usuario "
            + "WHERE gfu.id_grupo = ?";

    private static final String CONTAR_MIEMBROS_GRUPO
            = "SELECT COUNT(*) FROM grupo_familiar_usuario WHERE id_grupo = ?";

    private static final String USUARIO_PERTENECE_GRUPO_QUERY
            = "SELECT 1 FROM grupo_familiar_usuario "
            + "WHERE id_grupo = ? AND id_usuario = ?";

    private static final String ES_CREADOR_GRUPO_QUERY
            = "SELECT 1 FROM grupo_familiar WHERE id_grupo = ? AND id_usuario_dueño = ?";

    private static final String LISTAR_GRUPOS_DONDE_ES_MIEMBRO
            = "SELECT g.id_grupo, g.nombre_grupo, g.id_usuario_dueño "
            + "FROM grupo_familiar g "
            + "JOIN grupo_familiar_usuario gfu ON g.id_grupo = gfu.id_grupo "
            + "WHERE gfu.id_usuario = ?";

    public void crearGrupo(GrupoFamiliar grupo) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement stmt = conn.prepareStatement(CREAR_GRUPO_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, grupo.getNombreGrupo());
            stmt.setInt(2, grupo.getIdUsuarioDueño());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                grupo.setIdGrupo(rs.getInt(1));
            }
        }
    }

    public GrupoFamiliar obtenerGrupo(int idGrupo) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement stmt = conn.prepareStatement(OBTENER_GRUPO_QUERY)) {
            stmt.setInt(1, idGrupo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                GrupoFamiliar g = new GrupoFamiliar();
                g.setIdGrupo(rs.getInt("id_grupo"));
                g.setNombreGrupo(rs.getString("nombre_grupo"));
                g.setIdUsuarioDueño(rs.getInt("id_usuario_dueño"));
                return g;
            }
        }
        return null;
    }

    public List<GrupoFamiliar> listarGrupos() throws SQLException {
        List<GrupoFamiliar> grupos = new ArrayList<>();
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement stmt = conn.prepareStatement(LISTAR_GRUPOS_QUERY); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                GrupoFamiliar g = new GrupoFamiliar();
                g.setIdGrupo(rs.getInt("id_grupo"));
                g.setNombreGrupo(rs.getString("nombre_grupo"));
                g.setIdUsuarioDueño(rs.getInt("id_usuario_dueño"));
                grupos.add(g);
            }
        }
        return grupos;
    }

    public void actualizarGrupo(GrupoFamiliar grupo) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement stmt = conn.prepareStatement(ACTUALIZAR_GRUPO_QUERY)) {
            stmt.setString(1, grupo.getNombreGrupo());
            stmt.setInt(2, grupo.getIdGrupo());
            stmt.executeUpdate();
        }
    }

    public void eliminarGrupo(int idGrupo) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement stmt = conn.prepareStatement(ELIMINAR_GRUPO_QUERY)) {
            stmt.setInt(1, idGrupo);
            stmt.executeUpdate();
        }
    }

    public void agregarUsuarioAlGrupo(int idGrupo, int idUsuario) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement stmt = conn.prepareStatement(AGREGAR_USUARIO_GRUPO_QUERY)) {
            stmt.setInt(1, idGrupo);
            stmt.setInt(2, idUsuario);
            stmt.executeUpdate();
        }
    }

    public void eliminarUsuarioDelGrupo(int idGrupo, int idUsuario) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement stmt = conn.prepareStatement(ELIMINAR_USUARIO_GRUPO_QUERY)) {
            stmt.setInt(1, idGrupo);
            stmt.setInt(2, idUsuario);
            stmt.executeUpdate();
        }
    }

    public void enviarInvitacion(int idGrupo, int idUsuario) throws SQLException {
        String sql = "INSERT INTO invitacion_grupo (id_grupo, id_usuario) VALUES (?, ?)";
        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idGrupo);
            ps.setInt(2, idUsuario);
            ps.executeUpdate();
        }
    }

    public void responderInvitacion(int idGrupo, int idUsuario, String respuesta) throws SQLException {
        if (!respuesta.equals("ACEPTADA") && !respuesta.equals("RECHAZADA")) {
            throw new IllegalArgumentException("Respuesta inválida");
        }
        String sql = "UPDATE invitacion_grupo SET estado = ? WHERE id_grupo = ? AND id_usuario = ?";
        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, respuesta);
            ps.setInt(2, idGrupo);
            ps.setInt(3, idUsuario);
            ps.executeUpdate();

            if ("ACEPTADA".equals(respuesta)) {
                agregarUsuarioAlGrupo(idGrupo, idUsuario);
            }
        }
    }

    public List<InvitacionGrupo> listarInvitacionesPendientes(int idUsuario) throws SQLException {
        String sql = "SELECT * FROM invitacion_grupo WHERE id_usuario = ? AND estado = 'PENDIENTE'";
        List<InvitacionGrupo> invitaciones = new ArrayList<>();
        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(LISTA_INVITACIONES_RECIBIDAS_QUERY)) {
            query.setInt(1, idUsuario);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                InvitacionGrupo inv = new InvitacionGrupo(
                        resultSet.getInt("id_invitacion"),
                        resultSet.getInt("id_grupo"),
                        resultSet.getString("nombre_grupo"),
                        resultSet.getInt("id_usuario"),
                        resultSet.getString("estado"),
                        resultSet.getTimestamp("fecha").toLocalDateTime()
                );
                invitaciones.add(inv);
            }
        }
        return invitaciones;
    }

    public List<GrupoFamiliar> listarGruposPorUsuario(int idUsuario) throws SQLException {
        List<GrupoFamiliar> grupos = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(LISTAR_GRUPOS_POR_USUARIO)) {

            query.setInt(1, idUsuario);

            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                GrupoFamiliar g = new GrupoFamiliar();
                g.setIdGrupo(rs.getInt("id_grupo"));
                g.setNombreGrupo(rs.getString("nombre_grupo"));
                g.setIdUsuarioDueño(rs.getInt("id_usuario_dueño"));
                grupos.add(g);
            }
        }
        return grupos;
    }

    public List<Integer> obtenerGruposUsuario(int idUsuario) throws SQLException {
        List<Integer> grupos = new ArrayList<>();

        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(GRUPOS_USUARIO_QUERY)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                grupos.add(rs.getInt("id_grupo"));
            }
        }
        return grupos;
    }

    public void agregarJuegoAGrupo(int idGrupo, int idVideojuego) throws SQLException {
        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(INSERT_GRUPO_VIDEOJUEGO)) {

            ps.setInt(1, idGrupo);
            ps.setInt(2, idVideojuego);
            ps.executeUpdate();
        }
    }

    public List<MiembroGrupoRequest> obtenerMiembrosGrupo(int idGrupo) throws SQLException {

        List<MiembroGrupoRequest> miembros = new ArrayList<>();

        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(OBTENER_MIEMBROS_GRUPO)) {

            ps.setInt(1, idGrupo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MiembroGrupoRequest miembro = new MiembroGrupoRequest();
                miembro.setIdUsuario(rs.getInt("id_usuario"));
                miembro.setNombre(rs.getString("nombre"));
                miembro.setNickname(rs.getString("nickname"));
                miembros.add(miembro);
            }
        }
        return miembros;
    }

    public int contarMiembrosGrupo(int idGrupo) throws SQLException {

        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(CONTAR_MIEMBROS_GRUPO)) {

            ps.setInt(1, idGrupo);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
    }

    public boolean usuarioPerteneceAGrupo(int idGrupo, int idUsuario) throws SQLException {

        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(USUARIO_PERTENECE_GRUPO_QUERY)) {

            ps.setInt(1, idGrupo);
            ps.setInt(2, idUsuario);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public boolean esDuenoGrupo(int idGrupo, int idUsuario) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(ES_CREADOR_GRUPO_QUERY)) {

            ps.setInt(1, idGrupo);
            ps.setInt(2, idUsuario);

            return ps.executeQuery().next();
        }
    }

    public List<GrupoFamiliar> listarGruposDondeEsMiembro(int idUsuario) throws SQLException {
        List<GrupoFamiliar> grupos = new ArrayList<>();
        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(LISTAR_GRUPOS_DONDE_ES_MIEMBRO)) {

            query.setInt(1, idUsuario);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                GrupoFamiliar g = new GrupoFamiliar();
                g.setIdGrupo(rs.getInt("id_grupo"));
                g.setNombreGrupo(rs.getString("nombre_grupo"));
                g.setIdUsuarioDueño(rs.getInt("id_usuario_dueño"));
                grupos.add(g);
            }
        }
        return grupos;
    }

  

}
