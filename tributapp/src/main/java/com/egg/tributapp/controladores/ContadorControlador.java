/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.tributapp.controladores;

import com.egg.tributapp.entidades.Contador;
import com.egg.tributapp.excepciones.MiException;
import com.egg.tributapp.servicios.ContadorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author Joel
 */

@Controller
@RequestMapping("/contador")
public class ContadorControlador {

    @Autowired
    private ContadorServicio contadorServicio;
    
    @GetMapping("/")
    public String index(){
        return "index.html";
    }
    
    @GetMapping("/cargarContador") 
    public String cargar(ModelMap modelo) {
                  
        return "CreateDev.html";
    }

    @PostMapping("/cargar")
    public String cargar(@RequestParam String name,
            @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, @RequestParam Integer telefono, @RequestParam Integer matricula, @RequestParam String provincia, ModelMap modelo) {
        try {
            contadorServicio.registrar(name, email, password, password2, telefono, matricula, provincia);
            

            modelo.put("exito", "El Contador/a fue cargado correctamente!");

        } catch (MiException ex) {
            

            
            modelo.put("error", ex.getMessage());

            return "CreateDev.html";  // volvemos a cargar el formulario.
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
            @RequestParam String password2, @RequestParam Integer telefono, @RequestParam Integer matricula, @RequestParam String provincia, ModelMap modelo) {
        try {
            contadorServicio.modificarContador(email, name, email, password, password2, telefono, matricula, provincia);
                                   
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
