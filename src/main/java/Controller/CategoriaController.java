/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dtos.Categoria.NewCategoriaRequest;
import Dtos.Categoria.UpdateCategoriaRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Excepciones.EntityExists;
import Services.CategoriaService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alejandro
 */
@Path("/categoria")
public class CategoriaController {

    private CategoriaService categoriaService = new CategoriaService();

    @POST
    @Path("/crear-categoria")
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearEmpresa(NewCategoriaRequest newCategoriaRequest) {

        try {

            categoriaService.agregarCategoria(newCategoriaRequest);

            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Categoria Creada");

            return Response.status(Response.Status.CREATED).build();

        } catch (DatosInvalidos e) {

            Map<String, String> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", "Datos inválidos: " + e.getMessage());

            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (EntityExists e) {

            Map<String, String> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", "La categoria ya existe: " + e.getMessage());

            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @PUT
    @Path("/actualizar-categoria")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarCategoria(UpdateCategoriaRequest updateCategoriaRequest) throws EntidadNotFound {

        try {

            categoriaService.actualizarCategoria(updateCategoriaRequest);

            return Response.status(Response.Status.ACCEPTED)
                    .entity(Map.of("exito", "Se actualizo la pelicula"))
                    .build();

        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (EntidadNotFound e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (EntityExists e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error inesperado: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/videojuegos/{idVideojuego}/categorias/{idCategoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response solicitarCategoriaVideojuego(@PathParam("idVideojuego") Integer idVideojuego,
            @PathParam("idCategoria") Integer idCategoria) {

        try {

            categoriaService.solicitarCategoria(idVideojuego, idCategoria);

            return Response.status(Response.Status.CREATED)
                    .entity(Map.of("mensaje", "Categoría enviada a revisión"))
                    .build();

        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error inesperado: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/eliminar-categoria/{idCategoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarCategoria(@PathParam("idCategoria") Integer idCategoria) {

        try {
            categoriaService.eliminarCategoria(idCategoria);
            return Response.ok(Map.of("mensaje", "Categoría eliminada correctamente")).build();

        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (EntidadNotFound e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error interno"))
                    .build();
        }
    }

}
