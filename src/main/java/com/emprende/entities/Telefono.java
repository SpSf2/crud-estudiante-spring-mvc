package com.emprende.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name="telefonos")
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Telefono implements Serializable{

    private static final long SerialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    private Estudiante estudiante;


}
