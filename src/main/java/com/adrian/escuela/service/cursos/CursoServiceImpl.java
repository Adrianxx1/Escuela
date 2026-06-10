package com.adrian.escuela.service.cursos;


import com.adrian.escuela.dto.cursos.CursoRequest;
import com.adrian.escuela.dto.cursos.CursoResponse;
import com.adrian.escuela.entities.Curso;
import com.adrian.escuela.mappers.CursoMapper;
import com.adrian.escuela.repositories.CursoRepository;
import com.adrian.escuela.repositories.GrupoRepository;
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
public class CursoServiceImpl implements CursoService {


    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;
    private final GrupoRepository grupoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponse> listar() {
        log.info("Listado de todos los cursos solicitadas");
        return cursoRepository.findAll().stream()
                .map(cursoMapper::entidadAResponse).toList();
    }
    @Override

    @Transactional(readOnly = true)
    public CursoResponse obtenerPorId(Long id) {
        return cursoMapper.entidadAResponse(obtenerCurso(id));
    }
    @Override
    public CursoResponse registrar(CursoRequest request) {
        log.info("Registrando nuevo curso ...");

        validarNombreUnico(request);

        Curso curso = cursoMapper.requestAEntidad(request);
        cursoRepository.save(curso);

        log.info("Nuevo curso registrado: {}", curso.getNombre());
        return cursoMapper.entidadAResponse(curso);
    }
    @Override
    public CursoResponse actualizar(CursoRequest request, Long id) {
        Curso curso = obtenerCurso(id);

        log.info("Actualizando curso con id: {}", id);

        validarCambiosEnNombreUnico(request, id);

        curso.actualizar(
                request.nombre(),
                request.descripcion(),
                request.creditos());
        cursoRepository.save(curso);
        return cursoMapper.entidadAResponse(curso);
    }
    @Override
    public void eliminar(Long id) {
        Curso curso = obtenerCurso(id);

        log.info("Eliminando curso con id: {}", id);

        if (grupoRepository.existsByCursoId(id))
            throw new IllegalStateException("No se puede eliminar el curso porque tiene grupos asignados");

        cursoRepository.delete(curso);


        log.info("curso con id {} eliminado", id);
    }



    private Curso obtenerCurso(Long id) {
        return ServiceUtils.obtenerEntidadOException(cursoRepository, id, Curso.class);
    }

    private void validarNombreUnico(CursoRequest request) {
        log.info("Validando nombre unico...");
        if (cursoRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new IllegalArgumentException("Ya existe un curso con ese nombre registrado: " + request.nombre());
    }
    private void validarCambiosEnNombreUnico (CursoRequest request, Long id) {
        log.info("Validando nombre unico...");
        if (cursoRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id))
            throw new IllegalArgumentException("Ya existe un curso registrada con el nombre: " + request.nombre());

    }}


