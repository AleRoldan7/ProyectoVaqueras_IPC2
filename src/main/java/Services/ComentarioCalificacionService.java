/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.ComentarioCalificacionDBA;
import Dtos.ComentarioCalificacion.CalificacionResponse;
import Dtos.ComentarioCalificacion.ComentarioResponse;
import Dtos.ComentarioCalificacion.NewCalificacionRequest;
import Dtos.ComentarioCalificacion.NewComentarioRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntityExists;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class ComentarioCalificacionService {

    private ComentarioCalificacionDBA comentarioCalificacionDBA = new ComentarioCalificacionDBA();

    public void calificar(int idUsuario, int idVideojuego, NewCalificacionRequest request) throws SQLException, DatosInvalidos, EntityExists {
        comentarioCalificacionDBA.calificarJuego(idUsuario, idVideojuego, request.getCalificacion());
    }

    public CalificacionResponse obtenerCalificacion(int idVideojuego, int idUsuario) throws SQLException, DatosInvalidos, EntityExists {
        return comentarioCalificacionDBA.obtenerCalificacionVideojuego(idVideojuego, idUsuario);
    }

    public void comentar(int idUsuario, int idVideojuego, NewComentarioRequest request) throws SQLException, DatosInvalidos, EntityExists {
        comentarioCalificacionDBA.publicarComentario(idUsuario, idVideojuego, request);
    }

    public List<ComentarioResponse> obtenerComentarios(int idVideojuego) throws SQLException, DatosInvalidos, EntityExists {
        return comentarioCalificacionDBA.obtenerComentarios(idVideojuego);
    }

    public Double obtenerPromedioCalificacion(int idVideojuego) throws SQLException, DatosInvalidos, EntityExists {
        return comentarioCalificacionDBA.obtenerPromedioCalificacion(idVideojuego);
    }

}
