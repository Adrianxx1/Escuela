package com.adrian.escuela.dto;

public record CustomErrorResponse(
        int codigo,
        String mensaje
) {
}
