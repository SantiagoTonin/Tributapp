
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.tributapp.controladores;

import com.egg.tributapp.entidades.Admin;
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
    public String registroEmpresa(@RequestParam String razonSocial, @RequestParam String direccion,
            @RequestParam String nombre, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, ModelMap modelo) throws Exception {

        try {
            System.out.println("password" + password);
            empresaServicio.registrarEmpresa(razonSocial, direccion, nombre, email, password, password2);

            modelo.put("exito", "Empresa registrada exitosamente");

        } catch (Exception ex) {

            modelo.put("Error", ex.getMessage());

            return "login.html";
        }

        return "redirect:/empresa/listarEmpresa";
    }

    @GetMapping("/listarEmpresa")
    public String listarEmpresa(ModelMap modelo) {

        List<Empresa> empresas = empresaServicio.listarEmpresas();

        modelo.addAttribute("empresas", empresas);

        return "ListaEmpresas.html";

    }

    @GetMapping("/modificarEmpresa/{id}")
    public String modificarEmpresa(@PathVariable String id, ModelMap modelo) {

        modelo.put("empresa", empresaServicio.getOne(id));

        return "UpdateEmpresa.html";

    }

    @PostMapping("/modificarEmpresa/{id}")
    public String modificoEmpresa(@PathVariable String id, String razonSocial, String direccion,
            String nombre, String email, String password, String password2, ModelMap modelo) {

        try {
            System.out.println(id + "---------------");
            empresaServicio.modificar(id, razonSocial, direccion, nombre, email, password, password2);

            return "redirect:/empresa/listarEmpresa";

        } catch (Exception ex) {

            modelo.put("error", ex.getMessage());

            return "UpdateEmpresa.html";
        }

    }

    @GetMapping("/eliminarEmpresa/{id}")
    public String eliminarAdmin(@PathVariable String id, ModelMap modelo) {

        empresaServicio.eliminar(id);

        return "redirect:/empresa/listarEmpresa";

    }

    @GetMapping(value = "/busqueda")
    public String busquedaEmpresa(ModelMap modelo, @RequestParam(value = "param", required = false) String param) {

        try {

            List<Empresa> empresa = empresaServicio.buscarEmpresaNombre(param);

            modelo.addAttribute("empresas", empresa);
            return "busqueda.html";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
        }
        return null;
    }

}
