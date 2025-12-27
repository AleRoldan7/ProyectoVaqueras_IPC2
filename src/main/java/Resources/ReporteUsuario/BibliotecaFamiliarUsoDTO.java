/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources.ReporteUsuario;

/**
 *
 * @author alejandro
 */
public class BibliotecaFamiliarUsoDTO {

    private String titulo;
    private String estado;
    private boolean esPrestado;
    private double calificacionComunidad;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isEsPrestado() {
        return esPrestado;
    }

    public void setEsPrestado(boolean esPrestado) {
        this.esPrestado = esPrestado;
    }

    public double getCalificacionComunidad() {
        return calificacionComunidad;
    }

    public void setCalificacionComunidad(double calificacionComunidad) {
        this.calificacionComunidad = calificacionComunidad;
    }

}
