/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloEntidad;

import java.time.LocalDateTime;

/**
 *
 * @author alejandro
 */
public class Compra {

    private int idCompra;
    private int idUsuario;
    private int idVideojuego;
    private double precioPagado;
    private LocalDateTime fechaCompra;

    public Compra(int idCompra, int idUsuario, int idVideojuego, double precioPagado, LocalDateTime fechaCompra) {
        this.idCompra = idCompra;
        this.idUsuario = idUsuario;
        this.idVideojuego = idVideojuego;
        this.precioPagado = precioPagado;
        this.fechaCompra = fechaCompra;
    }

    public Compra(int idUsuario, int idVideojuego, double precioPagado, LocalDateTime fechaCompra) {
        this.idUsuario = idUsuario;
        this.idVideojuego = idVideojuego;
        this.precioPagado = precioPagado;
        this.fechaCompra = fechaCompra;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdVideojuego() {
        return idVideojuego;
    }

    public void setIdVideojuego(int idVideojuego) {
        this.idVideojuego = idVideojuego;
    }

    public double getPrecioPagado() {
        return precioPagado;
    }

    public void setPrecioPagado(double precioPagado) {
        this.precioPagado = precioPagado;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

}
