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

    @Query(value = "SELECT * FROM Contador WHERE Contador.nombre LIKE %:param%", nativeQuery = true)
    List<Contador> buscarContadorNombre(@Param("param") String param);

    @Query(value = "SELECT * FROM Contador WHERE Contador.email LIKE %:param%", nativeQuery = true)
    List<Contador> buscarContadorEmail(@Param("param") String param);

    @Query(value = "SELECT * FROM Contador WHERE Contador.matricula LIKE %:param%", nativeQuery = true)
    List<Contador> buscarContadorMatricula(@Param("param") String param);

    @Query(value = "SELECT * FROM Contador WHERE Contador.provincia LIKE %:param%", nativeQuery = true)
    List<Contador> buscarContadorProvincia(@Param("param") String param);
}
