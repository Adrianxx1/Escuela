package com.adrian.escuela.controllers;

import com.adrian.escuela.dto.maestro.MaestroRequest;
import com.adrian.escuela.dto.maestro.MaestroResponse;
import com.adrian.escuela.service.maestros.MaestroService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/maestros")
public class MestroController extends CommonController<MaestroRequest, MaestroResponse, MaestroService>{
    public MestroController(MaestroService service){
        super(service);
    }
}
