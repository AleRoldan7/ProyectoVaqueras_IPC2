/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloEntidad;

import java.time.LocalDateTime;

/**
 *
 * @author alejandro
 */
public class Biblioteca {

    private int idBiblioteca;
    private int idUsuario;
    private int idCompra;
    private String estadoInstalacion;
    private LocalDateTime fechaAdquisicion;

    public Biblioteca(int idBiblioteca, int idUsuario, int idCompra, String estadoInstalacion, LocalDateTime fechaAdquisicion) {
        this.idBiblioteca = idBiblioteca;
        this.idUsuario = idUsuario;
        this.idCompra = idCompra;
        this.estadoInstalacion = estadoInstalacion;
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public Biblioteca(int idUsuario, int idCompra, String estadoInstalacion, LocalDateTime fechaAdquisicion) {
        this.idUsuario = idUsuario;
        this.idCompra = idCompra;
        this.estadoInstalacion = estadoInstalacion;
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public int getIdBiblioteca() {
        return idBiblioteca;
    }

    public void setIdBiblioteca(int idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public String getEstadoInstalacion() {
        return estadoInstalacion;
    }

    public void setEstadoInstalacion(String estadoInstalacion) {
        this.estadoInstalacion = estadoInstalacion;
    }

    public LocalDateTime getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDateTime fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

}
