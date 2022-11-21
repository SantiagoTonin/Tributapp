/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.tributapp.enumeraciones;

import lombok.Getter;

/**
 *
 * @author marti
 */
@Getter
public enum Contratacion {
    FREELANCE("FREELANCE"),
    ENDEPENCIA("ENDEPENDECIA");


    private final String nameEnum;

    Contratacion(String nameEnum){
        this.nameEnum = nameEnum;
    }
}

