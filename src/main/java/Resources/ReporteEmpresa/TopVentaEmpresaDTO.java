/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources.ReporteEmpresa;

/**
 *
 * @author alejandro
 */
public class TopVentaEmpresaDTO {

    private int idVideojuego;
    private String titulo;
    private int totalVentas;

    public TopVentaEmpresaDTO(int idVideojuego, String titulo, int totalVentas) {
        this.idVideojuego = idVideojuego;
        this.titulo = titulo;
        this.totalVentas = totalVentas;
    }

    public int getIdVideojuego() {
        return idVideojuego;
    }

    public void setIdVideojuego(int idVideojuego) {
        this.idVideojuego = idVideojuego;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(int totalVentas) {
        this.totalVentas = totalVentas;
    }

}
