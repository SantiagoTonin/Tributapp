package com.egg.tributapp.controladores;

import com.egg.tributapp.entidades.Desarrollador;
import com.egg.tributapp.excepciones.MiException;
import com.egg.tributapp.servicios.DesarrolladorServicio;
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
 * @author martin
 */
@Controller
@RequestMapping("/desarrollador")
public class desarrolladorControlador {

    @Autowired
    private DesarrolladorServicio desarrolladorServicio;

    @GetMapping("/cargarDesarrollador")
    public String cargar() {

        return "CreateDev.html";

    }

    @PostMapping("/cargar")
    public String cargar(@RequestParam String nombre,
            @RequestParam String email, @RequestParam String password,
            @RequestParam String password2,
            ModelMap modelo) throws MiException {
        try {

            desarrolladorServicio.registrar(nombre, email, password, password2);

            modelo.put("Exito", "desarrollador fue cargado exitosamente");

        } catch (MiException ex) {

            modelo.put("Error", ex.getMessage());

            return "CreateDev.html";

        }
        return "CreateDev.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Desarrollador> desarrollador = desarrolladorServicio.listarDesarrolladores();
        modelo.addAttribute("desarrolladores", desarrollador);

        return "desarrollador_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {

        modelo.put("desarrollador", desarrolladorServicio.getOne(id));

        return "desarrollador_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, String email, String pass, String pass2, ModelMap modelo) {
        try {
            desarrolladorServicio.modificarDesarrollador(id, nombre, email, pass2, pass2);

            return "redirect:../lista";
            
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            
            return "desarrollador_modificar.html";
            
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Desarrollador desarrollador) {

        desarrolladorServicio.Eliminar(desarrollador);

        return "redirect:/noticia/lista";
    }
}
