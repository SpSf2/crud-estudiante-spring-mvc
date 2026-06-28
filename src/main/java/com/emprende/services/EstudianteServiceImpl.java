package com.emprende.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.emprende.dao.EstudianteDao;
import com.emprende.entities.Estudiante;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EstudianteServiceImpl implements EstudianteService{

    //Esta clase necesita del Dao para implementar sus metodos:

    //Inyectamos la capa Dao:
    private final EstudianteDao estudianteDao;

    @Override
    public List<Estudiante> getAllEstudiantes() {
        
        return estudianteDao.findAll();
    }

    @Override
    public Estudiante getEstudianteById(int id) {

        return estudianteDao.findById(id).orElseThrow(() ->
                new RuntimeException("Estudiante no encontrado"));
    }

    @Override
    public Estudiante saveEstudiante(Estudiante estudiante) {
        
        return estudianteDao.save(estudiante);    
    }

    @Override
    public Estudiante updateEstudiante(Estudiante estudiante) {
        
        return estudianteDao.save(estudiante);
    }

    @Override
    public void deleteEstudiante(int id) {
        
        estudianteDao.deleteById(id);    
    }

    @Override
    public void deleteEstudiante(Estudiante estudiante) {
        
        estudianteDao.delete(estudiante);
    }

    @Override
    public long count() {
        
        return estudianteDao.count();
    }

    @Override
    public List<Estudiante> findAllByOrderByBecaDesc() {
        
        return estudianteDao.findAllByOrderByBecaDesc();
    }

    

}
