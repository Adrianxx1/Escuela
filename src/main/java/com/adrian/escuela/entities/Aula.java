package com.adrian.escuela.entities;

import com.adrian.escuela.utils.StringCustomUtils;
import com.adrian.escuela.utils.ValoresNumericosUtils;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
@Table(name = "AULAS")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AULA")
    private Long id;

    @Column(name = "NOMBRE", length = 30, nullable = false, unique = true)
    private String nombre;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    @Builder.Default
    @OneToMany(mappedBy = "aula")
    private List<Grupo> grupos = new ArrayList<>();

    public void actualizar(
            String nombre, Integer capacidad
    ) {
        validarDatos(nombre, capacidad);
        this.nombre = nombre.trim();
        this.capacidad = capacidad;
    }
    private void validarDatos(String nombre, Integer capacidad) {
        StringCustomUtils.validarTamanio(nombre.trim(), 4, 30,
                "El nombre es requerido y debe tener entre 4 y 30 caracteres");

        ValoresNumericosUtils.validarEnteroPositivo(capacidad,
                "La capacidad debe ser mayor a 0");
    }
}