/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources.ReporteUsuario;

/**
 *
 * @author alejandro
 */
public class CategoriaFavoritaDTO {

    private String categoria;
    private long cantidadComprados;
    private String nickname;

    public CategoriaFavoritaDTO() {
    }

    public CategoriaFavoritaDTO(String categoria, long cantidadComprados, String nickname) {
        this.categoria = categoria;
        this.cantidadComprados = cantidadComprados;
        this.nickname = nickname;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public long getCantidadComprados() {
        return cantidadComprados;
    }

    public void setCantidadComprados(long cantidadComprados) {
        this.cantidadComprados = cantidadComprados;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
