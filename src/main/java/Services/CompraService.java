/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.CompraDBA;
import ConexionDBA.UsuarioDBA;
import ConexionDBA.VideojuegoDBA;
import Excepciones.DatosInvalidos;
import ModeloEntidad.Usuario;
import ModeloEntidad.Videojuego;
import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author alejandro
 */
public class CompraService {

    private CompraDBA compraDBA = new CompraDBA();
    private UsuarioDBA usuarioDBA = new UsuarioDBA();
    private VideojuegoDBA videojuegoDBA = new VideojuegoDBA();

    public void comprar(int idUsuario, int idVideojuego, LocalDate fechaCompra) throws Exception {

        Usuario usuario = usuarioDBA.obtenerUsuario(idUsuario);
        if (usuario == null) {
            throw new DatosInvalidos("Usuario no encontrado");
        }

        Videojuego juego = videojuegoDBA.obtenerVideojuego(idVideojuego);
        if (juego == null) {
            throw new DatosInvalidos("El videojuego no está disponible para la venta");
        }

        if (usuarioDBA.tieneVideojuego(idUsuario, idVideojuego)) {
            throw new DatosInvalidos("El videojuego ya fue comprado");
        }

        validarEdad(usuario, juego, fechaCompra);

        if (usuario.getDineroCartera() < juego.getPrecio()) {
            throw new DatosInvalidos("Saldo insuficiente");
        }

        compraDBA.realizarCompra(idUsuario, idVideojuego, juego.getPrecio(), fechaCompra);
    }

    private void validarEdad(Usuario usuario, Videojuego juego, LocalDate fechaCompra)
            throws DatosInvalidos {

        int edad = Period.between(usuario.getFechaNacimiento(), fechaCompra).getYears();

        int edadMinima;

        switch (juego.getClasificacionEdad()) {
            case ADOLESCENTE:
                edadMinima = 12;
                break;
            case ADULTO:
                edadMinima = 16;
                break;
            case TODOS:
            default:
                edadMinima = 5;
                break;
        }

        if (edad < edadMinima) {
            throw new DatosInvalidos(
                    "No cumple la edad mínima para este videojuego (" + edadMinima + "+)"
            );
        }
    }

}
