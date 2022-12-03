package com.egg.tributapp.controladores;

import com.egg.tributapp.entidades.Comentario;
import com.egg.tributapp.entidades.Contador;
import com.egg.tributapp.excepciones.MiException;
import com.egg.tributapp.servicios.ComentarioServicio;
import com.egg.tributapp.servicios.ContadorServicio;
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

@Controller
@RequestMapping("/contador")
public class ContadorControlador {

    @Autowired
    private ContadorServicio contadorServicio;

    @Autowired
    private ComentarioServicio comentarioServicio;

    @GetMapping("/cargarContador")
    public String cargarContador(ModelMap modelo) {

        return "CreateContador.html";
    }

    @PostMapping("/cargar")
    public String cargar(@RequestParam String nombre,
            @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, @RequestParam String telefono, @RequestParam String matricula,
            @RequestParam String provincia, MultipartFile foto, ModelMap modelo) throws IOException {

        try {
            contadorServicio.registrar(nombre, email, password, password2, telefono, matricula, provincia, foto);

            modelo.put("exito", "El Contador/a fue cargado correctamente!");

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "login.html"; // volvemos a cargar el formulario.
        }
        return "login.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Contador> contadores = contadorServicio.listarContadores();
        modelo.addAttribute("contadores", contadores);

        return "ContadorList.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {

        modelo.put("contador", contadorServicio.getOne(id));

        return "ContadorUpdate.html";
    }

    @PostMapping("/modificar/{id}")

    public String modificar(@PathVariable String id, String nombre,
            String email, String password,
            String password2, String telefono, String matricula, String provincia, MultipartFile foto, ModelMap modelo) throws IOException {
        try {
            contadorServicio.modificarContador(id,nombre, email, password,password2, telefono, matricula, provincia, foto);


            return "redirect:../lista";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "ContadorUpdate.html";
        }

    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) throws MiException {

        contadorServicio.eliminar(id);

        return "redirect:/contador/lista";
    }

    @GetMapping("/inicio")
    public String inicioContador(ModelMap modelo, HttpSession http) {

        Contador contador = (Contador) http.getAttribute("usuariosession");

        modelo.addAttribute("contador", contador);

        System.out.println(contador.getNombre());

        return "ContadorInicio.html";
    }

    @GetMapping("/inicio/{id}")
    public ResponseEntity<byte[]> imagenContador(@PathVariable String id) {

        Contador contador = contadorServicio.getOne(id);

        byte[] imagen = contador.getFoto();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/busquedaEmail")
    public String busquedaContadorEmail(ModelMap modelo,
            @RequestParam(value = "param", required = false) String param) {

        try {

            List<Contador> contador = contadorServicio.buscarContadorEmail(param);

            modelo.addAttribute("contador", contador);
            return "index.html";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
        }

        return null;
    }

    @GetMapping(value = "/busquedaProvincia")
    public String busquedaContadorProvincia(ModelMap modelo,
            @RequestParam(value = "param", required = false) String param) {

        try {

            List<Contador> contador = contadorServicio.buscarContadorProvincia(param);

            modelo.addAttribute("contador", contador);
            return "index.html";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
        }

        return "index.html";
    }

    @GetMapping(value = "/busquedaNombre")
    public String busquedaContadorNombre(ModelMap modelo,
            @RequestParam(value = "param", required = false) String param) {

        try {

            List<Contador> contador = contadorServicio.buscarContadorNombre(param);

            modelo.addAttribute("contador", contador);
            return "index.html";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
        }

        return null;
    }

    @GetMapping(value = "/busquedaMatricula")
    public String busquedaContadorMatricula(ModelMap modelo,
            @RequestParam(value = "param", required = false) String param) {

        try {

            List<Contador> contador = contadorServicio.buscarContadorMatricula(param);

            modelo.addAttribute("contador", contador);
            return "index.html";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
        }

        return null;
    }

    @GetMapping("/listaInfo")
    public String listaInfo(ModelMap modelo) {
        List<Contador> contadores = contadorServicio.listarContadores();
        List<Comentario> comentario = comentarioServicio.listarComentarios();
        modelo.addAttribute("comentario",comentario);
        modelo.addAttribute("contadores", contadores);

        return "ContadorInfo.html";
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<byte[]> imagenInfo(@PathVariable String id) {

        Contador contador = contadorServicio.getOne(id);

        byte[] imagen = contador.getFoto();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    @PostMapping("/cargarComen")
    public String cargarComentario(@RequestParam String texto,
            ModelMap modelo) throws MiException {

        try {

            comentarioServicio.nuevoComentario(texto);

            modelo.put("Exito", "El comentario fue cargado con exito");

        } catch (MiException ex) {

            modelo.put("Error", ex.getMessage());
            
            return "ContadorInfo.html";
        
        }

        return "ContadorInfo.html";

    }

}
