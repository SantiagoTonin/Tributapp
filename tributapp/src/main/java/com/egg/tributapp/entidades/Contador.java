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
<<<<<<< HEAD

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

=======
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
>>>>>>> ec8bcf2a75c42995dfd99a7c46e67856b3d9af38

/**
 *
 * @author Joel
 */
@Entity
@Getter @Setter
<<<<<<< HEAD
@NoArgsConstructor
@AllArgsConstructor
public class Contador extends Usuario {
   
   
=======
public class Contador {
   @Id
   @GeneratedValue(generator = "uuid")
   @GenericGenerator(name = "uuid", strategy = "uuid2")
   private String id;
   private String name;
   private String email;
   private String password;
   private String password2;
>>>>>>> ec8bcf2a75c42995dfd99a7c46e67856b3d9af38
   private Integer telefono;
   private Integer matricula;
   private String provincia;
   
   @Enumerated(EnumType.STRING)
   private Rol rol;
//   @OneToOne
//   private Imagen imagen;
}
