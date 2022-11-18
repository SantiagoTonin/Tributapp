
package com.egg.tributapp.repositorios;


import com.egg.tributapp.entidades.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepositorio extends JpaRepository<Admin, String> {
 
<<<<<<< HEAD
    @Query("SELECT a FROM Admin a WHERE a.email = :email")
=======
    @Query("SELECT u FROM Admin u WHERE u.email = :email")
>>>>>>> 9d2463eee6b0248cab49e19fbe4c4838ad69558c
    public Admin buscarPorEmail(@Param("email") String email);
}
