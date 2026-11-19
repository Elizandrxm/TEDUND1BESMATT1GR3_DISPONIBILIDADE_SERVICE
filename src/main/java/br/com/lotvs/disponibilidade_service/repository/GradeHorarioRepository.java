package br.com.lotvs.disponibilidade_service.repository;

import br.com.lotvs.disponibilidade_service.model.GradeHorarioEntity;
import br.com.lotvs.disponibilidade_service.model.enums.DiasSemana;
import br.com.lotvs.disponibilidade_service.model.enums.HorarioAula;
import br.com.lotvs.disponibilidade_service.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeHorarioRepository extends JpaRepository<GradeHorarioEntity, Long> {

    boolean existsByDiaSemanaAndHorarioAndStatus(DiasSemana diaSemana, HorarioAula horario, Status status);

    List<GradeHorarioEntity> findByStatus(Status status);
}
