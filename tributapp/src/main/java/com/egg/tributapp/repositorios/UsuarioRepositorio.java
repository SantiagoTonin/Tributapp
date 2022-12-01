/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.tributapp.repositorios;


import com.egg.tributapp.entidades.Contador;
import com.egg.tributapp.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Joel
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query(value="SELECT * FROM Usuario  WHERE email  = :email", nativeQuery = true)
    public Usuario buscarPorEmail(@Param("email")String email);
    
   
    
    
   
    
}
