package com.egg.tributapp.controladores;

import com.egg.tributapp.entidades.Desarrollador;
import com.egg.tributapp.entidades.Empresa;
import com.egg.tributapp.excepciones.MiException;
import com.egg.tributapp.servicios.DesarrolladorServicio;
import com.egg.tributapp.servicios.EmpresaServicio;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author martin
 */
@Controller
@RequestMapping("/desarrollador")
public class DesarrolladorControlador {

    @Autowired
    private DesarrolladorServicio desarrolladorServicio;

    @Autowired
    private EmpresaServicio empresaServicio;

    @GetMapping("/cargarDesarrollador")
    public String cargar(ModelMap modelo) {

        return "login.html";

    }

    @PostMapping("/cargar")
    public String cargar(@RequestParam String nombre, @RequestParam String email, @RequestParam String password,
            String contratacion, @RequestParam String password2, MultipartFile foto, String cuil, ModelMap modelo)
            throws MiException, IOException {

        try {

            desarrolladorServicio.registrar(nombre, email, password, password2, contratacion, foto, cuil);
            modelo.put("Exito", "desarrollador fue cargado exitosamente");

        } catch (MiException ex) {

            modelo.put("Error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "login.html";

        }
        return "login.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Desarrollador> desarrollador = desarrolladorServicio.listarDesarrolladores();
        modelo.addAttribute("desarrolladores", desarrollador);

        return "DesarrolladorList.html";
    }

    @GetMapping("/modificar/{id}") // end al front
    public String modificar(@PathVariable String id, ModelMap modelo) {

        modelo.put("desarrollador", desarrolladorServicio.getOne(id));

        return "Update.html";
    }

    @PostMapping("/modificar/{id}") // front al end
    public String modificar(@PathVariable String id, String nombre, String email, String password, String password2,
            String contratacion, MultipartFile foto, String cuil, ModelMap modelo) throws MiException, IOException {

        try {

            desarrolladorServicio.modificarDesarrollador(id, nombre, email, password, contratacion, password2, foto,
                    cuil);

            modelo.put("Exito", "Desarrollador actualizado");

            return "redirect:/desarrollador/lista";

        } catch (MiException ex) {

            System.out.println("estoy en el error de modificar" + id + "------------------------------------");

            modelo.put("Error", ex.getMessage());

            modelo.put("desarrollador", desarrolladorServicio.getOne(id));

            return "Update.html";

        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {

        desarrolladorServicio.Eliminar(id);

        return "redirect:/desarrollador/lista";
    }

    @GetMapping(value = "/busquedaDesarrollador")
    public String busquedaDesarrollador(ModelMap modelo,
            @RequestParam(value = "param", required = false) String param) {

        try {

            List<Desarrollador> desarrollador = desarrolladorServicio.buscarDesarrolladorNombre(param);

            modelo.addAttribute("desarrollador", desarrollador);
            return "busquedaDev.html";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
        }

        return null;
    }

    @GetMapping("/inicio")
    public String inicioDesarrollador(ModelMap modelo, HttpSession http) {

        List<Empresa> empresas = empresaServicio.listarEmpresas();

        modelo.addAttribute("empresas", empresas);

        Desarrollador desarrollador = (Desarrollador) http.getAttribute("usuariosession");

        modelo.addAttribute("desarrollador", desarrollador);

        return "DesarrolladorInicio.html";
    }

    @GetMapping("/inicio/{id}")
    public ResponseEntity<byte[]> imagenDesarrollador(@PathVariable String id) {

        Desarrollador desarrollador = desarrolladorServicio.getOne(id);

        byte[] imagen = desarrollador.getFoto();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    @GetMapping("/info")
    public String info(ModelMap modelo, HttpSession http) {
        Desarrollador desarrollador = (Desarrollador) http.getAttribute("usuariosession");

        modelo.addAttribute("desarrollador", desarrollador);

        return "DesarrolladorInfo.html";
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<byte[]> imagenInfo(@PathVariable String id) {

        Desarrollador desarrollador = desarrolladorServicio.getOne(id);

        byte[] imagen = desarrollador.getFoto();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    @GetMapping("/impuestos")
    public String imp(ModelMap modelo, HttpSession http) {
        Desarrollador desarrollador = (Desarrollador) http.getAttribute("usuariosession");

        modelo.addAttribute("desarrollador", desarrollador);

        List<Empresa> empresa = empresaServicio.listarEmpresas();

        modelo.addAttribute("empresa", empresa);

        return "DesarrolladorImp.html";
    }

    // post mapping que reciba datos de empresa (nombre y email de la empresa)
    // desarrolladorImp para crear una empresa
    @PostMapping("/impuestosCargaEmpresa")
    public String registroEmpresa(@RequestParam String razonSocial, @RequestParam String direccion,
            @RequestParam String nombre, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, ModelMap modelo) throws Exception {

        try {

            empresaServicio.registrarEmpresa("a completar", "a completar", nombre, email, "nullo", "nulo");

            modelo.put("exito", "Empresa registrada exitosamente");

        } catch (Exception ex) {

            modelo.put("Error", ex.getMessage());

            return "login.html";
        }

        return "redirect:/empresa/listarEmpresa";
    }

    // @PostMapping(/elegirEmpresa) revisar relacion en autores q me envie el id del
    // objeto
    @PostMapping("/elegirEmpresa")
    public String setEmpresa(@RequestParam String idEmpresa, @RequestParam String idDesarrollador, ModelMap modelo) {

        try {
            desarrolladorServicio.elegirEmpresa(idDesarrollador, idEmpresa);
            modelo.put("Exito", "El desarrollador a elegido correctamente una empresa");

        } catch (Exception e) {

            modelo.put("error", e.getMessage());
            return "redirect:/desarrollador/inicio";

        }

        return "redirect:/desarrollador/inicio";

    }
}
