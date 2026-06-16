package com.emprende;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

	private final EstudianteService estudianteService;
	private final FacultadService facultadService;
	
	public static void main(String[] args) {
		SpringApplication.run(CrudEstudianteSpringMvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
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
					.fechaMatriculación(LocalDate.of(15, Month.APRIL, 2))
					.direccion("Calle 3 numero 2 piso 4")
					.facultad(facultad1)
					.telefonos(Set.of(Telefono.builder().numero("123456789").build(),
				    Telefono.builder().numero("987654321").build()))
					.correos(Set.of(Correo.builder().email("jjj@jjj.com").build(),
					Correo.builder().email("kkk@jjj.com").build()))	
					.build();

					/*Antes de persistir el estudiante, para que en las tablas de correos y telefonos
					no salga "el campo estudiante_id "null", hay que estbalecer la relación entre el 
					estudiante y sus correos y telefonos:     */

					estudiante1.getTelefonos().forEach(telefono -> telefono.setEstudiante(estudiante1));
					estudiante1.getCorreos().forEach(correo -> correo.setEstudiante(estudiante1));
					
					estudianteService.saveEstudiante(estudiante1);

			







	}

}
