package com.emprende.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.emprende.dao.CorreoDao;
import com.emprende.entities.Correo;
import com.emprende.entities.Estudiante;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class CorreoServiceImpl implements CorreoService{

    //Esta clase necesita del Dao para implementar sus metodos:

    //Inyectamos la capa Dao:
    private final CorreoDao correoDao;

    @Override
    public Correo saveCorreo(Correo correo) {
        
        return correoDao.save(correo);
    }

    @Override
    public List<Correo> getAllCorreos() {
     
        return correoDao.findAll();
    }

    @Override
    public boolean existsByEstudiante(Estudiante estudiante) {
        
        return correoDao.existsByEstudiante(estudiante);
    }

    @Override
    @Transactional
    public void deleteByEstudiante(Estudiante estudiante) {

        correoDao.deleteByEstudiante(estudiante);
    }

    @Override
    public List<Correo> findByEstudiante(Estudiante estudiante) {

        return correoDao.findByEstudiante(estudiante);
    }

    @Override
    public boolean existsByEmail(String email) {
        return correoDao.existsByEmail(email);
    }

    @Override
    public Optional<Correo> findByEmail(String email) {
        return correoDao.findByEmail(email);
    }
}
