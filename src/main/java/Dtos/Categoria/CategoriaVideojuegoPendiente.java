/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.Categoria;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class CategoriaVideojuegoPendiente {

    private int idCategoriaVideojuego;
    private int idVideojuego;
    private String tituloVideojuego;
    private String descripcionVideojuego;
    private String nombreCategoria;
    private String descripcionCategoria;
    private String nombreEmpresaDesarrolladora;
    private double precio;
    private String clasificacionEdad;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaSolicitud;

    public CategoriaVideojuegoPendiente(int idCategoriaVideojuego, int idVideojuego, String tituloVideojuego, 
            String descripcionVideojuego, String nombreCategoria, String descripcionCategoria, String nombreEmpresaDesarrolladora, 
            double precio, String clasificacionEdad, LocalDate fechaSolicitud) {
        this.idCategoriaVideojuego = idCategoriaVideojuego;
        this.idVideojuego = idVideojuego;
        this.tituloVideojuego = tituloVideojuego;
        this.descripcionVideojuego = descripcionVideojuego;
        this.nombreCategoria = nombreCategoria;
        this.descripcionCategoria = descripcionCategoria;
        this.nombreEmpresaDesarrolladora = nombreEmpresaDesarrolladora;
        this.precio = precio;
        this.clasificacionEdad = clasificacionEdad;
        this.fechaSolicitud = fechaSolicitud;
    }

    public int getIdCategoriaVideojuego() {
        return idCategoriaVideojuego;
    }

    public void setIdCategoriaVideojuego(int idCategoriaVideojuego) {
        this.idCategoriaVideojuego = idCategoriaVideojuego;
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

    public String getDescripcionVideojuego() {
        return descripcionVideojuego;
    }

    public void setDescripcionVideojuego(String descripcionVideojuego) {
        this.descripcionVideojuego = descripcionVideojuego;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }

    public String getNombreEmpresaDesarrolladora() {
        return nombreEmpresaDesarrolladora;
    }

    public void setNombreEmpresaDesarrolladora(String nombreEmpresaDesarrolladora) {
        this.nombreEmpresaDesarrolladora = nombreEmpresaDesarrolladora;
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

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    
    

}
