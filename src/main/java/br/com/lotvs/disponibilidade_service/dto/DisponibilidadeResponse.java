package br.com.lotvs.disponibilidade_service.dto;

import br.com.lotvs.disponibilidade_service.model.DisponibilidadeEntity;
import br.com.lotvs.disponibilidade_service.model.enums.DiasSemana;
import br.com.lotvs.disponibilidade_service.model.enums.HorarioAula;
import br.com.lotvs.disponibilidade_service.model.enums.Status;

import java.time.LocalDate;
import java.time.LocalTime;

public record DisponibilidadeResponse(Long id, Long docenteId, String semestre,
                                      DiasSemana diaSemana, HorarioAula horario,
                                      LocalTime horarioInicio, LocalTime horarioFim,
                                      LocalDate dataCadastro, Status status) {

    public static DisponibilidadeResponse de(DisponibilidadeEntity disponibilidade) {
        return new DisponibilidadeResponse(disponibilidade.getId(), disponibilidade.getDocenteId(), disponibilidade.getSemestre(),
                disponibilidade.getDiaSemana(), disponibilidade.getHorario(), disponibilidade.getHorario().getInicio(), disponibilidade.getHorario().getFim(),
                disponibilidade.getDataCadastro(), disponibilidade.getStatus());
    }
}