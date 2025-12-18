/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources.ReporteAdministrador;

/**
 *
 * @author alejandro
 */
public class TopVentaDTO {

    private Integer idVideojuego;
    private String titulo;
    private int ventas;

    public TopVentaDTO(Integer idVideojuego, String titulo, int ventas) {
        this.idVideojuego = idVideojuego;
        this.titulo = titulo;
        this.ventas = ventas;
    }

    public Integer getIdVideojuego() {
        return idVideojuego;
    }

    public void setIdVideojuego(Integer idVideojuego) {
        this.idVideojuego = idVideojuego;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getVentas() {
        return ventas;
    }

    public void setVentas(int ventas) {
        this.ventas = ventas;
    }

}
