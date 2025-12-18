/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author alejandro
 */
public class EntityExists extends Exception{

    public EntityExists() {
    }

    public EntityExists(String message) {
        super(message);
    }
    
    
}
