package com.egg.tributapp.controladores;

import com.egg.tributapp.entidades.Desarrollador;
import com.egg.tributapp.servicios.DesarrolladorServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author marti
 */
@Controller
@RequestMapping("/")
public class PortalControlador {


    @Autowired
    private DesarrolladorServicio desarrolladorServicio;

    @GetMapping("/")
    public String index() {

        return "index.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos!");
        }

        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DESARROLLADOR', 'ROLE_EMPRESA', 'ROLE_CONTADOR')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {

        Desarrollador logueado=(Desarrollador) session.getAttribute("usuariosession");
        
        if (logueado.getRol().toString().equals("ADMIN")) {
            
            return "redirect:/admin/dashboard";
            
        }

        if (logueado.getRol().toString().equals("DESARROLLADOR")) {
            
            return "redirect:/desarrollador/inicio";
        }
        if (logueado.getRol().toString().equals("EMPRESA")) {
            
            return "redirect:/contador/inicio";
        }
        if (logueado.getRol().toString().equals("CONTADOR")) {
            
            return "redirect:/empresa/inicio";
        }
        
        return "index.html";

    }

    @GetMapping("/infogeneral")
    public String infoindex(){

        return "infoindex.html";
    }
}
