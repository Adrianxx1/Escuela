package com.adrian.escuela.repositories;

import com.adrian.escuela.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    boolean existsByMaestroId(Long idMaestro);
    boolean existsByAulaId(Long idAula);
    boolean existsByCursoId(Long cursoId);
    boolean existsByCurso_IdAndMaestro_IdAndAula_IdAndPeriodo(
            Long cursoId,
            Long maestroId,
            Long aulaId,
            String periodo
    );
}