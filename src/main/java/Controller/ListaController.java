/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dtos.Categoria.CategoriaResponse;
import Dtos.Usuario.UsuarioResponse;
import Services.ListaService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Base64;
import java.util.List;

/**
 *
 * @author alejandro
 */
@Path("/lista")
public class ListaController {

    private ListaService listaService = new ListaService();

    @GET
    @Path("/admin-empresa")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerAdminEmpresa() {
        System.out.println("Entro a los admin de empresa");
        List<UsuarioResponse> usuarioAdmin = listaService.obtenerAdminEmpresa()
                .stream()
                .map(UsuarioResponse::new)
                .toList();

        return Response.ok(usuarioAdmin).build();

    }

    @GET
    @Path("/imagenes/{idVideojuego}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerImagenes(@PathParam("idVideojuego") int idVideojuego) {

        List<byte[]> imagenes = listaService.obtenerImagenes(idVideojuego);

        List<String> base64 = imagenes.stream()
                .map(img -> Base64.getEncoder().encodeToString(img))
                .toList();

        return Response.ok(base64).build();
    }

    @GET
    @Path("/categorias")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCategorias() {

        List<CategoriaResponse> categorias = listaService.obtenerCategorias()
                .stream()
                .map(CategoriaResponse::new)
                .toList();
        

      
        return Response.ok(categorias).build();
    }
}
