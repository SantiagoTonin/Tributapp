/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.tributapp.repositorios;

import com.egg.tributapp.entidades.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author marti
 */
public interface ComentarioRepositorio extends JpaRepository<Comentario, String> {

//    "SELECT e FROM Editorial e where e.nombre LIKE :nombre"
    @Query("SELECT c FROM Comentario c WHERE e.nombre= :nombre")
    public Comentario buscarPorNombre(@Param("nombre") String nombre);

}
