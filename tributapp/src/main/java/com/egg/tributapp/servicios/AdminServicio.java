
package com.egg.tributapp.servicios;

import com.egg.tributapp.entidades.Admin;
import com.egg.tributapp.enumeraciones.Rol;
import com.egg.tributapp.repositorios.AdminRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminServicio {
    
    @Autowired
    private AdminRepositorio adminRepositorio;
    // CRUD
    
    @Transactional
    public void registrarAdmin (String name, String email, String password, String password2) throws Exception{
        
        validar(name, email, password, password2);
        
        Admin admin = new Admin();
        
        admin.setName(name);
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setRol(Rol.ADMIN);

        adminRepositorio.save(admin);

    }
    
    public List <Admin> listarAdmin(){
        
        List <Admin> admins = new ArrayList();
        admins = adminRepositorio.findAll();
        
        return admins;
    }
    
    @Transactional
    public void modificar (String id, String name, String email, String Password, String Password2) throws Exception{
        
        validar(name, email, Password, Password2);
        
        Optional<Admin> respuestaAdmin = adminRepositorio.findById(id);
        
        if (respuestaAdmin.isPresent()) {
            
            Admin admin = respuestaAdmin.get();
            
            admin.setName(name);
            admin.setEmail(email);
            admin.setPassword(Password);
            admin.setPassword2(Password2);
            
            adminRepositorio.save(admin);
            
        }
        
    }
    
    @Transactional 
    public void eliminar(String id){
        
        adminRepositorio.deleteById(id);
    }
     
    public Admin getOne (String id){
        
        return adminRepositorio.getOne(id);
    }
    
    
    public Admin buscarPorEmail (String email){
        
        Admin admin = adminRepositorio.buscarPorEmail(email);
        
        return admin;
    }
    
    
    public void validar(String name, String email, String Password, String Password2) throws Exception{
        
           if (name.isEmpty()|| name==null) {
            throw new Exception("el nombre no puede ser nulo o vacio");
        }
        if (email.isEmpty()|| email==null) {
            throw new Exception("el email no puede ser nulo o vacio");            
        }
        if (Password.isEmpty()|| Password==null ||Password.length()<=5 ) {
               throw new Exception("el password no puede ser nulo o vacio o tener menos de 5 caracteres"); 
        }
        if (!Password.equals(Password2)) {
            throw new Exception("el password no coincide");
        }
        
    }
    
}
