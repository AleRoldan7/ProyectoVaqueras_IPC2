/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources.ReporteAdministrador;

/**
 *
 * @author alejandro
 */
public class GananciaSistemaDTO {

    private double totalIngresos;
    private double totalEmpresas;
    private double comisionPlataforma;

    
    public double getTotalIngresos() {
        return totalIngresos;
    }

    public GananciaSistemaDTO(double totalIngresos, double totalEmpresas, double comisionPlataforma) {
        this.totalIngresos = totalIngresos;
        this.totalEmpresas = totalEmpresas;
        this.comisionPlataforma = comisionPlataforma;
    }

    public void setTotalIngresos(double totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public double getTotalEmpresas() {
        return totalEmpresas;
    }

    public void setTotalEmpresas(double totalEmpresas) {
        this.totalEmpresas = totalEmpresas;
    }

    public double getComisionPlataforma() {
        return comisionPlataforma;
    }

    public void setComisionPlataforma(double comisionPlataforma) {
        this.comisionPlataforma = comisionPlataforma;
    }

    
}
