/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.ListaDBA;
import ModeloEntidad.Usuario;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class ListaService {

    private ListaDBA listaDBA = new ListaDBA();

    public List<Usuario> obtenerAdminCine() {

        return listaDBA.listaAdminEmpresa();

    }

    public List<byte[]> obtenerImagenes(int idVideojuego) {
        return listaDBA.obtenerImagenesPorVideojuego(idVideojuego);
    }

}
