package com.emprende.services;

import java.util.List;

import com.emprende.entities.Facultad;

public interface FacultadService {

    //Definir los metodos que se van a implementar en la clase 
    // FacultadServiceImpl

    Facultad saveFacultad(Facultad facultad);

    // Metodo para obtener todo las Facultades:
     List<Facultad> getAllFacultades();

     long count();


     
}
