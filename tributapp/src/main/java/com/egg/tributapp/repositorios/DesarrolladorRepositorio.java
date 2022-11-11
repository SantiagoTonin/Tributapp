package com.egg.tributapp.repositorios;

import com.egg.tributapp.entidades.Desarrollador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author marti
 */
@Repository
public interface DesarrolladorRepositorio extends JpaRepository<Desarrollador, String> {

    @Query("SELECT u FROM Usuario u WHERE u.email= :email")
    public Desarrollador buscarPorEmail(@Param("email") String email);
    
}
