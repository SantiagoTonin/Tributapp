package com.egg.tributapp.servicios;

import com.egg.tributapp.entidades.Empresa;
import com.egg.tributapp.enumeraciones.Rol;
import com.egg.tributapp.repositorios.EmpresaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServicio {

    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Transactional
    public void registrarEmpresa(String razonSocial, String direccion, String nombre, String email, String password, String password2) throws Exception {

        validar(razonSocial, direccion, nombre, email, password, password2);

        Empresa empresa = new Empresa();

        empresa.setRazonSocial(razonSocial);
        empresa.setNombre(nombre);
        empresa.setDireccion(direccion);
        empresa.setEmail(email);
        empresa.setPassword(password);
        empresa.setPassword2(password2);
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
    public void eliminar(String id) {

        empresaRepositorio.deleteById(id);
    }

    public Empresa getone(String id) {

        return empresaRepositorio.getOne(id);
    }

    public void validar(String razonSocial, String direccion, String nombre, String email, String password, String password2) throws Exception {

        if (razonSocial.isEmpty() || razonSocial == null) {
            throw new Exception("la razon Social no puede ser nulo o vacio");
        }
        if (direccion.isEmpty() || direccion == null) {
            throw new Exception("la direccion no puede ser nula o vacia");
        }
        if (email.isEmpty() || email == null) {
            throw new Exception("el email no puede ser nulo o vacio");
        }
        if (nombre.isEmpty() || nombre == null) {
            throw new Exception("el nombre no puede ser nulo o vacio");
        }
         if (password.isEmpty()|| password==null ||password.length()<=5 ) {
               throw new Exception("el password no puede ser nulo o vacio o tener menos de 5 caracteres"); 
        }
        if (!password.equals(password2)) {
            throw new Exception("el password no coincide");
        }

    }
}
