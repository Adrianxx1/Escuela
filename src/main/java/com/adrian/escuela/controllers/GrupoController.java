package com.adrian.escuela.controllers;

import com.adrian.escuela.dto.grupos.GrupoRequest;
import com.adrian.escuela.dto.grupos.GrupoResponse;
import com.adrian.escuela.service.grupos.GrupoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController extends CommonController<GrupoRequest, GrupoResponse, GrupoService> {
    public GrupoController(GrupoService service) {
        super(service);
    }
}