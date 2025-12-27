/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Dtos.GrupoFamiliar.BibliotecaJuegos.JuegoGrupo;
import EnumOpciones.EstadoJuego;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class GrupoBibliotecaDBA {

    private static final String JUEGOS_GRUPO_QUERY
            = "SELECT v.id_videojuego, v.titulo_videojuego, "
            + " (SELECT imagen FROM imagen_videojuego WHERE id_videojuego = v.id_videojuego LIMIT 1) AS foto, "
            + " IF(EXISTS(SELECT 1 FROM compra WHERE id_videojuego = v.id_videojuego AND id_usuario = ?), 1, 0) AS es_propio, "
            + " IFNULL(uje.estado, 'NO_INSTALADO') AS estado "
            + " FROM grupo_videojuego gv "
            + " JOIN videojuego v ON v.id_videojuego = gv.id_videojuego "
            + " LEFT JOIN usuario_juego_estado uje ON uje.id_usuario = ? AND uje.id_videojuego = v.id_videojuego "
            + " WHERE gv.id_grupo = ?";

    private static final String PRESTADO_INSTALADO_QUERY
            = "SELECT COUNT(*)  "
            + "FROM usuario_juego_estado uje "
            + "WHERE uje.id_usuario = ? "
            + "AND uje.es_prestado = 1 "
            + "AND uje.estado = 'INSTALADO';";

    private static final String EXISTE_REGISTRO_QUERY = "SELECT id_usuario_juego FROM usuario_juego_estado "
            + "WHERE id_usuario = ? AND id_videojuego = ?";

    private static final String INSTALAR_JUEGO_QUERY = "INSERT INTO usuario_juego_estado (id_usuario, id_videojuego, estado, es_prestado) "
            + "VALUES (?, ?, 'INSTALADO', 1)";

    private static final String ACTUALIZAR_ESTADO_USUARIO = "UPDATE usuario_juego_estado SET estado = 'INSTALADO' "
            + "WHERE id_usuario = ? AND id_videojuego = ?";

    public List<JuegoGrupo> juegosGrupo(int idGrupo, int idUsuario) throws SQLException {
        List<JuegoGrupo> lista = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(JUEGOS_GRUPO_QUERY)) {

            query.setInt(1, idUsuario);
            query.setInt(2, idUsuario);
            query.setInt(3, idGrupo);

            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                byte[] imgBytes = rs.getBytes("foto"); 
                String base64 = (imgBytes != null) ? Base64.getEncoder().encodeToString(imgBytes) : null;

                lista.add(new JuegoGrupo(
                        rs.getInt("id_videojuego"),
                        rs.getString("titulo_videojuego"),
                        rs.getBoolean("es_propio"),
                        EstadoJuego.valueOf(rs.getString("estado")),
                        base64
                ));
            }
        }
        return lista;
    }

    public boolean tienePrestadoInstalado(int idUsuario) throws SQLException {
        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(PRESTADO_INSTALADO_QUERY)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public boolean existeRegistro(int idUsuario, int idVideojuego) throws SQLException {

        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(EXISTE_REGISTRO_QUERY)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idVideojuego);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public void insertarEstado(int idUsuario, int idVideojuego, String estado, boolean esPrestado)
            throws SQLException {

        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(
                "INSERT INTO usuario_juego_estado (id_usuario, id_videojuego, estado, es_prestado) "
                + "VALUES (?, ?, ?, ?)")) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idVideojuego);
            ps.setString(3, estado);
            ps.setBoolean(4, esPrestado);

            ps.executeUpdate();
        }
    }

    public void actualizarEstado(int idUsuario, int idVideojuego, String estado)
            throws SQLException {

        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(
                "UPDATE usuario_juego_estado SET estado = ? "
                + "WHERE id_usuario = ? AND id_videojuego = ?")) {

            ps.setString(1, estado);
            ps.setInt(2, idUsuario);
            ps.setInt(3, idVideojuego);

            ps.executeUpdate();
        }
    }

}
