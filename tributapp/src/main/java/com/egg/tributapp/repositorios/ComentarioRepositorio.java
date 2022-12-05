/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.tributapp.repositorios;

import com.egg.tributapp.entidades.Comentario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author marti
 */
@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, String> {

    @Query(value = "SELECT * FROM Comentario c WHERE c.contador_id LIKE %:id%",nativeQuery = true)
    public List<Comentario> buscarId(@Param("id") String nombre);
    

    
}
