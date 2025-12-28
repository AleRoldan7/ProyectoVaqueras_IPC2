/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Excepciones.JasperException;
import Resources.ReporteEmpresa.TopVentaEmpresaDTO;
import Resources.ReporteEmpresa.VentaPropiaDTO;
import Services.ReporteEmpresaService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alejandro
 */
@Path("/empresa/reportes")
public class ReporteEmpresaController {

    private ReporteEmpresaService reporte = new ReporteEmpresaService();

    @GET
    @Path("/ventas-propias/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ventasPropias(@PathParam("idEmpresa") int idEmpresa) {

        ArrayList<VentaPropiaDTO> datos = reporte.reporteVentasPropias(idEmpresa);

        return Response.ok(datos).build();
    }

    @GET
    @Path("/ventas-propias/{idEmpresa}/pdf")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generarPDFVentaPropia(@PathParam("idEmpresa") int idEmpresa) {

        try {
            byte[] pdf = reporte.generarPDFVentaPropia(idEmpresa);

            return Response.ok(pdf)
                    .header(
                            "Content-Disposition",
                            "attachment; filename=ReporteVentaPropia.pdf"
                    )
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error generando PDF global: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/feedback/calificaciones/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response calificacionPromedio(@PathParam("idEmpresa") int idEmpresa) {
        return Response.ok(reporte.calificacionPromedio(idEmpresa)).build();
    }

    @GET
    @Path("/feedback/calificaciones/{idEmpresa}/pdf")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generarPDFCalificacion(@PathParam("idEmpresa") int idEmpresa) throws Exception {

        try {
            byte[] pdf = reporte.generarPDFCalificaciones(idEmpresa);

            return Response.ok(pdf)
                    .header(
                            "Content-Disposition",
                            "attachment; filename=Calificacion_Promedio.pdf"
                    )
                    .build();

        } catch (JasperException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error generando PDF global: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/feedback/comentarios/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mejoresComentarios(@PathParam("idEmpresa") int idEmpresa,
            @QueryParam("inicio") String inicio, @QueryParam("fin") String fin) {
        return Response.ok(reporte.mejoresComentarios(idEmpresa)).build();
    }

    @GET
    @Path("/feedback/comentarios/{idEmpresa}/pdf")
    @Produces("application/pdf")
    public Response generarPDFComentarios(
            @PathParam("idEmpresa") int idEmpresa) {

        try {
            byte[] pdf = reporte.generarPDFMejoresComentarios(idEmpresa);
            return Response.ok(pdf)
                    .header("Content-Disposition", "attachment; filename=MejoresComentarios.pdf")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/feedback/peores-calificaciones/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response peoresCalificaciones(@PathParam("idEmpresa") int idEmpresa,
            @QueryParam("inicio") String inicio, @QueryParam("fin") String fin) {
        return Response.ok(reporte.peoresCalificaciones(idEmpresa)).build();
    }

    @GET
    @Path("/feedback/peores-calificaciones/{idEmpresa}/pdf")
    @Produces("application/pdf")
    public Response generarPDFPeorCalificacion(
            @PathParam("idEmpresa") int idEmpresa) {

        try {
            byte[] pdf = reporte.generarPDFPeorCalificacion(idEmpresa);
            return Response.ok(pdf)
                    .header("Content-Disposition", "attachment; filename=PeoresCalificaciones.pdf")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/top5-juegos/{idEmpresa}/{inicio}/{fin}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response top5Juegos(
            @PathParam("idEmpresa") int idEmpresa,
            @PathParam("inicio") String inicio,
            @PathParam("fin") String fin) {

        List<TopVentaEmpresaDTO> datos = reporte.top5Juegos(idEmpresa, inicio, fin);
        return Response.ok(datos).build();
    }

    @GET
    @Path("/top5-juegos/{idEmpresa}/{inicio}/{fin}/pdf")
    @Produces("application/pdf")
    public Response generarPDFTop5(
            @PathParam("idEmpresa") int idEmpresa,
            @PathParam("inicio") String inicio,
            @PathParam("fin") String fin) {

        try {
            byte[] pdf = reporte.generarPDFTop5(idEmpresa, inicio, fin);
            return Response.ok(pdf)
                    .header("Content-Disposition", "attachment; filename=Top5Ventas.pdf")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

}
