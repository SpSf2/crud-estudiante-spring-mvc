package com.emprende.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emprende.entities.Telefono;

public interface TelefonoDao extends JpaRepository<Telefono, Integer>{

    //Este Metodo muestra si un Estudiante tiene Telefono registrado:
    boolean existsByTelefono(Telefono telefono);

    //Este Metodo elimina los Telefonos de un Estudiante:
    void deleteByEstudiante(Telefono telefono);

    //Este Metodo muestra los Telefonos de un Estudiante:
    List<Telefono> findByEstudiante(Telefono telefono);

}
