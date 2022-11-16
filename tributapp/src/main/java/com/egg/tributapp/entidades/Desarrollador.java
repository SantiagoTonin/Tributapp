package com.egg.tributapp.entidades;

import com.egg.tributapp.enumeraciones.Contratacion;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
