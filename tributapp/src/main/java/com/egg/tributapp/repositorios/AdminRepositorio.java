
package com.egg.tributapp.repositorios;


import com.egg.tributapp.entidades.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepositorio extends JpaRepository<Admin, String> {
 
    @Query("SELECT u FROM Admin u WHERE u.email = :email")
    public Admin buscarPorEmail(@Param("email") String email);
}
