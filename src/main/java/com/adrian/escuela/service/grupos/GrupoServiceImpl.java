package com.adrian.escuela.service.grupos;

import com.adrian.escuela.dto.grupos.GrupoRequest;
import com.adrian.escuela.dto.grupos.GrupoResponse;
import com.adrian.escuela.entities.Aula;
import com.adrian.escuela.entities.Curso;
import com.adrian.escuela.entities.Grupo;
import com.adrian.escuela.entities.Maestro;
import com.adrian.escuela.mappers.GrupoMapper;
import com.adrian.escuela.repositories.*;
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
public class GrupoServiceImpl implements GrupoService {

    private final GrupoRepository grupoRepository;
    private final GrupoMapper grupoMapper;
    private final CursoRepository cursoRepository;
    private final MaestroRepository maestroRepository;
    private final AulaRepository aulaRepository;
    private final InscripcionRepository inscripcionRepository;
    private final HorarioRepository horarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GrupoResponse> listar() {
        log.info("Listado de todos los grupos solicitado");
        return grupoRepository.findAll().stream()
                .map(grupoMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GrupoResponse obtenerPorId(Long id) {
        return grupoMapper.entidadAResponse(obtenerGrupo(id));
    }

    @Override
    public GrupoResponse registrar(GrupoRequest request) {
        log.info("Registrando nuevo grupo...");

        Curso curso = ServiceUtils.obtenerEntidadOException(
                cursoRepository, request.idCurso(), Curso.class);
        Maestro maestro = ServiceUtils.obtenerEntidadOException(
                maestroRepository, request.idMaestro(), Maestro.class);
        Aula aula = ServiceUtils.obtenerEntidadOException(
                aulaRepository, request.idAula(), Aula.class);

        validarCombinacionUnica(request);

        Grupo grupo = grupoMapper.requestAEntidad(request);
        grupo.setCurso(curso);
        grupo.setMaestro(maestro);
        grupo.setAula(aula);

        grupoRepository.save(grupo);

        log.info("Nuevo grupo registrado con id: {}", grupo.getId());
        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public GrupoResponse actualizar(GrupoRequest request, Long id) {
        Grupo grupo = obtenerGrupo(id);

        log.info("Actualizando grupo con id: {}", id);

        Curso curso = ServiceUtils.obtenerEntidadOException(
                cursoRepository, request.idCurso(), Curso.class);
        Maestro maestro = ServiceUtils.obtenerEntidadOException(
                maestroRepository, request.idMaestro(), Maestro.class);
        Aula aula = ServiceUtils.obtenerEntidadOException(
                aulaRepository, request.idAula(), Aula.class);

        validarCombinacionUnicaActualizar(request, id);

        grupo.setCurso(curso);
        grupo.setMaestro(maestro);
        grupo.setAula(aula);
        grupo.setPeriodo(request.periodo().trim());

        grupoRepository.save(grupo);
        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public void eliminar(Long id) {
        Grupo grupo = obtenerGrupo(id);

        log.info("Eliminando grupo con id: {}", id);

        if (inscripcionRepository.existsByGrupo_Id(id))
            throw new IllegalStateException("No se puede eliminar el grupo porque tiene inscripciones");

        if (horarioRepository.existsByGrupoId(id))
            throw new IllegalStateException("No se puede eliminar el grupo porque tiene horarios");

        grupoRepository.delete(grupo);
        log.info("Grupo con id {} eliminado", id);
    }

    private Grupo obtenerGrupo(Long id) {
        return ServiceUtils.obtenerEntidadOException(grupoRepository, id, Grupo.class);
    }

    private void validarCombinacionUnica(GrupoRequest request) {
        if (grupoRepository.existsByCurso_IdAndMaestro_IdAndAula_IdAndPeriodo(
                request.idCurso(),
                request.idMaestro(),
                request.idAula(),
                request.periodo()))
            throw new IllegalArgumentException(
                    "Ya existe un grupo con esa combinación de curso, maestro, aula y periodo");
    }

    private void validarCombinacionUnicaActualizar(GrupoRequest request, Long id) {
        if (grupoRepository.existsByCurso_IdAndMaestro_IdAndAula_IdAndPeriodoAndIdNot(
                request.idCurso(),
                request.idMaestro(),
                request.idAula(),
                request.periodo(),
                id))
            throw new IllegalArgumentException(
                    "Ya existe un grupo con esa combinación de curso, maestro, aula y periodo");
    }
}