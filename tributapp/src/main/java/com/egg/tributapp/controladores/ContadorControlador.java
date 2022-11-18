
package com.egg.tributapp.controladores;


import com.egg.tributapp.entidades.Contador;
import com.egg.tributapp.excepciones.MiException;
import com.egg.tributapp.servicios.ContadorServicio;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;





@Controller
@RequestMapping("/contador")
public class ContadorControlador {

    @Autowired
    private ContadorServicio contadorServicio;
    
    
    
    @GetMapping("/cargarContador") 
    public String cargar(ModelMap modelo) {
                  
        return "CreateContador.html";
    }

    @PostMapping("/cargar")
    public String cargar(@RequestParam String name,
            @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, @RequestParam Integer telefono, @RequestParam Integer matricula, @RequestParam String provincia,MultipartFile foto, ModelMap modelo) throws IOException {
        try {
            contadorServicio.registrar(name, email, password, password2, telefono, matricula, provincia,foto);
            

            modelo.put("exito", "El Contador/a fue cargado correctamente!");

        } catch (MiException ex) {
            

            
            modelo.put("error", ex.getMessage());

            return "CreateContador.html";  // volvemos a cargar el formulario.
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Contador> contadores = contadorServicio.listarContadores();
        modelo.addAttribute("contadores", contadores);

        return "contador_list.html";
    }

    
    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
      
        modelo.put("contadores", contadorServicio.getOne(id));
                
        return "contador_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@RequestParam String name,
            @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, @RequestParam Integer telefono, @RequestParam Integer matricula, @RequestParam String provincia,MultipartFile foto, ModelMap modelo) throws IOException {
        try {
            contadorServicio.modificarContador(email, name, email, password, password2, telefono, matricula, provincia,foto);
                                   
            return "redirect:../lista";

        } catch (MiException ex) {
                       
            modelo.put("error", ex.getMessage());
            
            return "contador_modificar.html";
        }

    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) throws MiException {

        contadorServicio.eliminar(id);

        return "redirect:/contador/lista";
    }

}
