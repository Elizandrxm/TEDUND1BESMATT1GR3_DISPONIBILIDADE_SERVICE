package br.com.lotvs.disponibilidade_service.dto;

import br.com.lotvs.disponibilidade_service.model.enums.DiasSemana;
import br.com.lotvs.disponibilidade_service.model.enums.HorarioAula;
import jakarta.validation.constraints.NotNull;


public record GradeHorarioRequest(
        @NotNull(message = "diaSemana é obrigatório")
        DiasSemana diaSemana,

        @NotNull(message = "horario é obrigatório")
        HorarioAula horario) {
}
