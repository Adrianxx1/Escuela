package com.adrian.escuela.dto.alumnos;


public record AlumnoResponse(
        Long id,
        String nombre,
        String email,
        String matricula,
        String fechaIngreso
) {

}

