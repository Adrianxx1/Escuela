package com.adrian.escuela.dto.horarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HorarioRequest(

        @NotNull(message = "El grupo es requerido")
        Long idGrupo,

        @NotBlank(message = "El día es requerido")
        String dia,

        @NotBlank(message = "La hora de inicio es requerida")
        String horaInicio,

        @NotBlank(message = "La hora de fin es requerida")
        String horaFin

) {}