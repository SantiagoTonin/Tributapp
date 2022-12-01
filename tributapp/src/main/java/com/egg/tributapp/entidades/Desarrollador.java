package com.egg.tributapp.entidades;

import com.egg.tributapp.enumeraciones.Contratacion;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

    private String contratacion;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] foto;

    @Column
    private String cuitCuil;

    @Column
    private Double salario;

//    @Column
//    @OneToMany
//    private List<Comentario> comentarios;
    
//    get mapping de la lista de empresas en el controlador de desarrollador (/impuestos) desarrolladorImp
//    post mapping que reciba datos de empresa (nombre y email de la empresa) desarrolladorImp para crear una empresa    
//    como evitar el nulo, llenarlos, variables telefono desde el post
    @OneToOne
    private Empresa empresa;

}
