/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Resources.ReporteUsuario.HistorialGastosDTO;
import Services.ReporteUsuarioService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

/**
 *
 * @author alejandro
 */
@Path("/usuario/reportes")
public class ReporteUsuarioController {

    private ReporteUsuarioService usuarioService = new ReporteUsuarioService();

    @GET
    @Path("/historial-compras/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response historialCompras(@PathParam("idUsuario") int idUsuario) {

        try {
            ArrayList<HistorialGastosDTO> gasto = usuarioService.historialGastos(idUsuario);
            return Response.ok(gasto).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/historial-compras/{idUsuario}/pdf")
    @Produces("application/pdf")
    public Response generarPDFHistorialCompras(@PathParam("idUsuario") int idUsuario) {

        try {
            byte[] pdf = usuarioService.exportarPDFHistorial(idUsuario);

            return Response.ok(pdf)
                    .header(
                            "Content-Disposition",
                            "attachment; filename=ReporteHistorialCompras.pdf"
                    )
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error generando PDF global: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/biblioteca-analisis/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAnalisisBiblioteca(@PathParam("idUsuario") int idUsuario) {
        try {
            return Response.ok(usuarioService.analisisBiblioteca(idUsuario)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/biblioteca-analisis/{idUsuario}/pdf")
    @Produces("application/pdf")
    public Response generarPDFBibliotecaUsuario(@PathParam("idUsuario") int idUsuario) {

        try {
            byte[] pdf = usuarioService.exportarPDFAnalisisBiblioteca(idUsuario);

            return Response.ok(pdf)
                    .header(
                            "Content-Disposition",
                            "attachment; filename=BibliotecaPersonal.pdf"
                    )
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error generando PDF global: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/categorias-favoritas/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoriasFavoritas(@PathParam("idUsuario") int idUsuario) {
        try {
            return Response.ok(usuarioService.categoriaFavorita(idUsuario)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/categorias-favoritas/{idUsuario}/pdf")
    @Produces("application/pdf")
    public Response generarPDFCategoriaFavorita(@PathParam("idUsuario") int idUsuario) {

        try {
            byte[] pdf = usuarioService.exportarPDFCategoriaFavorita(idUsuario);

            return Response.ok(pdf)
                    .header(
                            "Content-Disposition",
                            "attachment; filename=CategoriaFavorita.pdf"
                    )
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error generando PDF global: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/uso-biblioteca-familiar/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBibliotecaFamiliar(@PathParam("idUsuario") int idUsuario) {
        try {
            System.out.println("si entro aca");
            return Response.ok(usuarioService.usoBiblioteca(idUsuario)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/uso-biblioteca-familiar/{idUsuario}/pdf")
    @Produces("application/pdf")
    public Response generarPDFBibliotecaFamiliar(@PathParam("idUsuario") int idUsuario) {

        try {
            byte[] pdf = usuarioService.exportarPDFUsoBiblioteca(idUsuario);

            return Response.ok(pdf)
                    .header(
                            "Content-Disposition",
                            "attachment; filename=BibliotecaFamiliar.pdf"
                    )
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error generando PDF global: " + e.getMessage())
                    .build();
        }
    }
}
