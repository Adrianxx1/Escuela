package com.adrian.escuela.dto.calificaciones;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CalificacionRequest(

        @NotNull(message = "La inscripcion es requerida")
        Long idInscripcion,

        @NotNull(message = "La calificacion es requerida")
        @DecimalMin(value = "0.0", message = "La calificacion minima es 0")
        @DecimalMax(value = "10.0", message = "La calificacion maxima es 10")
        BigDecimal calificacion

) {}