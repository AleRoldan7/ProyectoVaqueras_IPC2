/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dtos.Videojuego.NewImagenRequest;
import Dtos.Videojuego.NewVideojuegoRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntityExists;
import ModeloEntidad.Videojuego;
import Services.VideojuegoService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author alejandro
 */
@Path("/videojuego")
public class VideojuegoController {

    private VideojuegoService videojuegoService = new VideojuegoService();

    @POST
    @Path("/crear-videojuego")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearVideojuego(NewVideojuegoRequest newVideojuegoRequest) {

        try {

            Videojuego videojuego = videojuegoService.crearVideojuego(newVideojuegoRequest);

            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Videojuego creado exitosamente");

            return Response.status(Response.Status.CREATED)
                    .entity(Map.of(
                            "idVideojuego", videojuego.getIdVideojuego(),
                            "mensaje", "Videojuego creado exitosamente"
                    ))
                    .build();

        } catch (DatosInvalidos e) {

            Map<String, String> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", "Datos inválidos: " + e.getMessage());

            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (EntityExists e) {

            Map<String, String> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", "El videojuego ya existe: " + e.getMessage());

            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @POST
    @Path("/agregar-imagen")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarImagenVideojuego(
            @FormDataParam("imagenes") List<InputStream> imagenes,
            @FormDataParam("imagenes") List<FormDataContentDisposition> fileDetail,
            @FormDataParam("idVideojuego") Integer idVideojuego) throws IOException {

        try {

            if (idVideojuego == null || idVideojuego <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de videojuego inválido")
                        .build();
            }
            if (imagenes == null || imagenes.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("No se enviaron imágenes")
                        .build();
            }

            for (InputStream img : imagenes) {
                NewImagenRequest req = new NewImagenRequest();
                req.setImagen(img);
                req.setIdVideojuego(idVideojuego);

                videojuegoService.agregarImagenVideojuego(req);
            }

            return Response.status(Response.Status.CREATED)
                    .entity(Map.of(
                            "status", "success",
                            "mensaje", "Imagen agregada correctamente"
                    ))
                    .build();

        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }

    @GET
    @Path("/empresa/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerVideojuegosEmpresa(
            @PathParam("idEmpresa") int idEmpresa) {

        return Response.ok(
                videojuegoService.obtenerVideojuegosEmpresa(idEmpresa)
        ).build();
    }

    @GET
    @Path("/imagen/{idImagen}")
    @Produces({"image/jpeg", "image/png"})
    public Response getImagenVideojuego(@PathParam("idImagen") int idImagen) {

        byte[] imagen = videojuegoService.getImagen(idImagen);

        if (imagen == null) {
            return Response.status(404).build();
        }

        return Response.ok(imagen)
                .header("Cache-Control", "public, max-age=86400")
                .build();
    }

}
