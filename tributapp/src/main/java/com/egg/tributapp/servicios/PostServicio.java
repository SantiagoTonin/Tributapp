package com.egg.tributapp.servicios;

import com.egg.tributapp.entidades.Post;
import com.egg.tributapp.repositorios.PostRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    
    public Post getOne (String id){
        return postRepositorio.getOne(id);
    }
    
    @Transactional
    public void modificar(String id, String titulo, String texto, MultipartFile foto) throws Exception {

        validar(titulo, texto);

        Optional<Post> respuestaPost = postRepositorio.findById(id);

        if (respuestaPost.isPresent()) {

            Post post = respuestaPost.get();

            post.setTitulo(titulo);
            post.setTexto(texto);
            post.setFoto(foto.getBytes());
            
            postRepositorio.save(post);

        }

    }
    
    @Transactional
    public void eliminar(String id) {
        postRepositorio.deleteById(id);
    }

    public void validar(String titulo, String texto) throws Exception {

        if (titulo.isEmpty() || titulo == null) {
            throw new Exception("el titulo no puede ser nulo o vacio");
        }
        if (texto.isEmpty() || texto == null) {
            throw new Exception("el texto no puede ser nulo o vacio");
        }
    }

}
