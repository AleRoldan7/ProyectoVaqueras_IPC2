/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.Usuario;

import EnumOpciones.TipoUsuario;
import ModeloEntidad.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class UsuarioResponse {

    private Integer idUsuario;
    private String nombre;
    private String correo;
    private String nickname;
    private TipoUsuario tipoUsuario;
    private Double dineroCartera;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate fechaNacimiento;
    private Integer idEmpresa;

    public UsuarioResponse(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.nombre = usuario.getNombre();
        this.correo = usuario.getCorreo();
        this.nickname = usuario.getNickname();
        this.tipoUsuario = usuario.getTipoUsuario();
        this.dineroCartera = usuario.getDineroCartera();
        this.fechaNacimiento = usuario.getFechaNacimiento();
    }

    public UsuarioResponse() {
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNickname() {
        return nickname;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public Double getDineroCartera() {
        return dineroCartera;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

}
