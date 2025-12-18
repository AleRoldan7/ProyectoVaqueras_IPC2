/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloEntidad;

import EnumOpciones.TipoUsuario;
import org.apache.commons.lang3.StringUtils;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class Usuario {

    private Integer idUsuario;
    private String correo;
    private String password;
    private TipoUsuario tipoUsuario;
    private LocalDate fechaNacimiento;
    private String nickname;
    private String numeroTelefono;
    private String pais;
    private Double dineroCartera = 0.0;
    private String nombre;
    private Integer idEmpresa;

    public Usuario() {
    }

    public Usuario(Integer idUsuario, String nombre, String correo, String password, LocalDate fechaNacimiento) {
        this.idUsuario = idUsuario;
        this.correo = correo;
        this.password = password;
        this.tipoUsuario = tipoUsuario;
        this.fechaNacimiento = fechaNacimiento;
        this.nickname = nickname;
        this.numeroTelefono = numeroTelefono;
        this.pais = pais;
        this.nombre = nombre;
    }

    public Usuario(String nombre, String correo, String password, LocalDate fechaNacimiento, String nickname, String numeroTelefono, String pais) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.tipoUsuario = TipoUsuario.USUARIO_COMUN;
        this.fechaNacimiento = fechaNacimiento;
        this.nickname = nickname;
        this.numeroTelefono = numeroTelefono;
        this.pais = pais;
        this.dineroCartera = 0.0;
    }

    public Usuario(String nombre, String correo, String password, LocalDate fechaNacimiento) {
        this.correo = correo;
        this.password = password;
        this.tipoUsuario = TipoUsuario.ADMIN_EMPRESA;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Double getDineroCartera() {
        return dineroCartera;
    }

    public void setDineroCartera(Double dineroCartera) {
        this.dineroCartera = dineroCartera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public boolean esValido() {
        return StringUtils.isNotBlank(nombre)
                && StringUtils.isNotBlank(correo)
                && StringUtils.isNotBlank(password)
                && StringUtils.isNotBlank(nickname);
    }
}
