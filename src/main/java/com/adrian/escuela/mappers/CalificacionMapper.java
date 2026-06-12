package com.adrian.escuela.mappers;

import com.adrian.escuela.dto.calificaciones.CalificacionRequest;
import com.adrian.escuela.dto.calificaciones.CalificacionResponse;
import com.adrian.escuela.dto.datos.DatosAlumno;
import com.adrian.escuela.dto.datos.DatosGrupo;
import com.adrian.escuela.dto.datos.DatosInscripcion;
import com.adrian.escuela.entities.Calificacion;
import com.adrian.escuela.utils.StringCustomUtils;
import org.springframework.stereotype.Component;

@Component
public class CalificacionMapper implements CommonMapper<CalificacionRequest, CalificacionResponse, Calificacion> {

    @Override
    public Calificacion requestAEntidad(CalificacionRequest request) {
        return null; // el service construye la entidad directamente
    }

    @Override
    public CalificacionResponse entidadAResponse(Calificacion entidad) {
        if (entidad == null) return null;

        DatosAlumno datosAlumno = new DatosAlumno(
                String.join(" ",
                        entidad.getInscripcion().getAlumno().getNombre(),
                        entidad.getInscripcion().getAlumno().getApellidoPaterno(),
                        entidad.getInscripcion().getAlumno().getApellidoMaterno()),
                entidad.getInscripcion().getAlumno().getMatricula(),
                entidad.getInscripcion().getAlumno().getEmail(),
                StringCustomUtils.localDateAString(entidad.getInscripcion().getAlumno().getFechaIngreso())
        );

        DatosGrupo datosGrupo = new DatosGrupo(
                entidad.getInscripcion().getGrupo().getCurso().getNombre(),
                String.join(" ",
                        entidad.getInscripcion().getGrupo().getMaestro().getNombre(),
                        entidad.getInscripcion().getGrupo().getMaestro().getApellidoPaterno(),
                        entidad.getInscripcion().getGrupo().getMaestro().getApellidoMaterno()),
                entidad.getInscripcion().getGrupo().getAula().getNombre(),
                entidad.getInscripcion().getGrupo().getPeriodo()
        );

        DatosInscripcion datosInscripcion = new DatosInscripcion(
                datosAlumno,
                datosGrupo,
                StringCustomUtils.localDateAString(entidad.getInscripcion().getFechaInscripcion())
        );

        return new CalificacionResponse(
                entidad.getId(),
                datosInscripcion,
                entidad.getCalificacion(),
                StringCustomUtils.localDateAString(entidad.getFechaRegistro())
        );
    }
}