/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Resources.ReporteEmpresa.FeedbackCalificacionDTO;
import Resources.ReporteEmpresa.FeedbackComentarioDTO;
import Resources.ReporteEmpresa.FeedbackPeorCalificacionDTO;
import Resources.ReporteEmpresa.TopVentaEmpresaDTO;
import Resources.ReporteEmpresa.VentaPropiaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alejandro
 */
public class ReporteEmpresaDBA {

    private static final String VENTA_PROPIA_QUERY
            = "SELECT v.id_videojuego, v.titulo_videojuego, "
            + "SUM(c.precio_pagado) AS monto_bruto, "
            + "porc.comision_usada, "
            + "SUM(c.precio_pagado * porc.comision_usada) AS comision_plataforma, "
            + "SUM(c.precio_pagado - (c.precio_pagado * porc.comision_usada)) AS ingreso_neto "
            + "FROM videojuego v "
            + "LEFT JOIN compra c ON c.id_videojuego = v.id_videojuego "
            + "JOIN ( "
            + "   SELECT e.id_empresa, "
            + "   IF(ce.comision_especifica IS NULL, cfg.comision_global, ce.comision_especifica) AS comision_usada "
            + "   FROM empresa_desarrolladora e "
            + "   LEFT JOIN empresa_comision_especifica ce ON ce.id_empresa = e.id_empresa "
            + "   JOIN configuracion_comision cfg ON cfg.id_config = 1 "
            + ") porc ON porc.id_empresa = v.id_empresa "
            + "WHERE v.id_empresa = ? "
            + "GROUP BY v.id_videojuego, v.titulo_videojuego, porc.comision_usada";

    private static final String CALIFICACION_PROMEDIO
            = "SELECT v.id_videojuego, v.titulo_videojuego, AVG(ca.calificacion) AS promedio_calificacion, "
            + "COUNT(ca.id_calificacion) AS total_calificaciones "
            + "FROM videojuego v "
            + "LEFT JOIN calificacion ca ON ca.id_videojuego = v.id_videojuego "
            + "WHERE v.id_empresa = ? "
            + "GROUP BY v.id_videojuego, v.titulo_videojuego";

    private static final String MEJORES_COMENTARIOS
            = "SELECT c.id_comentario, c.id_videojuego, v.titulo_videojuego, c.texto, c.fecha, "
            + "(SELECT COUNT(*) FROM comentario r WHERE r.id_comentario_padre = c.id_comentario) AS total_respuestas "
            + "FROM comentario c "
            + "JOIN videojuego v ON v.id_videojuego = c.id_videojuego "
            + "WHERE v.id_empresa = ? "
            + "ORDER BY total_respuestas DESC LIMIT 10";

    private static final String PEORES_CALIFICACIONES
            = "SELECT v.id_videojuego, v.titulo_videojuego, ca.id_usuario, ca.calificacion, ca.fecha "
            + "FROM calificacion ca "
            + "JOIN videojuego v ON v.id_videojuego = ca.id_videojuego "
            + "WHERE v.id_empresa = ? "
            + "ORDER BY ca.calificacion ASC, ca.fecha DESC";

    private static final String TOP_5_JUEGOS
            = "SELECT v.id_videojuego, v.titulo_videojuego, "
            + "COUNT(c.id_compra) AS total_ventas "
            + "FROM videojuego v "
            + "LEFT JOIN compra c ON c.id_videojuego = v.id_videojuego "
            + "    AND c.fecha_compra BETWEEN ? AND ? "
            + "WHERE v.id_empresa = ? "
            + "GROUP BY v.id_videojuego, v.titulo_videojuego "
            + "ORDER BY total_ventas DESC "
            + "LIMIT 5";

    public ArrayList<VentaPropiaDTO> reporteVentaPropia(Integer idEmpresa) {

        ArrayList<VentaPropiaDTO> lista = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(VENTA_PROPIA_QUERY)) {

            query.setInt(1, idEmpresa);
            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                lista.add(new VentaPropiaDTO(
                        resultSet.getInt("id_videojuego"),
                        resultSet.getString("titulo_videojuego"),
                        resultSet.getDouble("monto_bruto"),
                        resultSet.getDouble("comision_plataforma"),
                        resultSet.getDouble("ingreso_neto"),
                        resultSet.getDouble("comision_usada") 
                ));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public ArrayList<FeedbackCalificacionDTO> obtenerCalificacionesPromedio(int idEmpresa) {
        ArrayList<FeedbackCalificacionDTO> lista = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(CALIFICACION_PROMEDIO)) {

            query.setInt(1, idEmpresa);

            try (ResultSet resultSet = query.executeQuery()) {
                while (resultSet.next()) {
                    lista.add(new FeedbackCalificacionDTO(
                            resultSet.getInt("id_videojuego"),
                            resultSet.getString("titulo_videojuego"),
                            resultSet.getDouble("promedio_calificacion"),
                            resultSet.getInt("total_calificaciones")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public ArrayList<FeedbackComentarioDTO> obtenerMejoresComentarios(int idEmpresa) {
        ArrayList<FeedbackComentarioDTO> lista = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(MEJORES_COMENTARIOS)) {

            query.setInt(1, idEmpresa);

            try (ResultSet resutSet = query.executeQuery()) {
                while (resutSet.next()) {
                    lista.add(new FeedbackComentarioDTO(
                            resutSet.getInt("id_comentario"),
                            resutSet.getInt("id_videojuego"),
                            resutSet.getString("titulo_videojuego"),
                            resutSet.getString("texto"),
                            resutSet.getInt("total_respuestas"),
                            resutSet.getDate("fecha").toLocalDate().toString()
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public ArrayList<FeedbackPeorCalificacionDTO> obtenerPeoresCalificaciones(int idEmpresa) {
        ArrayList<FeedbackPeorCalificacionDTO> lista = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(PEORES_CALIFICACIONES)) {

            query.setInt(1, idEmpresa);

            try (ResultSet resultSet = query.executeQuery()) {
                while (resultSet.next()) {
                    lista.add(new FeedbackPeorCalificacionDTO(
                            resultSet.getInt("id_videojuego"),
                            resultSet.getString("titulo_videojuego"),
                            resultSet.getInt("calificacion"),
                            resultSet.getInt("id_usuario"),
                            resultSet.getDate("fecha").toLocalDate().toString()
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public ArrayList<TopVentaEmpresaDTO> obtenerTop5Juegos(int idEmpresa, String fechaInicio, String fechaFin) {

        ArrayList<TopVentaEmpresaDTO> lista = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(TOP_5_JUEGOS)) {

            query.setString(1, fechaInicio);
            query.setString(2, fechaFin);
            query.setInt(3, idEmpresa);

            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                lista.add(new TopVentaEmpresaDTO(
                        rs.getInt("id_videojuego"),
                        rs.getString("titulo_videojuego"),
                        rs.getInt("total_ventas")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
