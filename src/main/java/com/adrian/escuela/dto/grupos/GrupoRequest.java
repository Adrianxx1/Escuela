package com.adrian.escuela.dto.grupos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GrupoRequest(

        @NotNull(message = "El curso es requerido")
        Long idCurso,

        @NotNull(message = "El maestro es requerido")
        Long idMaestro,

        @NotNull(message = "El aula es requerida")
        Long idAula,

        @NotBlank(message = "El periodo es requerido")
        String periodo

) {}