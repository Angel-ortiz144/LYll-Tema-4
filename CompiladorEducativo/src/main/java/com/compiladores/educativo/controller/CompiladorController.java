package com.compiladores.educativo.controller;

import com.compiladores.educativo.model.Resultado;
import com.compiladores.educativo.service.CompiladorService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compilador")
@CrossOrigin(origins = "*")
public class CompiladorController {

    private final CompiladorService service;

    public CompiladorController(
            CompiladorService service) {

        this.service = service;
    }


    @PostMapping("/analizar")
    public Resultado analizar(
            @RequestBody String codigo) {

        return service.analizar(codigo);
    }


    @PostMapping("/optimizar/suma")
    public String optimizarSuma(
            @RequestBody String codigo) {

        return service.optimizarSuma(codigo);
    }


    @PostMapping("/optimizar/resta")
    public String optimizarResta(
            @RequestBody String codigo) {

        return service.optimizarResta(codigo);
    }


    @PostMapping("/optimizar/multiplicacion")
    public String optimizarMultiplicacion(
            @RequestBody String codigo) {

        return service.optimizarMultiplicacion(codigo);
    }


    @PostMapping("/optimizar/todas")
    public String optimizarTodas(
            @RequestBody String codigo) {

        return service.optimizarTodas(codigo);
    }
}
