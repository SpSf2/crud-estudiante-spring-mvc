package com.emprende.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emprende.services.EstudianteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteService estudianteService;
    
    /*la anotacion @RequiredArgsConstructor  de lombok hace lo mismo que esto que 
    es crear un constructor que genera un objeto: 
           public EmpleadoController(EmpleadoService empleadoService) {
	this.empleadoService = empleadoService;
}      entonces la agregamos arriba en las anotaciones y borramos ese codigo.  
      Esto es para tener conocimiento de esto. */
    @GetMapping("/listar")
    public String listarEstudiantes(Model model) {

        model.addAttribute("estudiantes", estudianteService.getAllEstudiantes());
        return "listadoEstudiantes";
    }

}
