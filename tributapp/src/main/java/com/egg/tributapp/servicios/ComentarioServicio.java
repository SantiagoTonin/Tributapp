package com.egg.tributapp.servicios;

import com.egg.tributapp.entidades.Comentario;

import com.egg.tributapp.excepciones.MiException;
import com.egg.tributapp.repositorios.ComentarioRepositorio;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author marti
 */
@Service
public class ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Transactional
    public void nuevoComentario(String texto) throws MiException {

        validar(texto);

        Comentario comentario = new Comentario();

        comentario.setTexto(texto);
        comentario.setFecha(new Date());
        comentarioRepositorio.save(comentario);

    }

    public void validar(String texto) throws MiException {

        if (texto.isEmpty() || texto == null) {

            throw new MiException("El texto no puede ser nulo o estar vacio");

        }
    }

    @Transactional
    public void eliminarComentario(String id) {

        comentarioRepositorio.deleteById(id);

    }

    @Transactional
    public List<Comentario> listarComentarios() {

        List<Comentario> comentarios = new ArrayList();

        comentarios = comentarioRepositorio.findAll();

        Collections.sort(comentarios, Comparadores.ordenAsc);

        return comentarios;
    }

    // @Transactional
    // p ublic List<Comentario> listarComentariosPorNombre(Desarrollador
    // desarrollador) {
    //
    // String nombre = desarrollador.getNombre();
    // List<Comentario> comentarios = (List<Comentario>)
    // comentarioRepositorio.buscarPorNombre(nombre);
    //
    // return comentarios;
    // }
}
