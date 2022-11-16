
package com.egg.tributapp.repositorio;

import com.egg.tributapp.entidades.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositorio extends JpaRepository<Post, String> {
    
}
