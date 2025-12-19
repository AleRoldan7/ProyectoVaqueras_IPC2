package ConexionDBA;

import ConexionDBA.Conexion;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alejandro
 */
public class SistemaDBA {

    private static final String APROBAR_CATEGORIA_QUERY = "UPDATE videojuego_categoria "
            + "SET estado = 'APROBADA', fecha_revision = NOW() "
            + "WHERE id_categoria_videojuego = ? AND estado = 'PENDIENTE'";

    private static final String RECHAZAR_CATEGORIA_QUERY = "UPDATE videojuego_categoria "
            + "SET estado = 'RECHAZADA', fecha_revision = NOW() "
            + "WHERE id_categoria_videojuego = ? AND estado = 'PENDIENTE'";

    private static final String AGREGAR_COMISION_QUERY = "INSERT INTO empresa_comision_especifica (id_empresa, comision_especifica) "
            + "VALUES (?, ?) "
            + "ON DUPLICATE KEY UPDATE comision_especifica = ?";

    private static final String SELECT_GLOBAL = "SELECT comision_global FROM configuracion_comision WHERE id_config = 1";
    private static final String UPDATE_GLOBAL = "UPDATE configuracion_comision SET comision_global = ? WHERE id_config = 1";

    private static final String SELECT_EMPRESA = "SELECT comision_especifica FROM empresa_comision_especifica WHERE id_empresa = ?";
    private static final String UPDATE_EMPRESA = "UPDATE empresa_comision_especifica SET comision_especifica = ? WHERE id_empresa = ?";
    private static final String SELECT_EMPRESAS_MAYOR = "SELECT id_empresa FROM empresa_comision_especifica WHERE comision_especifica > ?";

    public void agregarComision(int idEmpresa, double comision) {
        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(AGREGAR_COMISION_QUERY)) {
            ps.setInt(1, idEmpresa);
            ps.setDouble(2, comision);
            ps.setDouble(3, comision);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean aprobarCategoria(int idCategoriaVideojuego) throws SQLException {
        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(APROBAR_CATEGORIA_QUERY)) {

            query.setInt(1, idCategoriaVideojuego);
            return query.executeUpdate() > 0;
        }
    }

    public boolean rechazarCategoria(int idCategoriaVideojuego) throws SQLException {
        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(RECHAZAR_CATEGORIA_QUERY)) {

            query.setInt(1, idCategoriaVideojuego);
            return query.executeUpdate() > 0;
        }
    }

    public double obtenerComisionGlobal() throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(SELECT_GLOBAL); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("comision_global");
            }
            return 0.0; // Por defecto
        }
    }

    public void actualizarComisionGlobal(double nuevoPorcentaje) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(UPDATE_GLOBAL)) {

            ps.setDouble(1, nuevoPorcentaje);
            ps.executeUpdate();
        }
    }

    public double obtenerComisionEspecifica(int idEmpresa) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(SELECT_EMPRESA)) {

            ps.setInt(1, idEmpresa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("comision_especifica");
                }
                return 0.0;
            }
        }
    }

    public void actualizarComisionEspecifica(int idEmpresa, double porcentaje) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(UPDATE_EMPRESA)) {

            ps.setDouble(1, porcentaje);
            ps.setInt(2, idEmpresa);
            ps.executeUpdate();
        }
    }

    public List<Integer> empresasConComisionMayor(double porcentaje) throws SQLException {
        List<Integer> lista = new ArrayList<>();
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(SELECT_EMPRESAS_MAYOR)) {

            ps.setDouble(1, porcentaje);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getInt("id_empresa"));
                }
            }
        }
        return lista;
    }

    public void actualizarComisionEmpresa(int idEmpresa, BigDecimal comision) throws SQLException {
        String queryCheck = "SELECT COUNT(*) FROM empresa_comision_especifica WHERE id_empresa = ?";
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement psCheck = conn.prepareStatement(queryCheck)) {

            psCheck.setInt(1, idEmpresa);
            ResultSet rs = psCheck.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                String updateQuery = "UPDATE empresa_comision_especifica SET comision_especifica = ? WHERE id_empresa = ?";
                try (PreparedStatement psUpdate = conn.prepareStatement(updateQuery)) {
                    psUpdate.setBigDecimal(1, comision);
                    psUpdate.setInt(2, idEmpresa);
                    psUpdate.executeUpdate();
                }
            } else {
                String insertQuery = "INSERT INTO empresa_comision_especifica (id_empresa, comision_especifica) VALUES (?, ?)";
                try (PreparedStatement psInsert = conn.prepareStatement(insertQuery)) {
                    psInsert.setInt(1, idEmpresa);
                    psInsert.setBigDecimal(2, comision);
                    psInsert.executeUpdate();
                }
            }
        }
    }

}
