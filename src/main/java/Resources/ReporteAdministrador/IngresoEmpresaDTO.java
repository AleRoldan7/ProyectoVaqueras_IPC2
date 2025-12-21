/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources.ReporteAdministrador;

import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class IngresoEmpresaDTO {

    private int idEmpresa;
    private String nombreEmpresa;
    private double totalVentas;
    private double comisionPlataforma;
    private double ingresoEmpresa;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public IngresoEmpresaDTO(int idEmpresa, String nombreEmpresa, double totalVentas, double comisionPlataforma,
            double ingresoEmpresa, LocalDate fechaInicio, LocalDate fechaFin) {
        this.idEmpresa = idEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.totalVentas = totalVentas;
        this.comisionPlataforma = comisionPlataforma;
        this.ingresoEmpresa = ingresoEmpresa;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public IngresoEmpresaDTO(int idEmpresa, String nombreEmpresa, double totalVentas, double comisionPlataforma, double ingresoEmpresa) {
        this.idEmpresa = idEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.totalVentas = totalVentas;
        this.comisionPlataforma = comisionPlataforma;
        this.ingresoEmpresa = ingresoEmpresa;
    }
    
    

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public double getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(double totalVentas) {
        this.totalVentas = totalVentas;
    }

    public double getComisionPlataforma() {
        return comisionPlataforma;
    }

    public void setComisionPlataforma(double comisionPlataforma) {
        this.comisionPlataforma = comisionPlataforma;
    }

    public double getIngresoEmpresa() {
        return ingresoEmpresa;
    }

    public void setIngresoEmpresa(double ingresoEmpresa) {
        this.ingresoEmpresa = ingresoEmpresa;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

}
