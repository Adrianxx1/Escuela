package com.adrian.escuela.service.horarios;

import com.adrian.escuela.dto.horarios.HorarioRequest;
import com.adrian.escuela.dto.horarios.HorarioResponse;
import com.adrian.escuela.entities.Grupo;
import com.adrian.escuela.entities.Horario;
import com.adrian.escuela.enums.DiaSemana;
import com.adrian.escuela.mappers.HorarioMapper;
import com.adrian.escuela.repositories.GrupoRepository;
import com.adrian.escuela.repositories.HorarioRepository;
import com.adrian.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository horarioRepository;
    private final HorarioMapper horarioMapper;
    private final GrupoRepository grupoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<HorarioResponse> listar() {
        log.info("Listado de todos los horarios solicitado");
        return horarioRepository.listarHorariosOrdenados()
                .stream()
                .map(horarioMapper::rowAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HorarioResponse obtenerPorId(Long id) {
        return horarioMapper.entidadAResponse(obtenerHorario(id));
    }

    @Override
    public HorarioResponse registrar(HorarioRequest request) {
        log.info("Registrando nuevo horario...");

        Grupo grupo = ServiceUtils.obtenerEntidadOException(
                grupoRepository, request.idGrupo(), Grupo.class);

        DiaSemana dia = DiaSemana.obtenerCategoriaPorDescripcion(request.dia());

        validarHoras(request.horaInicio(), request.horaFin());
        validarTraslapeGrupo(request.idGrupo(), dia.getDescripcion(),
                request.horaInicio(), request.horaFin(), null);
        validarTraslapeAula(grupo.getAula().getId(), dia.getDescripcion(),
                request.horaInicio(), request.horaFin(), null);

        Horario horario = horarioMapper.requestAEntidad(request);
        horario.setGrupo(grupo);
        horario.setDiaSemana(dia);

        horarioRepository.save(horario);
        log.info("Nuevo horario registrado con id: {}", horario.getId());
        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public HorarioResponse actualizar(HorarioRequest request, Long id) {
        Horario horario = obtenerHorario(id);
        log.info("Actualizando horario con id: {}", id);

        Grupo grupo = ServiceUtils.obtenerEntidadOException(
                grupoRepository, request.idGrupo(), Grupo.class);

        DiaSemana dia = DiaSemana.obtenerCategoriaPorDescripcion(request.dia());

        validarHoras(request.horaInicio(), request.horaFin());
        validarTraslapeGrupo(request.idGrupo(), dia.getDescripcion(),
                request.horaInicio(), request.horaFin(), id);
        validarTraslapeAula(grupo.getAula().getId(), dia.getDescripcion(),
                request.horaInicio(), request.horaFin(), id);

        horario.setGrupo(grupo);
        horario.setDiaSemana(dia);
        horario.setHoraInicio(request.horaInicio());
        horario.setHoraFin(request.horaFin());

        horarioRepository.save(horario);
        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public void eliminar(Long id) {
        Horario horario = obtenerHorario(id);
        log.info("Eliminando horario con id: {}", id);
        horarioRepository.delete(horario);
        log.info("Horario con id {} eliminado", id);
    }

    private Horario obtenerHorario(Long id) {
        return ServiceUtils.obtenerEntidadOException(horarioRepository, id, Horario.class);
    }

    private void validarHoras(String horaInicio, String horaFin) {
        if (horaFin.compareTo(horaInicio) <= 0)
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio");
    }

    private void validarTraslapeGrupo(Long grupoId, String dia,
                                      String inicio, String fin, Long idExcluir) {
        if (horarioRepository.existeTraslapeEnGrupo(grupoId, dia, inicio, fin, idExcluir) > 0)
            throw new IllegalArgumentException(
                    "El grupo ya tiene un horario que se traslapa en ese día y hora");
    }

    private void validarTraslapeAula(Long aulaId, String dia,
                                     String inicio, String fin, Long idExcluir) {
        if (horarioRepository.existeTraslapeEnAula(aulaId, dia, inicio, fin, idExcluir) > 0)
            throw new IllegalArgumentException(
                    "El aula ya está ocupada en ese día y hora");
    }
}