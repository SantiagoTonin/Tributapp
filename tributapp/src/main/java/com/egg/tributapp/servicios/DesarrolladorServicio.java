package com.egg.tributapp.servicios;

import com.egg.tributapp.entidades.Desarrollador;
import com.egg.tributapp.enumeraciones.Contratacion;
import com.egg.tributapp.enumeraciones.Rol;
import com.egg.tributapp.excepciones.MiException;
import com.egg.tributapp.repositorios.DesarrolladorRepositorio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
    private DesarrolladorRepositorio desarrolladorRepositorio;

    @Transactional
    public void registrar(String nombre, String email, String password,
            String password2, MultipartFile foto,
<<<<<<< HEAD
            String cuil, Double salario) throws MiException, IOException {
=======
            String cuil,String contratacion) throws MiException, IOException {
>>>>>>> 56f3299d63178a9d3b235a66646ce926ea2f469d

        validar(nombre, email, password, password2,cuil);

        Desarrollador desarrollador = new Desarrollador();

        desarrollador.setNombre(nombre);
        desarrollador.setEmail(email);
        desarrollador.setPassword(password);
        desarrollador.setPassword2(password2);
<<<<<<< HEAD
        desarrollador.setCuitCuil(cuil);


        desarrollador.setRol(Rol.DESARROLLADOR);
        desarrollador.setFoto(foto.getBytes());
        desarrollador.setActivo(Boolean.TRUE);
=======
//        desarrollador.setCuitCuil(cuil);
//
//        desarrollador.setRol(Rol.DESARROLLADOR);
//        desarrollador.setFoto(foto.getBytes());
//        desarrollador.setContratacion(Contratacion.FREELANCE);
//        desarrollador.setActivo(Boolean.TRUE);
>>>>>>> origin/JuanGota

        desarrolladorRepositorio.save(desarrollador);
    }

    private void validar(String nombre, String email, String password, String password2,String cuil) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("*El nombre no puede ser nulo o estar vacio");
        }
        if (email.isEmpty() || nombre == null) {
            throw new MiException("*El email no puede ser nulo o estar vacio");

        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("*La contraseña no puede ser nulo o estar vacio o ser menor a 5 caracteres");
            
        }
        if (!password.equals(password2)) {
            throw new MiException("*Las contraseñas ingresadas deben ser iguales");
            
        }
        if (cuil.isEmpty() || nombre == null) {
            throw new MiException("*El cuil no puede ser nulo o estar vacio");

        }
    }

    @Transactional
    public void modificarDesarrollador(MultipartFile archivo, String id,
            String nombre, String email, String password,
            String password2, MultipartFile foto, String cuil) throws MiException, IOException {

        validar(nombre, email, password, password2, cuil);

        Optional<Desarrollador> respuesta = desarrolladorRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Desarrollador desarrollador = respuesta.get();

            desarrollador.setNombre(nombre);

            desarrollador.setEmail(email);
            //falta la dependencia security
//            desarrollador.setPassword(new BCryptPasswordEncoder().encode(password));
            desarrollador.setRol(Rol.DESARROLLADOR);

            desarrollador.setContratacion(Contratacion.FREELANCE);

            desarrollador.setFoto(foto.getBytes());
            desarrollador.setCuitCuil(cuil);
            
<<<<<<< HEAD
            desarrollador.setSalario(salario);
=======
>>>>>>> 56f3299d63178a9d3b235a66646ce926ea2f469d

            desarrolladorRepositorio.save(desarrollador);
        }

    }

    public Desarrollador getOne(String id) {
        return desarrolladorRepositorio.getOne(id);
    }

    @Transactional
    public void Eliminar(String id) {

        desarrolladorRepositorio.deleteById(id);

    }

    @Transactional
    public void softEliminar(String id) {

        Desarrollador desarrollador = desarrolladorRepositorio.getOne(id);

        desarrollador.setActivo(Boolean.FALSE);

    }

    @Transactional
    public void darDeAlta(String id) {

        Desarrollador desarrollador = desarrolladorRepositorio.getOne(id);

        desarrollador.setAlta(new Date());

        desarrollador.setActivo(Boolean.TRUE);

        desarrolladorRepositorio.save(desarrollador);

    }

    @Transactional()
    public List<Desarrollador> listarDesarrolladores() {

        List<Desarrollador> desarrolladores = new ArrayList();

        desarrolladores = desarrolladorRepositorio.findAll();

        return desarrolladores;
    }

    @Transactional()
    public Desarrollador buscarPorNombre(String nombre) {

        Desarrollador desarrollador = desarrolladorRepositorio.buscarPorNombre(nombre);

        return desarrollador;
    }

}
