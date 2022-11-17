package com.egg.tributapp.servicios;

import com.egg.tributapp.entidades.Post;
import com.egg.tributapp.repositorios.PostRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PostServicio {

    @Autowired
    private PostRepositorio postRepositorio;

    @Transactional
    public void registrarPost(String titulo, String texto, MultipartFile foto) throws Exception {

        validar(titulo, texto);

        Post post = new Post();

        post.setTitulo(titulo);
        post.setTexto(texto);
        post.setFoto(foto.getBytes());

        postRepositorio.save(post);
    }

    public List<Post> listarPost() {

        List<Post> posts = new ArrayList();
        posts = postRepositorio.findAll();
        return posts;
    }

    @Transactional
    public void eliminar(String id) {
        postRepositorio.deleteById(id);
    }

    public void validar(String titulo, String texto) throws Exception {

        if (titulo.isEmpty() || titulo == null) {
            throw new Exception("la razon Social no puede ser nulo o vacio");
        }
        if (texto.isEmpty() || texto == null) {
            throw new Exception("la direccion no puede ser nulo o vacio");
        }
    }

}
