package br.com.lotvs.disponibilidade_service.service;

import br.com.lotvs.disponibilidade_service.dto.DisponibilidadeRequest;
import br.com.lotvs.disponibilidade_service.dto.DisponibilidadeResponse;
import br.com.lotvs.disponibilidade_service.exception.RegraDeNegocioException;
import br.com.lotvs.disponibilidade_service.model.DisponibilidadeEntity;
import br.com.lotvs.disponibilidade_service.model.enums.Status;
import br.com.lotvs.disponibilidade_service.repository.DisponibilidadeRepository;
import br.com.lotvs.disponibilidade_service.repository.GradeHorarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DisponibilidadeService {

    private final DisponibilidadeRepository disponibilidadeRepository;
    private final GradeHorarioRepository gradeRepository;

    @Transactional
    public DisponibilidadeResponse criar(DisponibilidadeRequest request) {
        exigirNaGrade(request);
        exigirSemDuplicidade(request, null);

        DisponibilidadeEntity disponibilidade = new DisponibilidadeEntity();
        preencher(disponibilidade, request);
        return DisponibilidadeResponse.de(disponibilidadeRepository.save(disponibilidade));
    }

    @Transactional(readOnly = true)
    public List<DisponibilidadeResponse> listar(Long docenteId, String semestre) {
        List<DisponibilidadeEntity> encontradas;
        if (docenteId != null && semestre != null) {
            encontradas = disponibilidadeRepository.findByDocenteIdAndSemestre(docenteId, semestre);
        } else if (docenteId != null) {
            encontradas = disponibilidadeRepository.findByDocenteId(docenteId);
        } else if (semestre != null) {
            encontradas = disponibilidadeRepository.findBySemestre(semestre);
        } else {
            encontradas = disponibilidadeRepository.findAll();
        }

        return encontradas.stream()
                .sorted(Comparator.comparing(DisponibilidadeEntity::getSemestre)
                        .thenComparing(DisponibilidadeEntity::getDocenteId)
                        .thenComparing(DisponibilidadeEntity::getDiaSemana)
                        .thenComparing(DisponibilidadeEntity::getHorario))
                .map(DisponibilidadeResponse::de)
                .toList();
    }

    @Transactional(readOnly = true)
    public DisponibilidadeResponse buscarPorId(Long id) {
        return DisponibilidadeResponse.de(obter(id));
    }

    @Transactional
    public DisponibilidadeResponse atualizar(Long id, DisponibilidadeRequest request) {
        DisponibilidadeEntity disponibilidade = obter(id);
        if (disponibilidade.getStatus() == Status.INATIVO) {
            throw new RegraDeNegocioException("Uma disponibilidade inativa não pode ser editada", HttpStatus.CONFLICT);
        }
        exigirNaGrade(request);
        exigirSemDuplicidade(request, id);

        preencher(disponibilidade, request);
        return DisponibilidadeResponse.de(disponibilidadeRepository.save(disponibilidade));
    }

    @Transactional
    public DisponibilidadeResponse inativar(Long id) {
        DisponibilidadeEntity disponibilidade = obter(id);
        if (disponibilidade.getStatus() == Status.INATIVO) {
            throw new RegraDeNegocioException("A disponibilidade já está inativa", HttpStatus.CONFLICT);
        }
        disponibilidade.setStatus(Status.INATIVO);
        return DisponibilidadeResponse.de(disponibilidadeRepository.save(disponibilidade));
    }

    // Item 10.1.2 do cenário: só vale o que o colaborador deixou ativo na grade
    private void exigirNaGrade(DisponibilidadeRequest r) {
        if (!gradeRepository.existsByDiaSemanaAndHorarioAndStatus(r.diaSemana(), r.horario(), Status.ATIVO)) {
            throw new RegraDeNegocioException(
                    "O dia e o horário informados não estão ativos na grade", HttpStatus.BAD_REQUEST);
        }
    }

    private void exigirSemDuplicidade(DisponibilidadeRequest r, Long idIgnorado) {
        boolean duplicada = idIgnorado == null
                ? disponibilidadeRepository.existsByDocenteIdAndSemestreAndDiaSemanaAndHorarioAndStatus(
                r.docenteId(), r.semestre(), r.diaSemana(), r.horario(), Status.ATIVO)
                : disponibilidadeRepository.existsByDocenteIdAndSemestreAndDiaSemanaAndHorarioAndStatusAndIdNot(
                r.docenteId(), r.semestre(), r.diaSemana(), r.horario(), Status.ATIVO, idIgnorado);

        if (duplicada) {
            throw new RegraDeNegocioException(
                    "O docente já informou disponibilidade nesse dia e horário neste semestre", HttpStatus.CONFLICT);
        }
    }

    private void preencher(DisponibilidadeEntity d, DisponibilidadeRequest r) {
        d.setDocenteId(r.docenteId());
        d.setSemestre(r.semestre());
        d.setDiaSemana(r.diaSemana());
        d.setHorario(r.horario());
    }

    private DisponibilidadeEntity obter(Long id) {
        return disponibilidadeRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException(
                "Disponibilidade não encontrada com o id: " + id, HttpStatus.NOT_FOUND));
    }
}
