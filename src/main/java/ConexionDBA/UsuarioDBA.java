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
}
