package com.emprende.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.emprende.entities.Correo;
import com.emprende.entities.Estudiante;
import com.emprende.entities.Telefono;
import com.emprende.services.EstudianteService;
import com.emprende.services.FacultadService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private static final Logger LOG = Logger.getLogger("EstudianteController");

    //Servicios inyectados
    private final EstudianteService estudianteService;
    private final FacultadService facultadService;
    
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

    @GetMapping("/alta")
    public String mostrarFormularioAlta(Model model, @ModelAttribute Estudiante estudiante) {

	    //Se necesitan las Facultades desde la capa de Servicios y las inyectamos arriba
        model.addAttribute("facultades", facultadService.getAllFacultades());

        /*Se necesita enviar un Objeto Estudiante vacio, para que se vinculen sus propiedades con cada control 
        (elemento, input, select, etc) del formulario */
        // El codigo siguiente se comenta porque el objeto se pasa como atributo 
        // al modelo a traves de la anotacion @ModelAttribute que se recibe como un 
        // parametro del metodo
       // model.addAttribute("estudiante", new Estudiante());
        
        return "formularioAltaModificacion";
    }


    /*Metodo para recibir los datos del formulario de Alta de Estudiante*/

    @PostMapping("/persistir")
    public String procesarFormuarioaltaModificacion(@Valid
        @ModelAttribute Estudiante estudiante,
        BindingResult result,
        @RequestParam String numerosTelefono,
        @RequestParam String dircorreos,
        Model model,
        @RequestParam(required = false) MultipartFile file) {

            //Comprobamos si hay errores en el formulario
            if (result.hasErrors()) {

                model.addAttribute("facultades", facultadService.getAllFacultades());
                return "formularioAltaModificacion";
            }

        /*Preguntar si han enviado foto del Empleado y si es asi, guardar el nombre
            de la foto en la propiedad, atributo o variable donde se guarde la foto
            y guardar el contengido de la foto como un archivo en el sistema de archivos
            (files system) del servidor*/
        if (file != null && !file.isEmpty()) {
            Path rutaRelativa = Paths.get("src/main/resources/static/imagenes");
            String rutaAbsoluta = rutaRelativa.toFile().getAbsolutePath();
            Path rutaCompleta = Paths.get(rutaAbsoluta + "/" + file.getOriginalFilename());

            try {
            byte[] bytesFotoRecibida = file.getBytes();
            Files.write(rutaCompleta, bytesFotoRecibida);
            estudiante.setFoto(file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    

        LOG.info("Objeto Estudiante Recibido");
	    LOG.info(estudiante.toString());
        LOG.info("Numeros de Telefono recibidos: " + numerosTelefono);
        LOG.info("Direcciones de Correo recibidas: " + dircorreos);

        //Hay que procesar los datos de los telefonos y correos que vienen en un String
        /*separados por comas y convertirlos en listas de objetos Telefono y Correo para
        * luego agregarlos al objeto Empleado antes de persistirlo en la DB */

        //Set<Telefono> telefonos = new HashSet<Telefono>();
        if(!numerosTelefono.isEmpty()&& !numerosTelefono.isBlank() ) {
            String[] arrayNumerosTelefono = numerosTelefono.split(";");
            List<String> listadoNumeros = Arrays.asList(arrayNumerosTelefono);

        listadoNumeros.forEach(numero -> {
            estudiante.getTelefonos().add(Telefono.builder().numero(numero).estudiante(estudiante).build());
        });
    }
    //estudiante.setTelefonos(telefonos);



        //Set<Correo> correos = new HashSet<>(); 

        if (!dircorreos.isBlank()&& !dircorreos.isEmpty()) {
            String[] arrayCorreos = dircorreos.split(";");
            List<String> listadoCorreos = Arrays.asList(arrayCorreos);

            listadoCorreos.forEach(correo -> {
            estudiante.getCorreos().add(Correo.builder()
            .email(correo.trim())
            .estudiante(estudiante)
            .build());
        });
    }

       // estudiante.setCorreos(correos);

        /**Se recibe un objeto Estudiante con los datos del formulario, se envia a la
	* capa de servicios para que lo guare en la DB
	*/
        estudianteService.saveEstudiante(estudiante);
        return "redirect:/estudiantes/listar";
    }
}









