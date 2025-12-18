/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Resources.ReporteAdministrador.GananciaSistemaDTO;
import Resources.ReporteAdministrador.TopBalanceDTO;
import Resources.ReporteAdministrador.TopCalidaDTO;
import Resources.ReporteAdministrador.TopVentaDTO;
import Services.ReporteSistemaService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author alejandro
 */
@Path("/reportes")
public class ReporteSistemaController {

    private final ReporteSistemaService reporteService = new ReporteSistemaService();

    /*Controller para reportes de administrador de sistema*/
    @GET
    @Path("/ganancias-globales")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gananciasGlobales() {
        return Response.ok(reporteService.reporteGananciaGlobal()).build();
    }

    @GET
    @Path("/ganancias-globales/pdf")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generarPDFGananciasGlobales() {

        try {
            GananciaSistemaDTO dto = reporteService.reporteGananciaGlobal();

            byte[] pdf = reporteService.generarPDFGanancias(
                    dto,
                    "Histórico",
                    "Actual"
            );

            return Response.ok(pdf)
                    .header("Content-Disposition", "attachment; filename=GananciasGlobales.pdf")
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(("Error generando PDF: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/top-ventas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response topVentas() {
        List<TopVentaDTO> data = reporteService.topVentas();
        return Response.ok(data).build();
    }

    @GET
    @Path("/top-calidad")
    @Produces(MediaType.APPLICATION_JSON)
    public Response topCalidad() {
        List<TopCalidaDTO> data = reporteService.topCalidad();
        return Response.ok(data).build();
    }

    @GET
    @Path("/top-balance")
    @Produces(MediaType.APPLICATION_JSON)
    public Response topBalance() {
        List<TopBalanceDTO> data = reporteService.topBalance();
        return Response.ok(data).build();
    }

    @GET
    @Path("/categoria/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtroCategoria(@PathParam("nombre") String nombre) {
        return Response.ok(reporteService.filtrarCategoria(nombre)).build();
    }

    @GET
    @Path("/clasificacion/{edad}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtroClasificacion(@PathParam("edad") String edad) {
        return Response.ok(reporteService.filtrarClasificacion(edad)).build();
    }

    @GET
    @Path("/ingresos-empresa")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ingresosPorEmpresa() {
        return Response.ok(new ReporteSistemaService().reporteIngresosEmpresa()).build();
    }

    @GET
    @Path("/ranking-usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response rankingUsuarios() {
        return Response.ok(new ReporteSistemaService().rankingUsuarios()).build();
    }

}
