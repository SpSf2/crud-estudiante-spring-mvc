package com.emprende.services;

import java.util.List;

import com.emprende.entities.Estudiante;

public interface EstudianteService {

    //Definir los metodos que se van a implementar en la clase 
    // EstudianteServiceImpl

    // Metodo para obtener todo los Estudiantes:
     List<Estudiante> getAllEstudiantes();

    // Metodo para obtener un Estudiante por su Id:
     Estudiante getEstudianteById(int id);

    // Metodo para guardar un Estudiante:
     Estudiante saveEstudiante(Estudiante estudiante);

    // Metodo para actualizar un Estudiante:
     Estudiante updateEstudiante(Estudiante estudiante);

    // Metodo para eliminar un Estudiante:
     void deleteEstudiante(int id);

    //Metodo para eliminar un Estudiante pasandole el objeto Estudiante:
     void deleteEstudiante(Estudiante estudiante);

     long count();

     List<Estudiante> findAllByOrderByBecaDesc();
}
