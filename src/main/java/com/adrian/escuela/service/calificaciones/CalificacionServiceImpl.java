package com.adrian.escuela.service.calificaciones;

import com.adrian.escuela.dto.calificaciones.CalificacionRequest;
import com.adrian.escuela.dto.calificaciones.CalificacionResponse;
import com.adrian.escuela.entities.Calificacion;
import com.adrian.escuela.entities.Inscripcion;
import com.adrian.escuela.mappers.CalificacionMapper;
import com.adrian.escuela.repositories.CalificacionRepository;
import com.adrian.escuela.repositories.InscripcionRepository;
import com.adrian.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CalificacionServiceImpl implements CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final CalificacionMapper calificacionMapper;
    private final InscripcionRepository inscripcionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CalificacionResponse> listar() {
        log.info("Listado de todas las calificaciones solicitado");
        return calificacionRepository.findAll().stream()
                .map(calificacionMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CalificacionResponse obtenerPorId(Long id) {
        return calificacionMapper.entidadAResponse(obtenerCalificacion(id));
    }

    @Override
    public CalificacionResponse registrar(CalificacionRequest request) {
        log.info("Registrando nueva calificacion...");

        Inscripcion inscripcion = ServiceUtils.obtenerEntidadOException(
                inscripcionRepository, request.idInscripcion(), Inscripcion.class);

        if (calificacionRepository.existsByInscripcionId(request.idInscripcion()))
            throw new IllegalArgumentException(
                    "Ya existe una calificacion registrada para esta inscripcion");

        Calificacion calificacion = calificacionMapper.requestAEntidad(request);
        calificacion.setInscripcion(inscripcion);
        calificacion.setFechaRegistro(LocalDate.now());

        calificacionRepository.save(calificacion);

        log.info("Nueva calificacion registrada con id: {}", calificacion.getId());
        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public CalificacionResponse actualizar(CalificacionRequest request, Long id) {
        Calificacion calificacion = obtenerCalificacion(id);

        log.info("Actualizando calificacion con id: {}", id);

        calificacion.setCalificacion(request.calificacion());
        calificacion.setFechaRegistro(LocalDate.now());

        calificacionRepository.save(calificacion);
        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public void eliminar(Long id) {
        Calificacion calificacion = obtenerCalificacion(id);
        log.info("Eliminando calificacion con id: {}", id);
        calificacionRepository.delete(calificacion);
        log.info("Calificacion con id {} eliminada", id);
    }

    private Calificacion obtenerCalificacion(Long id) {
        return ServiceUtils.obtenerEntidadOException(calificacionRepository, id, Calificacion.class);
    }
}