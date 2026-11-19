package br.com.lotvs.disponibilidade_service.controller;

import br.com.lotvs.disponibilidade_service.dto.DisponibilidadeRequest;
import br.com.lotvs.disponibilidade_service.dto.DisponibilidadeResponse;
import br.com.lotvs.disponibilidade_service.service.DisponibilidadeService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/disponibilidades")
@RequiredArgsConstructor
public class DisponibilidadeController {

    private final DisponibilidadeService service;

    @PostMapping
    public ResponseEntity<DisponibilidadeResponse> criar(@Valid @RequestBody DisponibilidadeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @GetMapping
    public List<DisponibilidadeResponse> listar(
            @RequestParam(name = "docenteId", required = false) Long docenteId,
            @RequestParam(name = "semestre", required = false) String semestre) {
        return service.listar(docenteId, semestre);
    }

    @GetMapping("/{id}")
    public DisponibilidadeResponse buscarPorId(@PathVariable("id") Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public DisponibilidadeResponse atualizar(@PathVariable("id") Long id,
                                             @Valid @RequestBody DisponibilidadeRequest request) {
        return service.atualizar(id, request);
    }

    @PatchMapping("/{id}/inativar")
    public DisponibilidadeResponse inativar(@PathVariable("id") Long id) {
        return service.inativar(id);
    }

    @PatchMapping("/{id}/ativar")
    public DisponibilidadeResponse ativar(@PathVariable("id") Long id) {
        return service.inativar(id);
    }
}