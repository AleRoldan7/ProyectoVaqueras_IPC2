/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources.ReporteEmpresa;

/**
 *
 * @author alejandro
 */
public class VentaPropiaDTO {

    private Integer idVideojuego;
    private String titulo;
    private double montoBruto;
    private double comisionPlataforma;
    private double ingresoNeto;
    private double porcentajeComision;

    public VentaPropiaDTO() {
    }

    
    public VentaPropiaDTO(Integer idVideojuego, String titulo, double montoBruto, double comisionPlataforma, double ingresoNeto, double porcentajeComision) {
        this.idVideojuego = idVideojuego;
        this.titulo = titulo;
        this.montoBruto = montoBruto;
        this.comisionPlataforma = comisionPlataforma;
        this.ingresoNeto = ingresoNeto;
        this.porcentajeComision = porcentajeComision;
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

    public double getMontoBruto() {
        return montoBruto;
    }

    public void setMontoBruto(double montoBruto) {
        this.montoBruto = montoBruto;
    }

    public double getComisionPlataforma() {
        return comisionPlataforma;
    }

    public void setComisionPlataforma(double comisionPlataforma) {
        this.comisionPlataforma = comisionPlataforma;
    }

    public double getIngresoNeto() {
        return ingresoNeto;
    }

    public void setIngresoNeto(double ingresoNeto) {
        this.ingresoNeto = ingresoNeto;
    }

    public double getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(double porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

}
