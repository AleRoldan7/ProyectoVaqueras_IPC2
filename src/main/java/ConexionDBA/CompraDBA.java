/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class CompraDBA {

    private final GrupoFamiliarDBA grupoDBA = new GrupoFamiliarDBA();

    private static final String COMPRA_QUERY = "INSERT INTO compra (id_usuario, id_videojuego, precio_pagado, fecha_compra) "
            + "VALUES (?, ?, ?, ?)";

    private static final String AGREGAR_BIBLIOTECA_QUERY = "INSERT INTO biblioteca_usuario (id_usuario, id_compra, fecha_adquisicion) "
            + "VALUES (?, ?, ?)";

    private static final String DESCONTAR_SALDO_QUERY = "UPDATE usuario SET dinero_cartera = dinero_cartera - ? "
            + "WHERE id_usuario = ?";

    private static final String OBTENER_IDS_COMPRADOS_QUERY = "SELECT id_videojuego FROM compra WHERE id_usuario = ?";

    public void realizarCompra(int idUsuario, int idVideojuego, double precio, LocalDate fechaCompra) throws SQLException {

        Connection connection = null;

        try {

            int idCompra;
            connection = Conexion.getInstance().getConnect();
            connection.setAutoCommit(false);

            try (PreparedStatement insert = connection.prepareStatement(COMPRA_QUERY, Statement.RETURN_GENERATED_KEYS)) {

                insert.setInt(1, idUsuario);
                insert.setInt(2, idVideojuego);
                insert.setDouble(3, precio);
                insert.setTimestamp(4, Timestamp.valueOf(fechaCompra.atStartOfDay()));

                insert.executeUpdate();

                ResultSet resultSet = insert.getGeneratedKeys();
                resultSet.next();
                idCompra = resultSet.getInt(1);

            }

            try (PreparedStatement insert = connection.prepareStatement(AGREGAR_BIBLIOTECA_QUERY)) {
                insert.setInt(1, idUsuario);
                insert.setInt(2, idCompra);
                insert.setTimestamp(3, Timestamp.valueOf(fechaCompra.atStartOfDay()));
                insert.executeUpdate();
            }

            try (PreparedStatement update = connection.prepareStatement(DESCONTAR_SALDO_QUERY)) {
                update.setDouble(1, precio);
                update.setInt(2, idUsuario);
                update.executeUpdate();
            }

            List<Integer> grupos = grupoDBA.obtenerGruposUsuario(idUsuario);
            for (int idGrupo : grupos) {
                grupoDBA.agregarJuegoAGrupo(idGrupo, idVideojuego);
            }
            connection.commit();

        } catch (SQLException e) {

            if (connection != null) {
                connection.rollback();
            }
            throw e;

        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<Integer> obtenerIdsJuegosComprados(int idUsuario) throws SQLException {
        List<Integer> juegos = new ArrayList<>();
        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(OBTENER_IDS_COMPRADOS_QUERY)) {

            query.setInt(1, idUsuario);
            try (ResultSet resultSet = query.executeQuery()) {
                while (resultSet.next()) {
                    juegos.add(resultSet.getInt("id_videojuego"));
                }
            }
        }
        return juegos;
    }
}
