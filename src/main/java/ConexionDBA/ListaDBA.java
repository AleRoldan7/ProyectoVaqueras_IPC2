/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Dtos.Categoria.CategoriaVideojuegoPendiente;
import EnumOpciones.TipoUsuario;
import ModeloEntidad.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class ListaDBA {

    private static final String LISTA_ADMIN_EMPRESA_QUERY = "SELECT * FROM usuario WHERE tipo_usuario = 'ADMIN_EMPRESA'";
    private static final String LISTA_CATEGORIAS_PENDIENTES_QUERY
            = "SELECT vc.id_categoria_videojuego, "
            + "       v.titulo_videojuego, "
            + "       c.nombre_categoria, "
            + "       vc.fecha_solicitud "
            + "FROM videojuego_categoria vc "
            + "JOIN videojuego v ON v.id_videojuego = vc.id_videojuego "
            + "JOIN categoria c ON c.id_categoria = vc.id_categoria "
            + "WHERE vc.estado = 'PENDIENTE'";

    private static final String OBTENER_IMAGENES_VIDEOJUEGO = "SELECT imagen FROM imagen_videojuego WHERE id_videojuego = ?";

    public List<Usuario> listaAdminEmpresa() {

        List<Usuario> usuarios = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(LISTA_ADMIN_EMPRESA_QUERY)) {

            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                Usuario usuario = new Usuario(
                        resultSet.getInt("id_usuario"),
                        resultSet.getString("nombre"),
                        resultSet.getString("correo"),
                        resultSet.getString("password"),
                        resultSet.getDate("fecha_nacimiento").toLocalDate()
                );
                usuarios.add(usuario);
                System.out.println("Encontrados" + usuarios.size());

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Usuarios:" + usuarios.size());
        return usuarios;

    }

    public List<CategoriaVideojuegoPendiente> listarCategoriasPendientes() {

        List<CategoriaVideojuegoPendiente> lista = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(LISTA_CATEGORIAS_PENDIENTES_QUERY); ResultSet rs = query.executeQuery()) {

            while (rs.next()) {
                CategoriaVideojuegoPendiente dto = new CategoriaVideojuegoPendiente(
                        rs.getInt("id_categoria_videojuego"),
                        rs.getString("titulo_videojuego"),
                        rs.getString("nombre_categoria"),
                        rs.getDate("fecha_solicitud").toLocalDate()
                );
                lista.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<byte[]> obtenerImagenesPorVideojuego(int idVideojuego) {

        List<byte[]> imagenes = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); 
                PreparedStatement query = connection.prepareStatement(OBTENER_IMAGENES_VIDEOJUEGO)) {

            query.setInt(1, idVideojuego);

            try (ResultSet resultSet = query.executeQuery()) {
                while (resultSet.next()) {
                    imagenes.add(resultSet.getBytes("imagen"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return imagenes;
    }

}
