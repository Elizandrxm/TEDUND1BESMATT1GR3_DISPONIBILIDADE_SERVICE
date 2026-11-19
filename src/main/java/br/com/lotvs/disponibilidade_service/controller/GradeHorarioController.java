package br.com.lotvs.disponibilidade_service.controller;

import br.com.lotvs.disponibilidade_service.dto.GradeHorarioRequest;
import br.com.lotvs.disponibilidade_service.dto.GradeHorarioResponse;
import br.com.lotvs.disponibilidade_service.model.enums.Status;
import br.com.lotvs.disponibilidade_service.service.GradeHorarioService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/grade-horarios")
@RequiredArgsConstructor
public class GradeHorarioController {

    private final GradeHorarioService service;

    @PostMapping
    public ResponseEntity<GradeHorarioResponse> criar(@Valid @RequestBody GradeHorarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @GetMapping
    public List<GradeHorarioResponse> listar(@RequestParam(name = "status", required = false) Status status) {
        return service.listar(status);
    }

    @GetMapping("/{id}")
    public GradeHorarioResponse buscarPorId(@PathVariable("id") Long id) {
        return service.buscarPorId(id);
    }

    @PatchMapping("/{id}/inativar")
    public GradeHorarioResponse inativar(@PathVariable("id") Long id) {
        return service.inativar(id);
    }

    @PatchMapping("/{id}/ativar")
    public GradeHorarioResponse ativar(@PathVariable("id") Long id) {
        return service.inativar(id);
    }
}
