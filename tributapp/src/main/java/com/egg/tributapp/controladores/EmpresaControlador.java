/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.tributapp.controladores;

import com.egg.tributapp.entidades.Empresa;
import com.egg.tributapp.servicios.EmpresaServicio;
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

/**
 *
 * @author eduar
 */
@Controller
@RequestMapping("/empresa")
public class EmpresaControlador {

    @Autowired
    private EmpresaServicio empresaServicio;

    @GetMapping("/registrarEmpresa")
    public String registarEmpresa(ModelMap modelo) {
        return "empresa_registrar";
    }
    
    
    @PostMapping("/registroEmpresa")
    public String registroEmpresa(@RequestParam String razonSocial, @RequestParam String direccion, @RequestParam String nombre, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, ModelMap modelo) throws Exception {

        try {
            empresaServicio.registrarEmpresa(razonSocial, direccion, nombre, email, password, password2);
                    
            modelo.put("exito", "Empresa registrada exitosamente");
            
        } catch (Exception ex) {

            modelo.put("error", ex.getMessage());

            return "index";
        }

        return "empresa_registrar";
    }
    
     @GetMapping("/listarEmpresa")
    public String listarEmpresa(ModelMap modelo) {

        List<Empresa> empresas = empresaServicio.listarEmpresas();

        modelo.addAttribute("empresas", empresas);

        return "empresa_listar";

    }
    
     @DeleteMapping ("/eliminarEmpresa/{id}")
    public String eliminarAdmin(@PathVariable String id, ModelMap modelo) {

        try {

            empresaServicio.eliminar(id);

            return ("redirect:..//listarEmpresa");

        } catch (Exception ex) {

            modelo.put("error", ex.getMessage());

            return "empresa_eliminar";

        }

    }
    
}
