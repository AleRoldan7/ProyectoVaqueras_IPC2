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
    private String nickname;
    private String fecha;
    private String nombreEmpresa;

    public FeedbackPeorCalificacionDTO(int idVideojuego, String tituloJuego, int calificacion,
            int idUsuario, String nickname, String fecha, String nombreEmpresa) {
        this.idVideojuego = idVideojuego;
        this.tituloJuego = tituloJuego;
        this.calificacion = calificacion;
        this.idUsuario = idUsuario;
        this.nickname = nickname;
        this.fecha = fecha;
        this.nombreEmpresa = nombreEmpresa;
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

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
