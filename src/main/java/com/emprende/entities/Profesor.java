package com.emprende.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.emprende.models.Genero;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "profesores")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"facultad"})
@Entity
@Builder
public class Profesor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El nombre no puede estar vacío ")
    @NotBlank(message = "El nombre no puede solo contener espacios en blanco")
    @Size(min = 4, max = 30, message = "El nombre tiene que estar entre 4 y 30 caracteres")
    @Pattern(regexp = "^([A-ZÁÉÍÓÚÑ]{1}[a-záéíóúñ]+(\s)?)+$", message = "El(los) nombres deben comenzar por Mayúscula y contener solo letras")
    private String nombre;

    @NotNull(message = "El primer apellido no puede estar vacío ")
    @NotBlank(message = "El primer apellido no puede solo contener espacios en blanco")
    @Size(min = 4, max = 30, message = "El primer apellido tiene que estar entre 4 y 30 caracteres")
    @Pattern(regexp = "^([A-ZÁÉÍÓÚÑ]{1}[a-záéíóúñ]+(\s)?)+$", message = "El primer apellido debe comenzar por Mayúscula y contener solo letras")
    private String primerApellido;
    private String segundoApellido;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Debe seleccionar un género")
    private Genero genero;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "La fecha de Alta tiene que ser igual o anterior a la fecha actual")
    private LocalDate fechaAltaFacultad;
       
    @Positive(message = "El valor no debe ser un número Negativo")
    private BigDecimal salario;
    private String foto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facultad_id", nullable = false)
    @NotNull(message = "Debe seleccionar una facultad")
    private Facultad facultad;
}
