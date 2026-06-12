package com.adrian.escuela.service.inscripciones;

import com.adrian.escuela.dto.inscripciones.InscripcionRequest;
import com.adrian.escuela.dto.inscripciones.InscripcionResponse;
import com.adrian.escuela.entities.Alumno;
import com.adrian.escuela.entities.Grupo;
import com.adrian.escuela.entities.Inscripcion;
import com.adrian.escuela.mappers.InscripcionMapper;
import com.adrian.escuela.repositories.AlumnoRepository;
import com.adrian.escuela.repositories.CalificacionRepository;
import com.adrian.escuela.repositories.GrupoRepository;
import com.adrian.escuela.repositories.InscripcionRepository;
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
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final InscripcionMapper inscripcionMapper;
    private final AlumnoRepository alumnoRepository;
    private final GrupoRepository grupoRepository;
    private final CalificacionRepository calificacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponse> listar() {
        log.info("Listado de todas las inscripciones solicitado");
        return inscripcionRepository.findAll().stream()
                .map(inscripcionMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InscripcionResponse obtenerPorId(Long id) {
        return inscripcionMapper.entidadAResponse(obtenerInscripcion(id));
    }

    @Override
    public InscripcionResponse registrar(InscripcionRequest request) {
        log.info("Registrando nueva inscripcion...");

        // 1. Obtenemos alumno y grupo
        Alumno alumno = ServiceUtils.obtenerEntidadOException(
                alumnoRepository, request.idAlumno(), Alumno.class);
        Grupo grupo = ServiceUtils.obtenerEntidadOException(
                grupoRepository, request.idGrupo(), Grupo.class);

        // 2. Validamos que no exista la misma combinacion
        if (inscripcionRepository.existsByAlumno_IdAndGrupo_Id(
                request.idAlumno(), request.idGrupo()))
            throw new IllegalArgumentException(
                    "El alumno ya está inscrito en ese grupo");

        // 3. Construimos y guardamos
        Inscripcion inscripcion = inscripcionMapper.requestAEntidad(request);
        inscripcion.setAlumno(alumno);
        inscripcion.setGrupo(grupo);

        inscripcionRepository.save(inscripcion);

        log.info("Nueva inscripcion registrada con id: {}", inscripcion.getId());
        return inscripcionMapper.entidadAResponse(inscripcion);
    }

    @Override
    public InscripcionResponse actualizar(InscripcionRequest request, Long id) {
        Inscripcion inscripcion = obtenerInscripcion(id);

        log.info("Actualizando inscripcion con id: {}", id);

        Alumno alumno = ServiceUtils.obtenerEntidadOException(
                alumnoRepository, request.idAlumno(), Alumno.class);
        Grupo grupo = ServiceUtils.obtenerEntidadOException(
                grupoRepository, request.idGrupo(), Grupo.class);

        // Validamos unicidad excluyendo la inscripcion actual
        if (inscripcionRepository.existsByAlumno_IdAndGrupo_IdAndIdNot(
                request.idAlumno(), request.idGrupo(), id))
            throw new IllegalArgumentException(
                    "El alumno ya está inscrito en ese grupo");

        inscripcion.setAlumno(alumno);
        inscripcion.setGrupo(grupo);

        inscripcionRepository.save(inscripcion);
        return inscripcionMapper.entidadAResponse(inscripcion);
    }

    @Override
    public void eliminar(Long id) {
        Inscripcion inscripcion = obtenerInscripcion(id);

        log.info("Eliminando inscripcion con id: {}", id);

        // No se puede eliminar si tiene calificacion
        if (calificacionRepository.existsByInscripcionId(id))
            throw new IllegalStateException(
                    "No se puede eliminar la inscripcion porque tiene una calificacion registrada");

        inscripcionRepository.delete(inscripcion);
        log.info("Inscripcion con id {} eliminada", id);
    }

    private Inscripcion obtenerInscripcion(Long id) {
        return ServiceUtils.obtenerEntidadOException(inscripcionRepository, id, Inscripcion.class);
    }
}