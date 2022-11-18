
package com.egg.tributapp.repositorios;

import com.egg.tributapp.entidades.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositorio extends JpaRepository<Post, String> {
    
}
