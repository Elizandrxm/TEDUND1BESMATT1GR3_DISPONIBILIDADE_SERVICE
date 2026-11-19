package br.com.lotvs.disponibilidade_service.dto;

import br.com.lotvs.disponibilidade_service.model.enums.DiasSemana;
import br.com.lotvs.disponibilidade_service.model.enums.HorarioAula;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DisponibilidadeRequest(
        @NotNull(message = "docenteId é obrigatório")
        Long docenteId,

        @NotNull(message = "semestre é obrigatório")
        @Pattern(regexp = "^\\d{4}\\.[12]$", message = "semestre deve estar no formato AAAA.N (ex.: 2027.1)")
        String semestre,

        @NotNull(message = "diaSemana é obrigatório")
        DiasSemana diaSemana,

        @NotNull(message = "horario é obrigatório")
        HorarioAula horario) {
}
