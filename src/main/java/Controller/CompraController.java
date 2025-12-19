/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dtos.Compra.NewCompraRequest;
import Excepciones.DatosInvalidos;
import Services.CompraService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author alejandro
 */
@Path("/compra")
public class CompraController {

    private CompraService compraService = new CompraService();

    @POST
    @Path("/realizar-compra")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response comprar(NewCompraRequest newCompraRequest) {
        try {
            compraService.comprar(
                    newCompraRequest.getIdUsuario(),
                    newCompraRequest.getIdVideojuego(),
                    newCompraRequest.getFechaCompra()
            );

            return Response.ok(
                    java.util.Map.of("mensaje", "Compra realizada con éxito")
            ).build();

        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(java.util.Map.of("mensaje", e.getMessage()))
                    .build();

        } catch (Exception e) {
            e.printStackTrace(); 
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(java.util.Map.of("mensaje", e.getMessage()))
                    .build();
        }

    }
}
