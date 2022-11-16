/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.tributapp.entidades;

import com.egg.tributapp.enumeraciones.Rol;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 *
 * @author Joel
 */
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contador extends Usuario {
   
   aaa
   private Integer telefono;
   private Integer matricula;
   private String provincia;
   
   @Enumerated(EnumType.STRING)
   private Rol rol;
//   @OneToOne
//   private Imagen imagen;
}
