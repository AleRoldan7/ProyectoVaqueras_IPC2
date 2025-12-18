/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.Videojuego;

import EnumOpciones.ClasificacionEdad;

/**
 *
 * @author alejandro
 */
public class NewVideojuegoRequest {

    private Integer idVideojuego;
    private String tituloVideojuego;
    private String descripcion;
    private double precio;
    private String recursosMinimos;
    private ClasificacionEdad clasificacionEdad;
    private Integer idEmpresa;
    private boolean estadoVenta;

    public Integer getIdVideojuego() {
        return idVideojuego;
    }

    public void setIdVideojuego(Integer idVideojuego) {
        this.idVideojuego = idVideojuego;
    }

    public String getTituloVideojuego() {
        return tituloVideojuego;
    }

    public void setTituloVideojuego(String tituloVideojuego) {
        this.tituloVideojuego = tituloVideojuego;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getRecursosMinimos() {
        return recursosMinimos;
    }

    public void setRecursosMinimos(String recursosMinimos) {
        this.recursosMinimos = recursosMinimos;
    }

    public ClasificacionEdad getClasificacionEdad() {
        return clasificacionEdad;
    }

    public void setClasificacionEdad(ClasificacionEdad clasificacionEdad) {
        this.clasificacionEdad = clasificacionEdad;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public boolean isEstadoVenta() {
        return estadoVenta;
    }

    public void setEstadoVenta(boolean estadoVenta) {
        this.estadoVenta = estadoVenta;
    }

}
