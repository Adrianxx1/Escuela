package com.adrian.escuela.repositories;

import com.adrian.escuela.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    boolean existsByCInscripcion_IdAndAlumno_IdAGrupoAndIdNot(
            Long inscripcionId,
            Long alumnoId,
            Long grupoId,
            Long id
    );
}
