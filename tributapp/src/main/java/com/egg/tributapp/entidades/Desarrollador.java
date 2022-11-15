package com.egg.tributapp.entidades;

import com.egg.tributapp.enumeraciones.Contratacion;
import com.egg.tributapp.enumeraciones.Rol;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

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

    private String cuit;

    private Double salario;

    
//    @ManyToOne
//    private Empresa empresa;
//
//    @ManyToOne
//    private Contador contador;
}
