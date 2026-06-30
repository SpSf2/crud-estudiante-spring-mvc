package com.emprende.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
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

import com.emprende.entities.Correo;
import com.emprende.entities.Estudiante;
import com.emprende.entities.Telefono;
import com.emprende.services.CorreoService;
import com.emprende.services.EstudianteService;
import com.emprende.services.FacultadService;
import com.emprende.services.TelefonoService;

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
    private final CorreoService correoService;
    private final TelefonoService telefonoService;
    
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
                model.addAttribute("numerosTelefono", numerosTelefono);
                model.addAttribute("dircorreos", dircorreos);
                return "formularioAltaModificacion";
            }

            //Comprobamos si el telefono ya existe para evitar duplicados
            model.addAttribute("numerosTelefono", numerosTelefono);
            model.addAttribute("dircorreos", dircorreos);

            if (!numerosTelefono.isBlank()) {
                String[] arrayNumerosTelefono = numerosTelefono.split(";");
                for (String numero : arrayNumerosTelefono) {
                    String numeroLimpio = numero.trim();

                    if (!numeroLimpio.isEmpty()) {
                        Optional<Telefono> telefonoExistente = telefonoService.findByNumero(numeroLimpio);

                        if (telefonoExistente.isPresent()) {
                            int idEstudianteTelefono = telefonoExistente.get().getEstudiante().getId();

                            if (estudiante.getId() == 0 || idEstudianteTelefono != estudiante.getId()) {
                                model.addAttribute("facultades", facultadService.getAllFacultades());
                                model.addAttribute("numerosTelefono", numerosTelefono);
                                model.addAttribute("dircorreos", dircorreos);
                                model.addAttribute("errorTelefono", "El teléfono " + numeroLimpio + " ya existe.");
                                return "formularioAltaModificacion";
                            }
                        }
                    }
                }
            }
                    //Comprobamos si el correo ya existe para evitar duplicados
            if (!dircorreos.isBlank()) {
                String[] arrayCorreos = dircorreos.split(";");
                for (String correo : arrayCorreos) {
                    String correoLimpio = correo.trim();

                    if (!correoLimpio.isEmpty()) {
                        Optional<Correo> correoExistente = correoService.findByEmail(correoLimpio);

                        if (correoExistente.isPresent()) {
                            int idEstudianteCorreo = correoExistente.get().getEstudiante().getId();

                            if (estudiante.getId() == 0 || idEstudianteCorreo != estudiante.getId()) {
                                model.addAttribute("facultades", facultadService.getAllFacultades());
                                model.addAttribute("numerosTelefono", numerosTelefono);
                                model.addAttribute("dircorreos", dircorreos);
                                model.addAttribute("errorCorreo", "El correo " + correoLimpio + " ya existe.");
                                return "formularioAltaModificacion";
                            }
                        }
                    }
                }
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
        /*separados por punto y comas y convertirlos en listas de objetos Telefono y Correo para
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

       // Antes de persistir el estudiante, hay que eliminar los telefonos y correos que ya existan en el estudiante seleccionado
       // antes de agregar los nuevos que se recibieron en el formulario

       if (estudiante.getId() != 0) {
            if (telefonoService.existsByEstudiante(estudiante)) 
                telefonoService.deleteByEstudiante(estudiante);
                
            if (correoService.existsByEstudiante(estudiante)) 
                correoService.deleteByEstudiante(estudiante);
        
       }
        /**Se recibe un objeto Estudiante con los datos del formulario, se envia a la
	* capa de servicios para que lo guare en la DB. Este bloque es la red de seguridad 
    por si algo se escapa a la validación previa.
	*/
            try {
                estudianteService.saveEstudiante(estudiante);
            } catch (DataIntegrityViolationException e) {
                model.addAttribute("facultades", facultadService.getAllFacultades());
                model.addAttribute("numerosTelefono", numerosTelefono);
                model.addAttribute("dircorreos", dircorreos);
                model.addAttribute("errorGeneral", "Ya existe un teléfono o correo registrado.");
                return "formularioAltaModificacion";
        }


        return "redirect:/estudiantes/listar";
    }


    // Metodo que muestra los detalles de un empleado cuyo id se recibe como parámetro:
    @GetMapping(path="/details/{id}")
    public String mostrarDetalles(Model model, 
                  @PathVariable(name = "id", required = true) int estudiante_id) {

        //Recuperar el estudiante cuyo id se recibe como parámetro:
        model.addAttribute("estudiante", 
                           estudianteService.getEstudianteById(estudiante_id));

        return "details";
        
    }

    @GetMapping(path="/update/{id}")
    public String updateEstudiante(Model model, @PathVariable(name = "id", required = true) int estudiante_id) {

        //Recuperar el estudiante cuyo id se recibe como parámetro para obtener sus telefonos y correos:
        Estudiante estudiante = estudianteService.getEstudianteById(estudiante_id);
        model.addAttribute("estudiante", estudiante);

        //Se necesitan las Facultades desde la capa de Servicios para que se muestren en el formulario al
        //llamar al estudiante
        model.addAttribute("facultades", facultadService.getAllFacultades());

        //Recuperando telefonos y correos del estudiante:
        Set<Telefono> telefonos = estudiante.getTelefonos();
        
        if(telefonos.size() > 0) {
            String numerosTelefono = telefonos.stream()
                    .map(telefono -> telefono.getNumero())
                    .collect(Collectors.joining(";"));
            
            model.addAttribute("numerosTelefono", numerosTelefono);

        }

        Set<Correo> correos = estudiante.getCorreos();
        
        if(correos.size() > 0) {
            String dircorreos = correos.stream()
                    .map(correo -> correo.getEmail())
                    .collect(Collectors.joining(";"));
            
            model.addAttribute("dircorreos", dircorreos);
        }

        return "formularioAltaModificacion";
    }


    /*Metodo para eliminar un empleado con sus telefonos y correos correspondientes.
    hay que eliminar tambien la foto del empleado si este la tuviera */

    @GetMapping("/delete/{idEstudiante}")
    public String deleteEstudiante(Model model, @PathVariable int idEstudiante) {

        //Primero Comprobar si el empleado tiene foto:

        Estudiante estudiante = estudianteService.getEstudianteById(idEstudiante);

        if (estudiante.getFoto() != null) {
            //necesitamos la ruta relativa de la foto que se va a eliminar:

            Path rutaRelativa = Paths.get("src/main/resources/static/imagenes/" + estudiante.getFoto());
            try {
                Files.delete(rutaRelativa);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        estudianteService.deleteEstudiante(estudiante);
        return "redirect:/estudiantes/listar";
    }


    @GetMapping("/listadoBeca")
    public String listadoBeca(Model model) {

        model.addAttribute("estudiantes", estudianteService.findAllByOrderByBecaDesc());
        return "listadoBeca";
    }

    //Metodo para ordenar los estudiantes por campo
    @GetMapping("/formOrdenar")
    public String mostrarFormularioOrdenar() {
        
        return "formOrdenar";
    }

    //Método que recibe el campo y devuelve el listado ordenado.

    @GetMapping("/ordenar")
    public String ordenarEstudiantes(@RequestParam("campo") String campo, Model model) {
        model.addAttribute("estudiantes", estudianteService.getEstudiantesOrdenados(campo));
        return "listadoEstudiantes";
    }
}









