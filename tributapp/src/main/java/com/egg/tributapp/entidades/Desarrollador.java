package com.egg.tributapp.entidades;

import com.egg.tributapp.enumeraciones.Contratacion;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

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

    @Column
    private String cuitCuil;

    @Column
    private Double salario;

    @Column
    @OneToMany
    private List<Comentario> comentarios;
//    @ManyToOne
//    private Empresa empresa;
//
//    @ManyToOne
//    private Contador contador;
}
