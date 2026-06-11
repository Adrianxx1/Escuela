package com.adrian.escuela.mappers;

import com.adrian.escuela.dto.alumnos.AlumnoRequest;
import com.adrian.escuela.dto.alumnos.AlumnoResponse;
import com.adrian.escuela.entities.Alumno;
import com.adrian.escuela.utils.StringCustomUtils;
import org.springframework.stereotype.Component;

@Component
public class AlumnoMapper implements CommonMapper<AlumnoRequest, AlumnoResponse, Alumno> {

    @Override
    public Alumno requestAEntidad(AlumnoRequest request) {
        if (request == null) return null;
        return Alumno.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .build();
    }

    @Override
    public AlumnoResponse entidadAResponse(Alumno entidad) {
        if (entidad == null) return null;
        return new AlumnoResponse(
                entidad.getId(),
                String.join(" ",
                        entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getMatricula(),
                StringCustomUtils.localDateAString(entidad.getFechaIngreso())
        );
    }
}