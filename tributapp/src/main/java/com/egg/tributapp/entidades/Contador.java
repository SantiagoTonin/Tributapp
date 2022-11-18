
package com.egg.tributapp.entidades;

import com.egg.tributapp.enumeraciones.Rol;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;




@Entity
@Getter @Setter

@NoArgsConstructor
@AllArgsConstructor
public class Contador extends Usuario {
   
   


   private Integer telefono;
   private Integer matricula;
   private String provincia;
   
   @Enumerated(EnumType.STRING)
   private Rol rol;
//   @OneToOne
//   private Imagen imagen;
}
