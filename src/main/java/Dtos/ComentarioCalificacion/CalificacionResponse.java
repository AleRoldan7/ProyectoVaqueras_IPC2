/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.ComentarioCalificacion;

/**
 *
 * @author alejandro
 */
public class CalificacionResponse {

    private double promedio;
    private int totalCalificaciones;
    private Integer calificacionUsuarioActual;

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

    public Integer getCalificacionUsuarioActual() {
        return calificacionUsuarioActual;
    }

    public void setCalificacionUsuarioActual(Integer calificacionUsuarioActual) {
        this.calificacionUsuarioActual = calificacionUsuarioActual;
    }

}
