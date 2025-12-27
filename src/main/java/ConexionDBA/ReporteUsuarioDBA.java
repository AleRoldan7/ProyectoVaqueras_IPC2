/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import EnumOpciones.EstadoJuego;
import Resources.ReporteUsuario.AnalisisBibliotecaDTO;
import Resources.ReporteUsuario.BibliotecaFamiliarUsoDTO;
import Resources.ReporteUsuario.CategoriaFavoritaDTO;
import Resources.ReporteUsuario.HistorialGastosDTO;
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
public class ReporteUsuarioDBA {

    private static final String HISTORIAL_GASTOS_QUERY
            = "SELECT c.id_compra, c.fecha_compra, c.precio_pagado, v.titulo_videojuego "
            + "FROM compra c "
            + "JOIN videojuego v ON v.id_videojuego = c.id_videojuego "
            + "WHERE c.id_usuario = ? "
            + "ORDER BY c.fecha_compra DESC";

    private static final String ANALISIS_BIBLIOTECA_QUERY
            = "SELECT v.id_videojuego, v.titulo_videojuego, "
            + "(SELECT AVG(c.calificacion) "
            + "FROM calificacion c WHERE c.id_videojuego = v.id_videojuego) AS calificacionComunidad, "
            + "(SELECT c2.calificacion FROM calificacion c2 WHERE c2.id_videojuego = v.id_videojuego AND c2.id_usuario = ?) "
            + "AS calificacionUsuario, uj.estado "
            + "FROM usuario_juego_estado uj "
            + "INNER JOIN videojuego v ON v.id_videojuego = uj.id_videojuego "
            + "WHERE uj.id_usuario = ?;";

    private static final String CATEGORIAS_FAVORITAS_QUERY
            = "SELECT c.nombre_categoria, COUNT(*) AS cantidad "
            + "FROM compra co "
            + "INNER JOIN videojuego v ON v.id_videojuego = co.id_videojuego "
            + "INNER JOIN videojuego_categoria vc ON vc.id_videojuego = v.id_videojuego "
            + "INNER JOIN categoria c ON c.id_categoria = vc.id_categoria "
            + "WHERE co.id_usuario = ? "
            + "GROUP BY c.nombre_categoria "
            + "ORDER BY cantidad DESC;";

    private static final String USO_BIBLIOTECA_QUERY
            = "SELECT v.titulo_videojuego, uj.estado, uj.es_prestado, "
            + "(SELECT AVG(c.calificacion) FROM calificacion c WHERE c.id_videojuego = v.id_videojuego) AS calificacionComunidad "
            + "FROM usuario_juego_estado uj "
            + "INNER JOIN videojuego v ON v.id_videojuego = uj.id_videojuego "
            + "WHERE uj.id_usuario = ? AND uj.es_prestado = TRUE;";

    public ArrayList<HistorialGastosDTO> historialGastos(int idUsuario) {

        ArrayList<HistorialGastosDTO> lista = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(HISTORIAL_GASTOS_QUERY)) {

            query.setInt(1, idUsuario);

            try (ResultSet resultSet = query.executeQuery()) {
                while (resultSet.next()) {
                    lista.add(new HistorialGastosDTO(
                            resultSet.getInt("id_compra"),
                            resultSet.getString("titulo_videojuego"),
                            resultSet.getDouble("precio_pagado"),
                            resultSet.getDate("fecha_compra").toString()
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<AnalisisBibliotecaDTO> obtenerAnalisisBiblioteca(int idUsuario) {

        List<AnalisisBibliotecaDTO> lista = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(ANALISIS_BIBLIOTECA_QUERY)) {

            query.setInt(1, idUsuario);
            query.setInt(2, idUsuario);

            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                AnalisisBibliotecaDTO dto = new AnalisisBibliotecaDTO();
                dto.setIdVideojuego(resultSet.getInt(1));
                dto.setTitulo(resultSet.getString(2));
                dto.setCalificacionComunidad(resultSet.getDouble(3));
                dto.setCalificacionUsuario(resultSet.getObject(4) != null ? resultSet.getInt(4) : null);
                dto.setEstadoInstalacion(EstadoJuego.valueOf(resultSet.getString(5)));
                lista.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<CategoriaFavoritaDTO> obtenerCategoriasFavoritas(int idUsuario) {
        List<CategoriaFavoritaDTO> lista = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(CATEGORIAS_FAVORITAS_QUERY)) {
            query.setInt(1, idUsuario);

            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                CategoriaFavoritaDTO dto = new CategoriaFavoritaDTO();
                dto.setCategoria(resultSet.getString(1));
                dto.setCantidadComprados(resultSet.getLong(2));
                lista.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<BibliotecaFamiliarUsoDTO> obtenerUsoBiblioteca(int idUsuario) {
        List<BibliotecaFamiliarUsoDTO> lista = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(USO_BIBLIOTECA_QUERY)) {
            query.setInt(1, idUsuario);

            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                BibliotecaFamiliarUsoDTO dto = new BibliotecaFamiliarUsoDTO();
                dto.setTitulo(resultSet.getString(1));
                dto.setEstado(resultSet.getString(2));
                dto.setEsPrestado(resultSet.getBoolean(3));
                dto.setCalificacionComunidad(resultSet.getDouble(4));
                lista.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
