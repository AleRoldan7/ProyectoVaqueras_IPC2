/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloEntidad;

/**
 *
 * @author alejandro
 */
public class Imagen {

    private Integer idImagen;
    private byte[] imagen;
    private int idVideojuego;

    public Imagen(Integer idImagen, byte[] imagen, int idVideojuego) {
        this.idImagen = idImagen;
        this.imagen = imagen;
        this.idVideojuego = idVideojuego;
    }

    public Imagen(byte[] imagen, int idVideojuego) {
        this.imagen = imagen;
        this.idVideojuego = idVideojuego;
    }

    public Imagen() {
    }

    public Integer getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Integer idImagen) {
        this.idImagen = idImagen;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public int getIdVideojuego() {
        return idVideojuego;
    }

    public void setIdVideojuego(int idVideojuego) {
        this.idVideojuego = idVideojuego;
    }

}
