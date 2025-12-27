/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.GrupoFamiliar;

import java.util.List;

/**
 *
 * @author alejandro
 */
public class GrupoMiembro {

    private int idGrupo;
    private String nombreGrupo;
    private int totalMiembros;
    private boolean cupoDisponible;
    private List<MiembroGrupoRequest> miembros;

    public GrupoMiembro() {
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

    public int getTotalMiembros() {
        return totalMiembros;
    }

    public void setTotalMiembros(int totalMiembros) {
        this.totalMiembros = totalMiembros;
    }

    public boolean isCupoDisponible() {
        return cupoDisponible;
    }

    public void setCupoDisponible(boolean cupoDisponible) {
        this.cupoDisponible = cupoDisponible;
    }

    public List<MiembroGrupoRequest> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<MiembroGrupoRequest> miembros) {
        this.miembros = miembros;
    }

}
