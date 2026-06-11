package com.adrian.escuela.dto.grupos;

import com.adrian.escuela.dto.datos.DatosAula;
import com.adrian.escuela.dto.datos.DatosCurso;
import com.adrian.escuela.dto.datos.DatosMaestro;

import java.util.List;

public record GrupoResponse(
        Long id,
        DatosCurso curso,
        DatosMaestro maestro,
        DatosAula aula,
        List<String> horarios,
        String periodo
) {}