package com.emprende.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.emprende.entities.Profesor;
import com.emprende.services.FacultadService;
import com.emprende.services.ProfesorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profesores")
public class ProfesorController {

    private static final Logger LOG = Logger.getLogger("ProfesorController");

    private final ProfesorService profesorService;
    private final FacultadService facultadService;

    @GetMapping("/alta")
    public String mostrarFormularioAltaProfesor(Model model, @ModelAttribute Profesor profesor) {
        model.addAttribute("facultades", facultadService.getAllFacultades());
        return "altaModificacionProfesores";
    }

    @PostMapping("/persistir")
    public String persistirProfesor(@Valid @ModelAttribute Profesor profesor,
                                BindingResult result,
                                Model model,
                                @RequestParam(required = false) MultipartFile file,
                                RedirectAttributes redirectAttributes) {

        model.addAttribute("facultades", facultadService.getAllFacultades());

        if (result.hasErrors()) {
            model.addAttribute("facultades", facultadService.getAllFacultades());
            return "altaModificacionProfesores";
        }

        if (file != null && !file.isEmpty()) {
            Path rutaRelativa = Paths.get("src/main/resources/static/imagenes");
            String rutaAbsoluta = rutaRelativa.toFile().getAbsolutePath();
            Path rutaCompleta = Paths.get(rutaAbsoluta + "/" + file.getOriginalFilename());

            try {
                byte[] bytesFotoRecibida = file.getBytes();
                Files.write(rutaCompleta, bytesFotoRecibida);
                profesor.setFoto(file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (profesor.getId() != null) {
            Profesor profesorDb = profesorService.getProfesorById(profesor.getId()).orElse(null);
            if (profesorDb != null) {
                profesor.setFoto(profesorDb.getFoto());
            }
        }

        LOG.info("Objeto Profesor recibido");
        LOG.info(profesor.toString());

        
        profesorService.persistirProfesor(profesor);
        redirectAttributes.addFlashAttribute("mensaje", "Profesor guardado correctamente");
        return "redirect:/profesores/listado";
    }

    @GetMapping("/listado")
    public String listarProfesores(Model model) {
        model.addAttribute("profesores", profesorService.getAllProfesores());
        return "listadoProfesores";
    }

    @GetMapping("/modificar/{id}")
    public String modificarProfesor(Model model, @PathVariable Integer id) {
        Profesor profesor = profesorService.getProfesorById(id).orElse(null);

        if (profesor == null) {
            return "redirect:/profesores/listado";
        }

        model.addAttribute("profesor", profesor);
        model.addAttribute("facultades", facultadService.getAllFacultades());
        return "altaModificacionProfesores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProfesor(@PathVariable Integer id) {
        Profesor profesor = profesorService.getProfesorById(id).orElse(null);

        if (profesor != null && profesor.getFoto() != null) {
            Path rutaRelativa = Paths.get("src/main/resources/static/imagenes/" + profesor.getFoto());
            try {
                Files.deleteIfExists(rutaRelativa);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        profesorService.eliminarProfesor(id);
        return "redirect:/profesores/listado";
    }

    @GetMapping("/detalle/{id}")
    public String verDetalleProfesor(@PathVariable Integer id, Model model) {
        Profesor profesor = profesorService.getProfesorById(id).orElse(null);

        if (profesor == null) {
            return "redirect:/profesores/listado";
        }

        model.addAttribute("profesor", profesor);
        return "detalleProfesor";
    }
}