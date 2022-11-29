package com.egg.tributapp.repositorios;

import com.egg.tributapp.entidades.Contador;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Joel
 */
@Repository
public interface ContadorRepositorio extends JpaRepository<Contador, String> {

    @Query("SELECT c FROM Contador c WHERE c.matricula LIKE %:matricula%")
    public Contador buscarPorMatricula(@Param("matricula") String matricula);

    @Query("SELECT c FROM Contador c WHERE c.provincia LIKE %:provincia%")
    public Contador buscarPorProvincia(@Param("provincia") String provincia);

    @Query(value = "SELECT * FROM Contador WHERE Contador.nombre LIKE %:param%", nativeQuery = true)
    List<Contador> buscarDesarrolladorNombre(@Param("param") String param);

    @Query(value = "SELECT * FROM Contador WHERE Contador.email LIKE %:param%", nativeQuery = true)
    List<Contador> buscarDesarrolladorEmail(@Param("param") String param);

    @Query(value = "SELECT * FROM Contador WHERE Contador.matricula LIKE %:param%", nativeQuery = true)
    List<Contador> buscarDesarrolladorMatricula(@Param("param") String param);

    @Query(value = "SELECT * FROM Contador WHERE Contador.provincia LIKE %:param%", nativeQuery = true)
    List<Contador> buscarDesarrolladorProvincia(@Param("param") String param);

}
