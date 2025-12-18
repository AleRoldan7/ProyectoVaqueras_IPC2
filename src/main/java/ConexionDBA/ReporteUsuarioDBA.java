/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Resources.ReporteUsuario.HistorialGastosDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
}
