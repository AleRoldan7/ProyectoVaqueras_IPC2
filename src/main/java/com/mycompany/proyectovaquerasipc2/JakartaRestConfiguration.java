package com.mycompany.proyectovaquerasipc2;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Configures Jakarta RESTful Web Services for the application.
 * @author Juneau
 */
@ApplicationPath("api/v1")
public class JakartaRestConfiguration extends ResourceConfig {
    
    public JakartaRestConfiguration() {
        
        packages("com.mycompany.proyectovaquerasipc2");
        packages("ConexionDBA");
        packages("Controller");
        packages("Dtos.Usuario");
        packages("Dtos.Empresa");
        packages("Dtos.Videojuego");
        packages("EnumOpciones");
        packages("Excepciones");
        packages("ModeloEntidad");
        packages("Services");
        packages("com.mycompany.proyectovaquerasipc2.resources");
        register(MultiPartFeature.class);
    }
}
