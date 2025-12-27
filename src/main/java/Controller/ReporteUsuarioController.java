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

        ArrayList<HistorialGastosDTO> gasto = usuarioService.historialGastos(idUsuario);
        return Response.ok(gasto).build();
    }

    @GET
    @Path("/biblioteca-analisis/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAnalisisBiblioteca(@PathParam("idUsuario") int idUsuario) {
        try {
            return Response.ok(usuarioService.analisisBiblioteca(idUsuario)).build();
        } catch (Exception e) {
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/categorias-favoritas/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoriasFavoritas(@PathParam("idUsuario") int idUsuario) {
        try {
            return Response.ok(usuarioService.categoriaFavorita(idUsuario)).build();
        } catch (Exception e) {
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/uso-biblioteca-familiar/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBibliotecaFamiliar(@QueryParam("idUsuario") int idUsuario) {
        try {
            return Response.ok(usuarioService.usoBiblioteca(idUsuario)).build();
        } catch (Exception e) {
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

}
