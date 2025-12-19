/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Excepciones.EntidadNotFound;
import Services.SistemaService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
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

    @POST
    @Path("/agregar/{idEmpresa}/{comision}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarComision(@PathParam("idEmpresa") int idEmpresa,
            @PathParam("comision") double comision) {
        sistemaService.agregarComision(idEmpresa, comision);
        return Response.ok("Comisión agregada").build();
    }

    @GET
    @Path("/global")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerGlobal() {
        try {
            double porcentaje = sistemaService.obtenerComisionGlobal();
            return Response.ok(Map.of("porcentaje", porcentaje)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/comision-global")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarGlobal(Map<String, Double> body) {
        try {
            double nuevoPorcentaje = body.get("porcentaje");
            sistemaService.actualizarComisionGlobal(nuevoPorcentaje);
            return Response.ok(Map.of("mensaje", "Comisión global actualizada")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage())).build();
        }
    }

    @GET
    @Path("/empresa-comision/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEmpresa(@PathParam("idEmpresa") int idEmpresa) {
        try {
            double porcentaje = sistemaService.obtenerComisionEmpresa(idEmpresa);
            return Response.ok(Map.of("porcentaje", porcentaje)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/empresa-actualizar-comision/{idEmpresa}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarEmpresa(@PathParam("idEmpresa") int idEmpresa, Map<String, Double> body) {
        try {
            double porcentaje = body.get("porcentaje");
            sistemaService.actualizarComisionEmpresa(idEmpresa, porcentaje);
            return Response.ok(Map.of("mensaje", "Comisión específica actualizada")).build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage())).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

  

}
