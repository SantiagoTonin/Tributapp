/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.tributapp.repositorios;

import com.egg.tributapp.entidades.Empresa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepositorio extends JpaRepository<Empresa, String> {
    
    @Query("SELECT e FROM empresa e WHERE e.nombre LIKE %?1% ")
    public List<Empresa> findAll(String palabraClave);

}
