package com.adrian.escuela.dto.aulas;

import jakarta.validation.constraints.*;

public record AulaRequest(

        @NotBlank(message = "El nombre del aula es requerido")
        @Size(min = 5, max = 30, message = "El nombre del aula debe tener entre 5 y 30 caracteres")
        String nombre,

        @NotNull(message = "La capacidad del aula es requerida")
        @Min(value = 1, message = "La capacidad mínima es 1")
        Integer capacidad


) {}