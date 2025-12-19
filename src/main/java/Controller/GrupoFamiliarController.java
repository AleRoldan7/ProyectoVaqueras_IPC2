/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import ConexionDBA.GrupoFamiliarDBA;
import Dtos.GrupoFamiliar.IntitacionRequest;
import Dtos.GrupoFamiliar.InvitacionGrupo;
import Dtos.GrupoFamiliar.NewGrupoRequest;
import Dtos.GrupoFamiliar.RespuestaRequest;
import Dtos.GrupoFamiliar.UpdateGrupoRequest;
import ModeloEntidad.GrupoFamiliar;
import Services.GrupoFamiliarService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author alejandro
 */
@Path("/grupo-familiar")
public class GrupoFamiliarController {

    private GrupoFamiliarService grupoService = new GrupoFamiliarService();
    private GrupoFamiliarDBA grupoDBA = new GrupoFamiliarDBA();

    @POST
    @Path("/crear")
    public Response crearGrupo(NewGrupoRequest newGrupoRequest) {
        try {
            GrupoFamiliar grupo = grupoService.crearGrupo(newGrupoRequest);
            return Response.status(Response.Status.CREATED).entity(grupo).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{idGrupo}")
    public Response obtenerGrupo(@PathParam("idGrupo") int idGrupo) {
        try {
            GrupoFamiliar grupo = grupoService.obtenerGrupo(idGrupo);
            if (grupo == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(grupo).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/listar")
    public Response listarGrupos() {
        try {
            List<GrupoFamiliar> grupos = grupoService.listarGrupos();
            return Response.ok(grupos).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/actualizar/{idGrupo}")
    public Response actualizarGrupo(@PathParam("idGrupo") int idGrupo, UpdateGrupoRequest updateGrupoRequest) {
        try {
            grupoService.actualizarGrupo(idGrupo, updateGrupoRequest.getNombreNuevo());
            return Response.ok("Grupo actualizado").build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/eliminar/{idGrupo}")
    public Response eliminarGrupo(@PathParam("idGrupo") int idGrupo) {
        try {
            grupoService.eliminarGrupo(idGrupo);
            return Response.ok("Grupo eliminado").build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

   
    @POST
    @Path("/agregar-usuario")
    public Response agregarUsuario(IntitacionRequest intitacionRequest) {
        try {
            grupoService.agregarUsuarioAlGrupo(intitacionRequest.getIdGrupo(), intitacionRequest.getIdUsuario());
            return Response.ok("Usuario agregado al grupo").build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/eliminar-usuario")
    public Response eliminarUsuario(IntitacionRequest intitacionRequest) {
        try {
            grupoService.eliminarUsuarioDelGrupo(intitacionRequest.getIdGrupo(), intitacionRequest.getIdUsuario());
            return Response.ok("Usuario eliminado del grupo").build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/invitar-usuario")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response invitarUsuario(IntitacionRequest intitacionRequest) {
        try {
            grupoDBA.enviarInvitacion(intitacionRequest.getIdGrupo(), intitacionRequest.getIdUsuario());
            return Response.ok("Invitación enviada").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/responder-invitacion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response responderInvitacion(RespuestaRequest request) {
        try {
            grupoDBA.responderInvitacion(request.getIdGrupo(), request.getIdUsuario(), request.getRespuesta());
            return Response.ok("Respuesta registrada").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/invitaciones-pendientes/{idUsuario}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerInvitacionesPendientes(@PathParam("idUsuario") int idUsuario) {
        try {
            List<InvitacionGrupo> invitaciones = grupoDBA.listarInvitacionesPendientes(idUsuario);
            return Response.ok(invitaciones).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }
}
