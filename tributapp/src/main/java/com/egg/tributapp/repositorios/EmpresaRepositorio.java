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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepositorio extends JpaRepository<Empresa, String> {

  @Query(value = "SELECT * FROM empresa WHERE empresa.nombre LIKE %:param%", nativeQuery = true)
  List<Empresa> buscarEmpresaNombre(@Param("param") String param);

  @Query("SELECT a FROM Empresa a WHERE a.email = :email")

  public Empresa buscarPorEmail(@Param("email") String email);
}
