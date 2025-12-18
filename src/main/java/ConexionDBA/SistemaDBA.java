package ConexionDBA;

import ConexionDBA.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alejandro
 */
public class SistemaDBA {

    private static final String MODERAR_CATEGORIA_QUERY = "UPDATE videojuego_categoria SET estado = ?, fecha_revision = NOW() "
            + "WHERE id_categoria_videojuego = ?";

    private static final String APROBAR_CATEGORIA_QUERY = "UPDATE videojuego_categoria "
            + "SET estado = 'APROBADA', fecha_revision = NOW() "
            + "WHERE id_categoria_videojuego = ? AND estado = 'PENDIENTE'";

    private static final String RECHAZAR_CATEGORIA_QUERY = "UPDATE videojuego_categoria "
            + "SET estado = 'RECHAZADA', fecha_revision = NOW() "
            + "WHERE id_categoria_videojuego = ? AND estado = 'PENDIENTE'";

    public void moderarCategoria(int idCategoriaVideojuego, String estado) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(MODERAR_CATEGORIA_QUERY)) {

            query.setString(1, estado);
            query.setInt(2, idCategoriaVideojuego);
            query.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void aprobarCategoria(int idVideojuegoCategoria) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(APROBAR_CATEGORIA_QUERY)) {

            query.setInt(1, idVideojuegoCategoria);
            query.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rechazarCategoria(int idVideojuegoCategoria) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(RECHAZAR_CATEGORIA_QUERY)) {

            query.setInt(1, idVideojuegoCategoria);
            query.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
