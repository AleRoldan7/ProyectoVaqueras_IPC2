/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.BibliotecaDBA;
import Dtos.Biblioteca.BibliotecaResponse;
import ModeloEntidad.Usuario;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class BibliotecaService {

    private BibliotecaDBA bibliotecaDBA = new BibliotecaDBA();

    public List<BibliotecaResponse> obtenerBibliotecaUsuario(int idUsuario) throws SQLException {
        return bibliotecaDBA.obtenerBiblioteca(idUsuario);
    }

    public List<BibliotecaResponse> obtenerBibliotecaPorNickname(String nickname) throws SQLException {
        int idUsuario = bibliotecaDBA.obtenerUsuarioPorNickname(nickname);
        if (idUsuario == -1) {
            return Collections.emptyList();
        }
        return bibliotecaDBA.obtenerBiblioteca(idUsuario);
    }

    public void cambiarEstadoInstalacion(int idUsuario, int idCompra, String nuevoEstado) throws SQLException {
        bibliotecaDBA.cambiarEstadoInstalacion(idUsuario, idCompra, nuevoEstado);
    }

}
