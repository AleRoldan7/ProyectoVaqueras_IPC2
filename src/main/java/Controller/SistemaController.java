/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Services.SistemaService;
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
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author alejandro
 */
@Path("/sistema")
public class SistemaController {

    private SistemaService sistemaService = new SistemaService();

    @GET
    @Path("/categorias/pendientes")
    public Response obtenerCategoriasPendientes() {
        try {
            return Response.ok(sistemaService.obtenerCategoriasPendientes()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al obtener solicitudes pendientes"))
                    .build();
        }
    }

    @PUT
    @Path("/categorias/{id}/aprobar")
    public Response aprobarCategoria(@PathParam("id") int idCategoriaVideojuego) {
        try {
            sistemaService.aprobarCategoria(idCategoriaVideojuego);
            return Response.ok(Map.of("mensaje", "Categoría aprobada correctamente")).build();
        } catch (EntidadNotFound e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al aprobar categoría"))
                    .build();
        }
    }

    @PUT
    @Path("/categorias/{id}/rechazar")
    public Response rechazarCategoria(@PathParam("id") int idCategoriaVideojuego) {
        try {
            sistemaService.rechazarCategoria(idCategoriaVideojuego);
            return Response.ok(Map.of("mensaje", "Categoría rechazada y empresa notificada")).build();
        } catch (EntidadNotFound e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al rechazar categoría"))
                    .build();
        }
    }

    @GET
    @Path("/comision-global")
    public Response obtenerComisionGlobal() {
        try {
            double porcentaje = sistemaService.obtenerComisionGlobal();
            return Response.ok(Map.of("porcentaje", porcentaje)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al obtener comisión global"))
                    .build();
        }
    }

    @PUT
    @Path("/comision-global-actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarComisionGlobal(Map<String, Double> body) {
        try {
            Double porcentaje = body.get("porcentaje");
            if (porcentaje == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("error", "El campo 'porcentaje' es obligatorio"))
                        .build();
            }
            sistemaService.actualizarComisionGlobal(porcentaje);
            return Response.ok(Map.of(
                    "mensaje", "Comisión global actualizada al " + porcentaje + "%",
                    "porcentaje", porcentaje
            )).build();
        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al actualizar comisión global"))
                    .build();
        }
    }

    @GET
    @Path("/comision-empresa/{idEmpresa}")
    public Response obtenerComisionEmpresa(@PathParam("idEmpresa") int idEmpresa) {
        try {
            double porcentaje = sistemaService.obtenerComisionEmpresa(idEmpresa);
            return Response.ok(Map.of("porcentaje", porcentaje)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al obtener comisión de empresa"))
                    .build();
        }
    }

    @PUT
    @Path("/comision-empresa-actualizar/{idEmpresa}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarComisionEmpresa(
            @PathParam("idEmpresa") int idEmpresa,
            Map<String, Double> body) {
        try {
            Double porcentaje = body.get("porcentaje");
            if (porcentaje == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("error", "El campo 'porcentaje' es obligatorio"))
                        .build();
            }
            sistemaService.actualizarComisionEmpresa(idEmpresa, porcentaje);
            return Response.ok(Map.of(
                    "mensaje", "Comisión específica actualizada al " + porcentaje + "%",
                    "porcentaje", porcentaje
            )).build();
        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al actualizar comisión específica"))
                    .build();
        }
    }

    @GET
    @Path("/usuario/{idUsuario}")
    public Response obtenerNotificaciones(@PathParam("idUsuario") int idUsuario) {
        try {
            return Response.ok(sistemaService.obtenerNotificacionesUsuario(idUsuario)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al obtener notificaciones"))
                    .build();
        }
    }

    @PUT
    @Path("/{id}/leida")
    public Response marcarComoLeida(
            @PathParam("id") int idNotificacion,
            @QueryParam("idUsuario") int idUsuario) {
        try {
            sistemaService.marcarComoLeida(idNotificacion, idUsuario);
            return Response.ok(Map.of("mensaje", "Notificación marcada como leída")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }
}
