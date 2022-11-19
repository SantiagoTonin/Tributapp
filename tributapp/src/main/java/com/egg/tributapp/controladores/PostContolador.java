package com.egg.tributapp.controladores;

import com.egg.tributapp.entidades.Post;
import com.egg.tributapp.excepciones.MiException;
import com.egg.tributapp.servicios.PostServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author eduar
 */
@Controller
@RequestMapping("/post")
public class PostContolador {

    @Autowired
    private PostServicio postServicio;

    @GetMapping("/cargarPost")
    public String cargarPost(ModelMap modelo) {
        return "post_cargar";
    }

    @PostMapping("/cargoPost")
    public String cargar(@RequestParam String titulo,
            @RequestParam String texto, MultipartFile foto,
            ModelMap modelo) throws Exception {

        try {

            postServicio.registrarPost(titulo, texto, foto);

            modelo.put("Exito", "desarrollador fue cargado exitosamente");

        } catch (MiException ex) {

            modelo.put("Error", ex.getMessage());

            return "index";

        }
        return "post_cargar";
    }

    @GetMapping("/listarPost")
    public String listarPost(ModelMap modelo) {

        List<Post> posts = postServicio.listarPost();

        modelo.addAttribute("posts", posts);

        return "post_listar";

    }

    @GetMapping("/modificarPost/{id}")
    public String modificarPost(@PathVariable String id, ModelMap modelo) {

        modelo.put("post", postServicio.getOne(id));

        return "post_eliminar";

    }

    @PostMapping("/modificoPost/{id}")
    public String modificoEmpresa(@PathVariable String id, String tiitulo,
            String texto, MultipartFile foto, ModelMap modelo) {

        try {

            postServicio.modificar(id, tiitulo, texto, foto);

            return ("redirect:..//listarPost");

        } catch (Exception ex) {

            modelo.put("error", ex.getMessage());

            return "post_eliminar";
        }
    }

    
    @DeleteMapping("/eliminarPost/{id}")
    public String eliminarPost(@PathVariable String id, ModelMap modelo) {

        try {
            postServicio.eliminar(id);

            return ("redirect:..//listarPost");

        } catch (Exception ex) {

            modelo.put("error", ex.getMessage());

            return "eliminar_post";
        }
    }
}
