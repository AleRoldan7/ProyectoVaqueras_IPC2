/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.Videojuego;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class VideojuegoImagenes extends VideojuegoDisponibleRequest {

    private List<Integer> idsImagenes = new ArrayList<>();

    public VideojuegoImagenes(int idVideojuego, String titulo, String descripcion,
            double precio, String clasificacionEdad, List<String> categorias) {
        super(idVideojuego, titulo, descripcion, precio, clasificacionEdad, categorias);
    }

    public List<Integer> getIdsImagenes() {
        return idsImagenes;
    }

    public void setIdsImagenes(List<Integer> idsImagenes) {
        this.idsImagenes = idsImagenes;
    }

    public void addIdImagen(int idImagen) {
        this.idsImagenes.add(idImagen);
    }
}
