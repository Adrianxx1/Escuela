package com.adrian.escuela.controllers;

import com.adrian.escuela.dto.inscripciones.InscripcionRequest;
import com.adrian.escuela.dto.inscripciones.InscripcionResponse;
import com.adrian.escuela.service.inscripciones.InscripcionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController extends CommonController<InscripcionRequest, InscripcionResponse, InscripcionService> {
    public InscripcionController(InscripcionService service) {
        super(service);
    }

}
