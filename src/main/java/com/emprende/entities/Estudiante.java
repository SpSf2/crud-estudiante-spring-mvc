package com.emprende.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.emprende.models.Genero;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name="estudiantes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"telefonos", "correos"})
@Entity
@Builder
public class Estudiante implements Serializable{

    private static final long SerialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "El nombre no puede estar vacío ")
    @NotBlank(message = "El nombre no puede solo contener espacios en blanco")
    @Size(min = 4, max = 30, message = "El nombre tiene que estar entre 4 y 30 caracteres")
    @Pattern(regexp = "^([A-ZÁÉÍÓÚÑ]{1}[a-záéíóúñ]+(\s)?)+$", message = "El(los) nombres deben comenzar por mayuscula y contener solo letras")
    private String nombre;

    @NotNull(message = "El primer apellido no puede estar vacío ")
    @NotBlank(message = "El primer apellido no puede solo contener espacios en blanco")
    @Size(min = 4, max = 30, message = "El primer apellido tiene que estar entre 4 y 30 caracteres")
    private String primerApellido;
    private String segundoApellido ;
    
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "La fecha de Matriculación tiene que ser igual o anterior a la fecha actual")
    private LocalDate fechaMatriculacion;

    @NotNull(message = "La direccion no puede estar vacía ")
    @NotBlank(message = "La direccion no puede solo contener espacios en blanco")
    @Size(min = 4, max = 100, message = "La direccion tiene que estar entre 4 y 100 caracteres")
    private String direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    private Facultad facultad;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "estudiante")
    private Set<Telefono> telefonos = new HashSet<>();

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "estudiante")
    private Set<Correo> correos = new HashSet<>();

    private String foto;
    
}
