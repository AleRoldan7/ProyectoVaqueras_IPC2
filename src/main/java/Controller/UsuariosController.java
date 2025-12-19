/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import ConexionDBA.UsuarioDBA;
import Dtos.Usuario.DineroUsuarioRequest;
import Dtos.Usuario.NewLoginRequest;
import Dtos.Usuario.NewUsuarioRequest;
import Dtos.Usuario.UpdateUsuarioRequest;
import Dtos.Usuario.UsuarioResponse;
import ModeloEntidad.Usuario;
import Excepciones.DatosInvalidos;
import Excepciones.EntityExists;
import Services.UsuarioService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alejandro
 */
@Path("/usuario")
public class UsuariosController {

    @Context
    UriInfo uriInfo;

    private UsuarioService usuarioService = new UsuarioService();
    private UsuarioDBA usuarioDBA = new UsuarioDBA();

    @POST
    @Path("/registroUsuarioComun")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registroUsuarioComun(NewUsuarioRequest newUsuarioRequest) {

        try {
            usuarioService.crearUsuarioComun(newUsuarioRequest);

            return Response.status(Response.Status.CREATED).build();
        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (EntityExists e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUsuario(NewLoginRequest newLoginRequest) {

        try {

            Usuario usuario = usuarioService.login(
                    newLoginRequest.getCorreo(),
                    newLoginRequest.getPassword()
            );

            if (usuario != null) {
                UsuarioResponse response = new UsuarioResponse(usuario);

                if ("ADMIN_EMPRESA".equals(usuario.getTipoUsuario().name())) {
                    Integer idEmpresa = usuarioDBA.obtenerEmpresaUsuario(usuario.getIdUsuario());
                    System.out.println("id empresa: " + idEmpresa);
                    response.setIdEmpresa(idEmpresa);
                }
                return Response.ok(response).build();

            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Datos incorrectos\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerJugador(@PathParam("id") int id) {
        try {
            Usuario u = usuarioService.obtenerJugador(id);
            if (u == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(u).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/listar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarJugadores() {
        try {
            List<Usuario> jugadores = usuarioService.listarJugadores();
            return Response.ok(jugadores).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/actualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarJugador(@PathParam("id") int id, UpdateUsuarioRequest request) {
        try {
            usuarioService.actualizarJugador(id, request);
            return Response.ok(Map.of("mensaje", "Jugador actualizado")).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/eliminar/{id}")
    public Response eliminarJugador(@PathParam("id") int id) {
        try {
            usuarioService.eliminarJugador(id);
            return Response.ok(Map.of("mensaje", "Jugador eliminado")).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/agregar-fondos")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agregarFondos(DineroUsuarioRequest request) {
        try {
            usuarioService.agregarFondos(request.getIdUsuario(), request.getMonto());
            return Response.ok(Map.of("mensaje", "Fondos agregados")).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

}
