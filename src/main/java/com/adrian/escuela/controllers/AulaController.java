package com.adrian.escuela.controllers;

import com.adrian.escuela.dto.aulas.AulaRequest;
import com.adrian.escuela.dto.aulas.AulaResponse;
import com.adrian.escuela.service.aulas.AulaService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aulas")
public class AulaController extends CommonController<AulaRequest, AulaResponse, AulaService>{
public AulaController(AulaService service){
    super(service);
}
}



