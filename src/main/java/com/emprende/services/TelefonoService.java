package com.emprende.services;

import java.util.List;

import com.emprende.entities.Estudiante;
import com.emprende.entities.Telefono;

public interface TelefonoService {

    //Definir los metodos que se van a implementar en la clase 
    // TelefonoServiceImpl

    // Metodo para guardar un Telefono:
     Telefono saveTelefono(Telefono telefono);

    // Metodo para obtener todo los Telefonos:
     List<Telefono> getAllTelefonos();

    // Metodo para eliminar un Telefono:
    void deleteByEstudiante(Estudiante estudiante);

    //Metodo para obtener todo los Telefonos de un Estudiante
    List<Telefono> findByEstudiante(Estudiante estudiante);

    boolean existsByEstudiante(Estudiante estudiante);

    // Metodo para evitar la duplicacion
    boolean existsByNumero(String numero);
}
