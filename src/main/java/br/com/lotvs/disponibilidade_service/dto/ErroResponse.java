package br.com.lotvs.disponibilidade_service.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ErroResponse(LocalDateTime timestamp, int status, String message, Map<String, String> erros) {
}
