package com.adrian.escuela.service.aulas;

import com.adrian.escuela.dto.aulas.AulaRequest;
import com.adrian.escuela.dto.aulas.AulaResponse;
import com.adrian.escuela.entities.Aula;
import com.adrian.escuela.mappers.AulaMapper;
import com.adrian.escuela.repositories.AulaRepository;
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
public class AulaServiceImpl implements AulaService {

    private final AulaRepository aulaRepository;

    private final AulaMapper aulaMapper;

    private final GrupoRepository grupoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AulaResponse> listar() {
        log.info("Listado de todas las aulas solicitadas");
        return aulaRepository.findAll().stream()
                .map(aulaMapper::entidadAResponse).toList();
    }
    @Override

    @Transactional(readOnly = true)
    public AulaResponse obtenerPorId(Long id) {
        return aulaMapper.entidadAResponse(obtenerAula(id));
    }
    @Override
    public AulaResponse registrar(AulaRequest request) {
        log.info("Registrando nueva aula ...");

        validarNombreUnico(request);

        Aula aula = aulaMapper.requestAEntidad(request);
        aulaRepository.save(aula);

        log.info("Nueva aula registrada: {}", aula.getNombre());
        return aulaMapper.entidadAResponse(aula);
    }
    @Override
    public AulaResponse actualizar(AulaRequest request, Long id) {
        Aula aula = obtenerAula(id);

        log.info("Actualizando aula con id: {}", id);

        validarCambiosEnNombreUnico(request, id);

        aula.actualizar(
                request.nombre(),
                request.capacidad());
        aulaRepository.save(aula);
        return aulaMapper.entidadAResponse(aula);
    }
    @Override
    public void eliminar(Long id) {
        Aula aula = obtenerAula(id);

        log.info("Eliminando aula con id: {}", id);

        if (grupoRepository.existsByAulaId(id))
            throw new IllegalStateException("No se puede eliminar el aula porque tiene grupos asignados");

        aulaRepository.delete(aula);


        log.info("aula con id {} eliminada", id);
    }



    private Aula obtenerAula(Long id) {
        return ServiceUtils.obtenerEntidadOException(aulaRepository, id, Aula.class);
    }

    private void validarNombreUnico(AulaRequest request) {
        log.info("Validando nombre unico...");
        if (aulaRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new IllegalArgumentException("Ya existe un aula con ese nombre registrado: " + request.nombre());
    }
        private void validarCambiosEnNombreUnico (AulaRequest request, Long id) {
            log.info("Validando nombre unico...");
            if (aulaRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id))
                throw new IllegalArgumentException("Ya existe un aula registrada con el nombre: " + request.nombre());

        }
    }