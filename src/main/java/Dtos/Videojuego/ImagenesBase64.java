/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.Videojuego;

import Excepciones.DatosInvalidos;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 *
 * @author alejandro
 */
public class ImagenesBase64 {

    public String convertirFotoBase64(byte[] fotoPerfil) throws DatosInvalidos {

        if (fotoPerfil != null) {

            return Base64.getEncoder().encodeToString(fotoPerfil);

        } else {
            try (InputStream defaultImg = getClass().getResourceAsStream("/icons/defalutUser.png")) {
                byte[] defaultFoto = defaultImg.readAllBytes();

                return Base64.getEncoder().encodeToString(defaultFoto);

            } catch (IOException ex) {
                throw new DatosInvalidos("No se han podido obtener la foto de perfil predeterminada");
            }
        }

    }
}
