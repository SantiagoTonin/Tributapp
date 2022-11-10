package com.egg.tributapp.servicios;

import com.egg.tributapp.entidades.Desarrollador;
import com.egg.tributapp.excepciones.MiException;
import com.egg.tributapp.repositorios.DesarrolladorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/*
 * @author martin
 */
@Service
public class DesarrolladorServicio {

    @Autowired
    DesarrolladorRepositorio desarrolladorRepositorio;

    @Transactional
    public void registrar(String nombre, String email, String password,
            String password2, String contratacion) throws MiException {

        validar(nombre, email, password, password2);

        Desarrollador desarrollador = new Desarrollador();

        desarrollador.setNombre(nombre);
        desarrollador.setEmail(email);
        desarrollador.setPassword(password);
        desarrollador.setPassword2(password2);

//        desarrollador.setRol(Rol.DESARROLLADOR);
        desarrolladorRepositorio.save(desarrollador);
    }

    private void validar(String nombre, String email, String password, String password2) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacio");
        }
        if (email.isEmpty() || nombre == null) {
            throw new MiException("el email no puede ser nulo o estar vacio");

        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("la contraseña no puede ser nulo o estar vacio");

        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");

        }
    }

    @Transactional
    public void actualizar(MultipartFile archivo, String idUsuario, String nombre, String email, String password, String password2) throws MiException {

        validar(nombre, email, password, password2);

        Optional<Desarrollador> respuesta = desarrolladorRepositorio.findById(idUsuario);
        if (respuesta.isPresent()) {

            Desarrollador desarrollador = respuesta.get();
            desarrollador.setNombre(nombre);
            desarrollador.setEmail(email);

//            desarrollador.setPassword(new BCryptPasswordEncoder().encode(password));
//            desarrollador.setRol(Rol.USER);
            String idImagen = null;

//            if (desarrollador.getImagen() != null) {
//                idImagen = desarrollador.getImagen().getId();
//            }
//            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
//            desarrollador.setImagen(imagen);
            desarrolladorRepositorio.save(desarrollador);
        }

    }

    public Desarrollador getOne(String id) {
        return desarrolladorRepositorio.getOne(id);
    }

    public void Eliminar(Desarrollador desarrolador) {

        desarrolladorRepositorio.delete(desarrolador);

    }

    @Transactional()
    public List<Desarrollador> listarDesarrolladores() {

        List<Desarrollador> desarrolladores = new ArrayList();

        desarrolladores = desarrolladorRepositorio.findAll();

        return desarrolladores;
    }

    @Transactional()
    public Desarrollador buscarPorEmail(String email) {

        Desarrollador desarrollador = desarrolladorRepositorio.buscarPorEmail(email);

        return desarrollador;
    }

}
