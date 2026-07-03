package com.emprende.services;

import java.util.List;
import java.util.Optional;

import com.emprende.entities.Profesor;

public interface ProfesorService {

    Profesor saveProfesor(Profesor profesor);

    List<Profesor> getAllProfesores();

    Optional<Profesor> getProfesorById(Integer id);

    void persistirProfesor(Profesor profesor);

    void eliminarProfesor(Integer id);

    long count();

    boolean facultadTieneProfesores(Integer id);
}
