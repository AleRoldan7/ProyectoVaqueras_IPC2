/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.Videojuego;

import ModeloEntidad.Videojuego;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class VideojuegoResponse {

    private int idVideojuego;
    private String tituloVideojuego;
    private String descripcion;
    private double precio;
    private String recursosMinimos;
    private String clasificacionEdad;
    private boolean estadoVenta;
    private String nombreEmpresa;
    private List<String> imagenes;
    private List<Integer> idsImagenes = new ArrayList<>();

    public VideojuegoResponse() {
    }

    public VideojuegoResponse(Videojuego videojuego) {
        this.idVideojuego = videojuego.getIdVideojuego();
        this.tituloVideojuego = videojuego.getTituloVideojuego();
        this.descripcion = videojuego.getDescripcion();
        this.estadoVenta = videojuego.isEstadoVenta();
    }

    public int getIdVideojuego() {
        return idVideojuego;
    }

    public void setIdVideojuego(int idVideojuego) {
        this.idVideojuego = idVideojuego;
    }

    public String getTituloVideojuego() {
        return tituloVideojuego;
    }

    public void setTituloVideojuego(String tituloVideojuego) {
        this.tituloVideojuego = tituloVideojuego;
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

    public String getRecursosMinimos() {
        return recursosMinimos;
    }

    public void setRecursosMinimos(String recursosMinimos) {
        this.recursosMinimos = recursosMinimos;
    }

    public String getClasificacionEdad() {
        return clasificacionEdad;
    }

    public void setClasificacionEdad(String clasificacionEdad) {
        this.clasificacionEdad = clasificacionEdad;
    }

    public boolean isEstadoVenta() {
        return estadoVenta;
    }

    public void setEstadoVenta(boolean estadoVenta) {
        this.estadoVenta = estadoVenta;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public List<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String> imagenes) {
        this.imagenes = imagenes;
    }

    public List<Integer> getIdsImagenes() {
        return idsImagenes;
    }

    public void setIdsImagenes(List<Integer> idsImagenes) {
        this.idsImagenes = idsImagenes;
    }

}
