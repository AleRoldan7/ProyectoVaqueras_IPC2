/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.EmpresaDBA;
import Dtos.Empresa.CrearEmpresaRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntityExists;
import ModeloEntidad.Empresa;

/**
 *
 * @author alejandro
 */
public class EmpresaService {

    private EmpresaDBA empresaDBA = new EmpresaDBA();

    public Empresa crearEmpresa(CrearEmpresaRequest crearEmpresaRequest) throws DatosInvalidos, EntityExists {

        Empresa entidadEmpresa = extraer(crearEmpresaRequest);

        if (empresaDBA.adminAsigandoEmpresa(entidadEmpresa.getIdUsuario())) {
            throw new EntityExists(
                    String.format("El usuario ya administra una empresa", entidadEmpresa.getIdUsuario()));
        }

        if (empresaDBA.existeEmpresa(entidadEmpresa.getNombreEmpresa())) {
            throw new EntityExists(
                    String.format("El nombre de la empresa %s ya existe", entidadEmpresa.getNombreEmpresa())
            );
        }

        empresaDBA.crearEmpresa(entidadEmpresa);

        return entidadEmpresa;
    }

    private Empresa extraer(CrearEmpresaRequest crearEmpresaRequest) throws DatosInvalidos {

        try {

            Empresa entidadEmpresa = new Empresa(
                    crearEmpresaRequest.getNombreEmpresa(),
                    crearEmpresaRequest.getDescripcionEmpresa(),
                    crearEmpresaRequest.getIdUsuario(),
                    crearEmpresaRequest.getPaisEmpresa()
            );

            if (!entidadEmpresa.esValido()) {
                throw new DatosInvalidos("Error en los datos enviados");
            }

            return entidadEmpresa;
        } catch (Exception e) {
            throw new DatosInvalidos("Error en los datos enviados" + e.getMessage());
        }

    }
    
    
    public String devolverNombreEmpresa(int idEmpresa) {
        
        return empresaDBA.obtenerNombreEmpresa(idEmpresa);
    }

}
