package com.adrian.escuela.repositories;

import com.adrian.escuela.entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByMatricula(String matricula);

    @Query(value = "SELECT GENERAR_MATRICULA(:nombre, :apPaterno, :apMaterno) FROM DUAL",
            nativeQuery = true)
    String generarMatricula(@Param("nombre") String nombre,
                            @Param("apPaterno") String apPaterno,
                            @Param("apMaterno") String apMaterno);

    @Query(value = "SELECT GENERAR_CORREO(:nombre, :apPaterno, :apMaterno) FROM DUAL",
            nativeQuery = true)
    String generarCorreo(@Param("nombre") String nombre,
                         @Param("apPaterno") String apPaterno,
                         @Param("apMaterno") String apMaterno);
}