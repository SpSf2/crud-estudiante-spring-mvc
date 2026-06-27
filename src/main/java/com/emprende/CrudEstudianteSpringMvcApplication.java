package com.emprende;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.Trigger;

import com.emprende.entities.Correo;
import com.emprende.entities.Estudiante;
import com.emprende.entities.Facultad;
import com.emprende.entities.Facultad.FacultadBuilder;
import com.emprende.entities.Telefono;
import com.emprende.models.Genero;
import com.emprende.services.EstudianteService;
import com.emprende.services.FacultadService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class CrudEstudianteSpringMvcApplication implements CommandLineRunner{
	//Servicios inyectados
	private final EstudianteService estudianteService;
	private final FacultadService facultadService;
	
	public static void main(String[] args) {
		SpringApplication.run(CrudEstudianteSpringMvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//Esto evita que se reinserten los registros de ejemplo en la DB cuando hay un update
		//activo en el application.properties
		if (facultadService.count() > 0 || estudianteService.count() > 0) {
        return;
    	}
	
		//Creamos registros de ejemplo que deberían reflejarse en la DB, lo cual nos permite comprobar
		//Que la aplicación funciona correctamente, la capa de servicio y la de persistencia.

			//Creamos Facultades:

			Facultad facultad1 = Facultad.builder()
					.nombre("IT")
					.build();

			Facultad facultad2 = Facultad.builder()
					.nombre("RRHH")
					.build();

			Facultad facultad3 = Facultad.builder()
					.nombre("Marketing")
					.build();

			Facultad facultad4 = Facultad.builder()
					.nombre("Finanzas")
					.build();

			//Persistimos (Guardamos) las Facultades:

			facultadService.saveFacultad(facultad1);
			facultadService.saveFacultad(facultad2);
			facultadService.saveFacultad(facultad3);
			facultadService.saveFacultad(facultad4);

			//Creamos Estudiantes:

			Estudiante estudiante1 = Estudiante.builder()
					.nombre("José")
					.primerApellido("López")
					.segundoApellido("García")
					.genero(Genero.HOMBRE)
					.fechaMatriculacion(LocalDate.of(2015, Month.APRIL, 2))
					.direccion("Calle 3 numero 2 piso 4")
					.facultad(facultad1)
					.telefonos(Set.of(Telefono.builder().numero("+34123456999").build(),
				    Telefono.builder().numero("+34987654333").build()))
					.correos(Set.of(Correo.builder().email("jfg@jjj.com").build(),
					Correo.builder().email("knmjjjj.com").build()))	
					.build();

					/*Antes de persistir el estudiante, para que en las tablas de correos y telefonos
					no salga "el campo estudiante_id "null", hay que establecer la relación entre el 
					estudiante y sus correos y telefonos:     */

					estudiante1.getTelefonos().forEach(telefono -> telefono.setEstudiante(estudiante1));
					estudiante1.getCorreos().forEach(correo -> correo.setEstudiante(estudiante1));
					
					estudianteService.saveEstudiante(estudiante1);

			Estudiante estudiante2 = Estudiante.builder()
					.nombre("Juan")
					.primerApellido("Pérez")
					.segundoApellido("Pérez")
					.genero(Genero.HOMBRE)
					.fechaMatriculacion(LocalDate.of(2016, Month.MARCH, 30))
					.direccion("Calle 6 numero 4 piso 5")
					.facultad(facultad2)
					.telefonos(Set.of(Telefono.builder().numero("+34963963963").build(),
				    Telefono.builder().numero("+34369369369").build()))
					.correos(Set.of(Correo.builder().email("nauj@hotmail.com").build(),
					Correo.builder().email("naujkkk@gmail.com").build()))	
					.build();

					estudiante2.getTelefonos().forEach(telefono -> telefono.setEstudiante(estudiante2));
					estudiante2.getCorreos().forEach(correo -> correo.setEstudiante(estudiante2));
					
					estudianteService.saveEstudiante(estudiante2);

			Estudiante estudiante3 = Estudiante.builder()
					.nombre("Pedro")
					.primerApellido("Suárez")
					.segundoApellido("Roldan")
					.genero(Genero.HOMBRE)
					.fechaMatriculacion(LocalDate.of(2016, Month.APRIL, 1))
					.direccion("Calle 1 numero 1 piso 1")
					.facultad(facultad3)
					.telefonos(Set.of(Telefono.builder().numero("+34852852852").build(),
				    Telefono.builder().numero("+34258258258").build()))
					.correos(Set.of(Correo.builder().email("ped@hotmail.com").build(),
					Correo.builder().email("ped@gmail.com").build()))	
					.build();

					estudiante3.getTelefonos().forEach(telefono -> telefono.setEstudiante(estudiante3));
					estudiante3.getCorreos().forEach(correo -> correo.setEstudiante(estudiante3));
					
					estudianteService.saveEstudiante(estudiante3);

			Estudiante estudiante4 = Estudiante.builder()
					.nombre("María")
					.primerApellido("Contreras")
					.segundoApellido("Parra")
					.genero(Genero.MUJER)
					.fechaMatriculacion(LocalDate.of(2018, Month.MARCH, 14))
					.direccion("Calle 6 numero 4 piso 5")
					.facultad(facultad4)
					.telefonos(Set.of(Telefono.builder().numero("+34741741741").build(),
				    Telefono.builder().numero("+34147147147").build()))
					.correos(Set.of(Correo.builder().email("mar@hotmail.com").build(),
					Correo.builder().email("mar@gmail.com").build()))	
					.build();

					estudiante4.getTelefonos().forEach(telefono -> telefono.setEstudiante(estudiante4));
					estudiante4.getCorreos().forEach(correo -> correo.setEstudiante(estudiante4));
					
					estudianteService.saveEstudiante(estudiante4);

		
	}

}
