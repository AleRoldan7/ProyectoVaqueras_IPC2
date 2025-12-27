/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.GrupoBibliotecaDBA;
import Dtos.GrupoFamiliar.BibliotecaJuegos.JuegoGrupo;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class GrupoBibliotecaService {

    private GrupoBibliotecaDBA grupoBibliotecaDBA = new GrupoBibliotecaDBA();

    public List<JuegoGrupo> obtenerJuegosDelGrupo(int idGrupo, int idUsuario) throws SQLException {
        return grupoBibliotecaDBA.juegosGrupo(idGrupo, idUsuario);
    }

    public void instalarJuegoPrestado(int idUsuario, int idVideojuego) throws SQLException {

        if (grupoBibliotecaDBA.tienePrestadoInstalado(idUsuario)) {
            throw new SQLException("Ya tienes un juego prestado instalado");
        }

        if (grupoBibliotecaDBA.existeRegistro(idUsuario, idVideojuego)) {
            grupoBibliotecaDBA.actualizarEstado(idUsuario, idVideojuego, "INSTALADO");
        } else {
            grupoBibliotecaDBA.insertarEstado(idUsuario, idVideojuego, "INSTALADO", true);
        }
    }

    public void desinstalarJuego(int idUsuario, int idVideojuego) throws Exception {

        if (!grupoBibliotecaDBA.existeRegistro(idUsuario, idVideojuego)) {
            throw new Exception("El juego no está instalado");
        }

        grupoBibliotecaDBA.actualizarEstado(idUsuario, idVideojuego, "NO_INSTALADO");
    }
}
