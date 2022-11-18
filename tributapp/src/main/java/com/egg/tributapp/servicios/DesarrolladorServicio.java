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
            String cuil, Double salario) throws MiException, IOException {

        validar(nombre, email, password, password2);

        Desarrollador desarrollador = new Desarrollador();

        desarrollador.setNombre(nombre);
        desarrollador.setEmail(email);
        desarrollador.setPassword(password);
        desarrollador.setPassword2(password2);
        desarrollador.setCuitCuil(cuil);
        desarrollador.setSalario(salario);

        desarrollador.setRol(Rol.DESARROLLADOR);
        desarrollador.setFoto(foto.getBytes());
        desarrollador.setContratacion(Contratacion.FREELANCE);
        desarrollador.setActivo(Boolean.TRUE);

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
    public void modificarDesarrollador(MultipartFile archivo, String idUsuario,
            String nombre, String email, String password,
            String password2, MultipartFile foto, String cuil, Double salario) throws MiException, IOException {

        validar(nombre, email, password, password2);

        Optional<Desarrollador> respuesta = desarrolladorRepositorio.findById(idUsuario);

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
            
            desarrollador.setSalario(salario);

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
