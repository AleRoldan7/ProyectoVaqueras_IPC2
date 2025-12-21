/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Resources.ReporteEmpresa.VentaPropiaDTO;
import Services.ReporteEmpresaService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

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
    @Path("/feedback/comentarios/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mejoresComentarios(@PathParam("idEmpresa") int idEmpresa) {
        return Response.ok(reporte.mejoresComentarios(idEmpresa)).build();
    }

    @GET
    @Path("/feedback/peores-calificaciones/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response peoresCalificaciones(@PathParam("idEmpresa") int idEmpresa) {
        return Response.ok(reporte.peoresCalificaciones(idEmpresa)).build();
    }

    @GET
    @Path("/top5-juegos/{idEmpresa}/{inicio}/{fin}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response top5Juegos(
            @PathParam("idEmpresa") int idEmpresa,
            @PathParam("inicio") String inicio,
            @PathParam("fin") String fin) {

        return Response.ok(reporte.top5Juegos(idEmpresa, inicio, fin)).build();
    }

}




