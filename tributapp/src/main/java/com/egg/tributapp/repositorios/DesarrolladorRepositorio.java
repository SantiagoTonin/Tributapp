package com.egg.tributapp.repositorios;

import com.egg.tributapp.entidades.Desarrollador;
import com.egg.tributapp.entidades.Empresa;
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
public interface DesarrolladorRepositorio extends JpaRepository<Desarrollador, String> {

//    @Query("SELECT d FROM Desarrollador d WHERE d.nombre= :nombre")
//    public Desarrollador buscarPorNombre(@Param("nombre") String nombre);
//
    @Query("SELECT d FROM Desarrollador d WHERE d.email= :email")
    public Desarrollador buscarPorEmail(@Param("email") String email);
    
    @Query(value = "SELECT * FROM Desarrollador WHERE Desarrollador.nombre LIKE %:param%",nativeQuery = true)
    List<Desarrollador> buscarDesarrolladorNombre(@Param("param") String param);

}

