package com.egg.tributapp.servicios;

import com.egg.tributapp.entidades.Empresa;
import com.egg.tributapp.enumeraciones.Rol;
import com.egg.tributapp.excepciones.MiException;
import com.egg.tributapp.repositorios.EmpresaRepositorio;
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

@Service
public class EmpresaServicio implements UserDetailsService {

    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Transactional
    public void registrarEmpresa(String razonSocial, String direccion, String nombre, String email, String password,
            String password2) throws Exception {

        validar(razonSocial, direccion, nombre, email, password, password2);

        Empresa empresa = new Empresa();

        empresa.setRazonSocial(razonSocial);
        empresa.setNombre(nombre);
        empresa.setDireccion(direccion);
        empresa.setEmail(email);
        empresa.setPassword(new BCryptPasswordEncoder().encode(password));
        
        empresa.setActivo(Boolean.TRUE);
        empresa.setAlta(new Date());
        empresa.setRol(Rol.EMPRESA);

        empresaRepositorio.save(empresa);

    }

    public List<Empresa> listarEmpresas() {

        List<Empresa> empresas = new ArrayList();
        empresas = empresaRepositorio.findAll();

        return empresas;
    }

    @Transactional
    public void modificar(String id, String razonSocial, String direccion, String nombre, String email, String password,
            String password2) throws Exception {

        validar(id, razonSocial, direccion, nombre, email, password, password2);

        Optional<Empresa> respuestaEmp = empresaRepositorio.findById(id);

        if (respuestaEmp.isPresent()) {

            Empresa empresa = respuestaEmp.get();

            empresa.setRazonSocial(razonSocial);
            empresa.setNombre(nombre);
            empresa.setDireccion(direccion);
            empresa.setEmail(email);

            empresaRepositorio.save(empresa);

        }

    }

    @Transactional
    public void eliminar(String id) {

        empresaRepositorio.deleteById(id);
    }

    public Empresa getOne(String id) {

        return empresaRepositorio.getOne(id);
    }

    public void validar(String razonSocial, String direccion, String nombre, String email, String password,
            String password2) throws Exception {
        List<Empresa> emails = empresaRepositorio.findAll();

        for (Empresa aux : emails) {

            if (aux.getEmail().equals(email)) {

                throw new MiException("El mail " + email + " ya se encuentra registrado");

            }
        }
        if (razonSocial.isEmpty() || razonSocial == null) {
            throw new MiException("la razon Social no puede ser nulo o vacio");
        }
        if (direccion.isEmpty() || direccion == null) {
            throw new MiException("la direccion no puede ser nula o vacia");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o vacio");
        }
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o vacio");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new Exception("el password no puede estar vacio o tener menos de 5 caracteres");
        }
        if (!password.equals(password2)) {
            throw new Exception("el password no coincide");
        }

    }

    @Transactional
    public List<Empresa> buscarEmpresaNombre(String param) throws Exception {

        try {
            List<Empresa> empresaName = empresaRepositorio.buscarEmpresaNombre(param);
            return empresaName;
        } catch (Exception e) {
            throw new Exception(e.getMessage());

        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Empresa empresa = empresaRepositorio.buscarPorEmail(email);
        if (empresa != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + empresa.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", empresa);
            return new User(empresa.getEmail(), empresa.getPassword(), permisos);
        } else {
            return null;
        }
    }

    private void validar(String id, String razonSocial, String direccion, String nombre, String email, String password, String password2) throws MiException {
        List<Empresa> emails = empresaRepositorio.findAll();

        Empresa empresa = empresaRepositorio.getOne(id);

        if (!empresa.getEmail().equals(email)) {
            for (Empresa aux : emails) {

                if (aux.getEmail().equals(email)) {

                    throw new MiException("El mail " + email + " ya se encuentra registrado");

                }
            }
            if (razonSocial.isEmpty() || razonSocial == null) {
                throw new MiException("la razon Social no puede ser nulo o vacio");
            }
            if (direccion.isEmpty() || direccion == null) {
                throw new MiException("la direccion no puede ser nula o vacia");
            }
            if (email.isEmpty() || email == null) {
                throw new MiException("el email no puede ser nulo o vacio");
            }
            if (nombre.isEmpty() || nombre == null) {
                throw new MiException("el nombre no puede ser nulo o vacio");
            }
            if (password.isEmpty() || password == null || password.length() <= 5) {
                throw new MiException("el password no puede estar vacio o tener menos de 5 caracteres");
            }
            if (!password.equals(password2)) {
                throw new MiException("el password no coincide");
            }

        }
    }
}
