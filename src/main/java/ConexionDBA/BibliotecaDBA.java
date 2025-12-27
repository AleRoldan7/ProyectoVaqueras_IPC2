/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Dtos.Biblioteca.BibliotecaResponse;
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
public class BibliotecaDBA {

    /*
    private static final String BIBLIOTECA_QUERY
            = "SELECT v.id_videojuego, v.titulo_videojuego, v.precio, "
            + "v.clasificacion_edad, bu.estado_instalacion, c.fecha_compra "
            + "FROM biblioteca_usuario bu "
            + "JOIN compra c ON bu.id_compra = c.id_compra "
            + "JOIN videojuego v ON c.id_videojuego = v.id_videojuego "
            + "WHERE bu.id_usuario = ?";

     */
    private static final String BIBLIOTECA_QUERY
            = "SELECT v.id_videojuego, v.titulo_videojuego, v.precio, v.clasificacion_edad, "
            + "MAX(c.fecha_compra) AS fecha_compra, v.descripcion, "
            + "COALESCE(uje.estado, 'NO_INSTALADO') AS estado_instalacion, "
            + "(SELECT id_imagen FROM imagen_videojuego WHERE id_videojuego = v.id_videojuego LIMIT 1) AS id_imagen "
            + "FROM biblioteca_usuario bu "
            + "JOIN compra c ON bu.id_compra = c.id_compra "
            + "JOIN videojuego v ON c.id_videojuego = v.id_videojuego "
            + "LEFT JOIN usuario_juego_estado uje ON uje.id_usuario = bu.id_usuario AND uje.id_videojuego = v.id_videojuego "
            + "WHERE bu.id_usuario = ? "
            + "GROUP BY v.id_videojuego";

    private static final String ESTADO_VIDEOJUEGO_QUERY
            = "INSERT INTO usuario_juego_estado (id_usuario, id_videojuego, estado, es_prestado) "
            + "VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE estado = VALUES(estado)";

    private static final String USUARIO_POR_NICKNAME_QUERY
            = "SELECT id_usuario FROM usuario WHERE nickname = ?";

    public List<BibliotecaResponse> obtenerBiblioteca(int idUsuario) throws SQLException {
        List<BibliotecaResponse> lista = new ArrayList<>();
        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(BIBLIOTECA_QUERY)) {

            query.setInt(1, idUsuario);
            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                BibliotecaResponse bibliotecaResponse = new BibliotecaResponse();

                bibliotecaResponse.setIdVideojuego(resultSet.getInt("id_videojuego"));
                bibliotecaResponse.setTitulo(resultSet.getString("titulo_videojuego"));
                bibliotecaResponse.setPrecioPagado(resultSet.getDouble("precio"));
                bibliotecaResponse.setClasificacionEdad(resultSet.getString("clasificacion_edad"));
                bibliotecaResponse.setFechaCompra(resultSet.getTimestamp("fecha_compra").toLocalDateTime());
                bibliotecaResponse.setIdImagen(resultSet.getInt("id_imagen"));
                bibliotecaResponse.setDescripcion(resultSet.getString("descripcion"));
                bibliotecaResponse.setEstadoInstalacion(resultSet.getString("estado_instalacion"));
                lista.add(bibliotecaResponse);
            }
        }
        return lista;
    }

    public int obtenerUsuarioPorNickname(String nickname) throws SQLException {
        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(USUARIO_POR_NICKNAME_QUERY)) {

            query.setString(1, nickname);
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id_usuario");
            }
            return -1;
        }
    }

    public void cambiarEstadoInstalacion(int idUsuario, int idVideojuego, String nuevoEstado) throws SQLException {
        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement update = connection.prepareStatement(ESTADO_VIDEOJUEGO_QUERY)) {

            update.setInt(1, idUsuario);
            update.setInt(2, idVideojuego);
            update.setString(3, nuevoEstado);
            update.setBoolean(4, false);
            update.executeUpdate();
        }
    }

    public boolean usuarioPoseeVideojuego(int idUsuario, int idVideojuego) throws SQLException {
        String query = "SELECT 1 FROM biblioteca_usuario bu "
                + "JOIN compra c ON bu.id_compra = c.id_compra "
                + "WHERE bu.id_usuario = ? AND c.id_videojuego = ? LIMIT 1";

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idVideojuego);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); 
        }
    }

}
