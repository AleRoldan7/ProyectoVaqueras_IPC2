/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validaciones;

import Dtos.Usuario.NewLoginRequest;
import Dtos.Usuario.NewUsuarioRequest;
import Excepciones.DatosInvalidos;
import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 *
 * @author alejandro
 */
public class ValidatorUsuario {
    
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private static final Pattern TELEFONO_PATTERN =
            Pattern.compile("^[0-9]{8,15}$");

    private ValidatorUsuario() {
    }

    
    public static void validarRegistroUsuarioComun(NewUsuarioRequest newUsuarioRequest)
            throws DatosInvalidos {

        if (newUsuarioRequest == null) {
            throw new DatosInvalidos("Datos vacíos");
        }

        validarCorreo(newUsuarioRequest.getCorreo());
        validarPassword(newUsuarioRequest.getPassword());
        validarNickname(newUsuarioRequest.getNickname());
        validarFechaNacimiento(newUsuarioRequest.getFechaNacimiento());
        validarTelefono(newUsuarioRequest.getNumeroTelefono());
        validarPais(newUsuarioRequest.getPais());
    }

    
    public static void validarLogin(NewLoginRequest newLoginRequest)
            throws DatosInvalidos {

        if (newLoginRequest == null) {
            throw new DatosInvalidos("Datos vacíos");
        }

        validarCorreo(newLoginRequest.getCorreo());

        if (newLoginRequest.getPassword() == null || newLoginRequest.getPassword().trim().isEmpty()) {
            throw new DatosInvalidos("Password inválido");
        }
    }

    
    private static void validarCorreo(String correo) throws DatosInvalidos {
        if (correo == null || !EMAIL_PATTERN.matcher(correo).matches()) {
            throw new DatosInvalidos("Correo inválido");
        }
    }

    private static void validarPassword(String password) throws DatosInvalidos {
        if (password == null || password.length() < 6) {
            throw new DatosInvalidos("Password debe tener al menos 6 caracteres");
        }
    }

    private static void validarNickname(String nickname) throws DatosInvalidos {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new DatosInvalidos("Nickname obligatorio");
        }
    }

    private static void validarFechaNacimiento(LocalDate fecha) throws DatosInvalidos {
        if (fecha == null || fecha.isAfter(LocalDate.now().minusYears(12))) {
            throw new DatosInvalidos("Fecha de nacimiento inválida");
        }
    }

    private static void validarTelefono(String telefono) throws DatosInvalidos {
        if (telefono == null || !TELEFONO_PATTERN.matcher(telefono).matches()) {
            throw new DatosInvalidos("Número telefónico inválido");
        }
    }

    private static void validarPais(String pais) throws DatosInvalidos {
        if (pais == null || pais.trim().isEmpty()) {
            throw new DatosInvalidos("País obligatorio");
        }
    }
}
