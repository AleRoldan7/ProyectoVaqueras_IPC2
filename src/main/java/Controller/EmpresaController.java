/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import ConexionDBA.EmpresaDBA;
import Dtos.Empresa.CrearEmpresaRequest;
import Dtos.Empresa.CrearEmpresaResponse;
import Excepciones.DatosInvalidos;
import Excepciones.EntityExists;
import ModeloEntidad.Empresa;
import Services.EmpresaService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author alejandro
 */
@Path("/empresa")
public class EmpresaController {

    private EmpresaService empresaService = new EmpresaService();

    @POST
    @Path("/crear-empresa")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearEmpresa(CrearEmpresaRequest crearEmpresaRequest) {

        try {
            Empresa empresa = empresaService.crearEmpresa(crearEmpresaRequest);
            CrearEmpresaResponse response = new CrearEmpresaResponse(empresa);

            return Response.status(Response.Status.CREATED)
                    .entity(response)
                    .build();

        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();

        } catch (EntityExists e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/nombre/{idEmpresa}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerNombreEmpresa(@PathParam("idEmpresa") int idEmpresa) {

        String nombre = empresaService.devolverNombreEmpresa(idEmpresa);

        if (nombre == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok("{\"nombreEmpresa\":\"" + nombre + "\"}").build();
    }

    @PUT
    @Path("/actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarEmpresa(Empresa empresa) {
        empresaService.actualizarEmpresa(empresa);
        return Response.ok("Empresa actualizada").build();
    }

    @GET
    @Path("/listar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarEmpresas() {
        List<Empresa> lista = empresaService.listarEmpresas();
        return Response.ok(lista).build();
    }

    @POST
    @Path("/deshabilitar/{idComentario}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deshabilitarComentario(@PathParam("idComentario") int idComentario) {
        empresaService.deshabilitarComentario(idComentario);
        return Response.ok("Comentario deshabilitado").build();
    }
}
