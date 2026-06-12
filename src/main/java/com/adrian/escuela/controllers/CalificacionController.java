package com.adrian.escuela.controllers;

import com.adrian.escuela.dto.calificaciones.CalificacionRequest;
import com.adrian.escuela.dto.calificaciones.CalificacionResponse;
import com.adrian.escuela.service.calificaciones.CalificacionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController extends CommonController<CalificacionRequest, CalificacionResponse, CalificacionService> {
    public CalificacionController(CalificacionService service) {
        super(service);
    }
}