package com.adrian.escuela.repositories;

import com.adrian.escuela.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    boolean existsByAlumno_IdAndGrupo_IdAndIdNot(
            Long alumnoId,
            Long grupoId,
            Long id
    );


    boolean existsByAlumno_IdAndGrupo_Id(
            Long alumnoId,
            Long grupoId
    );
    boolean existsByAlumno_Id(Long alumnoId);
    boolean existsByGrupo_Id(Long grupoId);
}