package com.egg.tributapp.entidades;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author martin
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor()
public class Desarrollador {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String email;
    private String password;
    private String password2;
    private String contratacion;
//    private Imagen imagen;
//    private Rol rol;
    private Boolean alta;

    public Desarrollador() {

    }

    

}
