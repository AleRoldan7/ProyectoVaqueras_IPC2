/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Dtos.ComentarioCalificacion.CalificacionResponse;
import Dtos.ComentarioCalificacion.ComentarioResponse;
import Dtos.ComentarioCalificacion.NewComentarioRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class ComentarioCalificacionDBA {

    private static final String INSERT_CALIFICACION
            = "INSERT INTO calificacion (id_usuario, id_videojuego, calificacion) "
            + "VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE calificacion = VALUES(calificacion)";

    private static final String OBTENER_PROMEDIO_Y_CANTIDAD
            = "SELECT AVG(calificacion) as promedio, COUNT(*) as total "
            + "FROM calificacion WHERE id_videojuego = ?";

    private static final String OBTENER_CALIFICACION_USUARIO
            = "SELECT calificacion FROM calificacion WHERE id_usuario = ? AND id_videojuego = ?";

    private static final String INSERT_COMENTARIO
            = "INSERT INTO comentario (id_usuario, id_videojuego, id_comentario_padre, texto) "
            + "VALUES (?, ?, ?, ?)";

    private static final String LISTAR_COMENTARIOS_PRINCIPALES
            = "SELECT c.id_comentario, c.id_usuario, u.nickname, c.texto, c.visible, c.fecha "
            + "FROM comentario c "
            + "JOIN usuario u ON c.id_usuario = u.id_usuario "
            + "WHERE c.id_videojuego = ? AND c.id_comentario_padre IS NULL AND c.visible = 1 "
            + "ORDER BY c.fecha DESC";

    private static final String LISTAR_RESPUESTAS
            = "SELECT c.id_comentario, c.id_usuario, u.nickname, c.texto, c.visible, c.fecha "
            + "FROM comentario c "
            + "JOIN usuario u ON c.id_usuario = u.id_usuario "
            + "WHERE c.id_comentario_padre = ? AND c.visible = 1 "
            + "ORDER BY c.fecha ASC";

    private static final String VERIFICAR_PROPIEDAD
            = "SELECT 1 FROM compra WHERE id_usuario = ? AND id_videojuego = ?";

    public boolean usuarioPoseeJuego(int idUsuario, int idVideojuego) throws SQLException {
        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(VERIFICAR_PROPIEDAD)) {
            query.setInt(1, idUsuario);
            query.setInt(2, idVideojuego);
            try (ResultSet resultSet = query.executeQuery()) {
                System.out.println(resultSet);
                return resultSet.next();
            }
        }
    }

    public void calificarJuego(int idUsuario, int idVideojuego, int calificacion) throws SQLException {

        Connection connection = null;
        try {
            connection = Conexion.getInstance().getConnect();
            connection.setAutoCommit(false);

            try (PreparedStatement insert = connection.prepareStatement(INSERT_CALIFICACION)) {
                insert.setInt(1, idUsuario);
                insert.setInt(2, idVideojuego);
                insert.setInt(3, calificacion);
                insert.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public CalificacionResponse obtenerCalificacionVideojuego(int idVideojuego, int idUsuarioActual) throws SQLException {
        CalificacionResponse resp = new CalificacionResponse();

        try (Connection connection = Conexion.getInstance().getConnect()) {

            try (PreparedStatement query = connection.prepareStatement(OBTENER_PROMEDIO_Y_CANTIDAD)) {
                query.setInt(1, idVideojuego);
                try (ResultSet rs = query.executeQuery()) {
                    if (rs.next()) {
                        resp.setPromedio(rs.getDouble("promedio"));
                        resp.setTotalCalificaciones(rs.getInt("total"));
                    }
                }
            }

            try (PreparedStatement query = connection.prepareStatement(OBTENER_CALIFICACION_USUARIO)) {
                query.setInt(1, idUsuarioActual);
                query.setInt(2, idVideojuego);
                try (ResultSet resultSet = query.executeQuery()) {
                    if (resultSet.next()) {
                        resp.setCalificacionUsuarioActual(resultSet.getInt("calificacion"));
                    }
                }
            }
        }
        return resp;
    }

    public void publicarComentario(int idUsuario, int idVideojuego, NewComentarioRequest newComentarioRequest) throws SQLException {
        if (!usuarioPoseeJuego(idUsuario, idVideojuego)) {
            throw new SQLException("Usuario no posee el juego");
        }

        Connection connection = null;
        try {
            connection = Conexion.getInstance().getConnect();
            connection.setAutoCommit(false);

            try (PreparedStatement insert = connection.prepareStatement(INSERT_COMENTARIO)) {
                insert.setInt(1, idUsuario);
                insert.setInt(2, idVideojuego);
                insert.setObject(3, newComentarioRequest.getIdComentarioPadre());
                insert.setString(4, newComentarioRequest.getComentario());
                insert.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    public List<ComentarioResponse> obtenerComentarios(int idVideojuego) throws SQLException {
        List<ComentarioResponse> principales = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(LISTAR_COMENTARIOS_PRINCIPALES)) {

            query.setInt(1, idVideojuego);
            try (ResultSet resultSet = query.executeQuery()) {
                while (resultSet.next()) {
                    ComentarioResponse comentario = mapearComentario(resultSet);
                    comentario.setRespuestas(obtenerRespuestas(comentario.getIdComentario(), connection));
                    principales.add(comentario);
                }
            }
        }
        return principales;
    }

    private List<ComentarioResponse> obtenerRespuestas(int idPadre, Connection connection) throws SQLException {
        List<ComentarioResponse> respuestas = new ArrayList<>();
        try (PreparedStatement query = connection.prepareStatement(LISTAR_RESPUESTAS)) {
            query.setInt(1, idPadre);
            try (ResultSet resultSet = query.executeQuery()) {
                while (resultSet.next()) {
                    respuestas.add(mapearComentario(resultSet));
                }
            }
        }
        return respuestas;
    }

    private ComentarioResponse mapearComentario(ResultSet resultSet) throws SQLException {
        ComentarioResponse comentarioResponse = new ComentarioResponse();
        comentarioResponse.setIdComentario(resultSet.getInt("id_comentario"));
        comentarioResponse.setIdUsuario(resultSet.getInt("id_usuario"));
        comentarioResponse.setNicknameUsuario(resultSet.getString("nickname"));
        comentarioResponse.setTexto(resultSet.getString("texto"));
        comentarioResponse.setVisible(resultSet.getBoolean("visible"));
        comentarioResponse.setFecha(resultSet.getTimestamp("fecha").toLocalDateTime());
        return comentarioResponse;
    }

    public Double obtenerPromedioCalificacion(int idVideojuego) throws SQLException {
        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(OBTENER_PROMEDIO_Y_CANTIDAD)) {

            query.setInt(1, idVideojuego);

            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("promedio"); 
                }
            }
        }
        return null; 
    }

}
