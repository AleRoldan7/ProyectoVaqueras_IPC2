/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import ConexionDBA.BibliotecaDBA;
import Services.BibliotecaService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author alejandro
 */
@Path("/biblioteca")
public class BibliotecaController {

    private BibliotecaService bibliotecaService = new BibliotecaService();
    private BibliotecaDBA bibliotecaDBA = new BibliotecaDBA();
    
    @GET
    @Path("/usuario/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerBiblioteca(@PathParam("idUsuario") int idUsuario) {
        try {
            return Response.ok(bibliotecaService.obtenerBibliotecaUsuario(idUsuario)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensaje", "Error al obtener biblioteca"))
                    .build();
        }
    }

    @GET
    @Path("/usuario/buscar/{nickname}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerBibliotecaPorNickname(@PathParam("nickname") String nickname) {
        try {
            return Response.ok(bibliotecaService.obtenerBibliotecaPorNickname(nickname)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensaje", "Error al obtener biblioteca"))
                    .build();
        }
    }

    @PUT
    @Path("/cambiar-estado/{idUsuario}/{idCompra}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cambiarEstadoInstalacion(@PathParam("idUsuario") int idUsuario,
            @PathParam("idCompra") int idCompra,
            Map<String, String> request) {
        try {
            System.out.println("Request recibido: " + request);
            String nuevoEstado = request.get("estado");
            System.out.println("Nuevo estado: " + nuevoEstado);

            if (nuevoEstado == null || (!nuevoEstado.equals("INSTALADO") && !nuevoEstado.equals("NO_INSTALADO"))) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("error", "Estado inválido")).build();
            }

            bibliotecaService.cambiarEstadoInstalacion(idUsuario, idCompra, nuevoEstado);
            return Response.ok(Map.of("mensaje", "Estado actualizado")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/biblioteca/verificar-propiedad")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verificarPropiedad(
            @QueryParam("idUsuario") int idUsuario,
            @QueryParam("idVideojuego") int idVideojuego
    ) throws SQLException {
        boolean posee = bibliotecaDBA.usuarioPoseeVideojuego(idUsuario, idVideojuego);
        return Response.ok(posee).build();
    }

}
