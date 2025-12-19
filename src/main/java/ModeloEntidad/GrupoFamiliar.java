/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloEntidad;

/**
 *
 * @author alejandro
 */
public class GrupoFamiliar {

    private int idGrupo;
    private String nombreGrupo;
    private int idUsuarioDueño;

    public GrupoFamiliar() {
    }

    public GrupoFamiliar(String nombreGrupo, int idUsuarioDueño) {
        this.nombreGrupo = nombreGrupo;
        this.idUsuarioDueño = idUsuarioDueño;
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
