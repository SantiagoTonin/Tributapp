/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.tributapp.repositorios;

import com.egg.tributapp.entidades.Contador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Joel
 */
@Repository
public interface ContadorRepositorio extends JpaRepository<Contador, String>{
    @Query("SELECT u FROM Contador u WHERE u.email = :email")
    public Contador buscarPorEmail(@Param("email")String email);
}
