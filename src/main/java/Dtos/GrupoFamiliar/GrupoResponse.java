/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.GrupoFamiliar;

import ModeloEntidad.GrupoFamiliar;

/**
 *
 * @author alejandro
 */
public class GrupoResponse {

    private int idGrupo;
    private String nombreGrupo;
    private int idUsuarioDueño;

    public GrupoResponse(GrupoFamiliar grupo) {
        this.idGrupo = grupo.getIdGrupo();
        this.nombreGrupo = grupo.getNombreGrupo();
        this.idUsuarioDueño = grupo.getIdUsuarioDueño();
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public int getIdUsuarioDueño() {
        return idUsuarioDueño;
    }

    public void setIdUsuarioDueño(int idUsuarioDueño) {
        this.idUsuarioDueño = idUsuarioDueño;
    }

}
