/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloEntidad;

/**
 *
 * @author alejandro
 */
public class Categoria {

    private Integer idCategoria;
    private String nombreCategoria;
    private String descripcionCategoria;
    private Integer idVideojuego;

    public Categoria(Integer idCategoria, String nombreCategoria, String descripcionCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.descripcionCategoria = descripcionCategoria;
    }

    public Categoria(String nombreCategoria, String descripcionCategoria) {
        this.nombreCategoria = nombreCategoria;
        this.descripcionCategoria = descripcionCategoria;
    }

    public Categoria(Integer idVideojuego, Integer idCategoria) {
        this.idCategoria = idCategoria;
        this.idVideojuego = idVideojuego;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }

    public Integer getIdVideojuego() {
        return idVideojuego;
    }

    public void setIdVideojuego(Integer idVideojuego) {
        this.idVideojuego = idVideojuego;
    }

}
