package br.com.lotvs.disponibilidade_service.dto;

import br.com.lotvs.disponibilidade_service.model.GradeHorarioEntity;
import br.com.lotvs.disponibilidade_service.model.enums.DiasSemana;
import br.com.lotvs.disponibilidade_service.model.enums.HorarioAula;
import br.com.lotvs.disponibilidade_service.model.enums.Status;

import java.time.LocalDate;
import java.time.LocalTime;

public record GradeHorarioResponse(Long id, DiasSemana diaSemana, HorarioAula horario,
                                   LocalTime horarioInicio, LocalTime horarioFim,
                                   LocalDate dataCadastro, Status status) {

    public static GradeHorarioResponse de(GradeHorarioEntity gradeHorario) {
        return new GradeHorarioResponse(gradeHorario.getId(), gradeHorario.getDiaSemana(), gradeHorario.getHorario(),
                gradeHorario.getHorario().getInicio(), gradeHorario.getHorario().getFim(), gradeHorario.getDataCadastro(), gradeHorario.getStatus());
    }
}
