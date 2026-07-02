package com.emprende.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.emprende.entities.Estudiante;

public interface EstudianteDao extends JpaRepository<Estudiante, Integer>{

    List<Estudiante> findAllByOrderByBecaDesc();

    //metodo que comprueba si una facultad está en uso
    boolean existsByFacultadId(Integer id);

}
