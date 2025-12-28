/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources.ReporteUsuario;

import EnumOpciones.EstadoJuego;

/**
 *
 * @author alejandro
 */
public class AnalisisBibliotecaDTO {

    private int idVideojuego;
    private String titulo;
    private double calificacionComunidad;
    private Integer calificacionUsuario;
    private String estadoInstalacion;
    private String nickname;

    public AnalisisBibliotecaDTO() {
    }

    public AnalisisBibliotecaDTO(int idVideojuego, String titulo, double calificacionComunidad, Integer calificacionUsuario, String estadoInstalacion, String nickname) {
        this.idVideojuego = idVideojuego;
        this.titulo = titulo;
        this.calificacionComunidad = calificacionComunidad;
        this.calificacionUsuario = calificacionUsuario;
        this.estadoInstalacion = estadoInstalacion;
        this.nickname = nickname;
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

    public double getCalificacionComunidad() {
        return calificacionComunidad;
    }

    public void setCalificacionComunidad(double calificacionComunidad) {
        this.calificacionComunidad = calificacionComunidad;
    }

    public Integer getCalificacionUsuario() {
        return calificacionUsuario;
    }

    public void setCalificacionUsuario(Integer calificacionUsuario) {
        this.calificacionUsuario = calificacionUsuario;
    }

    public String getEstadoInstalacion() {
        return estadoInstalacion;
    }

    
    public void setEstadoInstalacion(String estadoInstalacion) {
        this.estadoInstalacion = estadoInstalacion;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
