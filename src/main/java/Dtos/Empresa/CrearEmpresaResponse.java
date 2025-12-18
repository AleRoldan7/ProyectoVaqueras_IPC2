/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.Empresa;

import ModeloEntidad.Empresa;

/**
 *
 * @author alejandro
 */
public class CrearEmpresaResponse {

    private String nombreEmpresa;
    private String descripcionEmpresa;
    private Integer idUsuario;
    private String paisEmpresa;

    public CrearEmpresaResponse(Empresa empresa) {
        this.nombreEmpresa = empresa.getNombreEmpresa();
        this.descripcionEmpresa = empresa.getDescripcionEmpresa();
        this.idUsuario = empresa.getIdUsuario();
        this.paisEmpresa = empresa.getPaisEmpresa();
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDescripcionEmpresa() {
        return descripcionEmpresa;
    }

    public void setDescripcionEmpresa(String descripcionEmpresa) {
        this.descripcionEmpresa = descripcionEmpresa;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPaisEmpresa() {
        return paisEmpresa;
    }

    public void setPaisEmpresa(String paisEmpresa) {
        this.paisEmpresa = paisEmpresa;
    }

}
