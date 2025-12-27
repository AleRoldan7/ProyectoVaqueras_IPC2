/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import ConexionDBA.GrupoFamiliarDBA;
import Dtos.GrupoFamiliar.BibliotecaJuegos.InstalarJuegoRequest;
import Dtos.GrupoFamiliar.BibliotecaJuegos.JuegoGrupo;
import Services.GrupoBibliotecaService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alejandro
 */
@Path("/biblioteca-grupo")
public class GrupoBibliotecaController {

    private GrupoBibliotecaService grupo = new GrupoBibliotecaService();
    private GrupoFamiliarDBA grupoFamiliarDBA = new GrupoFamiliarDBA();

    @GET
    @Path("/{idGrupo}/usuario/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerBibliotecaGrupo(
            @PathParam("idGrupo") int idGrupo,
            @PathParam("idUsuario") int idUsuario) {

        try {

            List<JuegoGrupo> juegos = grupo.obtenerJuegosDelGrupo(idGrupo, idUsuario);
            return Response.ok(juegos).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error interno: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/instalar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response instalar(@QueryParam("idUsuario") int idUsuario,
            @QueryParam("idVideojuego") int idVideojuego) {
        try {
            grupo.instalarJuegoPrestado(idUsuario, idVideojuego);
            return Response.ok("Juego instalado correctamente").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/desinstalar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response desinstalar(@QueryParam("idUsuario") int idUsuario,
            @QueryParam("idVideojuego") int idVideojuego) {
        try {
            grupo.desinstalarJuego(idUsuario, idVideojuego);
            return Response.ok("Juego desinstalado correctamente").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

}
