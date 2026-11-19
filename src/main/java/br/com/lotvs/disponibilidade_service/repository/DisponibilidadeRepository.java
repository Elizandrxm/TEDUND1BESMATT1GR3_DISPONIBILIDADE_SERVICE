package br.com.lotvs.disponibilidade_service.repository;

import br.com.lotvs.disponibilidade_service.model.DisponibilidadeEntity;
import br.com.lotvs.disponibilidade_service.model.enums.DiasSemana;
import br.com.lotvs.disponibilidade_service.model.enums.HorarioAula;
import br.com.lotvs.disponibilidade_service.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisponibilidadeRepository extends JpaRepository<DisponibilidadeEntity, Long> {

    List<DisponibilidadeEntity> findByDocenteIdAndSemestre(Long docenteId, String semestre);

    List<DisponibilidadeEntity> findByDocenteId(Long docenteId);

    List<DisponibilidadeEntity> findBySemestre(String semestre);

    boolean existsByDocenteIdAndSemestreAndDiaSemanaAndHorarioAndStatus(
            Long docenteId, String semestre, DiasSemana diaSemana, HorarioAula horario, Status status);

    boolean existsByDocenteIdAndSemestreAndDiaSemanaAndHorarioAndStatusAndIdNot(
            Long docenteId, String semestre, DiasSemana diaSemana, HorarioAula horario,
            Status status, Long id);
}
