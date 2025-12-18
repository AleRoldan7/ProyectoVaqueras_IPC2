/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources.ReporteEmpresa;

import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class FeedbackPeorCalificacionDTO {

    private int idVideojuego;
    private String tituloJuego;
    private int calificacion;
    private int idUsuario;
    private String fecha;

    public FeedbackPeorCalificacionDTO(int idVideojuego, String tituloJuego, int calificacion, int idUsuario, String fecha) {
        this.idVideojuego = idVideojuego;
        this.tituloJuego = tituloJuego;
        this.calificacion = calificacion;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
    }

    public int getIdVideojuego() {
        return idVideojuego;
    }

    public void setIdVideojuego(int idVideojuego) {
        this.idVideojuego = idVideojuego;
    }

    public String getTituloJuego() {
        return tituloJuego;
    }

    public void setTituloJuego(String tituloJuego) {
        this.tituloJuego = tituloJuego;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
