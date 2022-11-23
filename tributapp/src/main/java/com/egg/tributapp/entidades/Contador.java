
package com.egg.tributapp.entidades;

import com.egg.tributapp.enumeraciones.Rol;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;




@Entity
@Getter @Setter

@NoArgsConstructor
@AllArgsConstructor
public class Contador extends Usuario {
   private String telefono;
   private String matricula;
   private String provincia;
   
    @Enumerated(EnumType.STRING)
    private Rol rol;
    
   @Lob
   @Basic(fetch = FetchType.LAZY)
   private byte[] foto;
}
