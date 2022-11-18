
package com.egg.tributapp.controladores;

import com.egg.tributapp.servicios.PostServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author eduar
 */

@Controller
@RequestMapping("/post")
public class PostContolador {
    
    @Autowired
    private PostServicio postServicio;
    
    
    
    
    
}
