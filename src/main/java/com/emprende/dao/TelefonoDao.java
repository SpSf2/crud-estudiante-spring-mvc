package com.emprende.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emprende.entities.Estudiante;
import com.emprende.entities.Telefono;

public interface TelefonoDao extends JpaRepository<Telefono, Integer>{

    //Este Metodo muestra si un Estudiante tiene Telefono registrado:
    boolean existsByEstudiante(Estudiante estudiante);

    //Este Metodo elimina los Telefonos de un Estudiante:
    void deleteByEstudiante(Estudiante estudiante);

    //Este Metodo muestra los Telefonos de un Estudiante:
    List<Telefono> findByEstudiante(Estudiante estudiante);

    //Este Metodo es para usarlo en el formulario del link Actualizar para
    //evitar que el mismo estudiante le de error su propio numero al actualizar otro campo

    Optional<Telefono> findByNumero(String numero);

}
