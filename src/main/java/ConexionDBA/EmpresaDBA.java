/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import ModeloEntidad.Empresa;
import ModeloEntidad.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alejandro
 */
public class EmpresaDBA {

    private static final String CREAR_EMPRESA_QUERY = "INSERT INTO empresa_desarrolladora (nombre_empresa, descripcion_empresa, "
            + "id_usuario, pais_empresa) VALUES (?,?,?,?)";

    private static final String VERIFICAR_EMPRESA_QUERY = "SELECT * FROM empresa_desarrolladora WHERE nombre_empresa = ?";
    private static final String VERIFICAR_USUARIO_EMPRESA_QUERY = "SELECT * FROM empresa_desarrolladora WHERE id_usuario = ?";
    private static final String OBTENER_EMPRESA_QUERY = "SELECT id_empresa FROM empresa_desarrolladora WHERE id_usuario = ?";
    private static final String OBTENER_NOMBRE_EMPRESA_QUERY = "SELECT nombre_empresa FROM empresa_desarrolladora WHERE id_empresa = ?";

    public void crearEmpresa(Empresa empresa) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement insert = connection.prepareStatement(CREAR_EMPRESA_QUERY)) {

            insert.setString(1, empresa.getNombreEmpresa());
            insert.setString(2, empresa.getDescripcionEmpresa());
            insert.setInt(3, empresa.getIdUsuario());
            insert.setString(4, empresa.getPaisEmpresa());
            insert.executeUpdate();

            System.out.println("Datos empresa" + insert);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existeEmpresa(String nombreEmpresa) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(VERIFICAR_EMPRESA_QUERY)) {

            query.setString(1, nombreEmpresa);

            try (ResultSet resultSet = query.executeQuery();) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean adminAsigandoEmpresa(int idUsuario) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(VERIFICAR_USUARIO_EMPRESA_QUERY)) {

            query.setInt(1, idUsuario);

            try (ResultSet resultSet = query.executeQuery();) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Integer obtenerIdEmpresaAdmin(int idUsuario) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(OBTENER_EMPRESA_QUERY)) {

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
    
    public String obtenerNombreEmpresa(int idEmpresa) {
        
        try (Connection connection = Conexion.getInstance().getConnect();
                PreparedStatement query = connection.prepareStatement(OBTENER_NOMBRE_EMPRESA_QUERY)){
            
            query.setInt(1, idEmpresa);
            
            try (ResultSet resultSet = query.executeQuery()){
                
                if (resultSet.next()) {
                    return resultSet.getString("nombre_empresa");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

}
