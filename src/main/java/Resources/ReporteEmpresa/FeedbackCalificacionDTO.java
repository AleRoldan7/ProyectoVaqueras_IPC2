/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources.ReporteEmpresa;

/**
 *
 * @author alejandro
 */
public class FeedbackCalificacionDTO {

    private int idVideojuego;
    private String titulo;
    private double promedio;
    private int totalCalificaciones;

    public FeedbackCalificacionDTO(int idVideojuego, String titulo, double promedio, int totalCalificaciones) {
        this.idVideojuego = idVideojuego;
        this.titulo = titulo;
        this.promedio = promedio;
        this.totalCalificaciones = totalCalificaciones;
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

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public int getTotalCalificaciones() {
        return totalCalificaciones;
    }

    public void setTotalCalificaciones(int totalCalificaciones) {
        this.totalCalificaciones = totalCalificaciones;
    }
    
    
}
