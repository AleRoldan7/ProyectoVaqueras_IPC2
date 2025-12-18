/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import ConexionDBA.UsuarioDBA;
import Dtos.Usuario.NewLoginRequest;
import Dtos.Usuario.NewUsuarioRequest;
import Dtos.Usuario.UsuarioResponse;
import ModeloEntidad.Usuario;
import Excepciones.DatosInvalidos;
import Excepciones.EntityExists;
import Services.UsuarioService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

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

}
