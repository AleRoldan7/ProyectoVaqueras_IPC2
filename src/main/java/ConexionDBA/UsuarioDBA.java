/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import EnumOpciones.TipoUsuario;
import ModeloEntidad.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class UsuarioDBA {

    private static final String REGISTRAR_USUARIO_COMUN_QUERY = "INSERT INTO usuario (nombre, correo, password, tipo_usuario, fecha_nacimiento, "
            + "nickname, numero_telefono, pais, dinero_cartera) VALUES (?, ?, ?, 'USUARIO_COMUN', ?, ?, ?, ?, 0.00)";

    private static final String CREAR_ADMIN_EMPRESA_QUERY = "INSERT INTO usuario (nombre, correo, password, tipo_usuario, fecha_nacimiento) "
            + "VALUES (?,?,?,?,?)";

    private static final String ENCONTRAR_USUARIO_QUERY = "SELECT * FROM usuario WHERE nickName = ?";
    private static final String VERIFICAR_USUARIO_QUERY = "SELECT * FROM usuario WHERE correo = ? AND password = ?";
    private static final String EMPRESA_ASIGNADA_USUARIO_QUERY = "SELECT id_empresa FROM empresa_desarrolladora WHERE id_usuario = ?";

    private static final String LISTAR_USUARIOS_QUERY = "SELECT * FROM usuario WHERE tipo_usuario = 'USUARIO_COMUN'";
    private static final String ACTUALIZAR_USUARIO_QUERY = "UPDATE usuario SET nombre=?, correo = ? , nickname=?  WHERE id_usuario=?";
    private static final String ELIMINAR_USUARIO_QUERY = "DELETE FROM usuario WHERE id_usuario=?";
    private static final String AGREGAR_FONDOS_QUERY = "UPDATE usuario SET dinero_cartera = dinero_cartera + ? WHERE id_usuario=?";

    public void registrarUsuarioComun(Usuario usuario) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement insert = connection.prepareStatement(REGISTRAR_USUARIO_COMUN_QUERY)) {

            insert.setString(1, usuario.getNombre());
            insert.setString(2, usuario.getCorreo());
            insert.setString(3, usuario.getPassword());
            insert.setDate(4, Date.valueOf(usuario.getFechaNacimiento()));
            insert.setString(5, usuario.getNickname());
            insert.setString(6, usuario.getNumeroTelefono());
            insert.setString(7, usuario.getPais());

            int datosAgregados = insert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public boolean existeUsuario(String nickName) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(ENCONTRAR_USUARIO_QUERY)) {

            query.setString(1, nickName);

            try (ResultSet resultSet = query.executeQuery();) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Usuario verificarUsuario(String correo, String password) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(VERIFICAR_USUARIO_QUERY)) {

            query.setString(1, correo);
            query.setString(2, password);

            System.out.println(correo);
            System.out.println(password);

            try (ResultSet resultSet = query.executeQuery()) {

                if (resultSet.next()) {

                    Usuario usuario = new Usuario();

                    usuario.setIdUsuario(resultSet.getInt("id_usuario"));
                    usuario.setCorreo(resultSet.getString("correo"));
                    usuario.setPassword(resultSet.getString("password"));
                    usuario.setTipoUsuario(TipoUsuario.valueOf(resultSet.getString("tipo_usuario")));
                    usuario.setFechaNacimiento(resultSet.getDate("fecha_nacimiento").toLocalDate());
                    usuario.setNickname(resultSet.getString("nickname"));
                    usuario.setNumeroTelefono(resultSet.getString("numero_telefono"));
                    usuario.setPais(resultSet.getString("pais"));
                    usuario.setDineroCartera(resultSet.getDouble("dinero_cartera"));
                    usuario.setNombre(resultSet.getString("nombre"));

                    return usuario;

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int crearAdminEmpresa(String nombre, String correo, String password, LocalDate fecha)
            throws SQLException {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement insert = connection.prepareStatement(
                CREAR_ADMIN_EMPRESA_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            insert.setString(1, nombre);
            insert.setString(2, correo);
            insert.setString(3, password);
            insert.setString(4, "ADMIN_EMPRESA");
            insert.setDate(5, Date.valueOf(fecha));
            insert.executeUpdate();

            ResultSet keys = insert.getGeneratedKeys();
            keys.next();
            return keys.getInt(1);
        }
    }

    public Integer obtenerEmpresaUsuario(int idUsuario) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(EMPRESA_ASIGNADA_USUARIO_QUERY)) {

            query.setInt(1, idUsuario);

            try (ResultSet resultSet = query.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt("id_empresa");
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Usuario obtenerUsuario(int idUsuario) throws SQLException {

        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(sql)) {

            query.setInt(1, idUsuario);
            ResultSet rs = query.executeQuery();

            if (!rs.next()) {
                return null;
            }

            Usuario u = new Usuario();
            u.setIdUsuario(rs.getInt("id_usuario"));
            u.setNombre(rs.getString("nombre"));
            u.setCorreo(rs.getString("correo"));
            u.setNickname(rs.getString("nickname"));
            u.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
            u.setDineroCartera(rs.getDouble("dinero_cartera"));

            return u;
        }
    }

    public boolean tieneVideojuego(int idUsuario, int idVideojuego) throws SQLException {

        String sql = "SELECT 1 FROM biblioteca_usuario bu "
                + "JOIN compra c ON bu.id_compra = c.id_compra "
                + "WHERE bu.id_usuario = ? "
                + "AND c.id_videojuego = ?";

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(sql)) {

            query.setInt(1, idUsuario);
            query.setInt(2, idVideojuego);

            ResultSet rs = query.executeQuery();
            return rs.next();
        }
    }

    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement stmt = conn.prepareStatement(LISTAR_USUARIOS_QUERY); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setNickname(rs.getString("nickname"));
                u.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                u.setNumeroTelefono(rs.getString("numero_telefono"));
                u.setPais(rs.getString("pais"));
                u.setDineroCartera(rs.getDouble("dinero_cartera"));
                usuarios.add(u);
            }
        }
        return usuarios;
    }

    public void actualizarUsuario(Usuario usuario) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement stmt = conn.prepareStatement(ACTUALIZAR_USUARIO_QUERY)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getNickname());
            stmt.setInt(4, usuario.getIdUsuario());
            stmt.executeUpdate();
        }
    }

    public void eliminarUsuario(int idUsuario) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement stmt = conn.prepareStatement(ELIMINAR_USUARIO_QUERY)) {
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
        }
    }

    public void agregarFondos(int idUsuario, double monto) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement stmt = conn.prepareStatement(AGREGAR_FONDOS_QUERY)) {
            stmt.setDouble(1, monto);
            stmt.setInt(2, idUsuario);
            stmt.executeUpdate();
        }
    }

    

}
