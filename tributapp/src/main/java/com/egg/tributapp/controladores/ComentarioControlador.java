/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.tributapp.controladores;

import com.egg.tributapp.entidades.Desarrollador;
import com.egg.tributapp.excepciones.MiException;
import com.egg.tributapp.servicios.ComentarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author marti
 */
@Controller
@RequestMapping("/comentarios")
public class ComentarioControlador {

    @Autowired
    private ComentarioServicio comentarioServicio;

    @GetMapping("/cargarComentario")
    public String cargarComentario() {

        return "comentario_cargar.html";
    }

    @PostMapping("/cargarComen")
    public String cargarComentario(@RequestParam String texto,
            @RequestParam Desarrollador desarrollador, ModelMap modelo) throws MiException {

        try {

            comentarioServicio.nuevoComentario(texto, desarrollador);

            modelo.put("Exito", "El comentario fue cargado con exito");

        } catch (MiException ex) {

            modelo.put("Error", ex.getMessage());
            
            return "comentario_cargar.html";
        
        }

        return "comentario.html";

    }
    

}
