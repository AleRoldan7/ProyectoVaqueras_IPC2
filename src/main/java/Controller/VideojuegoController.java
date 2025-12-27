/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dtos.Videojuego.NewImagenRequest;
import Dtos.Videojuego.NewVideojuegoRequest;
import Dtos.Videojuego.UpdateVideojuegoRequest;
import Dtos.Videojuego.VideojuegoResponse;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Excepciones.EntityExists;
import ModeloEntidad.Videojuego;
import Services.VideojuegoService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
            VideojuegoResponse videojuegoResponse = new VideojuegoResponse(videojuego);

            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Videojuego creado exitosamente");

            return Response.ok(videojuegoResponse).build();

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
    public Response getImagenVideojuego(@PathParam("idImagen") int idImagen) {
        try {
            byte[] imagen = videojuegoService.getImagen(idImagen);
            if (imagen == null || imagen.length == 0) {
                System.out.println("Imagen no encontrada o vacía para ID: " + idImagen);
                return Response.status(404)
                        .entity("Imagen no encontrada para id: " + idImagen)
                        .build();
            }

            System.out.println("Imagen encontrada para ID " + idImagen + ", tamaño: " + imagen.length + " bytes");

            String contentType = detectarContentType(imagen);
            System.out.println("Content-Type detectado: " + contentType);

            return Response.ok(imagen)
                    .type(contentType)
                    .header("Cache-Control", "public, max-age=31536000")
                    .header("Access-Control-Allow-Origin", "*")
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500)
                    .entity("Error al cargar imagen")
                    .build();
        }
    }

    private String detectarContentType(byte[] bytes) {
        if (bytes == null || bytes.length < 12) {
            return "image/jpeg";
        }

        if ((bytes[0] & 0xFF) == 0xFF && (bytes[1] & 0xFF) == 0xD8 && (bytes[2] & 0xFF) == 0xFF) {
            return "image/jpeg";
        }

        if (bytes[0] == (byte) 0x89 && bytes[1] == 0x50 && bytes[2] == 0x4E && bytes[3] == 0x47) {
            return "image/png";
        }

        if (bytes[0] == 'G' && bytes[1] == 'I' && bytes[2] == 'F' && bytes[3] == '8') {
            return "image/gif";
        }

        if (bytes.length >= 12
                && bytes[0] == 'R' && bytes[1] == 'I' && bytes[2] == 'F' && bytes[3] == 'F'
                && bytes[8] == 'W' && bytes[9] == 'E' && bytes[10] == 'B' && bytes[11] == 'P') {
            return "image/webp";
        }

        return "application/octet-stream";
    }

    @GET
    @Path("/disponibles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarDisponibles() {
        return Response.ok(videojuegoService.listarVideojuegosDisponibles()).build();
    }

    @PUT
    @Path("/actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarVideojuego(UpdateVideojuegoRequest updateVideojuegoRequest) {

        try {
            videojuegoService.actualizarVideojuego(updateVideojuegoRequest);

            return Response.ok(
                    Map.of("mensaje", "Videojuego actualizado correctamente")
            ).build();

        } catch (DatosInvalidos e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (EntidadNotFound e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error interno"))
                    .build();
        }
    }

    @PUT
    @Path("/suspender-venta/{idVideojuego}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response suspenderVenta(@PathParam("idVideojuego") int idVideojuego) {

        try {
            videojuegoService.suspenderVenta(idVideojuego);

            return Response.ok(
                    Map.of("mensaje", "Videojuego suspendido de la venta")
            ).build();

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

    @PUT
    @Path("/reactivar/{idVideojuego}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response reactivarVenta(@PathParam("idVideojuego") int idVideojuego) {

        try {
            videojuegoService.reactivarVenta(idVideojuego);
            return Response.ok(
                    java.util.Map.of("mensaje", "Venta reactivada")
            ).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/catalogo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarCatalogo(@QueryParam("titulo") String titulo,
            @QueryParam("precioMin") Double precioMin,
            @QueryParam("precioMax") Double precioMax,
            @QueryParam("disponibles") Boolean disponibles) {

        List<Videojuego> lista = videojuegoService.listarCatalogo(titulo, precioMin, precioMax, disponibles);
        return Response.ok(lista).build();
    }

    @GET
    @Path("/buscar-videojuego")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarVideojuegos(
            @QueryParam("titulo") String titulo,
            @QueryParam("categoria") String categoria,
            @QueryParam("precioMin") Double precioMin,
            @QueryParam("precioMax") Double precioMax,
            @QueryParam("nombreEmpresa") String empresa
    ) {
        try {
            List<VideojuegoResponse> resultados = videojuegoService.buscarVideojuegos(titulo, categoria, precioMin, precioMax, empresa);
            return Response.ok(resultados).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("Error al buscar videojuegos").build();
        }
    }

}
