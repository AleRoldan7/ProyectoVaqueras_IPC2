/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Excepciones.EntidadNotFound;
import Services.SistemaService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author alejandro
 */
@Path("/sistema")
public class SistemaController {

    private SistemaService sistemaService = new SistemaService();

    @GET
    @Path("/categorias/pendientes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCategoriasPendientes() {

        try {

            return Response.ok(
                    sistemaService.obtenerCategoriasPendientes()
            ).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error inesperado")
                    .build();
        }
    }

    @PUT
    @Path("/categorias/{id}/aprobar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response aprobarCategoria(@PathParam("id") Integer idCategoriaVideojuego) {

        try {

            sistemaService.aprobarCategoria(idCategoriaVideojuego);

            return Response.ok(
                    java.util.Map.of("mensaje", "Categoría aprobada correctamente")
            ).build();

        } catch (EntidadNotFound e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error inesperado")
                    .build();
        }
    }

    @PUT
    @Path("/categorias/{id}/rechazar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response rechazarCategoria(@PathParam("id") Integer idCategoriaVideojuego) {

        try {

            sistemaService.rechazarCategoria(idCategoriaVideojuego);

            return Response.ok(
                    java.util.Map.of("mensaje", "Categoría rechazada correctamente")
            ).build();

        } catch (EntidadNotFound e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error inesperado")
                    .build();
        }
    }

}
