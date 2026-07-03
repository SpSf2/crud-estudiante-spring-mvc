package com.emprende.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.emprende.dao.ProfesorDao;
import com.emprende.entities.Profesor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProfesorServiceImpl implements ProfesorService {

    private final ProfesorDao profesorDao;

    @Override
    public Profesor saveProfesor(Profesor profesor) {
        return profesorDao.save(profesor);
    }

    @Override
    public List<Profesor> getAllProfesores() {
        return profesorDao.findAll();
    }

    @Override
    public Optional<Profesor> getProfesorById(Integer id) {
        return profesorDao.findById(id);
    }

    @Override
    public void persistirProfesor(Profesor profesor) {
        profesorDao.save(profesor);
    }

    @Override
    public void eliminarProfesor(Integer id) {
        profesorDao.deleteById(id);
    }

    @Override
    public long count() {
        return profesorDao.count();
    }

    @Override
    public boolean facultadTieneProfesores(Integer id) {
        return profesorDao.existsByFacultadId(id);
    }
}
