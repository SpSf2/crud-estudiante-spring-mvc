package com.emprende.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name="telefonos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
public class Telefono implements Serializable{

    private static final long SerialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotNull(message = "El numero no puede estar vacío ")
    @NotBlank(message = "El numero no puede solo contener espacios en blanco")
    @Size(min = 4, max = 30, message = "El numero tiene que estar entre 4 y 30 caracteres")
    @Column(name="numero", nullable=false, unique=true)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    private Estudiante estudiante;


}
