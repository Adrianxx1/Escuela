package com.adrian.escuela.dto.horarios;

import com.adrian.escuela.dto.datos.DatosGrupo;

public record HorarioResponse(
        Long id,
        DatosGrupo grupo,
        String horario

) {
}
