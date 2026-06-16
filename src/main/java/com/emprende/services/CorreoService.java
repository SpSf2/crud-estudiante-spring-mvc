package com.emprende.services;

import java.util.List;

import com.emprende.entities.Correo;
import com.emprende.entities.Estudiante;

public interface CorreoService {

    //Definir los metodos que se van a implementar en la clase 
    // CorreoServiceImpl

    // Metodo para guardar un Correo:
     Correo saveCorreo(Correo correo);

    // Metodo para obtener todo los Correos:
     List<Correo> getAllCorreos();

     boolean existsByCorreo(Correo correo);

    // Metodo para eliminar un Correo:
    void deleteByEstudiante(Estudiante estudiante);

    //Metodo para obtener todo los Correos de un Estudiante
    List<Correo> getCorreosByEstudiante(Estudiante estudiante);

}
