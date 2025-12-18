/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources.ReporteAdministrador;

/**
 *
 * @author alejandro
 */
public class TopBalanceDTO {

    private Integer idVideojuego;
    private String titulo;
    private int ventas;
    private double promedio;
    private double score;

    public TopBalanceDTO(Integer idVideojuego, String titulo, int ventas, double promedio, double score) {
        this.idVideojuego = idVideojuego;
        this.titulo = titulo;
        this.ventas = ventas;
        this.promedio = promedio;
        this.score = score;
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

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
