package com.adrian.escuela.entities;

import com.adrian.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
@Table(name = "ALUMNOS")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALUMNO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO_PATERNO", nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", nullable = false, length = 50)
    private String apellidoMaterno;

    @Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "MATRICULA", nullable = false, length = 10, unique = true)
    private String matricula;

    @Builder.Default
    @Column(name = "FECHA_INGRESO")
    private LocalDate fechaIngreso = LocalDate.now();

    @Builder.Default
    @OneToMany(mappedBy = "alumno")
    private List<Inscripcion> inscripciones = new ArrayList<>();

    public void actualizar(String nombre, String apellidoPaterno, String apellidoMaterno) {
        validarDatos(nombre, apellidoPaterno, apellidoMaterno);
        this.nombre = nombre.trim();
        this.apellidoPaterno = apellidoPaterno.trim();
        this.apellidoMaterno = apellidoMaterno.trim();
    }

    private void validarDatos(String nombre, String apellidoPaterno, String apellidoMaterno) {
        StringCustomUtils.validarTamanio(nombre.trim(), 4, 50,
                "El nombre es requerido y debe tener entre 4 y 50 caracteres");

        StringCustomUtils.validarTamanio(apellidoPaterno.trim(), 4, 50,
                "El apellido paterno es requerido y debe tener entre 4 y 50 caracteres");

        StringCustomUtils.validarTamanio(apellidoMaterno.trim(), 4, 50,
                "El apellido materno es requerido y debe tener entre 4 y 50 caracteres");
    }
}