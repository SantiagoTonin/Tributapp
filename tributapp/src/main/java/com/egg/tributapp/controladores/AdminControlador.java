package com.egg.tributapp.controladores;

import com.egg.tributapp.entidades.Admin;
import com.egg.tributapp.servicios.AdminServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private AdminServicio adminServicio;

    @GetMapping("/registrarAdmin")
    public String registarAdmin(ModelMap modelo) {
        return "admin_form";
    }

    @PostMapping("/registroAdmin")
    public String registroAdmin(@RequestParam String name, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, ModelMap modelo) throws Exception {

        validar(name, email, password, password2);

        try {
            adminServicio.registrarAdmin(name, email, password, password2);

            modelo.put("exito", "Admin registrado exitosamente");
        } catch (Exception ex) {

            modelo.put("error", ex.getMessage());

            return "index";
        }

        return "admin_form";
    }

    @GetMapping("/listarAdmin")
    public String listarAdmin(ModelMap modelo) {

        List<Admin> admins = adminServicio.listarAdmin();

        modelo.addAttribute("admins", admins);

        return "admin_list";

    }

    @GetMapping("/modificarAdmin/{id}")
    public String modificarAdmin(@PathVariable String id, ModelMap modelo) {

        modelo.put("admin", adminServicio.getOne(id));

        return "modificar_admin";

    }

    @PostMapping("/modificoAdmin/{id}")
    public String modificoAdmin(@PathVariable String id, String name, String email,
            String password, String password2, ModelMap modelo) {
        try {

            adminServicio.modificar(id, name, email, password, password2);

            return ("redirect:..//listarAdmin");
        } catch (Exception ex) {

            modelo.put("error", ex.getMessage());

            return "modificar_admin";
        }
    }
    
  
    @DeleteMapping ("/eliminarAdmin/{id}")
    public String eliminarAdmin(@PathVariable String id, ModelMap modelo) {

        try {

            adminServicio.eliminar(id);

            return ("redirect:..//listarAdmin");

        } catch (Exception ex) {

            modelo.put("error", ex.getMessage());

            return "eliminar_admin";

        }

    }

    public void validar(String name, String email, String password, String password2) throws Exception {

        if (name.isEmpty() || name == null) {
            throw new Exception("el nombre no puede ser nulo o vacio");
        }
        if (email.isEmpty() || email == null) {
            throw new Exception("el email no puede ser nulo o vacio");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new Exception("el password no puede ser nulo o vacio o tener menos de 5 caracteres");
        }
        if (!password.equals(password2)) {
            throw new Exception("el password no coincide");
        }

    }

}
