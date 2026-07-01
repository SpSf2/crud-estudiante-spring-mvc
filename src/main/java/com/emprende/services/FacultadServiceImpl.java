package com.emprende.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.emprende.dao.FacultadDao;
import com.emprende.entities.Facultad;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FacultadServiceImpl implements FacultadService {

    //Esta clase necesita del Dao para implementar sus metodos:

    //Inyectamos la capa Dao:
    private final FacultadDao facultadDao;

    @Override
    public Facultad saveFacultad(Facultad facultad) {
        
        return facultadDao.save(facultad);
    }

    @Override
    public List<Facultad> getAllFacultades() {
        
        return facultadDao.findAll();
    }

    @Override
    public long count() {
       return facultadDao.count();
    }

    @Override
    public void persistirFacultad(Facultad facultad) {
        facultadDao.save(facultad);
    }


    @Override
    public boolean existeFacultadPorNombre(String nombre) {
        return facultadDao.existsByNombre(nombre);
    }

    
}
