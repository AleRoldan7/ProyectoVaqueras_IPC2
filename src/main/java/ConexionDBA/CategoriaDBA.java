/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Dtos.Categoria.UpdateCategoriaRequest;
import ModeloEntidad.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alejandro
 */
public class CategoriaDBA {

    private static final String AGREGAR_CATEGORIA_QUERY = "INSERT INTO categoria (nombre_categoria, descripcion_categoria) VALUES (?,?)";
    private static final String ACTUALIZAR_CATEGORIA_QUERY = "UPDATE categoria SET nombre_categoria = ?, descripcion_categoria = ? "
            + "WHERE id_categoria = ?";

    private static final String EXISTE_CATEGORIAID_QUERY = "SELECT * FROM categoria WHERE id_categoria = ?";
    private static final String EXISTE_CATEGORIA_QUERY = "SELECT * FROM categoria WHERE nombre_categoria = ?";
    private static final String AGREGAR_CATEGORIA_VIDEOJUEGO_QUERY = "INSERT INTO videojuego_categoria (id_videojuego, id_categoria) "
            + "VALUES (?,?)";

    private static final String ELIMINAR_CATEGORIA_LOGICA_QUERY = "UPDATE categoria SET estado = FALSE WHERE id_categoria = ?";
    private static final String EXISTE_CATEGORIA_ACTIVA_QUERY = "SELECT estado FROM categoria WHERE id_categoria = ?";

    public void agregarCategoria(Categoria categoria) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement insert = connection.prepareStatement(AGREGAR_CATEGORIA_QUERY)) {

            insert.setString(1, categoria.getNombreCategoria());
            insert.setString(2, categoria.getDescripcionCategoria());
            insert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existeCategoria(String nombreCategoria) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(EXISTE_CATEGORIA_QUERY)) {

            query.setString(1, nombreCategoria);

            try (ResultSet resultSet = query.executeQuery();) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public void actualizarCategoria(UpdateCategoriaRequest updateCategoriaRequest) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement update = connection.prepareStatement(ACTUALIZAR_CATEGORIA_QUERY)) {

            update.setString(1, updateCategoriaRequest.getNombreCategoria());
            update.setString(2, updateCategoriaRequest.getDescripcionCategoria());
            update.setInt(3, updateCategoriaRequest.getIdCategoria());
            update.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existeIdCategoria(Integer idCategoria) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(EXISTE_CATEGORIAID_QUERY)) {

            query.setInt(1, idCategoria);

            try (ResultSet resultSet = query.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void solicitarCategoriaVideojuego(int idVideojuego, int idCategoria) {

        try (Connection connection = Conexion.getInstance().getConnect(); 
                PreparedStatement insert = connection.prepareStatement(AGREGAR_CATEGORIA_VIDEOJUEGO_QUERY)) {

            insert.setInt(1, idVideojuego);
            insert.setInt(2, idCategoria);
            insert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarCategoriaLogica(Integer idCategoria) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement update = connection.prepareStatement(ELIMINAR_CATEGORIA_LOGICA_QUERY)) {

            update.setInt(1, idCategoria);
            update.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existeCategoriaActiva(Integer idCategoria) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(EXISTE_CATEGORIA_ACTIVA_QUERY)) {

            query.setInt(1, idCategoria);

            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("estado");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
