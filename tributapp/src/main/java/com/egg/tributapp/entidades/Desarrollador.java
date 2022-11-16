package com.egg.tributapp.entidades;

import com.egg.tributapp.enumeraciones.Contratacion;
import com.egg.tributapp.enumeraciones.Rol;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 *
 * @author martin
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Desarrollador extends Usuario {

    private Contratacion contratacion;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] foto;

    private String cuitCuil;

    private Double salario;

    @OneToMany
    private List<Comentario> comentarios;
//    @ManyToOne
//    private Empresa empresa;
//
//    @ManyToOne
//    private Contador contador;
}
