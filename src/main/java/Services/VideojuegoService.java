/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.EmpresaDBA;
import ConexionDBA.VideojuegoDBA;
import Dtos.Videojuego.NewImagenRequest;
import Dtos.Videojuego.NewVideojuegoRequest;
import Dtos.Videojuego.VideojuegoResponse;
import Excepciones.DatosInvalidos;
import Excepciones.EntityExists;
import ModeloEntidad.Imagen;
import ModeloEntidad.Videojuego;
import Validaciones.ValidatorVideojuego;
import java.io.ByteArrayOutputStream;
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
                    String.format("El usuario con nickName %s ya existe", entidadVideojuego.getTituloVideojuego())
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

        try {
            byte[] imagen = null;
            if (newImagenRequest.getImagen() != null) {

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int datosRead;
                byte[] data = new byte[16384];

                while ((datosRead = newImagenRequest.getImagen().read(data, 0, data.length)) != -1) {
                    byteArrayOutputStream.write(data, 0, datosRead);
                }
                byteArrayOutputStream.flush();
                imagen = byteArrayOutputStream.toByteArray();
            }

            Imagen entidadImagen = new Imagen(
                    imagen,
                    newImagenRequest.getIdVideojuego()
            );

            videojuegoDBA.agregarImagenVideojuego(entidadImagen);

            return entidadImagen;

        } catch (Exception e) {
            throw new DatosInvalidos("Error en los datos enviados" + e.getMessage());
        }

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

}
