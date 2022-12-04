package com.egg.tributapp.servicios;

import com.egg.tributapp.entidades.Comentario;
import com.egg.tributapp.entidades.Contador;
import com.egg.tributapp.enumeraciones.Rol;
import com.egg.tributapp.excepciones.MiException;
import com.egg.tributapp.repositorios.ContadorRepositorio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

@Service
public class ContadorServicio implements UserDetailsService {

    @Autowired
    private ContadorRepositorio contadorRepositorio;

    @Autowired
    private ComentarioServicio comentarioServicio;

    @Transactional
    public void registrar(String nombre, String email, String password, String password2, String telefono,
            String matricula, String provincia, MultipartFile foto) throws MiException, IOException {

        validar(nombre, email, password, password2, telefono, matricula, provincia);

        Contador contador = new Contador();

        contador.setNombre(nombre);
        contador.setEmail(email);
        contador.setPassword(new BCryptPasswordEncoder().encode(password));
        // contador.setPassword2(password2);
        contador.setTelefono(telefono);
        contador.setMatricula(matricula);
        contador.setProvincia(provincia);
        contador.setRol(Rol.CONTADOR);
        contador.setFoto(foto.getBytes());

        // Imagen imagen = imagenServicio.guardar(archivo);
        // contador.setImagen(imagen);
        contadorRepositorio.save(contador);
    }

    @Transactional
    public List<Contador> listarContadores() {

        List<Contador> contadores = new ArrayList();

        contadores = contadorRepositorio.findAll();

        return contadores;
    }

    public HashMap<String,List<Comentario>> contadoresComentarios() {
        // HashMap<String,List<Comentario>>
        List<Contador> contador = listarContadores();
        HashMap<String,List<Comentario>> comentarios = new HashMap<String,List<Comentario>>();

        

        for (Contador contador2 : contador) {

            comentarios.put(contador2.getId(),comentarioServicio.listarComentario(contador2.getId()));        
        }


        // for (int i = 0; i < contador.size(); i++) {
            
        //     List<Comentario> p = comentarios.get(contador.get(i).getId());
        //     for (Comentario comentario : p) {
                
        //         System.out.println(comentario.getTexto());
        //     }
        //     System.out.println("----------------");
        // }

        
        return comentarios;
    }

    @Transactional
    public void modificarContador(String id, String nombre, String email, String password, String password2,
            String telefono, String matricula, String provincia, MultipartFile foto) throws MiException, IOException {

        validar(nombre, email, password, password2, telefono, matricula, provincia);

        Optional<Contador> respuesta = contadorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Contador contador = respuesta.get();
            contador.setNombre(nombre);
            contador.setEmail(email);
            contador.setPassword(new BCryptPasswordEncoder().encode(password));
            contador.setRol(Rol.CONTADOR);
            contador.setTelefono(telefono);
            contador.setMatricula(matricula);
            contador.setProvincia(provincia);
            contador.setFoto(foto.getBytes());

            contadorRepositorio.save(contador);
        }
    }

    public Contador getOne(String id) {
        return contadorRepositorio.getOne(id);
    }

    private void validar(String nombre, String email, String password, String password2, String telefono,
            String matricula, String provincia) throws MiException {

        List<Contador> emails = contadorRepositorio.findAll();

        for (Contador aux : emails) {

            if (aux.getEmail().equals(email)) {

                throw new MiException("El mail " + email + " ya se encuentra registrado");

            }
        }
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacio");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("email no puede ser nulo");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }

        if (telefono.isEmpty() || telefono == null) {
            throw new MiException("El campo telefono no puede estar vacío");
        }
        if (matricula.isEmpty() || matricula == null) {
            throw new MiException("El campo matricula no puede estar vacío");
        }
        if (provincia.isEmpty() || provincia == null) {
            throw new MiException("El campo Provincia no puede ser nulo");
        }
    }

    public void eliminar(String id) throws MiException {

        Contador contadores = contadorRepositorio.getById(id);

        contadorRepositorio.delete(contadores);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Contador contador = contadorRepositorio.buscarPorEmail(email);

        if (contador != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + contador.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", contador);

            return new User(contador.getEmail(), contador.getPassword(), permisos);

        } else {

            return null;

        }
    }

    private void validar(String id, String nombre, String email, String password, String password2, String telefono,
            String matricula, String provincia) throws MiException {
        List<Contador> emails = contadorRepositorio.findAll();

        Contador contador = contadorRepositorio.getOne(id);
        if (!contador.getEmail().equals(email)) {
            for (Contador aux : emails) {
                if (aux.getEmail().equals(email)) {
                    throw new MiException("El mail " + email + " ya se encuentra registrado");
                }
            }
            if (nombre.isEmpty() || nombre == null) {
                throw new MiException("el nombre no puede ser nulo o estar vacio");
            }
            if (email.isEmpty() || email == null) {
                throw new MiException("email no puede ser nulo");
            }
            if (password.isEmpty() || password == null || password.length() <= 5) {
                throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
            }
            if (!password.equals(password2)) {
                throw new MiException("Las contraseñas ingresadas deben ser iguales");
            }

            if (telefono.isEmpty() || telefono == null) {
                throw new MiException("El campo telefono no puede estar vacío");
            }
            if (matricula.isEmpty() || matricula == null) {
                throw new MiException("El campo matricula no puede estar vacío");
            }
            if (provincia.isEmpty() || provincia == null) {
                throw new MiException("El campo Provincia no puede ser nulo");
            }
        }
    }

    @Transactional
    public List<Contador> buscarContadorNombre(String param) throws Exception {

        try {

            List<Contador> contName = contadorRepositorio.buscarContadorNombre(param);

            return contName;

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    @Transactional
    public List<Contador> buscarContadorProvincia(String param) throws Exception {

        try {

            List<Contador> contProv = contadorRepositorio.buscarContadorProvincia(param);

            return contProv;

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    @Transactional
    public List<Contador> buscarContadorEmail(String param) throws Exception {

        try {

            List<Contador> contEmail = contadorRepositorio.buscarContadorEmail(param);

            return contEmail;

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    @Transactional
    public List<Contador> buscarContadorMatricula(String param) throws Exception {

        try {

            List<Contador> contMatr = contadorRepositorio.buscarContadorMatricula(param);

            return contMatr;

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }
}
