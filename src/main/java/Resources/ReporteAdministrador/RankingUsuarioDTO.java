/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources.ReporteAdministrador;

/**
 *
 * @author alejandro
 */
public class RankingUsuarioDTO {

    private int idUsuario;
    private String nickname;
    private String correo;
    private int totalCompras;
    private int totalComentarios;

    public RankingUsuarioDTO(int idUsuario, String nickname, String correo, int totalCompras, int totalComentarios) {
        this.idUsuario = idUsuario;
        this.nickname = nickname;
        this.correo = correo;
        this.totalCompras = totalCompras;
        this.totalComentarios = totalComentarios;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getTotalCompras() {
        return totalCompras;
    }

    public void setTotalCompras(int totalCompras) {
        this.totalCompras = totalCompras;
    }

    public int getTotalComentarios() {
        return totalComentarios;
    }

    public void setTotalComentarios(int totalComentarios) {
        this.totalComentarios = totalComentarios;
    }

}
