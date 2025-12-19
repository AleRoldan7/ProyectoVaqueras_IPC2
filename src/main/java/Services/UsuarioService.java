/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.EmpresaDBA;
import ConexionDBA.UsuarioDBA;
import Dtos.Usuario.NewLoginRequest;
import Dtos.Usuario.NewUsuarioRequest;
import Dtos.Usuario.UpdateUsuarioRequest;
import ModeloEntidad.Usuario;
import Excepciones.DatosInvalidos;
import Excepciones.EntityExists;
import Validaciones.ValidatorUsuario;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class UsuarioService {

    private UsuarioDBA usuarioDBA = new UsuarioDBA();

    public Usuario crearUsuarioComun(NewUsuarioRequest newUsuarioRequest) throws DatosInvalidos, EntityExists {

        ValidatorUsuario.validarRegistroUsuarioComun(newUsuarioRequest);

        Usuario entidadUsuario = extraer(newUsuarioRequest);

        if (usuarioDBA.existeUsuario(entidadUsuario.getNickname())) {
            throw new EntityExists(
                    String.format("El usuario con nickName %s ya existe", entidadUsuario.getNickname())
            );
        }

        usuarioDBA.registrarUsuarioComun(entidadUsuario);

        return entidadUsuario;
    }

    private Usuario extraer(NewUsuarioRequest newUsuarioRequest) throws DatosInvalidos {

        try {
            Usuario entidadUsuario = new Usuario(
                    newUsuarioRequest.getNombre(),
                    newUsuarioRequest.getCorreo(),
                    newUsuarioRequest.getPassword(),
                    newUsuarioRequest.getFechaNacimiento(),
                    newUsuarioRequest.getNickname(),
                    newUsuarioRequest.getNumeroTelefono(),
                    newUsuarioRequest.getPais()
            );

            return entidadUsuario;
        } catch (Exception e) {
            throw new DatosInvalidos("Error en los datos enviados" + e.getMessage());
        }

    }

    public Usuario login(String correo, String password) throws DatosInvalidos {

        NewLoginRequest newLoginRequest = new NewLoginRequest();
        newLoginRequest.setCorreo(correo);
        newLoginRequest.setPassword(password);

        ValidatorUsuario.validarLogin(newLoginRequest);

        return usuarioDBA.verificarUsuario(correo, password);
    }

    public Usuario obtenerJugador(int idUsuario) throws SQLException {
        return usuarioDBA.obtenerUsuario(idUsuario);
    }

    public List<Usuario> listarJugadores() throws SQLException {
        return usuarioDBA.listarUsuarios();
    }

    public void actualizarJugador(int idUsuario, UpdateUsuarioRequest request) throws SQLException {
        Usuario u = usuarioDBA.obtenerUsuario(idUsuario);
        if (u != null) {
            u.setNombre(request.getNombre());
            u.setNickname(request.getNickname());
            u.setCorreo(request.getCorreo());
            u.setNumeroTelefono(request.getNumeroTelefono());
            u.setPais(request.getPais());
            u.setFechaNacimiento(request.getFechaNacimiento());
            usuarioDBA.actualizarUsuario(u);
        }
    }

    public void eliminarJugador(int idUsuario) throws SQLException {
        usuarioDBA.eliminarUsuario(idUsuario);
    }

    public void agregarFondos(int idUsuario, double monto) throws SQLException {
        usuarioDBA.agregarFondos(idUsuario, monto);
    }

}
