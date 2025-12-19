/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.CategoriaDBA;
import Dtos.Categoria.NewCategoriaRequest;
import Dtos.Categoria.UpdateCategoriaRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Excepciones.EntityExists;
import ModeloEntidad.Categoria;
import Validaciones.ValidatorCategoria;

/**
 *
 * @author alejandro
 */
public class CategoriaService {

    private CategoriaDBA categoriaDBA = new CategoriaDBA();

    public Categoria agregarCategoria(NewCategoriaRequest newCategoriaRequest) throws DatosInvalidos, EntityExists {

        ValidatorCategoria.validarRegistroCategoria(newCategoriaRequest);

        Categoria entidadCategoria = extraer(newCategoriaRequest);

        if (categoriaDBA.existeCategoria(entidadCategoria.getNombreCategoria())) {

            throw new EntityExists(
                    String.format("El nombre de la categoria %s ya existe", entidadCategoria.getNombreCategoria())
            );
        }

        categoriaDBA.agregarCategoria(entidadCategoria);

        return entidadCategoria;

    }

    private Categoria extraer(NewCategoriaRequest newCategoriaRequest) throws DatosInvalidos {

        try {
            Categoria entidadCategoria = new Categoria(
                    newCategoriaRequest.getNombreCategoria(),
                    newCategoriaRequest.getDescripcionCategoria()
            );

            return entidadCategoria;

        } catch (Exception e) {
            throw new DatosInvalidos("Error en los datos enviados" + e.getMessage());
        }

    }

    public void actualizarCategoria(UpdateCategoriaRequest updateCategoriaRequest) throws DatosInvalidos, EntidadNotFound, EntityExists {

        if (updateCategoriaRequest.getIdCategoria() == null || updateCategoriaRequest.getIdCategoria() <= 0) {
            System.out.println("id" + updateCategoriaRequest.getIdCategoria());
            throw new DatosInvalidos("El ID de la categoria no es válido.");
        }

        if (!categoriaDBA.existeIdCategoria(updateCategoriaRequest.getIdCategoria())) {
            throw new EntidadNotFound(
                    String.format("No se encontró ninguna categoria con ID = %d", updateCategoriaRequest.getIdCategoria())
            );
        }

        categoriaDBA.actualizarCategoria(updateCategoriaRequest);

    }

    public void solicitarCategoria(int idVideojuego, int idCategoria) throws DatosInvalidos {

        if (idVideojuego <= 0 || idCategoria <= 0) {
            throw new DatosInvalidos("Datos inválidos");
        }

        if (!categoriaDBA.existeIdCategoria(idCategoria)) {
            throw new DatosInvalidos("La categoría no está disponible");
        }

        categoriaDBA.solicitarCategoriaVideojuego(idVideojuego, idCategoria);
    }

    public void eliminarCategoria(int idCategoria)
            throws DatosInvalidos, EntidadNotFound {

        if (idCategoria <= 0) {
            throw new DatosInvalidos("ID inválido");
        }

        if (!categoriaDBA.existeIdCategoria(idCategoria)) {
            throw new EntidadNotFound("Categoría no existe");
        }
        categoriaDBA.eliminarCategoria(idCategoria);
    }

}
