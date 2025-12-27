/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.GrupoFamiliar.BibliotecaJuegos;

import EnumOpciones.EstadoJuego;

/**
 *
 * @author alejandro
 */
public class JuegoGrupo {

    private int idVideojuego;
    private String nombre;
    private boolean esPropio;
    private EstadoJuego estado;
    private String imagenBase64;

    public JuegoGrupo(int idVideojuego, String nombre, boolean esPropio, EstadoJuego estado, String imagenBase64) {
        this.idVideojuego = idVideojuego;
        this.nombre = nombre;
        this.esPropio = esPropio;
        this.estado = estado;
        this.imagenBase64 = imagenBase64;
    }

    public int getIdVideojuego() {
        return idVideojuego;
    }

    public void setIdVideojuego(int idVideojuego) {
        this.idVideojuego = idVideojuego;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEsPropio() {
        return esPropio;
    }

    public void setEsPropio(boolean esPropio) {
        this.esPropio = esPropio;
    }

    public EstadoJuego getEstado() {
        return estado;
    }

    public void setEstado(EstadoJuego estado) {
        this.estado = estado;
    }

    public String getImagenBase64() {
        return imagenBase64;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

}
