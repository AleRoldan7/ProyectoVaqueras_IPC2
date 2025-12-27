package ConexionDBA;

import ConexionDBA.Conexion;
import Dtos.Empresa.NotificacionResponse;
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

    private static final String APROBAR_CATEGORIA_QUERY
            = "UPDATE videojuego_categoria "
            + "SET estado = 'APROBADA', fecha_revision = NOW() "
            + "WHERE id_categoria_videojuego = ? AND estado = 'PENDIENTE'";

    private static final String ACTIVAR_VENTA_VIDEOJUEGO
            = "UPDATE videojuego SET estado_venta = 1 WHERE id_videojuego = ?";

    private static final String CONTAR_CATEGORIAS_APROBADAS_ANTES
            = "SELECT COUNT(*) FROM videojuego_categoria WHERE id_videojuego = ? AND estado = 'APROBADA'";

    private static final String RECHAZAR_CATEGORIA_QUERY
            = "UPDATE videojuego_categoria "
            + "SET estado = 'RECHAZADA', fecha_revision = NOW() "
            + "WHERE id_categoria_videojuego = ? AND estado = 'PENDIENTE'";

    private static final String OBTENER_VIDEOJUEGO_POR_SOLICITUD
            = "SELECT v.id_videojuego, v.titulo_videojuego "
            + "FROM videojuego_categoria vc "
            + "JOIN videojuego v ON vc.id_videojuego = v.id_videojuego "
            + "WHERE vc.id_categoria_videojuego = ?";

    private static final String INSERT_NOTIFICACION_RECHAZO
            = "INSERT INTO notificacion (id_usuario, titulo, mensaje, fecha, leida) "
            + "SELECT u.id_usuario, ?, ?, NOW(), 0 "
            + "FROM usuario u "
            + "JOIN empresa_desarrolladora ed ON u.id_usuario = ed.id_usuario "
            + "JOIN videojuego v ON ed.id_empresa = v.id_empresa "
            + "WHERE v.id_videojuego = ?";

    private static final String AGREGAR_COMISION_QUERY
            = "INSERT INTO empresa_comision_especifica (id_empresa, comision_especifica) "
            + "VALUES (?, ?) ON DUPLICATE KEY UPDATE comision_especifica = VALUES(comision_especifica)";

    private static final String SELECT_GLOBAL = "SELECT comision_global FROM configuracion_comision WHERE id_config = 1";

    private static final String UPDATE_GLOBAL = "UPDATE configuracion_comision SET comision_global = ? WHERE id_config = 1";

    private static final String SELECT_EMPRESA = "SELECT comision_especifica FROM empresa_comision_especifica WHERE id_empresa = ?";

    private static final String SELECT_EMPRESAS_MAYOR = "SELECT id_empresa FROM empresa_comision_especifica WHERE comision_especifica > ?";

    private static final String UPDATE_COMISION_ESPECIFICA = "UPDATE empresa_comision_especifica SET comision_especifica = ? WHERE id_empresa = ?";

    private static final String LISTAR_NOTIFICACIONES_USUARIO
            = "SELECT id_notificacion, titulo, mensaje, fecha, leida "
            + "FROM notificacion "
            + "WHERE id_usuario = ? "
            + "ORDER BY fecha DESC";

    private static final String MARCAR_COMO_LEIDA
            = "UPDATE notificacion SET leida = 1 WHERE id_notificacion = ? AND id_usuario = ?";

    public boolean aprobarCategoria(int idCategoriaVideojuego) throws SQLException {
        Connection conn = null;
        try {
            conn = Conexion.getInstance().getConnect();
            conn.setAutoCommit(false);

            int idVideojuego = 0;
            try (PreparedStatement psInfo = conn.prepareStatement(OBTENER_VIDEOJUEGO_POR_SOLICITUD)) {
                psInfo.setInt(1, idCategoriaVideojuego);
                try (ResultSet rs = psInfo.executeQuery()) {
                    if (rs.next()) {
                        idVideojuego = rs.getInt("id_videojuego");
                    } else {
                        throw new SQLException("Solicitud no encontrada");
                    }
                }
            }

            int categoriasAprobadasAntes = 0;
            try (PreparedStatement psCount = conn.prepareStatement(CONTAR_CATEGORIAS_APROBADAS_ANTES)) {
                psCount.setInt(1, idVideojuego);
                try (ResultSet rs = psCount.executeQuery()) {
                    if (rs.next()) {
                        categoriasAprobadasAntes = rs.getInt(1);
                    }
                }
            }

            try (PreparedStatement psAprobar = conn.prepareStatement(APROBAR_CATEGORIA_QUERY)) {
                psAprobar.setInt(1, idCategoriaVideojuego);
                int updated = psAprobar.executeUpdate();
                if (updated == 0) {
                    throw new SQLException("No se pudo aprobar: ya procesada o no pendiente");
                }
            }

            if (categoriasAprobadasAntes == 0) {
                try (PreparedStatement psActivar = conn.prepareStatement(ACTIVAR_VENTA_VIDEOJUEGO)) {
                    psActivar.setInt(1, idVideojuego);
                    psActivar.executeUpdate();
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    public boolean rechazarCategoria(int idCategoriaVideojuego) throws SQLException {
        Connection conn = null;
        try {
            conn = Conexion.getInstance().getConnect();
            conn.setAutoCommit(false);

            int idVideojuego = 0;
            String tituloVideojuego = "";

            try (PreparedStatement psInfo = conn.prepareStatement(OBTENER_VIDEOJUEGO_POR_SOLICITUD)) {
                psInfo.setInt(1, idCategoriaVideojuego);
                try (ResultSet rs = psInfo.executeQuery()) {
                    if (rs.next()) {
                        idVideojuego = rs.getInt("id_videojuego");
                        tituloVideojuego = rs.getString("titulo_videojuego");
                    } else {
                        throw new SQLException("Solicitud no encontrada");
                    }
                }
            }

            try (PreparedStatement psRechazo = conn.prepareStatement(RECHAZAR_CATEGORIA_QUERY)) {
                psRechazo.setInt(1, idCategoriaVideojuego);
                int updated = psRechazo.executeUpdate();
                if (updated == 0) {
                    throw new SQLException("No se pudo rechazar: ya procesada o no pendiente");
                }
            }

            String tituloNotif = "Categoría rechazada";
            String mensajeNotif = String.format(
                    "La categoría solicitada para tu videojuego \"%s\" ha sido rechazada por el administrador.",
                    tituloVideojuego
            );

            try (PreparedStatement psNotif = conn.prepareStatement(INSERT_NOTIFICACION_RECHAZO)) {
                psNotif.setString(1, tituloNotif);
                psNotif.setString(2, mensajeNotif);
                psNotif.setInt(3, idVideojuego);
                int filasAfectadas = psNotif.executeUpdate();
                if (filasAfectadas == 0) {
                    System.out.println("Advertencia: No se encontró representante para notificar (videojuego ID: " + idVideojuego + ")");
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    public void agregarOActualizarComision(int idEmpresa, double comisionDecimal) throws SQLException {
        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(AGREGAR_COMISION_QUERY)) {
            ps.setInt(1, idEmpresa);
            ps.setDouble(2, comisionDecimal);
            ps.executeUpdate();
        }
    }

    public double obtenerComisionGlobal() throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(SELECT_GLOBAL); ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getDouble("comision_global") : 0.15; // valor por defecto 15%
        }
    }

    public void actualizarComisionGlobal(double nuevoValorDecimal) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(UPDATE_GLOBAL)) {
            ps.setDouble(1, nuevoValorDecimal);
            ps.executeUpdate();
        }
    }

    public double obtenerComisionEspecifica(int idEmpresa) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(SELECT_EMPRESA)) {
            ps.setInt(1, idEmpresa);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getDouble("comision_especifica") : 0.0;
            }
        }
    }

    public List<Integer> empresasConComisionMayor(double porcentajeDecimal) throws SQLException {
        List<Integer> lista = new ArrayList<>();
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(SELECT_EMPRESAS_MAYOR)) {
            ps.setDouble(1, porcentajeDecimal);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getInt("id_empresa"));
                }
            }
        }
        return lista;
    }

    public void actualizarComisionEspecifica(int idEmpresa, double comisionDecimal) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(UPDATE_COMISION_ESPECIFICA)) {
            ps.setDouble(1, comisionDecimal);
            ps.setInt(2, idEmpresa);
            ps.executeUpdate();
        }
    }

    public void actualizarComisionGlobalYAdjustarEmpresas(double nuevoValorDecimal, double porcentajeHumano) throws SQLException {
        Connection conn = null;
        try {
            conn = Conexion.getInstance().getConnect();
            conn.setAutoCommit(false);

            try (PreparedStatement psGlobal = conn.prepareStatement(UPDATE_GLOBAL)) {
                psGlobal.setDouble(1, nuevoValorDecimal);
                psGlobal.executeUpdate();
            }

            List<Integer> empresasAjustar = new ArrayList<>();
            try (PreparedStatement psMayor = conn.prepareStatement(SELECT_EMPRESAS_MAYOR)) {
                psMayor.setDouble(1, nuevoValorDecimal);
                try (ResultSet rs = psMayor.executeQuery()) {
                    while (rs.next()) {
                        empresasAjustar.add(rs.getInt("id_empresa"));
                    }
                }
            }

            try (PreparedStatement psUpdate = conn.prepareStatement(UPDATE_COMISION_ESPECIFICA)) {
                for (int idEmpresa : empresasAjustar) {
                    psUpdate.setDouble(1, nuevoValorDecimal);
                    psUpdate.setInt(2, idEmpresa);
                    psUpdate.addBatch();
                }
                psUpdate.executeBatch();
            }

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    public List<NotificacionResponse> obtenerNotificaciones(int idUsuario) throws SQLException {
        List<NotificacionResponse> lista = new ArrayList<>();
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(LISTAR_NOTIFICACIONES_USUARIO)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NotificacionResponse n = new NotificacionResponse();
                    n.setIdNotificacion(rs.getInt("id_notificacion"));
                    n.setTitulo(rs.getString("titulo"));
                    n.setMensaje(rs.getString("mensaje"));
                    n.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                    n.setLeida(rs.getBoolean("leida"));
                    lista.add(n);
                }
            }
        }
        return lista;
    }

    public boolean marcarComoLeida(int idNotificacion, int idUsuario) throws SQLException {
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(MARCAR_COMO_LEIDA)) {
            ps.setInt(1, idNotificacion);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }
}
