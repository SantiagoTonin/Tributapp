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

        return "desarrollador_cargar.html";

    }

    @PostMapping("/cargar")
//     private String id;
//    private String nombre;
//    private String email;
    public String cargar(@RequestParam String nombre,
            @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, @RequestParam String contratacion,
            ModelMap modelo) throws MiException {
        try {

            desarrolladorServicio.registrar(nombre, email, password, password2, contratacion);

            modelo.put("Exito", "desarrollador fue cargado exitosamente");

        } catch (MiException ex) {

            modelo.put("Error", ex.getMessage());

            return "desarrolador_cargar.html";

        }
        return "desarrollador.html";
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

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Desarrollador desarrollador) {

        desarrolladorServicio.Eliminar(desarrollador);

        return "redirect:/noticia/lista";
    }
}
