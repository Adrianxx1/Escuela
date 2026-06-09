package com.adrian.escuela.repositories;

import com.adrian.escuela.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    // Verifica si ya existe una inscripción del mismo alumno en el mismo grupo,
    // excluyendo el registro actual (para validaciones en actualizaciones)
    boolean existsByAlumno_IdAndGrupo_IdAndIdNot(
            Long alumnoId,
            Long grupoId,
            Long id
    );

    // Si también necesitas buscar solo por alumno y grupo (para registros nuevos)
    boolean existsByAlumno_IdAndGrupo_Id(
            Long alumnoId,
            Long grupoId
    );
}