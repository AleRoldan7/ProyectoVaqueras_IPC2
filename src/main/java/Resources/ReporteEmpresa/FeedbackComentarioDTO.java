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
public class FeedbackComentarioDTO {

    private int idComentario;
    private int idVideojuego;
    private String tituloJuego;
    private String texto;
    private int totalRespuestas;
    private String fecha;

    public FeedbackComentarioDTO(int idComentario, int idVideojuego, String tituloJuego, String texto, int totalRespuestas, String fecha) {
        this.idComentario = idComentario;
        this.idVideojuego = idVideojuego;
        this.tituloJuego = tituloJuego;
        this.texto = texto;
        this.totalRespuestas = totalRespuestas;
        this.fecha = fecha;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
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

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getTotalRespuestas() {
        return totalRespuestas;
    }

    public void setTotalRespuestas(int totalRespuestas) {
        this.totalRespuestas = totalRespuestas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
