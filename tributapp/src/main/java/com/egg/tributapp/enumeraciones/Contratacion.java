/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.tributapp.enumeraciones;

/**
 *
 * @author marti
 */
public enum Contratacion {
    FREELANCE("freelance"),
    ENDEPENCIA("endependecia");

    private final String nameEnum;

    Contratacion(String nameEnum){
        this.nameEnum = nameEnum;
    }
}
