/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.GrupoFamiliar;

/**
 *
 * @author alejandro
 */
public class NewGrupoRequest {

    private String nombreGrupo;
    private int idUsuarioDueño;

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
