/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.ComentarioCalificacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class ComentarioResponse {

    private int idComentario;
    private int idUsuario;
    private String nicknameUsuario;
    private String texto;
    private boolean visible;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime fecha;
    private List<ComentarioResponse> respuestas;

    public ComentarioResponse() {
        this.respuestas = new ArrayList<>();
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNicknameUsuario() {
        return nicknameUsuario;
    }

    public void setNicknameUsuario(String nicknameUsuario) {
        this.nicknameUsuario = nicknameUsuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<ComentarioResponse> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<ComentarioResponse> respuestas) {
        this.respuestas = respuestas;
    }

}
