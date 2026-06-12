package com.adrian.escuela.dto.inscripciones;

import com.adrian.escuela.dto.datos.DatosAlumno;
import com.adrian.escuela.dto.datos.DatosGrupo;

import java.math.BigDecimal;

public record InscripcionResponse(
        Long id,
        DatosAlumno alumno,
        DatosGrupo grupo,
        BigDecimal calificacion,
        String fechaInscripcion
) {}