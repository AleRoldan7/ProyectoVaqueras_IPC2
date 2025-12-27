/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.GrupoFamiliar;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author alejandro
 */
public class InvitacionGrupo {

    private int idInvitacion;
    private int idGrupo;
    private String nombreGrupo;
    private int idUsuario;
    private String estado;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime fecha;

    public InvitacionGrupo(int idInvitacion, int idGrupo, String nombreGrupo, int idUsuario, String estado, LocalDateTime fecha) {
        this.idInvitacion = idInvitacion;
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.idUsuario = idUsuario;
        this.estado = estado;
        this.fecha = fecha;
    }

    public int getIdInvitacion() {
        return idInvitacion;
    }

    public void setIdInvitacion(int idInvitacion) {
        this.idInvitacion = idInvitacion;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

}
