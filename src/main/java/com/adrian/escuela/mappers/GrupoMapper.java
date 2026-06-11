package com.adrian.escuela.mappers;

import com.adrian.escuela.dto.grupos.GrupoRequest;
import com.adrian.escuela.dto.grupos.GrupoResponse;
import com.adrian.escuela.dto.datos.DatosAula;
import com.adrian.escuela.dto.datos.DatosMaestro;
import com.adrian.escuela.entities.Grupo;
import com.adrian.escuela.entities.Horario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GrupoMapper implements CommonMapper<GrupoRequest, GrupoResponse, Grupo> {

    private final CursoMapper cursoMapper;

    @Override
    public Grupo requestAEntidad(GrupoRequest request) {
        if (request == null) return null;
        return Grupo.builder()
                .periodo(request.periodo().trim())
                .build();
    }

    @Override
    public GrupoResponse entidadAResponse(Grupo entidad) {
        if (entidad == null) return null;

        List<String> horarios = entidad.getHorarios().stream()
                .map(this::horarioAString)
                .toList();

        DatosMaestro datosMaestro = new DatosMaestro(
                String.join(" ",
                        entidad.getMaestro().getNombre(),
                        entidad.getMaestro().getApellidoPaterno(),
                        entidad.getMaestro().getApellidoMaterno()),
                entidad.getMaestro().getEmail(),
                entidad.getMaestro().getTelefono()
        );

        DatosAula datosAula = new DatosAula(
                entidad.getAula().getNombre(),
                entidad.getAula().getCapacidad()
        );

        return new GrupoResponse(
                entidad.getId(),
                cursoMapper.entidadDatosCurso(entidad.getCurso()),
                datosMaestro,
                datosAula,
                horarios,
                entidad.getPeriodo()
        );
    }

    private String horarioAString(Horario horario) {
        return horario.getDiaSemana().getDescripcion() + " " +
                horario.getHoraInicio() + " - " +
                horario.getHoraFin();
    }
}