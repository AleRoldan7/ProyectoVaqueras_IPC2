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

    private static final String BIBLIOTECA_QUERY
            = "SELECT v.id_videojuego, v.titulo_videojuego, v.precio, "
            + "v.clasificacion_edad, bu.estado_instalacion, c.fecha_compra "
            + "FROM biblioteca_usuario bu "
            + "JOIN compra c ON bu.id_compra = c.id_compra "
            + "JOIN videojuego v ON c.id_videojuego = v.id_videojuego "
            + "WHERE bu.id_usuario = ?";

    private static final String CAMBIAR_ESTADO_QUERY =
            "UPDATE biblioteca_usuario SET estado_instalacion = ? " +
            "WHERE id_usuario = ? AND id_compra = ?";

    private static final String USUARIO_POR_NICKNAME_QUERY =
            "SELECT id_usuario FROM usuario WHERE nickname = ?";

    public List<BibliotecaResponse> obtenerBiblioteca(int idUsuario) throws SQLException {
        List<BibliotecaResponse> lista = new ArrayList<>();
        try (Connection conn = Conexion.getInstance().getConnect();
             PreparedStatement ps = conn.prepareStatement(BIBLIOTECA_QUERY)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BibliotecaResponse item = new BibliotecaResponse();
                item.setIdVideojuego(rs.getInt("id_videojuego"));
                item.setTitulo(rs.getString("titulo_videojuego"));
                item.setPrecioPagado(rs.getDouble("precio"));
                item.setClasificacionEdad(rs.getString("clasificacion_edad"));
                item.setEstadoInstalacion(rs.getString("estado_instalacion"));
                item.setFechaCompra(rs.getTimestamp("fecha_compra").toLocalDateTime());
                lista.add(item);
            }
        }
        return lista;
    }

    public int obtenerUsuarioPorNickname(String nickname) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect();
             PreparedStatement ps = conn.prepareStatement(USUARIO_POR_NICKNAME_QUERY)) {

            ps.setString(1, nickname);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_usuario");
            }
            return -1; 
        }
    }

    public void cambiarEstadoInstalacion(int idUsuario, int idCompra, String nuevoEstado) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect();
             PreparedStatement ps = conn.prepareStatement(CAMBIAR_ESTADO_QUERY)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idUsuario);
            ps.setInt(3, idCompra);
            ps.executeUpdate();
        }
    }
}
