/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Resources.ReporteAdministrador.GananciaSistemaDTO;
import Resources.ReporteAdministrador.IngresoEmpresaDTO;
import Resources.ReporteAdministrador.RankingUsuarioDTO;
import Resources.ReporteAdministrador.TopBalanceDTO;
import Resources.ReporteAdministrador.TopCalidaDTO;
import Resources.ReporteAdministrador.TopVentaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class ReporteSistemaDBA {

    /*Querys  para reportes de administrador de sistema */
    private static final String GANANCIA_GLOBAL_QUERY
            = "SELECT "
            + "    SUM(c.precio_pagado) AS total_ingresos, "
            + "    SUM( "
            + "        c.precio_pagado * "
            + "        (CASE "
            + "            WHEN ece.comision_especifica IS NOT NULL THEN (1 - ece.comision_especifica) "
            + "            ELSE (1 - cc.comision_global) "
            + "        END) "
            + "    ) AS total_empresas, "
            + "    SUM( "
            + "        c.precio_pagado * "
            + "        (CASE "
            + "            WHEN ece.comision_especifica IS NOT NULL THEN ece.comision_especifica "
            + "            ELSE cc.comision_global "
            + "        END) "
            + "    ) AS comision_plataforma "
            + "FROM compra c "
            + "JOIN videojuego v ON c.id_videojuego = v.id_videojuego "
            + "LEFT JOIN empresa_comision_especifica ece ON v.id_empresa = ece.id_empresa "
            + "JOIN configuracion_comision cc ON cc.id_config = 1";

    private static final String TOP_VENTAS_QUERY
            = "SELECT v.id_videojuego, v.titulo_videojuego, COUNT(c.id_compra) AS ventas "
            + "FROM videojuego v "
            + "LEFT JOIN compra c ON v.id_videojuego = c.id_videojuego "
            + "GROUP BY v.id_videojuego "
            + "ORDER BY ventas DESC "
            + "LIMIT 10";

    private static final String TOP_CALIDAD_QUERY
            = "SELECT v.id_videojuego, v.titulo_videojuego, "
            + "AVG(cal.calificacion) AS promedio, COUNT(cal.id_calificacion) AS cantidad "
            + "FROM videojuego v "
            + "LEFT JOIN calificacion cal ON v.id_videojuego = cal.id_videojuego "
            + "GROUP BY v.id_videojuego "
            + "ORDER BY promedio DESC "
            + "LIMIT 10";

    private static final String TOP_BALANCE_QUERY
            = "SELECT v.id_videojuego, v.titulo_videojuego, "
            + "COUNT(c.id_compra) AS ventas, "
            + "IFNULL(AVG(cal.calificacion),0) AS promedio, "
            + "( IFNULL(AVG(cal.calificacion),0) "
            + "  + LOG10(COUNT(c.id_compra) + 1) "
            + "  + (LEAST(COUNT(c.id_compra),50) / 50) "
            + ") AS score "
            + "FROM videojuego v "
            + "LEFT JOIN compra c ON v.id_videojuego = c.id_videojuego "
            + "LEFT JOIN calificacion cal ON v.id_videojuego = cal.id_videojuego "
            + "GROUP BY v.id_videojuego "
            + "ORDER BY score DESC "
            + "LIMIT 10";

    private static final String FILTRO_CATEGORIA_QUERY
            = "SELECT v.id_videojuego, v.titulo_videojuego, COUNT(c.id_compra) AS ventas "
            + "FROM videojuego v "
            + "JOIN videojuego_categoria vc ON v.id_videojuego = vc.id_videojuego "
            + "JOIN categoria cat ON vc.id_categoria = cat.id_categoria "
            + "LEFT JOIN compra c ON v.id_videojuego = c.id_videojuego "
            + "WHERE cat.nombre_categoria = ? "
            + "GROUP BY v.id_videojuego "
            + "ORDER BY ventas DESC";

    private static final String FILTRO_CLASIFICACION_QUERY
            = "SELECT v.id_videojuego, v.titulo_videojuego, COUNT(c.id_compra) AS ventas "
            + "FROM videojuego v "
            + "LEFT JOIN compra c ON v.id_videojuego = c.id_videojuego "
            + "WHERE v.clasificacion_edad = ? "
            + "GROUP BY v.id_videojuego "
            + "ORDER BY ventas DESC";

    /*
    private static final String REPORTE_INGRESOS_EMPRESA_QUERY
            = "SELECT "
            + "    e.id_empresa, "
            + "    e.nombre_empresa, "
            + "    COALESCE(SUM(c.precio_pagado), 0) AS total_ventas, "
            + "    porc.comision_usada, "
            + "    COALESCE(SUM(c.precio_pagado * porc.comision_usada), 0) "
            + "        AS comision_plataforma, "
            + "    COALESCE(SUM(c.precio_pagado * (1 - porc.comision_usada)), 0) "
            + "        AS ingreso_empresa "
            + "FROM empresa_desarrolladora e "
            + "LEFT JOIN videojuego v "
            + "    ON v.id_empresa = e.id_empresa "
            + "LEFT JOIN compra c "
            + "    ON c.id_videojuego = v.id_videojuego "
            + "LEFT JOIN ( "
            + "    SELECT "
            + "        e2.id_empresa, "
            + "        CASE "
            + "            WHEN ce.comision_especifica IS NULL "
            + "                THEN cfg.comision_global "
            + "            ELSE ce.comision_especifica "
            + "        END AS comision_usada "
            + "    FROM empresa_desarrolladora e2 "
            + "    LEFT JOIN empresa_comision_especifica ce "
            + "        ON ce.id_empresa = e2.id_empresa "
            + "    JOIN configuracion_comision cfg "
            + "        ON cfg.id_config = 1 "
            + ") porc "
            + "    ON porc.id_empresa = e.id_empresa "
            + "GROUP BY "
            + "    e.id_empresa, "
            + "    e.nombre_empresa, "
            + "    porc.comision_usada "
            + "ORDER BY e.nombre_empresa;";
     */
    private static final String REPORTE_INGRESOS_EMPRESA_QUERY
            = "SELECT "
            + "    e.id_empresa, "
            + "    e.nombre_empresa, "
            + "    COALESCE(SUM(c.precio_pagado), 0) AS total_ventas, "
            + "    porc.comision_usada, "
            + "    COALESCE(SUM(c.precio_pagado * porc.comision_usada), 0) AS comision_plataforma, "
            + "    COALESCE(SUM(c.precio_pagado * (1 - porc.comision_usada)), 0) AS ingreso_empresa "
            + "FROM empresa_desarrolladora e "
            + "LEFT JOIN videojuego v "
            + "    ON v.id_empresa = e.id_empresa "
            + "LEFT JOIN compra c "
            + "    ON c.id_videojuego = v.id_videojuego "
            + "    AND (? IS NULL OR c.fecha_compra >= ?) "
            + "    AND (? IS NULL OR c.fecha_compra <= ?) "
            + "LEFT JOIN ( "
            + "    SELECT "
            + "        e2.id_empresa, "
            + "        CASE "
            + "            WHEN ce.comision_especifica IS NULL "
            + "                THEN cfg.comision_global "
            + "            ELSE ce.comision_especifica "
            + "        END AS comision_usada "
            + "    FROM empresa_desarrolladora e2 "
            + "    LEFT JOIN empresa_comision_especifica ce "
            + "        ON ce.id_empresa = e2.id_empresa "
            + "    JOIN configuracion_comision cfg "
            + "        ON cfg.id_config = 1 "
            + ") porc ON porc.id_empresa = e.id_empresa "
            + "GROUP BY "
            + "    e.id_empresa, "
            + "    e.nombre_empresa, "
            + "    porc.comision_usada "
            + "ORDER BY e.nombre_empresa;";

    private static final String RANKING_USUARIO_QUERY
            = "SELECT u.id_usuario, u.nickname, u.correo, "
            + "       COUNT(DISTINCT c.id_compra) AS total_compras, "
            + "       COUNT(DISTINCT cm.id_comentario) AS total_comentarios "
            + "FROM usuario u "
            + "LEFT JOIN compra c ON c.id_usuario = u.id_usuario "
            + "LEFT JOIN comentario cm ON cm.id_usuario = u.id_usuario "
            + "WHERE u.tipo_usuario = 'USUARIO_COMUN' "
            + "GROUP BY u.id_usuario, u.nickname, u.correo "
            + "ORDER BY total_compras DESC, total_comentarios DESC;";

    public GananciaSistemaDTO obtenerReporteGanancia() {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(GANANCIA_GLOBAL_QUERY)) {

            try (ResultSet resultSet = query.executeQuery()) {

                if (resultSet.next()) {

                    return new GananciaSistemaDTO(
                            resultSet.getDouble("total_ingresos"),
                            resultSet.getDouble("total_empresas"),
                            resultSet.getDouble("comision_plataforma")
                    );
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("No hay datos para mostrar");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new GananciaSistemaDTO(0, 0, 0);
    }

    public List<TopVentaDTO> topVentas() {
        List<TopVentaDTO> lista = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(TOP_VENTAS_QUERY); ResultSet resultSet = query.executeQuery()) {

            while (resultSet.next()) {
                lista.add(new TopVentaDTO(
                        resultSet.getInt("id_videojuego"),
                        resultSet.getString("titulo_videojuego"),
                        resultSet.getInt("ventas")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<TopCalidaDTO> topCalidad() {
        List<TopCalidaDTO> lista = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(TOP_CALIDAD_QUERY); ResultSet resultSet = query.executeQuery()) {

            while (resultSet.next()) {
                lista.add(new TopCalidaDTO(
                        resultSet.getInt("id_videojuego"),
                        resultSet.getString("titulo_videojuego"),
                        resultSet.getDouble("promedio"),
                        resultSet.getInt("cantidad")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<TopBalanceDTO> topBalance() {
        List<TopBalanceDTO> lista = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(TOP_BALANCE_QUERY); ResultSet resultSet = query.executeQuery()) {

            while (resultSet.next()) {
                lista.add(new TopBalanceDTO(
                        resultSet.getInt("id_videojuego"),
                        resultSet.getString("titulo_videojuego"),
                        resultSet.getInt("ventas"),
                        resultSet.getDouble("promedio"),
                        resultSet.getDouble("score")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<TopVentaDTO> filtrarPorCategoria(String categoria) {
        List<TopVentaDTO> lista = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(FILTRO_CATEGORIA_QUERY)) {

            query.setString(1, categoria);

            try (ResultSet resultSet = query.executeQuery()) {

                while (resultSet.next()) {
                    lista.add(new TopVentaDTO(
                            resultSet.getInt("id_videojuego"),
                            resultSet.getString("titulo_videojuego"),
                            resultSet.getInt("ventas")
                    ));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<TopVentaDTO> filtrarPorClasificacion(String clasificacion) {
        List<TopVentaDTO> lista = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(FILTRO_CLASIFICACION_QUERY)) {

            query.setString(1, clasificacion);

            try (ResultSet resultSet = query.executeQuery()) {

                while (resultSet.next()) {
                    lista.add(new TopVentaDTO(
                            resultSet.getInt("id_videojuego"),
                            resultSet.getString("titulo_videojuego"),
                            resultSet.getInt("ventas")
                    ));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /*
    public ArrayList<IngresoEmpresaDTO> obtenerReporteIngresosEmpresa() {

        ArrayList<IngresoEmpresaDTO> lista = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(REPORTE_INGRESOS_EMPRESA_QUERY); ResultSet resultSet = query.executeQuery()) {

            while (resultSet.next()) {
                IngresoEmpresaDTO dto = new IngresoEmpresaDTO(
                        resultSet.getInt("id_empresa"),
                        resultSet.getString("nombre_empresa"),
                        resultSet.getDouble("total_ventas"),
                        resultSet.getDouble("comision_plataforma"),
                        resultSet.getDouble("ingreso_empresa")
                );

                lista.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
     */
    public ArrayList<IngresoEmpresaDTO> obtenerReporteIngresosEmpresa(String fechaInicio, String fechaFin) {

        ArrayList<IngresoEmpresaDTO> lista = new ArrayList<>();

        try (
                Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(
                REPORTE_INGRESOS_EMPRESA_QUERY
        )) {

            query.setString(1, fechaInicio);
            query.setString(2, fechaInicio);
            query.setString(3, fechaFin);
            query.setString(4, fechaFin);

            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                IngresoEmpresaDTO dto = new IngresoEmpresaDTO(
                        resultSet.getInt("id_empresa"),
                        resultSet.getString("nombre_empresa"),
                        resultSet.getDouble("total_ventas"),
                        resultSet.getDouble("comision_plataforma"),
                        resultSet.getDouble("ingreso_empresa")
                );
                lista.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public ArrayList<RankingUsuarioDTO> obtenerRankingUsuarios() {

        ArrayList<RankingUsuarioDTO> lista = new ArrayList<>();

        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(RANKING_USUARIO_QUERY); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RankingUsuarioDTO dto = new RankingUsuarioDTO(
                        rs.getInt("id_usuario"),
                        rs.getString("nickname"),
                        rs.getString("correo"),
                        rs.getInt("total_compras"),
                        rs.getInt("total_comentarios")
                );
                lista.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
