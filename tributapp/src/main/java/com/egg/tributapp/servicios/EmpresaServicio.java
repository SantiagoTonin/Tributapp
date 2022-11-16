
package com.egg.tributapp.servicios;

import com.egg.tributapp.repositorio.PostRepositorio;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServicio {
    
    @Autowired
    private PostRepositorio postRepositorio;
    
    @Transactional
    public void registrarEmpresa (){
        
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
