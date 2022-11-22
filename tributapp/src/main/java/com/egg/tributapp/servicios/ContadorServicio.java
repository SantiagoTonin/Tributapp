
package com.egg.tributapp.servicios;


import com.egg.tributapp.entidades.Contador;
import com.egg.tributapp.enumeraciones.Rol;
import com.egg.tributapp.excepciones.MiException;
import com.egg.tributapp.repositorios.ContadorRepositorio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;






@Service
public class ContadorServicio {
    @Autowired
    private ContadorRepositorio contadorRepositorio;
    
    @Transactional
    public void registrar(String nombre, String email, String password, String password2, Integer telefono, Integer matricula, String provincia,MultipartFile foto) throws MiException, IOException{
        
        validar(nombre, email, password, password2, telefono, matricula, provincia);
        
        Contador contador = new Contador();

        contador.setNombre(nombre);
        contador.setEmail(email);
        contador.setPassword(password);
        contador.setPassword2(password2);
        contador.setTelefono(telefono);
        contador.setMatricula(matricula);
        contador.setProvincia(provincia);
        contador.setRol(Rol.CONTADOR);
        contador.setFoto(foto.getBytes());
        
        
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
    public void modificarContador(String id,String nombre, String email, String password, String password2, Integer telefono, Integer matricula, String provincia,MultipartFile foto) throws MiException, IOException{
        
        validar(nombre, email, password, password2, telefono, matricula, provincia);
        
        Optional<Contador> respuesta = contadorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Contador contador = respuesta.get();
            contador.setNombre(nombre);
            contador.setEmail(email);
            contador.setPassword(password);
            contador.setRol(Rol.CONTADOR);
            contador.setTelefono(telefono);
            contador.setMatricula(matricula);
            contador.setProvincia(provincia);
            contador.setFoto(foto.getBytes());
                        
            contadorRepositorio.save(contador);
        }
    }
    public Contador getOne(String id){
        return contadorRepositorio.getOne(id);
    }
    
    
    
    private void validar(String nombre, String email, String password, String password2, Integer telefono, Integer matricula, String provincia) throws MiException{
       
        
        if(nombre.isEmpty() || nombre == null){
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