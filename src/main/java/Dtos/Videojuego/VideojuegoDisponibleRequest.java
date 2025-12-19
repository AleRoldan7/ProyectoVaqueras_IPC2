/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.Videojuego;

import java.util.List;

/**
 *
 * @author alejandro
 */
public class VideojuegoDisponibleRequest {

    private int idVideojuego;
    private String titulo;
    private String descripcion;
    private double precio;
    private String clasificacionEdad;
    private List<String> categorias;

    public VideojuegoDisponibleRequest(int idVideojuego, String titulo, String descripcion, double precio,
            String clasificacionEdad, List<String> categorias) {
        this.idVideojuego = idVideojuego;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.clasificacionEdad = clasificacionEdad;
        this.categorias = categorias;
    }

    public int getIdVideojuego() {
        return idVideojuego;
    }

    public void setIdVideojuego(int idVideojuego) {
        this.idVideojuego = idVideojuego;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getClasificacionEdad() {
        return clasificacionEdad;
    }

    public void setClasificacionEdad(String clasificacionEdad) {
        this.clasificacionEdad = clasificacionEdad;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

}
