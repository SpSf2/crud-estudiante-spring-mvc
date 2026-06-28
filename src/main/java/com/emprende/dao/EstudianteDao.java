package com.emprende.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emprende.entities.Estudiante;

public interface EstudianteDao extends JpaRepository<Estudiante, Integer>{

    List<Estudiante> findAllByOrderByBecaDesc();

}
