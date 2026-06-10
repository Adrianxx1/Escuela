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
@Table(name = "CURSOS")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CURSO")
    private Long id;

    @Column(name = "NOMBRE", length = 100, nullable = false, unique = true)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;
    @Column(name = "CREDITOS", nullable = false)
    private Integer creditos;


    @Builder.Default
    @OneToMany(mappedBy = "curso")
    private List<Grupo> grupos = new ArrayList<>();

    public void actualizar(String nombre, String descripcion, Integer creditos) {
        validarDatos(nombre, descripcion, creditos);
        this.nombre = nombre.trim();
        this.descripcion = descripcion != null ? descripcion.trim() : null;
        this.creditos = creditos;
    }

    private void validarDatos(String nombre, String descripcion, Integer creditos) {
        StringCustomUtils.validarTamanio(nombre.trim(), 4, 100,
                "El nombre es requerido y debe tener entre 4 y 100 caracteres");

        if (descripcion != null)
            StringCustomUtils.validarTamanio(descripcion.trim(), 1, 200,
                    "La descripción debe tener máximo 200 caracteres");

        ValoresNumericosUtils.validarEnteroPositivo(creditos,
                "Los créditos deben ser mayor a 0");
    }
}