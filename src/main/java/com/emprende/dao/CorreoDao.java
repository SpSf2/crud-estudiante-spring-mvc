package com.emprende.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.emprende.entities.Correo;
import com.emprende.entities.Estudiante;

import java.util.List;


public interface CorreoDao extends JpaRepository<Correo, Integer>{

    //Este Metodo muestra si un Estudiante tiene Correo registrado:
    boolean existsByCorreo(Correo correo);

    //Este Metodo elimina los Correos de un Estudiante:
    void deleteByEstudiante(Estudiante estudiante);


    //Este Metodo muestra los Correos de un Estudiante:
    List<Correo> findByEstudiante(Estudiante estudiante);
}
