package com.adrian.escuela.mappers;

import com.adrian.escuela.dto.datos.DatosAlumno;
import com.adrian.escuela.dto.datos.DatosGrupo;
import com.adrian.escuela.dto.inscripciones.InscripcionRequest;
import com.adrian.escuela.dto.inscripciones.InscripcionResponse;
import com.adrian.escuela.entities.Inscripcion;
import com.adrian.escuela.utils.StringCustomUtils;
import org.springframework.stereotype.Component;

@Component
public class InscripcionMapper implements CommonMapper<InscripcionRequest, InscripcionResponse, Inscripcion> {

    @Override
    public Inscripcion requestAEntidad(InscripcionRequest request) {
        // Solo creamos la inscripcion vacía, el service asigna alumno y grupo
        if (request == null) return null;
        return Inscripcion.builder().build();
    }

    @Override
    public InscripcionResponse entidadAResponse(Inscripcion entidad) {
        if (entidad == null) return null;

        // DatosAlumno
        DatosAlumno datosAlumno = new DatosAlumno(
                String.join(" ",
                        entidad.getAlumno().getNombre(),
                        entidad.getAlumno().getApellidoPaterno(),
                        entidad.getAlumno().getApellidoMaterno()),
                entidad.getAlumno().getMatricula(),
                entidad.getAlumno().getEmail(),
                StringCustomUtils.localDateAString(entidad.getAlumno().getFechaIngreso())
        );

        // DatosGrupo
        DatosGrupo datosGrupo = new DatosGrupo(
                entidad.getGrupo().getCurso().getNombre(),
                String.join(" ",
                        entidad.getGrupo().getMaestro().getNombre(),
                        entidad.getGrupo().getMaestro().getApellidoPaterno(),
                        entidad.getGrupo().getMaestro().getApellidoMaterno()),
                entidad.getGrupo().getAula().getNombre(),
                entidad.getGrupo().getPeriodo()
        );

        // Calificacion — puede ser null si no tiene calificacion registrada
        var calificacion = entidad.getCalificacion() != null
                ? entidad.getCalificacion().getCalificacion()
                : null;

        return new InscripcionResponse(
                entidad.getId(),
                datosAlumno,
                datosGrupo,
                calificacion,
                StringCustomUtils.localDateAString(entidad.getFechaInscripcion())
        );
    }
}