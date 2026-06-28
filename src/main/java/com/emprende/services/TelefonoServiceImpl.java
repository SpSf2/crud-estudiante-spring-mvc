package com.emprende.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.emprende.dao.TelefonoDao;
import com.emprende.entities.Estudiante;
import com.emprende.entities.Telefono;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class TelefonoServiceImpl implements TelefonoService {

    //Esta clase necesita del Dao para implementar sus metodos:

    //Inyectamos la capa Dao:
    private final TelefonoDao telefonoDao;

    @Override
    public Telefono saveTelefono(Telefono telefono) {
 
            return telefonoDao.save(telefono);
    }

    @Override
    public List<Telefono> getAllTelefonos() {

            return telefonoDao.findAll();
    }

    @Override
    @Transactional
    public void deleteByEstudiante(Estudiante estudiante) {

            telefonoDao.deleteByEstudiante(estudiante);
    }

    @Override
    public List<Telefono> findByEstudiante(Estudiante estudiante) {

            return telefonoDao.findByEstudiante(estudiante);
    }

    @Override
    public boolean existsByEstudiante(Estudiante estudiante) {

            return telefonoDao.existsByEstudiante(estudiante);
    }

    @Override
    public boolean existsByNumero(String numero) {
        
            return telefonoDao.existsByNumero(numero);
        }

    @Override
    public Optional<Telefono> findByNumero(String numero) {
        return telefonoDao.findByNumero(numero);
    }

}