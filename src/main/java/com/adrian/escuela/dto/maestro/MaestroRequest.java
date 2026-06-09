package com.adrian.escuela.dto.maestro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MaestroRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(min = 4, max = 50, message = "El nombre debe tener entre 4 y 50 caracteres")
        String nombre,

        @NotBlank(message = "El apellido paterno es requerido")
        @Size(min = 4, max = 50, message = "El nombre debe tener entre 4 y 50 caracteres")
        String apellidoPaterno,

        @NotBlank(message = "El apellido materno es requerido")
        @Size(min = 4, max = 50, message = "El apellido materno debe tener entre 4 y 50 caracteres")
        String apellidoMaterno,

        @NotBlank(message = "El email es requerido")
        @Size(min = 8, max = 100, message = "El email debe tener entre 4 y 50 caracteres")
        String email,

        @NotBlank(message = "El teléfono es requerido")
        @Pattern(regexp = "^[0-9]{10}$", message = "El telefono solo debe tener 10 digitos")
        String telefono
) {}