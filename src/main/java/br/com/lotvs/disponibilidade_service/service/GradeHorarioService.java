package br.com.lotvs.disponibilidade_service.service;

import br.com.lotvs.disponibilidade_service.dto.GradeHorarioRequest;
import br.com.lotvs.disponibilidade_service.dto.GradeHorarioResponse;
import br.com.lotvs.disponibilidade_service.exception.RegraDeNegocioException;
import br.com.lotvs.disponibilidade_service.model.GradeHorarioEntity;
import br.com.lotvs.disponibilidade_service.model.enums.Status;
import br.com.lotvs.disponibilidade_service.repository.GradeHorarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeHorarioService {

    private final GradeHorarioRepository repository;

    @Transactional
    public GradeHorarioResponse criar(GradeHorarioRequest request) {
        if (repository.existsByDiaSemanaAndHorarioAndStatus(
                request.diaSemana(), request.horario(), Status.ATIVO)) {
            throw new RegraDeNegocioException("Esse dia e horário já estão ativos na grade", HttpStatus.CONFLICT);
        }

        GradeHorarioEntity grade = new GradeHorarioEntity();
        grade.setDiaSemana(request.diaSemana());
        grade.setHorario(request.horario());
        return GradeHorarioResponse.de(repository.save(grade));
    }

    @Transactional(readOnly = true)
    public List<GradeHorarioResponse> listar(Status status) {
        List<GradeHorarioEntity> grade = status == null ? repository.findAll() : repository.findByStatus(status);

        // Ordena pelo enum (SEG, TER... e PRIMEIRO, SEGUNDO...), não pelo texto da coluna
        return grade.stream()
                .sorted(Comparator.comparing(GradeHorarioEntity::getDiaSemana)
                        .thenComparing(GradeHorarioEntity::getHorario))
                .map(GradeHorarioResponse::de)
                .toList();
    }

    @Transactional(readOnly = true)
    public GradeHorarioResponse buscarPorId(Long id) {
        return GradeHorarioResponse.de(obter(id));
    }

    @Transactional
    public GradeHorarioResponse inativar(Long id) {
        GradeHorarioEntity grade = obter(id);
        if (grade.getStatus() == Status.INATIVO) {
            throw new RegraDeNegocioException("O dia e horário já estão inativos", HttpStatus.CONFLICT);
        }
        grade.setStatus(Status.INATIVO);
        return GradeHorarioResponse.de(repository.save(grade));
    }

    private GradeHorarioEntity obter(Long id) {
        return repository.findById(id).orElseThrow(() -> new RegraDeNegocioException(
                "Item da grade não encontrado com o id: " + id, HttpStatus.NOT_FOUND));
    }
}
