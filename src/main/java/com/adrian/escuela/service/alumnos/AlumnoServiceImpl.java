package com.adrian.escuela.service.alumnos;

import com.adrian.escuela.dto.alumnos.AlumnoRequest;
import com.adrian.escuela.dto.alumnos.AlumnoResponse;
import com.adrian.escuela.entities.Alumno;
import com.adrian.escuela.mappers.AlumnoMapper;
import com.adrian.escuela.repositories.AlumnoRepository;
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
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final AlumnoMapper alumnoMapper;
    private final InscripcionRepository inscripcionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoResponse> listar() {
        log.info("Listado de todos los alumnos solicitado");
        return alumnoRepository.findAll().stream()
                .map(alumnoMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AlumnoResponse obtenerPorId(Long id) {
        return alumnoMapper.entidadAResponse(obtenerAlumno(id));
    }

    @Override
    public AlumnoResponse registrar(AlumnoRequest request) {
        log.info("Registrando nuevo alumno...");

        String matricula = alumnoRepository.generarMatricula(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno());

        String email = alumnoRepository.generarCorreo(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno());

        validarDatosUnicos(email, matricula);

        Alumno alumno = alumnoMapper.requestAEntidad(request);
        alumno.setEmail(email);
        alumno.setMatricula(matricula);

        alumnoRepository.save(alumno);

        log.info("Nuevo alumno registrado: {}", alumno.getNombre());
        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public AlumnoResponse actualizar(AlumnoRequest request, Long id) {
        Alumno alumno = obtenerAlumno(id);

        log.info("Actualizando alumno con id: {}", id);

        alumno.actualizar(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno());

        alumnoRepository.save(alumno);
        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public void eliminar(Long id) {
        Alumno alumno = obtenerAlumno(id);

        log.info("Eliminando alumno con id: {}", id);

        if (inscripcionRepository.existsByAlumno_Id(id))
            throw new IllegalStateException("No se puede eliminar el alumno porque tiene inscripciones");

        alumnoRepository.delete(alumno);
        log.info("Alumno con id {} eliminado", id);
    }

    private Alumno obtenerAlumno(Long id) {
        return ServiceUtils.obtenerEntidadOException(alumnoRepository, id, Alumno.class);
    }

    private void validarDatosUnicos(String email, String matricula) {
        if (alumnoRepository.existsByEmailIgnoreCase(email))
            throw new IllegalArgumentException("Ya existe un alumno con el email: " + email);

        if (alumnoRepository.existsByMatricula(matricula))
            throw new IllegalArgumentException("Ya existe un alumno con la matrícula: " + matricula);
    }
}