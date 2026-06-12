package com.adrian.escuela.dto.calificaciones;

import com.adrian.escuela.dto.datos.DatosInscripcion;
import java.math.BigDecimal;

public record CalificacionResponse(
        Long id,
        DatosInscripcion inscripcion,
        BigDecimal calificacion,
        String fechaRegistro
) {}