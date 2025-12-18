/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Dtos.Videojuego.VideojuegoResponse;
import ModeloEntidad.Imagen;
import ModeloEntidad.Videojuego;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class VideojuegoDBA {

    private static final String AGREGAR_VIDEOJUEGO_QUERY = "INSERT INTO videojuego (titulo_videojuego, descripcion, precio, recursos_minimos,"
            + "clasificacion_edad, id_empresa, estado_venta) VALUES (?,?,?,?,?,?,?)";

    private static final String EXISTE_VIDEOJUEGO_QUERY = "SELECT * FROM videojuego WHERE titulo_videojuego = ?";
    private static final String AGREGAR_IMAGEN_VIDEOJUEGO_QUERY = "INSERT INTO imagen_videojuego (imagen, id_videojuego) VALUES (?,?)";
    private static final String OBTENER_VIDEOJUEGOS_EMPRESA = "SELECT * FROM videojuego WHERE id_empresa = ?";

    public void agregarVideojuego(Videojuego videojuego) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement insert = connection.prepareStatement(AGREGAR_VIDEOJUEGO_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            insert.setString(1, videojuego.getTituloVideojuego());
            insert.setString(2, videojuego.getDescripcion());
            insert.setDouble(3, videojuego.getPrecio());
            insert.setString(4, videojuego.getRecursosMinimos());
            insert.setString(5, videojuego.getClasificacionEdad().name());
            insert.setInt(6, videojuego.getIdEmpresa());
            insert.setBoolean(7, videojuego.isEstadoVenta());

            insert.executeUpdate();

            try (ResultSet resultSet = insert.getGeneratedKeys()) {

                if (resultSet.next()) {
                    videojuego.setIdVideojuego(resultSet.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existeVideojuego(String tituloVideojuego) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(EXISTE_VIDEOJUEGO_QUERY)) {

            query.setString(1, tituloVideojuego);

            try (ResultSet resultSet = query.executeQuery();) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void agregarImagenVideojuego(Imagen imagen) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement insert = connection.prepareStatement(AGREGAR_IMAGEN_VIDEOJUEGO_QUERY)) {

            insert.setBytes(1, imagen.getImagen());
            insert.setInt(2, imagen.getIdVideojuego());
            insert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<VideojuegoResponse> obtenerVideojuegosEmpresa(int idEmpresa) {

        List<VideojuegoResponse> lista = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(OBTENER_VIDEOJUEGOS_EMPRESA)) {

            query.setInt(1, idEmpresa);
            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                VideojuegoResponse v = new VideojuegoResponse();
                v.setIdVideojuego(resultSet.getInt("id_videojuego"));
                v.setTituloVideojuego(resultSet.getString("titulo_videojuego"));
                v.setDescripcion(resultSet.getString("descripcion"));
                v.setPrecio(resultSet.getDouble("precio"));
                v.setRecursosMinimos(resultSet.getString("recursos_minimos"));
                v.setClasificacionEdad(resultSet.getString("clasificacion_edad"));
                v.setEstadoVenta(resultSet.getBoolean("estado_venta"));

                lista.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public byte[] obtenerImagenVideojuego(int idImagen) {

        String sql = "SELECT imagen FROM imagen_videojuego WHERE id_imagen = ?";

        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idImagen);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Blob blob = rs.getBlob("imagen");
                return blob.getBytes(1, (int) blob.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Integer> obtenerIdsImagenes(int idVideojuego) {

        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id_imagen FROM imagen_videojuego WHERE id_videojuego = ?";

        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVideojuego);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ids.add(rs.getInt("id_imagen"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

}
