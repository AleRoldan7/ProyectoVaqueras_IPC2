/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import ConexionDBA.VideojuegoDBA;
import Dtos.Videojuego.ImganenDTO;
import Dtos.Videojuego.NewImagenRequest;
import Dtos.Videojuego.NewVideojuegoRequest;
import Dtos.Videojuego.UpdateVideojuegoRequest;
import Dtos.Videojuego.VideojuegoResponse;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Excepciones.EntityExists;
import ModeloEntidad.Imagen;
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
import jakarta.ws.rs.core.StreamingOutput;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Base64;
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
    private VideojuegoDBA videojuegoDBA = new VideojuegoDBA();

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
            @FormDataParam("idVideojuego") Integer idVideojuego) {

        try {
            if (idVideojuego == null || idVideojuego <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de videojuego inválido").build();
            }

            if (imagenes == null || imagenes.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("No se recibieron imágenes").build();
            }

            for (InputStream imgStream : imagenes) {
                byte[] bytes = imgStream.readAllBytes();

                if (bytes.length == 0) {
                    System.out.println("Imagen vacía, no se guardó.");
                    continue;
                }

                NewImagenRequest req = new NewImagenRequest();
                req.setImagen(bytes);
                req.setIdVideojuego(idVideojuego);

                videojuegoService.agregarImagenVideojuego(req);
            }

            return Response.status(Response.Status.CREATED)
                    .entity(Map.of("status", "success", "mensaje", "Imágenes agregadas correctamente"))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
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

    private String detectarTipoImagen(byte[] bytes) {
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            String tipo = URLConnection.guessContentTypeFromStream(is);
            return tipo != null ? tipo : "application/octet-stream";
        } catch (IOException e) {
            return "application/octet-stream";
        }
    }

    /*
    @GET
    @Path("/imagen-base64/{idImagen}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImagenBase64(@PathParam("idImagen") int idImagen) {
        try {
            byte[] imagenBytes = videojuegoService.getImagen(idImagen);

            if (imagenBytes == null || imagenBytes.length == 0) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("error", "Imagen no encontrada o vacía"))
                        .build();
            }

            // Detectar correctamente el MIME type
            String mimeType = detectarContentType(imagenBytes);

            // Codificar en Base64
            String base64 = Base64.getEncoder().encodeToString(imagenBytes);

            // Data URL completo (lo más práctico para usar directamente en <img src="...">)
            String dataUrl = "data:" + mimeType + ";base64," + base64;

            // Respuesta JSON limpia y útil
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("idImagen", idImagen);
            respuesta.put("dataUrl", dataUrl);        // Usa este directamente en HTML
            respuesta.put("mimeType", mimeType);
            respuesta.put("base64", base64);          // Solo la cadena, si prefieres construir tú el data URL
            respuesta.put("size", imagenBytes.length);

            return Response.ok(respuesta).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al procesar la imagen: " + e.getMessage()))
                    .build();
        }
    }
     */
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
    @Path("/{idVideojuego}/imagenes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerIdsImagenesVideojuego(@PathParam("idVideojuego") int idVideojuego) {
        try {
            List<Integer> idsImagenes = videojuegoDBA.obtenerIdsImagenes(idVideojuego);
            return Response.ok(Map.of(
                    "status", "success",
                    "idVideojuego", idVideojuego,
                    "idsImagenes", idsImagenes,
                    "total", idsImagenes.size()
            )).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("status", "error", "message", "Error al obtener IDs de imágenes"))
                    .build();
        }
    }

    private String detectarContentType(byte[] bytes) {
        if (bytes.length < 12) {
            return "application/octet-stream";
        }

        // WebP: RIFF....WEBP
        if (bytes[0] == 'R' && bytes[1] == 'I' && bytes[2] == 'F' && bytes[3] == 'F'
                && bytes[8] == 'W' && bytes[9] == 'E' && bytes[10] == 'B' && bytes[11] == 'P') {
            return "image/webp";
        }
        // PNG: 89 50 4E 47 0D 0A 1A 0A
        if (bytes[0] == (byte) 0x89 && bytes[1] == 'P' && bytes[2] == 'N' && bytes[3] == 'G') {
            return "image/png";
        }
        // JPEG: FF D8 FF
        if (bytes[0] == (byte) 0xFF && bytes[1] == (byte) 0xD8 && bytes[2] == (byte) 0xFF) {
            return "image/jpeg";
        }
        // GIF: GIF87a o GIF89a
        if (bytes[0] == 'G' && bytes[1] == 'I' && bytes[2] == 'F' && bytes[3] == '8') {
            return "image/gif";
        }

        // Fallback a URLConnection
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            String type = URLConnection.guessContentTypeFromStream(is);
            return type != null ? type : "application/octet-stream";
        } catch (IOException e) {
            return "application/octet-stream";
        }
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
