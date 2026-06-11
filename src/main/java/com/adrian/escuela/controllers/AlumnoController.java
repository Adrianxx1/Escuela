package com.adrian.escuela.controllers;

import com.adrian.escuela.dto.alumnos.AlumnoRequest;
import com.adrian.escuela.dto.alumnos.AlumnoResponse;
import com.adrian.escuela.service.alumnos.AlumnoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController extends CommonController<AlumnoRequest, AlumnoResponse, AlumnoService> {
    public AlumnoController(AlumnoService service) {
        super(service);
    }
}