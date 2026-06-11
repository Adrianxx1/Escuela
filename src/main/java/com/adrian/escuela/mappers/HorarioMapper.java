package com.adrian.escuela.mappers;

import com.adrian.escuela.dto.datos.DatosGrupo;
import com.adrian.escuela.dto.horarios.HorarioRequest;
import com.adrian.escuela.dto.horarios.HorarioResponse;
import com.adrian.escuela.entities.Horario;
import org.springframework.stereotype.Component;

@Component
public class HorarioMapper implements CommonMapper<HorarioRequest, HorarioResponse, Horario> {

    @Override
    public Horario requestAEntidad(HorarioRequest request) {
        if (request == null) return null;
        return Horario.builder()
                .horaInicio(request.horaInicio())
                .horaFin(request.horaFin())
                .build();
    }

    @Override
    public HorarioResponse entidadAResponse(Horario entidad) {
        if (entidad == null) return null;

        DatosGrupo datosGrupo = new DatosGrupo(
                entidad.getGrupo().getCurso().getNombre(),
                String.join(" ",
                        entidad.getGrupo().getMaestro().getNombre(),
                        entidad.getGrupo().getMaestro().getApellidoPaterno(),
                        entidad.getGrupo().getMaestro().getApellidoMaterno()),
                entidad.getGrupo().getAula().getNombre(),
                entidad.getGrupo().getPeriodo()
        );

        String horarioFormateado = entidad.getDiaSemana().getDescripcion() + " " +
                entidad.getHoraInicio() + " - " +
                entidad.getHoraFin();

        return new HorarioResponse(
                entidad.getId(),
                datosGrupo,
                horarioFormateado
        );
    }
}