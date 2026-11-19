package br.com.lotvs.disponibilidade_service.model.enums;

import java.time.LocalTime;

public enum HorarioAula {
    PRIMEIRO(LocalTime.of(7, 0), LocalTime.of(8, 15)),
    SEGUNDO(LocalTime.of(8, 25), LocalTime.of(9, 40)),
    TERCEIRO(LocalTime.of(9, 50), LocalTime.of(11, 5)),
    QUARTO(LocalTime.of(11, 15), LocalTime.of(12, 20)),
    QUINTO(LocalTime.of(13, 30), LocalTime.of(14, 45)),
    SEXTO(LocalTime.of(14, 55), LocalTime.of(16, 10)),
    SETIMO(LocalTime.of(17, 0), LocalTime.of(18, 15)),
    OITAVO(LocalTime.of(19, 0), LocalTime.of(21, 30));

    private final LocalTime inicio;
    private final LocalTime fim;

    HorarioAula(LocalTime inicio, LocalTime fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public LocalTime getInicio() { return inicio; }

    public LocalTime getFim() { return fim; }
}
