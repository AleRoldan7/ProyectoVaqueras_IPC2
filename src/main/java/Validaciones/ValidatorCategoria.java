/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validaciones;

import Dtos.Categoria.NewCategoriaRequest;
import Excepciones.DatosInvalidos;

/**
 *
 * @author alejandro
 */
public class ValidatorCategoria {
    
     public static void validarRegistroCategoria(NewCategoriaRequest newCategoriaRequest)
            throws DatosInvalidos {

        if (newCategoriaRequest == null) {
            throw new DatosInvalidos("Datos vacíos");
        }

        validarNombreCategoria(newCategoriaRequest.getNombreCategoria());
        validarDescripcion(newCategoriaRequest.getDescripcionCategoria());
       

    }

    private static void validarNombreCategoria(String tituloVideojuego) throws DatosInvalidos {
        if (tituloVideojuego == null) {
            throw new DatosInvalidos("Categoria sin nombre");
        }
    }

    private static void validarDescripcion(String descripcion) throws DatosInvalidos {
        if (descripcion == null) {
            throw new DatosInvalidos("Descripcion vacia o invalida");
        }
    }


}
