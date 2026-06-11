package com.adrian.escuela.repositories;

import com.adrian.escuela.entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    boolean existsByGrupoId(Long grupoId);

    @Query(value = """
            SELECT COUNT(*) FROM HORARIOS H
            INNER JOIN GRUPOS G USING(ID_GRUPO)
            WHERE ID_GRUPO = :grupoId
            AND H.DIA = :dia
            AND H.HORA_INICIO < :fin
            AND H.HORA_FIN > :inicio
            AND (:idExcluir IS NULL OR H.ID_HORARIO <> :idExcluir)
            """, nativeQuery = true)
    int existeTraslapeEnGrupo(@Param("grupoId") Long grupoId,
                              @Param("dia") String dia,
                              @Param("inicio") String inicio,
                              @Param("fin") String fin,
                              @Param("idExcluir") Long idExcluir);

    @Query(value = """
            SELECT COUNT(*) FROM HORARIOS H
            INNER JOIN GRUPOS G ON H.ID_GRUPO = G.ID_GRUPO
            WHERE G.ID_AULA = :aulaId
            AND H.DIA = :dia
            AND H.HORA_INICIO < :fin
            AND H.HORA_FIN > :inicio
            AND (:idExcluir IS NULL OR H.ID_HORARIO <> :idExcluir)
            """, nativeQuery = true)
    int existeTraslapeEnAula(@Param("aulaId") Long aulaId,
                             @Param("dia") String dia,
                             @Param("inicio") String inicio,
                             @Param("fin") String fin,
                             @Param("idExcluir") Long idExcluir);
}