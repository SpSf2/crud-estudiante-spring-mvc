package com.emprende.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.emprende.entities.Facultad;
import com.emprende.services.FacultadService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/facultades")
public class FacultadController {

    private final FacultadService facultadService;

    @GetMapping("/alta")
    public String mostrarFormularioAltaFacultad(Model model) {
        model.addAttribute("facultad", new Facultad());
        
        return "formAltaFacultad";
    }

    @PostMapping("/persistir")
    public String persistirFacultad(@ModelAttribute Facultad facultad, RedirectAttributes redirectAttributes, Model model) {

        if (facultadService.existeFacultadPorNombre(facultad.getNombre())) {
            model.addAttribute("error", "La facultad ya existe");
            model.addAttribute("facultad", facultad);
            return "formAltaFacultad";
        }

        facultadService.persistirFacultad(facultad);
        redirectAttributes.addFlashAttribute("mensaje", "Facultad guardada correctamente");
        return "redirect:/facultades/alta";
    }

    @GetMapping("/listado")
    public String listarFacultades(Model model) {
        model.addAttribute("facultades", facultadService.getAllFacultades());
        return "listadoFacultades";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarFacultad(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

        if (facultadService.facultadTieneEstudiantes(id)) {
            redirectAttributes.addFlashAttribute("error",
                    "No se puede eliminar la facultad porque tiene estudiantes asociados");
            return "redirect:/facultades/listado";
        }

        facultadService.eliminarFacultad(id);
        redirectAttributes.addFlashAttribute("mensaje", "Facultad eliminada correctamente");
        return "redirect:/facultades/listado";
    }



}
