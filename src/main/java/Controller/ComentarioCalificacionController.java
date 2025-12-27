/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dtos.ComentarioCalificacion.CalificacionResponse;
import Dtos.ComentarioCalificacion.ComentarioResponse;
import Dtos.ComentarioCalificacion.NewCalificacionRequest;
import Dtos.ComentarioCalificacion.NewComentarioRequest;
import Services.ComentarioCalificacionService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author alejandro
 */
@Path("/comentario-calificacion")
public class ComentarioCalificacionController {

    private ComentarioCalificacionService comentarioCalificacionService = new ComentarioCalificacionService();

    @GET
    @Path("/videojuegos/{idVideojuego}/detalle")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerDetalle(
            @PathParam("idVideojuego") int idVideojuego,
            @QueryParam("idUsuario") Integer idUsuario 
    ) {
        try {
            Double promedio = comentarioCalificacionService.obtenerPromedioCalificacion(idVideojuego);
            CalificacionResponse califUsuario = null;
            boolean puedeComentar = false; 

            if (idUsuario != null) {
                try {
                    califUsuario = comentarioCalificacionService.obtenerCalificacion(idVideojuego, idUsuario);

                    ConexionDBA.BibliotecaDBA bibliotecaDBA = new ConexionDBA.BibliotecaDBA();
                    puedeComentar = bibliotecaDBA.usuarioPoseeVideojuego(idUsuario, idVideojuego);
                } catch (Exception ignored) {
                }
            }

            List<ComentarioResponse> comentarios = comentarioCalificacionService.obtenerComentarios(idVideojuego);

            return Response.ok(new DetalleResponse(
                    promedio,
                    califUsuario,
                    comentarios,
                    puedeComentar 
            )).build();

        } catch (Exception e) {
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/videojuegos/{idVideojuego}/calificar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response calificar(
            @PathParam("idVideojuego") int idVideojuego,
            @QueryParam("idUsuario") int idUsuario,
            NewCalificacionRequest request
    ) {
        try {
            comentarioCalificacionService.calificar(idUsuario, idVideojuego, request);
            return Response.ok("Calificación registrada con éxito").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(403).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/videojuegos/{idVideojuego}/comentar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response comentar(
            @PathParam("idVideojuego") int idVideojuego,
            @QueryParam("idUsuario") int idUsuario,
            NewComentarioRequest request
    ) {
        try {
            comentarioCalificacionService.comentar(idUsuario, idVideojuego, request);
            return Response.ok("Comentario publicado correctamente").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(403).entity(e.getMessage()).build();
        }
    }
}

class DetalleResponse {

    private Double promedioGeneral;
    private CalificacionResponse calificacionUsuario;
    private List<ComentarioResponse> comentarios;
    private boolean puedeComentar; 

    public DetalleResponse(Double promedioGeneral, CalificacionResponse calificacionUsuario,
            List<ComentarioResponse> comentarios, boolean puedeComentar) {
        this.promedioGeneral = promedioGeneral;
        this.calificacionUsuario = calificacionUsuario;
        this.comentarios = comentarios;
        this.puedeComentar = puedeComentar;
    }

    public Double getPromedioGeneral() {
        return promedioGeneral;
    }

    public CalificacionResponse getCalificacionUsuario() {
        return calificacionUsuario;
    }

    public List<ComentarioResponse> getComentarios() {
        return comentarios;
    }

    public boolean isPuedeComentar() {
        return puedeComentar;
    } 
}
