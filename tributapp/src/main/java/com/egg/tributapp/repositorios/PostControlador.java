
package com.egg.tributapp.repositorios;

import com.egg.tributapp.entidades.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author eduar
 */
public interface PostControlador extends JpaRepository<Post, String> {
    
}
