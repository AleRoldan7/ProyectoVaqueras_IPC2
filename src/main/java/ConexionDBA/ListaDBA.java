/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Dtos.Categoria.CategoriaVideojuegoPendiente;
import EnumOpciones.TipoUsuario;
import ModeloEntidad.Categoria;
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
            = "SELECT"
            + "    vc.id_categoria_videojuego, "
            + "    v.id_videojuego, "
            + "    v.titulo_videojuego, "
            + "    v.descripcion, "
            + "    c.nombre_categoria, "
            + "    c.descripcion_categoria, "
            + "    e.nombre_empresa, "
            + "    v.precio, "
            + "    v.clasificacion_edad, "
            + "    vc.fecha_solicitud "
            + "FROM videojuego_categoria vc "
            + "JOIN videojuego v ON v.id_videojuego = vc.id_videojuego "
            + "JOIN categoria c ON c.id_categoria = vc.id_categoria "
            + "JOIN empresa_desarrolladora e ON e.id_empresa = v.id_empresa "
            + "WHERE vc.estado = 'PENDIENTE' ";

    private static final String OBTENER_IMAGENES_VIDEOJUEGO = "SELECT imagen FROM imagen_videojuego WHERE id_videojuego = ?";
    private static final String LISTA_CATEGORIAS_QUERY = "SELECT * FROM categoria";

    private static final String LISTA_USUARIO_COMUN_QUERY = "SELECT * FROM usuario WHERE tipo_usuario = 'USUARIO_COMUN'";

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

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(LISTA_CATEGORIAS_PENDIENTES_QUERY); ResultSet resultSet = query.executeQuery()) {

            while (resultSet.next()) {
                CategoriaVideojuegoPendiente dto = new CategoriaVideojuegoPendiente(
                        resultSet.getInt("id_categoria_videojuego"),
                        resultSet.getInt("id_videojuego"),
                        resultSet.getString("titulo_videojuego"),
                        resultSet.getString("descripcion"),
                        resultSet.getString("nombre_categoria"),
                        resultSet.getString("descripcion_categoria"),
                        resultSet.getString("nombre_empresa"), // ✅ ahora sí
                        resultSet.getDouble("precio"),
                        resultSet.getString("clasificacion_edad"),
                        resultSet.getDate("fecha_solicitud").toLocalDate()
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

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(OBTENER_IMAGENES_VIDEOJUEGO)) {

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

    public List<Categoria> listaCategorias() {

        List<Categoria> categorias = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(LISTA_CATEGORIAS_QUERY)) {

            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                Categoria categoria = new Categoria(
                        resultSet.getInt("id_categoria"),
                        resultSet.getString("nombre_categoria"),
                        resultSet.getString("descripcion_categoria")
                );
                categorias.add(categoria);
                System.out.println("Encontrados" + categorias.size());

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Categorias:" + categorias.size());
        return categorias;

    }

    public List<Usuario> listaUsuarioComun() {

        List<Usuario> usuarios = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(LISTA_USUARIO_COMUN_QUERY)) {

            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                Usuario usuario = new Usuario(
                        resultSet.getInt("id_usuario"),
                        resultSet.getString("nombre"),
                        resultSet.getString("correo"),
                        resultSet.getString("password"),
                        resultSet.getDate("fecha_nacimiento").toLocalDate(),
                        resultSet.getString("nickname"),
                        resultSet.getString("numero_telefono"),
                        resultSet.getString("pais")
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

}
