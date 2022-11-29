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
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/*
 * @author martin
 */
@Service
public class DesarrolladorServicio implements UserDetailsService {

    @Autowired
    private DesarrolladorRepositorio desarrolladorRepositorio;

    //registro con validacion
    @Transactional
    public void registrar(String nombre, String email, String password,
            String password2, String contratacion, MultipartFile foto,
            String cuil) throws MiException, IOException {
        validar(nombre, email, password, password2, contratacion, foto, cuil);

        Desarrollador desarrollador = new Desarrollador();

        desarrollador.setNombre(nombre);
        desarrollador.setEmail(email);
        desarrollador.setPassword(new BCryptPasswordEncoder().encode(password));
        desarrollador.setCuitCuil(cuil);
        desarrollador.setRol(Rol.DESARROLLADOR);
        desarrollador.setFoto(foto.getBytes());

        if (contratacion.equals("1")) {

            desarrollador.setContratacion(Contratacion.FREELANCE.getNameEnum());
        }
        if (contratacion.equals("2")) {

            System.out.println("llegue aqui!!!!!!!!!!");
            desarrollador.setContratacion(Contratacion.ENDEPENCIA.getNameEnum());
        }
        desarrollador.setActivo(Boolean.TRUE);

        desarrolladorRepositorio.save(desarrollador);
    }

    private void validar(String nombre, String email, String password, String password2, String contratacion, MultipartFile foto, String cuil) throws MiException {

        List<Desarrollador> emails = desarrolladorRepositorio.findAll();

        for (Desarrollador aux : emails) {

            if (aux.getEmail().equals(email)) {

                throw new MiException("El mail " + email + " ya se encuentra registrado");

            }
        }
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacio");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o estar vacio");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("la contraseña no puede estar vacia y debe tener mas de 5 digitos");

        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");

        }
        if (contratacion == null) {
            throw new MiException("Debe seleccionar una de las opciones");
        }
        if (foto.isEmpty()) {
            throw new MiException("Debe colocar una foto");

        }
        if (cuil.isEmpty() || cuil == null) {

            throw new MiException("Debe ingresar el cuit o cuil");

        }
    }

    //actualizacion desarrollador con validacion
    @Transactional
    public void modificarDesarrollador(String idUsuario,
            String nombre, String email, String password, String contratacion,
            String password2, MultipartFile foto, String cuil) throws MiException, IOException {
        validar(idUsuario, nombre, email, password, password2, contratacion, foto, cuil);

        Optional<Desarrollador> respuesta = desarrolladorRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {

            Desarrollador desarrollador = respuesta.get();

            desarrollador.setNombre(nombre);

            desarrollador.setEmail(email);
            //falta la dependencia security
//            desarrollador.setPassword(new BCryptPasswordEncoder().encode(password));
            desarrollador.setRol(Rol.DESARROLLADOR);

            if (contratacion.equals("1")) {

                desarrollador.setContratacion(Contratacion.FREELANCE.getNameEnum());
            }
            if (contratacion.equals("2")) {

                desarrollador.setContratacion(Contratacion.ENDEPENCIA.getNameEnum());
            }

            desarrollador.setFoto(foto.getBytes());
            desarrollador.setCuitCuil(cuil);
            desarrolladorRepositorio.save(desarrollador);
        }

    }

    //metodo getOne para encontrar un desarrollador por su id
    public Desarrollador getOne(String id) {
        return desarrolladorRepositorio.getOne(id);
    }

    //metodo eliminar
    @Transactional
    public void Eliminar(String id) {

        desarrolladorRepositorio.deleteById(id);

    }

    //soft delete para q al desactivar al usuario, no sean eliminados sus datos de la BD
    @Transactional
    public void softEliminar(String id) {

        Desarrollador desarrollador = desarrolladorRepositorio.getOne(id);

        desarrollador.setActivo(Boolean.FALSE);

    }

    //metodo para dar de alta donde se guarda la fecha de alta
    @Transactional
    public void darDeAlta(String id) {

        Desarrollador desarrollador = desarrolladorRepositorio.getOne(id);

        desarrollador.setAlta(new Date());

        desarrollador.setActivo(Boolean.TRUE);

        desarrolladorRepositorio.save(desarrollador);

    }

//    metodo leer o listar
    @Transactional()
    public List<Desarrollador> listarDesarrolladores() {

        List<Desarrollador> desarrolladores = new ArrayList();

        desarrolladores = desarrolladorRepositorio.findAll();

        return desarrolladores;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Desarrollador desarrollador = desarrolladorRepositorio.buscarPorEmail(email);

        if (desarrollador != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + desarrollador.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", desarrollador);

            return new User(desarrollador.getEmail(), desarrollador.getPassword(), permisos);

        } else {

            return null;

        }
    }

    @Transactional
    public List<Desarrollador> buscarDesarrolladorNombre(String param) throws Exception {

        try {

            List<Desarrollador> devName = desarrolladorRepositorio.buscarDesarrolladorNombre(param);

            return devName;

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    private void validar(String idUsuario, String nombre, String email, String password, String password2, String contratacion, MultipartFile foto, String cuil) throws MiException {
        List<Desarrollador> emails = desarrolladorRepositorio.findAll();

        Desarrollador desarrollador = desarrolladorRepositorio.getOne(idUsuario);
        if (!desarrollador.getEmail().equals(email)) {
            for (Desarrollador aux : emails) {

                if (aux.getEmail().equals(email)) {

                    throw new MiException("El mail " + email + " ya se encuentra registrado");

                }
            }
        }
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacio");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o estar vacio");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("la contraseña no puede estar vacia y debe tener mas de 5 digitos");

        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");

        }
        if (contratacion == null) {
            throw new MiException("Debe seleccionar una de las opciones");
        }
        if (foto.isEmpty()) {
            throw new MiException("Debe colocar una foto");

        }
        if (cuil.isEmpty() || cuil == null) {

            throw new MiException("Debe ingresar el cuit o cuil");

        }
    }
}
