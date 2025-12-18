/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validaciones;

import Dtos.Videojuego.NewVideojuegoRequest;
import EnumOpciones.ClasificacionEdad;
import Excepciones.DatosInvalidos;

/**
 *
 * @author alejandro
 */
public class ValidatorVideojuego {

    public static void validarRegistroUsuarioComun(NewVideojuegoRequest newVideojuegoRequest)
            throws DatosInvalidos {

        if (newVideojuegoRequest == null) {
            throw new DatosInvalidos("Datos vacíos");
        }

        validarTitulo(newVideojuegoRequest.getTituloVideojuego());
        validarDescripcion(newVideojuegoRequest.getDescripcion());
        validarPrecio(newVideojuegoRequest.getPrecio());
        validarRecursos(newVideojuegoRequest.getRecursosMinimos());
        validarClasificacion(newVideojuegoRequest.getClasificacionEdad());

    }

    private static void validarTitulo(String tituloVideojuego) throws DatosInvalidos {
        if (tituloVideojuego == null) {
            throw new DatosInvalidos("Titulo de videojuego inválido");
        }
    }

    private static void validarDescripcion(String descripcion) throws DatosInvalidos {
        if (descripcion == null) {
            throw new DatosInvalidos("Descripcion vacia o invalida");
        }
    }

    private static void validarPrecio(double precio) throws DatosInvalidos {
        if (precio <= 0) {
            throw new DatosInvalidos("El precio del videojuego tiene que ser mayor a cero");
        }
    }

    private static void validarRecursos(String recurso) throws DatosInvalidos {
        if (recurso == null) {
            throw new DatosInvalidos("La descripcion de recursos esta vacia");
        }
    }

    private static void validarClasificacion(ClasificacionEdad clasificacionEdad) throws DatosInvalidos {
        if (clasificacionEdad == null) {
            throw new DatosInvalidos("Debe de agregar una clasificacion al juego");
        }
    }

}
