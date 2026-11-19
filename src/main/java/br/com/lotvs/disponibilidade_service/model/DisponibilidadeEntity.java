package br.com.lotvs.disponibilidade_service.model;

import br.com.lotvs.disponibilidade_service.model.enums.DiasSemana;
import br.com.lotvs.disponibilidade_service.model.enums.HorarioAula;
import br.com.lotvs.disponibilidade_service.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "disponibilidade")
@Getter
@Setter
@NoArgsConstructor
public class DisponibilidadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "docente_id", nullable = false)
    private Long docenteId;

    @Column(nullable = false, length = 6)
    private String semestre;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false)
    private DiasSemana diaSemana;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HorarioAula horario;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDate dataCadastro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @PrePersist
    void aoCriar() {
        if (dataCadastro == null) {
            dataCadastro = LocalDate.now();
        }
        if (status == null) {
            status = Status.ATIVO;
        }
    }
}