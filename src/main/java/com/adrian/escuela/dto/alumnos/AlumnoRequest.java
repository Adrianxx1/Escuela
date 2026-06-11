package com.adrian.escuela.dto.alumnos;

import jakarta.validation.constraints.*;

public record AlumnoRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(min = 4, max = 50, message = "El nombre debe tener entre 4 y 50 caracteres")
        String nombre,

        @NotBlank(message = "El apellido paterno es requerido")
        @Size(min = 4, max = 50, message = "El apellido paterno debe tener entre 4 y 50 caracteres")
        String apellidoPaterno,

        @NotBlank(message = "El apellido materno es requerido")
        @Size(min = 4, max = 50, message = "El apellido materno debe tener entre 4 y 50 caracteres")
        String apellidoMaterno


) {}