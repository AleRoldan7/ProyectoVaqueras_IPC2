/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Dtos.Videojuego.UpdateVideojuegoRequest;
import Dtos.Videojuego.VideojuegoDisponibleRequest;
import Dtos.Videojuego.VideojuegoImagenes;
import Dtos.Videojuego.VideojuegoResponse;
import EnumOpciones.ClasificacionEdad;
import ModeloEntidad.Imagen;
import ModeloEntidad.Videojuego;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final String VIDEOJUEGOS_DISPONIBLES_QUERY
            = "SELECT DISTINCT v.id_videojuego, v.titulo_videojuego, v.descripcion, "
            + "v.precio, v.clasificacion_edad "
            + "FROM videojuego v "
            + "JOIN videojuego_categoria vc ON vc.id_videojuego = v.id_videojuego "
            + "WHERE vc.estado = 'APROBADA' AND v.estado_venta = 1";

    private static final String CATEGORIAS_APROBADAS_QUERY
            = "SELECT c.nombre_categoria "
            + "FROM videojuego_categoria vc "
            + "JOIN categoria c ON c.id_categoria = vc.id_categoria "
            + "WHERE vc.id_videojuego = ? AND vc.estado = 'APROBADA'";

    private static final String UPDATE_VIDEOJUEGO = "UPDATE videojuego SET titulo_videojuego = ?, descripcion = ?, "
            + "precio = ?, recursos_minimos = ?, clasificacion_edad = ? WHERE id_videojuego = ?";

    private static final String UPDATE_ESTADO_VENTA = "UPDATE videojuego SET estado_venta = ? WHERE id_videojuego = ?";

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

    public void agregarImagenVideojuego(Imagen entidad) {
        String sql = "INSERT INTO imagen_videojuego (imagen, id_videojuego) VALUES (?, ?)";

        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBytes(1, entidad.getImagen());
            pstmt.setInt(2, entidad.getIdVideojuego());

            pstmt.executeUpdate();
            System.out.println("DBA: Imagen insertada correctamente en la base de datos.");

        } catch (SQLException e) {
            System.err.println("Error al insertar imagen: " + e.getMessage());
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
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Blob blob = rs.getBlob("imagen");
                    if (blob != null) {
                        return blob.getBytes(1, (int) blob.length());
                    }
                }
            }

        } catch (SQLException e) {
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

    public Videojuego obtenerVideojuego(int idVideojuego) throws SQLException {

        String sql = "SELECT * FROM videojuego WHERE id_videojuego = ? AND estado_venta = 1";

        try (Connection connetion = Conexion.getInstance().getConnect(); PreparedStatement query = connetion.prepareStatement(sql)) {

            query.setInt(1, idVideojuego);
            ResultSet rs = query.executeQuery();

            if (!rs.next()) {
                return null;
            }

            Videojuego v = new Videojuego();
            v.setIdVideojuego(rs.getInt("id_videojuego"));
            v.setTituloVideojuego(rs.getString("titulo_videojuego"));
            v.setPrecio(rs.getDouble("precio"));
            v.setClasificacionEdad(ClasificacionEdad.valueOf(rs.getString("clasificacion_edad")));

            return v;
        }
    }

    public List<VideojuegoImagenes> listarDisponibles() {
        Map<Integer, VideojuegoImagenes> mapa = new HashMap<>();

        try (Connection con = Conexion.getInstance().getConnect()) {
            try (PreparedStatement ps = con.prepareStatement(VIDEOJUEGOS_DISPONIBLES_QUERY); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_videojuego");
                    mapa.put(id, new VideojuegoImagenes(
                            id,
                            rs.getString("titulo_videojuego"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio"),
                            rs.getString("clasificacion_edad"),
                            new ArrayList<>()
                    ));
                }
            }

            try (PreparedStatement psCat = con.prepareStatement(CATEGORIAS_APROBADAS_QUERY)) {
                for (VideojuegoImagenes dto : mapa.values()) {
                    psCat.setInt(1, dto.getIdVideojuego());
                    try (ResultSet rsCat = psCat.executeQuery()) {
                        while (rsCat.next()) {
                            dto.getCategorias().add(rsCat.getString("nombre_categoria"));
                        }
                    }
                }
            }

            String sqlImagenes = "SELECT id_imagen FROM imagen_videojuego WHERE id_videojuego = ?";
            try (PreparedStatement psImg = con.prepareStatement(sqlImagenes)) {
                for (VideojuegoImagenes dto : mapa.values()) {
                    psImg.setInt(1, dto.getIdVideojuego());
                    try (ResultSet rsImg = psImg.executeQuery()) {
                        while (rsImg.next()) {
                            dto.addIdImagen(rsImg.getInt("id_imagen"));
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(mapa.values());
    }

    public void actualizarVideojuego(UpdateVideojuegoRequest updateVideojuegoRequest) throws SQLException {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement update = connection.prepareStatement(UPDATE_VIDEOJUEGO)) {

            update.setString(1, updateVideojuegoRequest.getTituloVideojuego());
            update.setString(2, updateVideojuegoRequest.getDescripcion());
            update.setDouble(3, updateVideojuegoRequest.getPrecio());
            update.setString(4, updateVideojuegoRequest.getRecursosMinimos());
            update.setString(5, updateVideojuegoRequest.getClasificacionEdad().name());
            update.setInt(6, updateVideojuegoRequest.getIdVideojuego());

            update.executeUpdate();
        }
    }

    public void cambiarEstadoVenta(int idVideojuego, boolean estado) throws SQLException {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(UPDATE_ESTADO_VENTA)) {

            query.setBoolean(1, estado);
            query.setInt(2, idVideojuego);

            query.executeUpdate();
        }
    }

    public List<Videojuego> listarCatalogo(String titulo, Double precioMin, Double precioMax, Boolean disponibles) {
        List<Videojuego> lista = new ArrayList<>();
        String sql = "SELECT * FROM videojuego WHERE 1=1";

        if (titulo != null && !titulo.isEmpty()) {
            sql += " AND titulo_videojuego LIKE ?";
        }
        if (precioMin != null) {
            sql += " AND precio >= ?";
        }
        if (precioMax != null) {
            sql += " AND precio <= ?";
        }
        if (disponibles != null) {
            sql += " AND estado_venta = ?";
        }

        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            int i = 1;
            if (titulo != null && !titulo.isEmpty()) {
                ps.setString(i++, "%" + titulo + "%");
            }
            if (precioMin != null) {
                ps.setDouble(i++, precioMin);
            }
            if (precioMax != null) {
                ps.setDouble(i++, precioMax);
            }
            if (disponibles != null) {
                ps.setBoolean(i++, disponibles);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Videojuego v = new Videojuego();
                v.setIdVideojuego(rs.getInt("id_videojuego"));
                v.setTituloVideojuego(rs.getString("titulo_videojuego"));
                v.setDescripcion(rs.getString("descripcion"));
                v.setPrecio(rs.getDouble("precio"));
                v.setRecursosMinimos(rs.getString("recursos_minimos"));
                v.setClasificacionEdad(ClasificacionEdad.valueOf(rs.getString("clasificacion_edad")));
                v.setIdEmpresa(rs.getInt("id_empresa"));
                v.setEstadoVenta(rs.getBoolean("estado_venta"));
                lista.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<VideojuegoResponse> buscarVideojuegos(
            String titulo, String categoria, Double precioMin,
            Double precioMax, String empresa) throws SQLException {

        List<VideojuegoResponse> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT v.id_videojuego, v.titulo_videojuego, v.precio, v.clasificacion_edad, "
                + "v.estado_venta, e.nombre_empresa, "
                + "GROUP_CONCAT(iv.id_imagen SEPARATOR ',') AS ids_imagenes "
                + "FROM videojuego v "
                + "JOIN empresa_desarrolladora e ON v.id_empresa = e.id_empresa "
                + "LEFT JOIN imagen_videojuego iv ON iv.id_videojuego = v.id_videojuego "
                + "WHERE 1=1 "
        );

        if (titulo != null && !titulo.isEmpty()) {
            sql.append("AND v.titulo_videojuego LIKE ? ");
        }
        if (categoria != null && !categoria.isEmpty()) {
            sql.append("AND v.clasificacion_edad = ? ");
        }
        if (precioMin != null) {
            sql.append("AND v.precio >= ? ");
        }
        if (precioMax != null) {
            sql.append("AND v.precio <= ? ");
        }
        if (empresa != null && !empresa.isEmpty()) {
            sql.append("AND e.nombre_empresa LIKE ? ");
        }
        sql.append(" GROUP BY v.id_videojuego ");

        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (titulo != null && !titulo.isEmpty()) {
                stmt.setString(index++, "%" + titulo + "%");
            }
            if (categoria != null && !categoria.isEmpty()) {
                stmt.setString(index++, categoria);
            }
            if (precioMin != null) {
                stmt.setDouble(index++, precioMin);
            }
            if (precioMax != null) {
                stmt.setDouble(index++, precioMax);
            }
            if (empresa != null && !empresa.isEmpty()) {
                stmt.setString(index++, "%" + empresa + "%");
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VideojuegoResponse v = new VideojuegoResponse();
                v.setIdVideojuego(rs.getInt("id_videojuego"));
                v.setTituloVideojuego(rs.getString("titulo_videojuego"));
                v.setPrecio(rs.getDouble("precio"));
                v.setClasificacionEdad(rs.getString("clasificacion_edad"));
                v.setEstadoVenta(rs.getBoolean("estado_venta"));
                v.setNombreEmpresa(rs.getString("nombre_empresa"));

                String idsImgStr = rs.getString("ids_imagenes");
                List<Integer> idsImagenes = new ArrayList<>();
                if (idsImgStr != null && !idsImgStr.isEmpty()) {
                    for (String idStr : idsImgStr.split(",")) {
                        try {
                            idsImagenes.add(Integer.parseInt(idStr.trim()));
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
                v.setIdsImagenes(idsImagenes);

                lista.add(v);
            }
        }
        return lista;
    }

    public boolean videojuegoPuedeVenderse(int idVideojuego) throws SQLException {
        String query = "SELECT COUNT(*) FROM videojuego_categoria "
                + "WHERE id_videojuego = ? AND estado = 'APROBADA'";

        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idVideojuego);
            ResultSet rs = ps.executeQuery();
            rs.next();

            return rs.getInt(1) > 0;
        }
    }

}
