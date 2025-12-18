/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Resources.ReporteUsuario.HistorialGastosDTO;
import Services.ReporteUsuarioService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

/**
 *
 * @author alejandro
 */
@Path("/usuario/reportes")
public class ReporteUsuarioController {
    
    private ReporteUsuarioService usuarioService = new ReporteUsuarioService();
    
    @GET
    @Path("/historial-compras/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response historialCompras(@PathParam("idUsuario") int idUsuario) {
        
        ArrayList<HistorialGastosDTO> gasto = usuarioService.historialGastos(idUsuario);
        return Response.ok(gasto).build();
    }
}
