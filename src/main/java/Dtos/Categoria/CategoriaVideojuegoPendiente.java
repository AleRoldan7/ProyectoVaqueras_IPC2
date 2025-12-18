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

    private int idVideojuegoCategoria;
    private String nombreVideojuego;
    private String nombreCategoria;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaSolicitud;

    public CategoriaVideojuegoPendiente(int idVideojuegoCategoria, String nombreVideojuego, String nombreCategoria, LocalDate fechaSolicitud) {
        this.idVideojuegoCategoria = idVideojuegoCategoria;
        this.nombreVideojuego = nombreVideojuego;
        this.nombreCategoria = nombreCategoria;
        this.fechaSolicitud = fechaSolicitud;
    }

    public int getIdVideojuegoCategoria() {
        return idVideojuegoCategoria;
    }

    public void setIdVideojuegoCategoria(int idVideojuegoCategoria) {
        this.idVideojuegoCategoria = idVideojuegoCategoria;
    }

    public String getNombreVideojuego() {
        return nombreVideojuego;
    }

    public void setNombreVideojuego(String nombreVideojuego) {
        this.nombreVideojuego = nombreVideojuego;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

}
