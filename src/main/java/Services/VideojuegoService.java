/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.EmpresaDBA;
import ConexionDBA.VideojuegoDBA;
import Dtos.Videojuego.NewImagenRequest;
import Dtos.Videojuego.NewVideojuegoRequest;
import Dtos.Videojuego.UpdateVideojuegoRequest;
import Dtos.Videojuego.VideojuegoDisponibleRequest;
import Dtos.Videojuego.VideojuegoImagenes;
import Dtos.Videojuego.VideojuegoResponse;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Excepciones.EntityExists;
import ModeloEntidad.Imagen;
import ModeloEntidad.Videojuego;
import Validaciones.ValidatorVideojuego;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alejandro
 */
public class VideojuegoService {

    private VideojuegoDBA videojuegoDBA = new VideojuegoDBA();

    public Videojuego crearVideojuego(NewVideojuegoRequest newVideojuegoRequest) throws DatosInvalidos, EntityExists {

        ValidatorVideojuego.validarRegistroUsuarioComun(newVideojuegoRequest);

        Videojuego entidadVideojuego = extraer(newVideojuegoRequest);

        if (videojuegoDBA.existeVideojuego(entidadVideojuego.getTituloVideojuego())) {

            throw new EntityExists(
                    String.format("El videojuego con el titulo %s ya existe", entidadVideojuego.getTituloVideojuego())
            );
        }

        videojuegoDBA.agregarVideojuego(entidadVideojuego);

        return entidadVideojuego;

    }

    private Videojuego extraer(NewVideojuegoRequest newVideojuegoRequest) throws DatosInvalidos {

        try {
            Videojuego entidadVideojuego = new Videojuego(
                    newVideojuegoRequest.getTituloVideojuego(),
                    newVideojuegoRequest.getDescripcion(),
                    newVideojuegoRequest.getPrecio(),
                    newVideojuegoRequest.getRecursosMinimos(),
                    newVideojuegoRequest.getClasificacionEdad(),
                    newVideojuegoRequest.getIdEmpresa(),
                    newVideojuegoRequest.isEstadoVenta()
            );

            return entidadVideojuego;
        } catch (Exception e) {
            throw new DatosInvalidos("Error en los datos enviados" + e.getMessage());
        }

    }

    public Imagen agregarImagenVideojuego(NewImagenRequest newImagenRequest) throws DatosInvalidos {

        if (newImagenRequest.getImagen() == null || newImagenRequest.getImagen().length == 0) {
            throw new DatosInvalidos("La imagen no contiene datos");
        }

        System.out.println(
                "SERVICE: Guardando imagen de " + newImagenRequest.getImagen().length
                + " bytes para videojuego ID: " + newImagenRequest.getIdVideojuego()
        );

        Imagen entidadImagen = new Imagen(
                newImagenRequest.getImagen(), 
                newImagenRequest.getIdVideojuego()
        );

        videojuegoDBA.agregarImagenVideojuego(entidadImagen);

        return entidadImagen;
    }

    public Map<String, Object> obtenerVideojuegosEmpresa(int idEmpresa) {

        List<VideojuegoResponse> juegos = videojuegoDBA.obtenerVideojuegosEmpresa(idEmpresa);
        Map<String, Object> response = new HashMap<>();

        for (VideojuegoResponse v : juegos) {
            List<Integer> imagenes
                    = videojuegoDBA.obtenerIdsImagenes(v.getIdVideojuego());
            response.put(String.valueOf(v.getIdVideojuego()), imagenes);
        }

        return Map.of(
                "videojuegos", juegos,
                "imagenes", response
        );
    }

    public byte[] getImagen(int idImagen) {
        return videojuegoDBA.obtenerImagenVideojuego(idImagen);
    }

    public List<VideojuegoImagenes> listarVideojuegosDisponibles() {
        return videojuegoDBA.listarDisponibles();
    }

    public void actualizarVideojuego(UpdateVideojuegoRequest req)
            throws DatosInvalidos, EntidadNotFound, SQLException {

        if (req.getIdVideojuego() <= 0) {
            throw new DatosInvalidos("ID inválido");
        }

        Videojuego existente = videojuegoDBA.obtenerVideojuego(req.getIdVideojuego());
        if (existente == null) {
            throw new EntidadNotFound("Videojuego no encontrado");
        }

        videojuegoDBA.actualizarVideojuego(req);
    }

    public void suspenderVenta(int idVideojuego) throws EntidadNotFound, SQLException {

        Videojuego v = videojuegoDBA.obtenerVideojuego(idVideojuego);
        if (v == null) {
            throw new EntidadNotFound("Videojuego no encontrado");
        }

        videojuegoDBA.cambiarEstadoVenta(idVideojuego, false);
    }

    public void reactivarVenta(int idVideojuego) throws Exception {
        videojuegoDBA.cambiarEstadoVenta(idVideojuego, true);
    }

    public List<Videojuego> listarCatalogo(String titulo, Double precioMin, Double precioMax, Boolean disponibles) {
        return videojuegoDBA.listarCatalogo(titulo, precioMin, precioMax, disponibles);
    }

    public List<VideojuegoResponse> buscarVideojuegos(String titulo, String categoria, Double precioMin, Double precioMax,
            String empresa) throws SQLException {

        return videojuegoDBA.buscarVideojuegos(titulo, categoria, precioMin, precioMax, empresa);
    }

    public List<Integer> obtenerIdsImagenes(int idVideojuego) throws SQLException {
        return videojuegoDBA.obtenerIdsImagenes(idVideojuego);
    }

}
