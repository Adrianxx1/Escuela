package com.adrian.escuela.repositories;

import com.adrian.escuela.entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    boolean existsByGrupoId(Long grupoId);

    @Query(value = """
            SELECT C.NOMBRE CURSO, A.NOMBRE AULA, DIA, HORA_INICIO, HORA_FIN
            FROM HORARIOS H
            INNER JOIN GRUPOS G USING(ID_GRUPO)
            INNER JOIN CURSOS C USING(ID_CURSO)
            INNER JOIN AULAS A USING(ID_AULA)
            ORDER BY CASE DIA
                WHEN 'LUNES' THEN 1
                WHEN 'MARTES' THEN 2
                WHEN 'MIERCOLES' THEN 3
                WHEN 'JUEVES' THEN 4
                WHEN 'VIERNES' THEN 5
                WHEN 'SABADO' THEN 6
            END
            """, nativeQuery = true)
    List<Object[]> listarHorariosOrdenados();

    @Query(value = """
            SELECT COUNT(*) FROM HORARIOS H
            INNER JOIN GRUPOS G USING(ID_GRUPO)
            WHERE G.ID_GRUPO = :grupoId
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
            INNER JOIN GRUPOS G USING(ID_GRUPO)
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