package com.emprende.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emprende.entities.Profesor;

public interface ProfesorDao extends JpaRepository<Profesor, Integer> {

    boolean existsByFacultadId(Integer id);


}
