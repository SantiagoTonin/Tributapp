/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.tributapp.servicios;

import com.egg.tributapp.entidades.Contador;
import com.egg.tributapp.enumeraciones.Rol;
import com.egg.tributapp.excepciones.MiException;
import com.egg.tributapp.repositorios.ContadorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 *
 * @author Joel
 */
@Service
public class ContadorServicio {
    @Autowired
    private ContadorRepositorio contadorRepositorio;
    
    @Transactional
    public void registrar(String name, String email, String password, String password2, Integer telefono, Integer matricula, String provincia) throws MiException{
        
        validar(name, email, password, password2, telefono, matricula, provincia);
        
        Contador contador = new Contador();

        contador.setName(name);
        contador.setEmail(email);

        contador.setPassword(password);

        contador.setRol(Rol.CONTADOR);
        
        //Imagen imagen = imagenServicio.guardar(archivo);

        //contador.setImagen(imagen);
        
        contadorRepositorio.save(contador);
    }
    @Transactional
    public List<Contador> listarContadores() {

        List<Contador> contadores = new ArrayList();

        contadores = contadorRepositorio.findAll();

        return contadores;
    }
    
    @Transactional
    public void modificarContador(String id, String name, String email, String password, String password2, Integer telefono, Integer matricula, String provincia) throws MiException{
        
        validar(name, email, password, password2, telefono, matricula, provincia);
        
        Optional<Contador> respuesta = contadorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Contador contador = respuesta.get();
            contador.setName(name);
            contador.setEmail(email);

            contador.setPassword(password);

            contador.setRol(Rol.CONTADOR);
            
            //String idImagen = null;
            
//            if (contador.getImagen() != null) {
//                idImagen = contador.getImagen().getId();
//            }
            
//            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
//            
//            usuario.setImagen(imagen);
            
            contadorRepositorio.save(contador);
        }
    }
    public Contador getOne(String id){
        return contadorRepositorio.getOne(id);
    }
    
    
    
    private void validar(String name, String email, String password, String password2, Integer telefono, Integer matricula, String provincia) throws MiException{
       
        
        if(name.isEmpty() || name == null){
            throw new MiException("el nombre no puede ser nulo o estar vacio");
        }
        if(email.isEmpty() || email == null){
            throw new MiException("email no puede ser nulo");
        }
        if(password.isEmpty() || password == null || password.length() <=5){
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
        
        if(telefono == null){
            throw new MiException("El campo telefono no puede estar vacío");
        }
        if(matricula == null){
            throw new MiException("El campo matricula no puede estar vacío");
        }
         if(provincia.isEmpty() || provincia == null){
            throw new MiException("El campo Provincia no puede ser nulo");
        }
    }
    
   
    public void eliminar(String id) throws MiException {

        Contador contadores = contadorRepositorio.getById(id);

        contadorRepositorio.delete(contadores);

    }

}
